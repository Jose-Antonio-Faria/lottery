package com.jose.lottery.winner;

import com.jose.lottery.models.BallotModel;
import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.repositories.BallotRepository;
import java.util.Optional;

/**
 *
 * @author jose
 */
public class WinningBallotSelector {

    private BallotRepository ballotRepository;
    private final IRandomNumberGenerator randomNumberGenerator;

    public WinningBallotSelector(BallotRepository ballotRepository, IRandomNumberGenerator randomNumberGenerator) {
        this.ballotRepository = ballotRepository;
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public Optional<BallotModel> selectWinningBallot(LotteryEventModel lotteryEvent) {

        Optional<BallotModel> winner;

        int numBallotsInLottery = ballotRepository.getNumBallots(lotteryEvent);
        if (numBallotsInLottery != 0) {
            int randomRow = randomNumberGenerator.generateNumber(numBallotsInLottery);
            winner = Optional.of(ballotRepository.selectBallot(lotteryEvent, randomRow));
        } else {
            winner = Optional.empty();
        }

        return winner;
    }

}
