package com.jose.lottery.services;

import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.repositories.LotteryEventRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
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
    
    public boolean existsByDate(LocalDateTime date){
        return lotteryEventRepository.existsByDate(date);
    }
    
    public Optional<LotteryEventModel> findByDate(LocalDateTime date) {
        return lotteryEventRepository.findByDate(date);
    }
    
}
