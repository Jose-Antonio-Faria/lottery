package com.jose.lottery.services;

import com.jose.lottery.models.BallotModel;
import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.models.UserModel;
import com.jose.lottery.repositories.BallotRepository;
import com.jose.lottery.winner.RandomNumberGenerator;
import com.jose.lottery.winner.WinningBallotSelector;
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
public class BallotService {

    private final BallotRepository ballotRepository;
    private final WinningBallotSelector winningBallotSelector;

    public BallotService(BallotRepository ballotRepository) {
        this.ballotRepository = ballotRepository;
        this.winningBallotSelector = new WinningBallotSelector(ballotRepository, new RandomNumberGenerator());
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
    
    public Optional<BallotModel> findWinningBallotForDate(LocalDate date){
        return ballotRepository.findWinningBallotForDate(date);
    }
    
    public Optional<BallotModel> getSimulatedWinner(LotteryEventModel todayLotteryEvent){
        Optional<BallotModel> simulatedWinner = winningBallotSelector.selectWinningBallot(todayLotteryEvent);
        return simulatedWinner;
    }
}
