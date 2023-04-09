package com.jose.lottery.event.manager;

import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.services.LotteryEventService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author jose
 */
@Component
public class LotteryEventManager {

    final LotteryEventService lotteryEventService;

    public LotteryEventManager(LotteryEventService lotteryEventService) {
        this.lotteryEventService = lotteryEventService;
        execute();
    }

//    @Scheduled(fixedRate = 5000)
    public void execute() {
        if(!lotteryEventService.existsByDate(LocalDateTime.now(ZoneId.of("UTC")))){
            createLotteryEvent();     
        }
    }
    
    private void createLotteryEvent(){
        LotteryEventModel lotteryEventModel = new LotteryEventModel();
        lotteryEventModel.setDate(LocalDateTime.now(ZoneId.of("UTC")));
        lotteryEventModel.setStatus(LotteryEventModel.Status.OPEN);
        lotteryEventService.save(lotteryEventModel);
    }
    
}
