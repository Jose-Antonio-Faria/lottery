package com.jose.lottery.controllers;

import com.jose.lottery.dtos.BallotDto;
import com.jose.lottery.models.BallotModel;
import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.models.UserModel;
import com.jose.lottery.services.BallotService;
import com.jose.lottery.services.LotteryEventService;
import com.jose.lottery.services.UserService;
import com.jose.lottery.utils.DateUtils;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jose
 */
@RestController
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

    @PostMapping
    public ResponseEntity<Object> saveBallot(@RequestBody @Valid BallotDto ballotDto){
        Optional<UserModel> userOptional = userService.findById(ballotDto.getUser_id());
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
        ballotModel.setNumbers(ballotDto.getNumbers());
        ballotModel.setWinner(ballotDto.isWinner());
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

    @GetMapping("/winning/{date}")
    public ResponseEntity<Object> getWinningBallots(@PathVariable("date") @Valid LocalDate date){
        
        List<BallotModel> winningBallots = ballotService.findWinningBallotsForDate(date);
        
        if (winningBallots.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Winning ballots not found.");
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(winningBallots);
    }

}
