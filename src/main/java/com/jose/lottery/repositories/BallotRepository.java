package com.jose.lottery.repositories;

import com.jose.lottery.models.BallotModel;
import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.models.UserModel;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jose
 */
@Repository
public interface BallotRepository extends JpaRepository<BallotModel, UUID>{
    
    public List<BallotModel> findByUser(UserModel user);
    
    @Query(""
            + "SELECT b FROM BallotModel b "
            + "WHERE b.lotteryEvent = ?1 "
            + "AND b.winner = true")
    List<BallotModel> findWinningBallotsByLotteryEvent(LotteryEventModel lotteryEvent);
    
    @Query("SELECT b FROM BallotModel b "
            + "WHERE b.lotteryEvent = ?1 "
            + "AND b.numbers = ?2")
    List<BallotModel> findBallotsWithKey(LotteryEventModel lotteryEvent, int[] key);

    @Query("SELECT b FROM BallotModel b "
            + "JOIN b.lotteryEvent l "
            + "WHERE l.date = :date "
            + "AND b.winner = true")
    List<BallotModel> findWinningBallotsForDate(LocalDate date);
}
