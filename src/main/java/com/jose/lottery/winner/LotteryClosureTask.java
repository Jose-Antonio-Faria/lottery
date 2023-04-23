package com.jose.lottery.winner;

import com.jose.lottery.models.BallotModel;
import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.repositories.BallotRepository;
import com.jose.lottery.repositories.LotteryEventRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author jose
 */
@Component
public class LotteryClosureTask {

    private final int CLOCK_DRIFT_MARGIN_IN_MINUTES = 5;

    private LotteryEventRepository lotteryEventRepository;
    private BallotRepository ballotRepository;
    private final WinningBallotSelector winningBallotMarker;

    private static Logger logger = LoggerFactory.getLogger(LotteryClosureTask.class);

    public LotteryClosureTask(LotteryEventRepository lotteryEventRepository, BallotRepository ballotRepository) {
        this.lotteryEventRepository = lotteryEventRepository;
        this.ballotRepository = ballotRepository;
        winningBallotMarker = new WinningBallotSelector(ballotRepository, new RandomNumberGenerator());
    }

    @Scheduled(cron = "0 0 0 * * ?", zone = "UTC")
    public void selectWinners() {

        List<LotteryEventModel> eventsToClose = getLotteryEventsToClose();

        for (LotteryEventModel event : eventsToClose) {
            Optional<BallotModel> winner = winningBallotMarker.selectWinningBallot(event);
            if (winner.isPresent()) {
                markWinningBallot(winner.get());
            } else {
                logger.info("Unable to select winner for event on day ", event.getDate().toString());
            }
            closeLotteryEvent(event);
        }

        createTodayLotteryEvent();
    }

    private List<LotteryEventModel> getLotteryEventsToClose() {
        LocalDate previousDay = getPreviousDay();
        return lotteryEventRepository.findOpenEventsBeforeOrEqualDate(previousDay);
    }

    private LocalDate getPreviousDay() {
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("UTC"));
        LocalDateTime previousDay = currentDateTime.plusMinutes(CLOCK_DRIFT_MARGIN_IN_MINUTES).minusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return previousDay.toLocalDate();
    }

    private void markWinningBallot(BallotModel winningBallot) {
        winningBallot.setWinner(true);
        ballotRepository.save(winningBallot);
    }

    private void closeLotteryEvent(LotteryEventModel event) {
        event.setStatus(LotteryEventModel.Status.CLOSED);
        lotteryEventRepository.save(event);
    }

    private void createTodayLotteryEvent() {
        LotteryEventModel lotteryEventModel = new LotteryEventModel();
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("UTC"));
        LocalDateTime currentDay = currentDateTime.plusMinutes(CLOCK_DRIFT_MARGIN_IN_MINUTES).withHour(0).withMinute(0).withSecond(0).withNano(0);
        if (!lotteryEventRepository.existsByDate(currentDay.toLocalDate())) {
            lotteryEventModel.setDate(currentDay.toLocalDate());
            lotteryEventModel.setStatus(LotteryEventModel.Status.OPEN);
            lotteryEventRepository.save(lotteryEventModel);
        }
    }
}
