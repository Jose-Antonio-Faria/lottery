package com.jose.lottery.repositories;

import com.jose.lottery.models.BallotModel;
import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.models.UserModel;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 *
 * @author jose
 */
@DataJpaTest
public class BallotRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LotteryEventRepository lotteryRepository;
    
    @Autowired
    private BallotRepository ballotRepository;
    
    public BallotRepositoryTest() {
    }
    
    @Test
    void itShouldGetWinningBallotsForDate(){
        
        //given 
        UserModel userModel = new UserModel();
        userModel.setName("Jose Faria");
        userModel.setIdentificationDocumentNumber("14229735");
        userModel.setTaxIdentificationNumber("260150380");
        userModel.setAddress("Av. Doutor Mário Moutinho, nº29, 6ºD");
        userModel.setPostalCode("1400-136");
        userModel.setEmail("joseantfaria108@gmail.com");
        
        userRepository.save(userModel);
        
        LotteryEventModel firstLotteryEvent = new LotteryEventModel();
        LocalDateTime lotteryDate = LocalDateTime.of(2023, Month.APRIL, 13, 0, 0);
        firstLotteryEvent.setDate(lotteryDate);
        firstLotteryEvent.setStatus(LotteryEventModel.Status.OPEN);

        LotteryEventModel secondLotteryEventModel = new LotteryEventModel();
        lotteryDate = LocalDateTime.of(2023, Month.APRIL, 14, 0, 0);
        secondLotteryEventModel.setDate(lotteryDate);
        secondLotteryEventModel.setStatus(LotteryEventModel.Status.OPEN);
        
        lotteryRepository.save(firstLotteryEvent);
        lotteryRepository.save(secondLotteryEventModel);
        
        BallotModel firstBallot = new BallotModel();
        firstBallot.setUser(userModel);
        firstBallot.setLotteryEvent(firstLotteryEvent);
        firstBallot.setNumbers(new int[]{1,2,3,4,5});
        firstBallot.setWinner(true);
        
        BallotModel secondBallot = new BallotModel();
        secondBallot.setUser(userModel);
        secondBallot.setLotteryEvent(firstLotteryEvent);
        secondBallot.setNumbers(new int[]{2,4,6,8,10});
        secondBallot.setWinner(false);
        
        BallotModel thirdBallot = new BallotModel();
        thirdBallot.setUser(userModel);
        thirdBallot.setLotteryEvent(secondLotteryEventModel);
        thirdBallot.setNumbers(new int[]{3,6,7,9,8});
        thirdBallot.setWinner(false);
        
        BallotModel fourthBallot = new BallotModel();
        fourthBallot.setUser(userModel);
        fourthBallot.setLotteryEvent(secondLotteryEventModel);
        fourthBallot.setNumbers(new int[]{8,1,1,3,4});
        fourthBallot.setWinner(true);
        
        ballotRepository.save(firstBallot);
        ballotRepository.save(secondBallot);
        ballotRepository.save(thirdBallot);
        ballotRepository.save(fourthBallot);
        
        //when
        LocalDateTime dateToSearchWinners = LocalDateTime.of(2023, Month.APRIL, 14, 0, 0);
        List<BallotModel> winningBallots = ballotRepository.findWinningBallotsForDate(dateToSearchWinners);
        
        //then
        assertThat(winnerIsFourthBallot(winningBallots, fourthBallot.getId()))
                .isTrue();
    }
    
    private boolean winnerIsFourthBallot(List<BallotModel> winningBallots, UUID fourthBallotID){
        return winningBallots.size() == 1 && winningBallots.get(0).getId().equals(fourthBallotID);
    }
    
}