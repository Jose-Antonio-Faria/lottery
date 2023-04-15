package com.jose.lottery.winner;

import com.jose.lottery.models.BallotModel;
import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.repositories.BallotRepository;
import java.util.List;

/**
 *
 * @author jose
 */
public class WinningBallotsMarker {

    private BallotRepository ballotRepository;
    private final IRandomKeyGenerator randomKeyGenerator;

    public WinningBallotsMarker(BallotRepository ballotRepository, IRandomKeyGenerator randomKeyGenerator) {
        this.ballotRepository = ballotRepository;
        this.randomKeyGenerator = randomKeyGenerator;
    }

    public void markWinningBallots(LotteryEventModel lotteryEvent) {
        int[] jackpotKey = randomKeyGenerator.generateKey();

        List<BallotModel> winningBallots = ballotRepository.findBallotsWithKey(lotteryEvent, jackpotKey);

        markWinningBallots(winningBallots);
    }

    private void markWinningBallots(List<BallotModel> winningBallots) {
        for (BallotModel ballot : winningBallots) {
            ballot.setWinner(true);
            ballotRepository.save(ballot);
        }
    }

}
