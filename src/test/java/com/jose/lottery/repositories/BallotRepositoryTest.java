package com.jose.lottery.repositories;

import com.jose.lottery.models.BallotModel;
import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.models.UserModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
    
    private static UserModel userModel;
    private static LotteryEventModel firstLotteryEvent;
    private static LotteryEventModel secondLotteryEvent;
    
    public BallotRepositoryTest() {
    }
    
    @BeforeEach
    public void setUp() {
        userModel = new UserModel();
        userModel.setName("Jose Faria");
        userModel.setIdentificationDocumentNumber("14229735");
        userModel.setTaxIdentificationNumber("260150380");
        userModel.setAddress("Av. Doutor Mário Moutinho, nº29, 6ºD");
        userModel.setPostalCode("1400-136");
        userModel.setEmail("joseantfaria108@gmail.com");
        userRepository.save(userModel);
        
        firstLotteryEvent = new LotteryEventModel();
        LocalDate lotteryDate = LocalDate.of(2023, Month.APRIL, 13);
        firstLotteryEvent.setDate(lotteryDate);
        firstLotteryEvent.setStatus(LotteryEventModel.Status.OPEN);

        secondLotteryEvent = new LotteryEventModel();
        lotteryDate = LocalDate.of(2023, Month.APRIL, 14);
        secondLotteryEvent.setDate(lotteryDate);
        secondLotteryEvent.setStatus(LotteryEventModel.Status.OPEN);
        
        lotteryRepository.save(firstLotteryEvent);
        lotteryRepository.save(secondLotteryEvent);
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    void itShouldGetWinningBallotsForDate(){

        //given
        BallotModel firstBallot = new BallotModel();
        firstBallot.setUser(userModel);
        firstBallot.setLotteryEvent(firstLotteryEvent);
        firstBallot.setWinner(true);
        firstBallot.setRegistrationDate(LocalDateTime.of(2023, Month.APRIL, 13, 13, 0));
        
        BallotModel secondBallot = new BallotModel();
        secondBallot.setUser(userModel);
        secondBallot.setLotteryEvent(firstLotteryEvent);
        secondBallot.setWinner(false);
        secondBallot.setRegistrationDate(LocalDateTime.of(2023, Month.APRIL, 13, 14, 0));

        BallotModel thirdBallot = new BallotModel();
        thirdBallot.setUser(userModel);
        thirdBallot.setLotteryEvent(secondLotteryEvent);
        thirdBallot.setWinner(false);
        thirdBallot.setRegistrationDate(LocalDateTime.of(2023, Month.APRIL, 14, 15, 0));

        BallotModel fourthBallot = new BallotModel();
        fourthBallot.setUser(userModel);
        fourthBallot.setLotteryEvent(secondLotteryEvent);
        fourthBallot.setWinner(true);
        fourthBallot.setRegistrationDate(LocalDateTime.of(2023, Month.APRIL, 14, 16, 0));

        ballotRepository.save(firstBallot);
        ballotRepository.save(secondBallot);
        ballotRepository.save(thirdBallot);
        ballotRepository.save(fourthBallot);
        
        //when
        LocalDate dateToSearchWinners = LocalDate.of(2023, Month.APRIL, 14);
        Optional<BallotModel> winningBallot = ballotRepository.findWinningBallotForDate(dateToSearchWinners);
        
        //then
        assertThat(winningBallot.isPresent() && winnerIsFourthBallot(winningBallot.get(), fourthBallot.getId()))
                .isTrue();
    }
    
    private boolean winnerIsFourthBallot(BallotModel winningBallot, UUID fourthBallotID){
        return winningBallot.getId().equals(fourthBallotID);
    }
    
    @Test
    void testGetNumBallots(){
        
        //given 
        BallotModel firstBallot = new BallotModel();
        firstBallot.setUser(userModel);
        firstBallot.setLotteryEvent(firstLotteryEvent);
        firstBallot.setWinner(false);
        firstBallot.setRegistrationDate(LocalDateTime.of(2023, Month.APRIL, 13, 13, 0));
        
        BallotModel secondBallot = new BallotModel();
        secondBallot.setUser(userModel);
        secondBallot.setLotteryEvent(firstLotteryEvent);
        secondBallot.setWinner(false);
        secondBallot.setRegistrationDate(LocalDateTime.of(2023, Month.APRIL, 13, 14, 0));

        BallotModel thirdBallot = new BallotModel();
        thirdBallot.setUser(userModel);
        thirdBallot.setLotteryEvent(secondLotteryEvent);
        thirdBallot.setWinner(false);
        thirdBallot.setRegistrationDate(LocalDateTime.of(2023, Month.APRIL, 13, 15, 0));
        
        BallotModel fourthBallot = new BallotModel();
        fourthBallot.setUser(userModel);
        fourthBallot.setLotteryEvent(firstLotteryEvent);
        fourthBallot.setWinner(true);
        fourthBallot.setRegistrationDate(LocalDateTime.of(2023, Month.APRIL, 13, 16, 0));
        
        ballotRepository.save(firstBallot);
        ballotRepository.save(secondBallot);
        ballotRepository.save(thirdBallot);
        ballotRepository.save(fourthBallot);
        
        int numBalots = ballotRepository.getNumBallots(firstLotteryEvent);
        assertTrue(numBalots == 3);
    }
    
    @Test
    void testSelectBallot(){
        
        //given 
        BallotModel firstBallot = new BallotModel();
        firstBallot.setUser(userModel);
        firstBallot.setLotteryEvent(firstLotteryEvent);
        firstBallot.setWinner(true);
        firstBallot.setRegistrationDate(LocalDateTime.of(2023, Month.APRIL, 13, 13, 0));
        
        BallotModel secondBallot = new BallotModel();
        secondBallot.setUser(userModel);
        secondBallot.setLotteryEvent(firstLotteryEvent);
        secondBallot.setWinner(false);
        secondBallot.setRegistrationDate(LocalDateTime.of(2023, Month.APRIL, 13, 14, 0));

        BallotModel thirdBallot = new BallotModel();
        thirdBallot.setUser(userModel);
        thirdBallot.setLotteryEvent(firstLotteryEvent);
        thirdBallot.setWinner(false);
        thirdBallot.setRegistrationDate(LocalDateTime.of(2023, Month.APRIL, 13, 15, 0));

        BallotModel fourthBallot = new BallotModel();
        fourthBallot.setUser(userModel);
        fourthBallot.setLotteryEvent(firstLotteryEvent);
        fourthBallot.setWinner(true);
        fourthBallot.setRegistrationDate(LocalDateTime.of(2023, Month.APRIL, 13, 16, 0));
        
        BallotModel fifthBallot = new BallotModel();
        fifthBallot.setUser(userModel);
        fifthBallot.setLotteryEvent(firstLotteryEvent);
        fifthBallot.setWinner(true);
        fifthBallot.setRegistrationDate(LocalDateTime.of(2023, Month.APRIL, 14, 16, 0));
        
        ballotRepository.save(firstBallot);
        ballotRepository.save(secondBallot);
        ballotRepository.save(thirdBallot);
        ballotRepository.save(fifthBallot);
        
        BallotModel ballotModel = ballotRepository.selectBallot(firstLotteryEvent, 1);
        assertTrue(ballotModel.getId().equals(secondBallot.getId()));
    }
}
