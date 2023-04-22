package com.jose.lottery.repositories;

import com.jose.lottery.models.LotteryEventModel;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 *
 * @author jose
 */
@DataJpaTest
public class LotteryEventRepositoryTest {
    
    @Autowired
    private LotteryEventRepository lotteryRepository;
    
    public LotteryEventRepositoryTest() {
    }

    @Test
    public void testGetOpenEventsBeforeDate() {
        
        //given
        LotteryEventModel firstLotteryEvent = new LotteryEventModel();
        LocalDate lotteryDate = LocalDate.of(2023, Month.APRIL, 2);
        firstLotteryEvent.setDate(lotteryDate);
        firstLotteryEvent.setStatus(LotteryEventModel.Status.OPEN);
        
        LotteryEventModel secondLotteryEvent = new LotteryEventModel();
        lotteryDate = LocalDate.of(2023, Month.APRIL, 3);
        secondLotteryEvent.setDate(lotteryDate);
        secondLotteryEvent.setStatus(LotteryEventModel.Status.CLOSED);
        
        LotteryEventModel thirdLotteryEvent = new LotteryEventModel();
        lotteryDate = LocalDate.of(2023, Month.APRIL, 4);
        thirdLotteryEvent.setDate(lotteryDate);
        thirdLotteryEvent.setStatus(LotteryEventModel.Status.OPEN);
        
        LotteryEventModel fourthLotteryEvent = new LotteryEventModel();
        lotteryDate = LocalDate.of(2023, Month.APRIL, 5);
        fourthLotteryEvent.setDate(lotteryDate);
        fourthLotteryEvent.setStatus(LotteryEventModel.Status.OPEN);
        
        lotteryRepository.save(firstLotteryEvent);
        lotteryRepository.save(secondLotteryEvent);
        lotteryRepository.save(thirdLotteryEvent);
        lotteryRepository.save(fourthLotteryEvent);
        
        //when
        LocalDate date = LocalDate.of(2023, Month.APRIL, 4);
        List<LotteryEventModel> events = lotteryRepository.findOpenEventsBeforeOrEqualDate(date);
        
        //then
        assertTrue(events.size() == 2 && eventDatesCorrect(events));
    }
    
    private boolean eventDatesCorrect(List<LotteryEventModel> events){
        LocalDate firstEventDate = LocalDate.of(2023, Month.APRIL, 2);
        LotteryEventModel firstLotteryEvent = events.get(0);        
        LocalDate secondEventDate = LocalDate.of(2023, Month.APRIL, 4);
        LotteryEventModel secondLotteryEvent = events.get(1);
        
        return firstLotteryEvent.getDate().equals(firstEventDate) &&
                secondLotteryEvent.getDate().equals(secondEventDate);
    }
    
}
