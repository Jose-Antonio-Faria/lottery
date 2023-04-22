package com.jose.lottery;

import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.repositories.LotteryEventRepository;
import com.jose.lottery.utils.DateUtils;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author jose
 */
public class LotteryEventInitializer {
    
    @Autowired
    private LotteryEventRepository lotteryEventRepository;

    public void openTodayLottery(){
        LocalDate today = DateUtils.getTodayDate();
        if(!lotteryEventRepository.existsByDate(today)){
            LotteryEventModel lotteryEventModel = new LotteryEventModel();
            lotteryEventModel.setDate(today);
            lotteryEventModel.setStatus(LotteryEventModel.Status.OPEN);
            lotteryEventRepository.save(lotteryEventModel);
        }
    }
    
}
