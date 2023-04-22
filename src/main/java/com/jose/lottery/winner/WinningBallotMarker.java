package com.jose.lottery.winner;

import com.jose.lottery.models.BallotModel;
import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.repositories.BallotRepository;

/**
 *
 * @author jose
 */
public class WinningBallotMarker {

    private BallotRepository ballotRepository;
    private final IRandomNumberGenerator randomNumberGenerator;

    public WinningBallotMarker(BallotRepository ballotRepository, IRandomNumberGenerator randomNumberGenerator) {
        this.ballotRepository = ballotRepository;
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void markWinningBallot(LotteryEventModel lotteryEvent) {

        int numBallotsInLottery = ballotRepository.getNumBallots(lotteryEvent);

        int randomRow = randomNumberGenerator.generateNumber(numBallotsInLottery);

        BallotModel winner = ballotRepository.selectBallot(lotteryEvent, randomRow);

        markWinningBallot(winner);
    }

    private void markWinningBallot(BallotModel winningBallot) {
        winningBallot.setWinner(true);
        ballotRepository.save(winningBallot);
    }

}
