package com.jose.lottery.services;

import com.jose.lottery.models.BallotModel;
import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.repositories.BallotRepository;
import com.jose.lottery.repositories.LotteryEventRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jose
 */
@Service
public class LotteryEventService {

    final LotteryEventRepository lotteryEventRepository;

    private BallotRepository ballotRepository;
    
    public LotteryEventService(LotteryEventRepository lotteryEventRepository) {
        this.lotteryEventRepository = lotteryEventRepository;
    }

    @Transactional
    public LotteryEventModel save(LotteryEventModel lotteryEventModel) {
        return lotteryEventRepository.save(lotteryEventModel);
    }
    
    public Page<LotteryEventModel> findAll(Pageable pageable) {
        return lotteryEventRepository.findAll(pageable);
    }
    
    public boolean existsByDate(LocalDate date){
        return lotteryEventRepository.existsByDate(date);
    }
    
    public Optional<LotteryEventModel> findByDate(LocalDate date) {
        return lotteryEventRepository.findByDate(date);
    }
    
    public List<BallotModel> findWinningBallots(LotteryEventModel lotteryEvent) {
        return ballotRepository.findWinningBallotsByLotteryEvent(lotteryEvent);
    }
    
}
