package com.jose.lottery.winner;

import com.jose.lottery.models.BallotModel;
import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.models.UserModel;
import com.jose.lottery.repositories.BallotRepository;
import com.jose.lottery.repositories.LotteryEventRepository;
import com.jose.lottery.repositories.UserRepository;
import com.jose.lottery.utils.DateUtils;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 *
 * @author jose
 */
@DataJpaTest
public class WinningBallotsMarkerTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LotteryEventRepository lotteryRepository;
    
    @Autowired
    private BallotRepository ballotRepository;
    
    @Test
    void if_there_is_winner_then_get_winner() {
        
        //given
        UserModel userModel = new UserModel();
        userModel.setName("Jose Faria");
        userModel.setIdentificationDocumentNumber("14229735");
        userModel.setTaxIdentificationNumber("260150380");
        userModel.setAddress("Av. Doutor Mário Moutinho, nº29, 6ºD");
        userModel.setPostalCode("1400-136");
        userModel.setEmail("joseantfaria108@gmail.com");
        
        UserModel secondUserModel = new UserModel();
        secondUserModel.setName("Rui Feliz");
        secondUserModel.setIdentificationDocumentNumber("14229734");
        secondUserModel.setTaxIdentificationNumber("260150385");
        secondUserModel.setAddress("Jardim Zoológico, nº2, 4ºE");
        secondUserModel.setPostalCode("1430-166");
        secondUserModel.setEmail("rui.feliz@gmail.com");
        
        userRepository.save(userModel);
        userRepository.save(secondUserModel);
        
        LotteryEventModel lotteryEventModel = new LotteryEventModel();
        lotteryEventModel.setDate(DateUtils.getTodayDate());
        lotteryEventModel.setStatus(LotteryEventModel.Status.OPEN);

        lotteryRepository.save(lotteryEventModel);
        
        BallotModel firstBallot = new BallotModel();
        firstBallot.setUser(userModel);
        firstBallot.setLotteryEvent(lotteryEventModel);
        firstBallot.setRegistrationDate(LocalDateTime.of(2023, Month.APRIL, 14, 16, 0));
        
        BallotModel secondBallot = new BallotModel();
        secondBallot.setUser(userModel);
        secondBallot.setLotteryEvent(lotteryEventModel);
        secondBallot.setRegistrationDate(LocalDateTime.of(2023, Month.APRIL, 14, 17, 0));
        
        BallotModel thirdBallot = new BallotModel();
        thirdBallot.setUser(secondUserModel);
        thirdBallot.setLotteryEvent(lotteryEventModel);
        thirdBallot.setRegistrationDate(LocalDateTime.of(2023, Month.APRIL, 14, 18, 0));
        
        BallotModel fourthBallot = new BallotModel();
        fourthBallot.setUser(secondUserModel);
        fourthBallot.setLotteryEvent(lotteryEventModel);
        fourthBallot.setRegistrationDate(LocalDateTime.of(2023, Month.APRIL, 14, 19, 0));
        
        BallotModel fifthBallot = new BallotModel();
        fifthBallot.setUser(userModel);
        fifthBallot.setLotteryEvent(lotteryEventModel);
        fifthBallot.setRegistrationDate(LocalDateTime.of(2023, Month.APRIL, 14, 20, 0));
        
        ballotRepository.save(firstBallot);
        ballotRepository.save(secondBallot);
        ballotRepository.save(thirdBallot);
        ballotRepository.save(fourthBallot);
        ballotRepository.save(fifthBallot);
        
        WinningBallotMarker winningBallotMarker = new WinningBallotMarker(ballotRepository, new DummyRandomNumberGenerator());
        
        //when
        winningBallotMarker.markWinningBallot(lotteryEventModel);
        
        //then
        BallotModel expectedWinner = secondBallot;
        Optional<BallotModel> winner = ballotRepository.findWinningBallotByLotteryEvent(lotteryEventModel);
        assertTrue(winner.isPresent() && winner.get().getId().equals(expectedWinner.getId()));
    }
}
