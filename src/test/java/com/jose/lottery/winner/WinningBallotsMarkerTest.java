package com.jose.lottery.winner;

import com.jose.lottery.models.BallotModel;
import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.models.UserModel;
import com.jose.lottery.repositories.BallotRepository;
import com.jose.lottery.repositories.LotteryEventRepository;
import com.jose.lottery.repositories.UserRepository;
import com.jose.lottery.utils.DateUtils;
import java.util.List;
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
        
        UserModel userModel = new UserModel();
        userModel.setName("Jose Faria");
        userModel.setIdentificationDocumentNumber("14229735");
        userModel.setTaxIdentificationNumber("260150380");
        userModel.setAddress("Av. Doutor Mário Moutinho, nº29, 6ºD");
        userModel.setPostalCode("1400-136");
        userModel.setEmail("joseantfaria108@gmail.com");
        
        userRepository.save(userModel);
        
        LotteryEventModel lotteryEventModel = new LotteryEventModel();
        lotteryEventModel.setDate(DateUtils.getTodayDate());
        lotteryEventModel.setStatus(LotteryEventModel.Status.OPEN);

        lotteryRepository.save(lotteryEventModel);
        
        BallotModel firstBallot = new BallotModel();
        firstBallot.setUser(userModel);
        firstBallot.setLotteryEvent(lotteryEventModel);
        firstBallot.setNumbers(new int[]{1,2,3,4,5});
        
        BallotModel secondBallot = new BallotModel();
        secondBallot.setUser(userModel);
        secondBallot.setLotteryEvent(lotteryEventModel);
        secondBallot.setNumbers(new int[]{2,4,6,8,10});
        
        BallotModel thirdBallot = new BallotModel();
        thirdBallot.setUser(userModel);
        thirdBallot.setLotteryEvent(lotteryEventModel);
        thirdBallot.setNumbers(new int[]{3,6,7,9,8});
        
        ballotRepository.save(firstBallot);
        ballotRepository.save(secondBallot);
        ballotRepository.save(thirdBallot);
        
        IRandomKeyGenerator keyGenerator = new DummyRandomKeyGenerator();
        WinningBallotsMarker winningBallotsMarker = new WinningBallotsMarker(ballotRepository, keyGenerator);
        
        winningBallotsMarker.markWinningBallots(lotteryEventModel);

        List<BallotModel> winningBallots = ballotRepository.findWinningBallotsByLotteryEvent(lotteryEventModel);
        
        assertTrue(winningBallots.size() == 1);
    }
}
