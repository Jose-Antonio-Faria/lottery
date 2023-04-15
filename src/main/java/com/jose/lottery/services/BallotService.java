package com.jose.lottery.services;

import com.jose.lottery.models.BallotModel;
import com.jose.lottery.models.UserModel;
import com.jose.lottery.repositories.BallotRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jose
 */
@Service
public class BallotService {

    final BallotRepository ballotRepository;

    public BallotService(BallotRepository ballotRepository) {
        this.ballotRepository = ballotRepository;
    }

    @Transactional
    public BallotModel save(BallotModel ballotModel) {
        return ballotRepository.save(ballotModel);
    }
    
    public Page<BallotModel> findAll(Pageable pageable) {
        return ballotRepository.findAll(pageable);
    }
    
    public List<BallotModel> findByUser(UserModel user) {
        return ballotRepository.findByUser(user);
    }
    
    public List<BallotModel> findWinningBallotsForDate(LocalDate date){
        return ballotRepository.findWinningBallotsForDate(date);
    }
}
