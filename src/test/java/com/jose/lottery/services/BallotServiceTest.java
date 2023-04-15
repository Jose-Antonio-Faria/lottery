package com.jose.lottery.services;

import com.jose.lottery.repositories.BallotRepository;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author jose
 */
@ExtendWith(MockitoExtension.class)
public class BallotServiceTest {
    
    @Mock private BallotRepository ballotRepository;
    private BallotService underTest;

    @BeforeEach
    void setUp(){
        underTest = new BallotService(ballotRepository);
    }
    
    @Test
    void canFindWinningBallotForDate(){
        //when
        LocalDateTime date = LocalDateTime.of(2023, Month.APRIL, 13, 0, 0);
        underTest.findWinningBallotsForDate(date);
        
        //then
        verify(ballotRepository).findWinningBallotsForDate(date);
    }
    
}
