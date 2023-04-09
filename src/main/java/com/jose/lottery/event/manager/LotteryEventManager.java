package com.jose.lottery.event.manager;

import com.jose.lottery.configs.DateConfig;
import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.services.LotteryEventService;
import com.jose.lottery.utils.DateUtils;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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
        if (!lotteryEventService.existsByDate(DateUtils.getTodayDate())) {
            createLotteryEvent();
        }
    }

    private void createLotteryEvent() {
        LotteryEventModel lotteryEventModel = new LotteryEventModel();
        lotteryEventModel.setDate(DateUtils.getTodayDate());
        lotteryEventModel.setStatus(LotteryEventModel.Status.OPEN);
        lotteryEventService.save(lotteryEventModel);
    }

}
