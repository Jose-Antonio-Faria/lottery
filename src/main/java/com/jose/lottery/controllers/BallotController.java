package com.jose.lottery.controllers;

import com.jose.lottery.dtos.BallotDto;
import com.jose.lottery.models.BallotModel;
import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.models.UserModel;
import com.jose.lottery.services.BallotService;
import com.jose.lottery.services.LotteryEventService;
import com.jose.lottery.services.UserService;
import com.jose.lottery.utils.DateUtils;
import com.jose.lottery.winner.RandomNumberGenerator;
import com.jose.lottery.winner.WinningBallotSelector;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jose
 */
@Controller
@RequestMapping("/ballot")
public class BallotController {

    private final BallotService ballotService;
    
    @Autowired
    private UserService userService;
    @Autowired
    private LotteryEventService lotteryEventService;

    public BallotController(BallotService ballotService) {
        this.ballotService = ballotService;
    }

    @GetMapping("/submit")
    public String showBallotSubmissionForm(Model model) {
        model.addAttribute("ballotdto", new BallotDto());
        return "ballot_submission";
    }
    
    @GetMapping("/winning/form")
    public String showCheckWinnersForm(Model model) {
        return "check_winning_ballots";
    }
    
    @PostMapping("/submit")
    public ResponseEntity<Object> saveBallot(@Valid @ModelAttribute BallotDto ballotDto, BindingResult errors, Model model){
        Optional<UserModel> userOptional = userService.findByEmail(ballotDto.getEmail());
        LocalDate today = DateUtils.getTodayDate();
        Optional<LotteryEventModel> lotteryEventOptional = lotteryEventService.findByDate(today);
        if (!userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        if (!lotteryEventOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LotteryEvent not found.");
        }
                
        var ballotModel = new BallotModel();
        ballotModel.setUser(userOptional.get());
        ballotModel.setLotteryEvent(lotteryEventOptional.get());
        ballotModel.setWinner(false);
        ballotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(ballotService.save(ballotModel));
    }
    
    @GetMapping
    public ResponseEntity<Page<BallotModel>> getAllBallots(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(ballotService.findAll(pageable));
    }
    
    @GetMapping("/{user_id}")
    public ResponseEntity<Object> getAllUserBallots(@PathVariable(value = "user_id") UUID user_id){
        Optional<UserModel> userOptional = userService.findById(user_id);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(ballotService.findByUser(userOptional.get()));
    }

    @GetMapping("/winners")
    public ResponseEntity<Object> getWinningBallots(@RequestParam @Valid LocalDate date){
        
        Optional<BallotModel> winningBallot = ballotService.findWinningBallotForDate(date);
        
        if (winningBallot.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Winning ballots not found.");
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(winningBallot);
    }
    
    @GetMapping("/simulate/winner")
    public ResponseEntity<Object> getSimulatedWinner(){
        
        Optional<LotteryEventModel> todayLotteryEvent = lotteryEventService.findByDate(DateUtils.getTodayDate());
        if (todayLotteryEvent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no lottery event today.");
        }
        
        Optional<BallotModel> winningBallot = ballotService.getSimulatedWinner(todayLotteryEvent.get());
        if (winningBallot.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lottery has no ballots. Can't simulate winner.");
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(winningBallot);
    }

}
