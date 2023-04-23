package com.jose.lottery.repositories;

import com.jose.lottery.models.BallotModel;
import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.models.UserModel;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
       
    @Query("SELECT COUNT(*) FROM BallotModel b "
            + "JOIN b.lotteryEvent l "
            + "WHERE b.lotteryEvent = :lotteryEvent ")
    int getNumBallots(LotteryEventModel lotteryEvent);
    
    @Query("SELECT b FROM BallotModel b "
            + "WHERE b.lotteryEvent = :lotteryEvent "
            + "ORDER BY b.registrationDate asc "
            + "LIMIT 1 "
            + "OFFSET :row")
    BallotModel selectBallot(LotteryEventModel lotteryEvent, int row);
    
    @Query(""
            + "SELECT b FROM BallotModel b "
            + "WHERE b.lotteryEvent = :lotteryEvent "
            + "AND b.winner = true")
    Optional<BallotModel> findWinningBallotByLotteryEvent(LotteryEventModel lotteryEvent);

    @Query("SELECT b FROM BallotModel b "
            + "JOIN b.lotteryEvent l "
            + "WHERE l.date = :date "
            + "AND b.winner = true")
    Optional<BallotModel> findWinningBallotForDate(LocalDate date);
   
    
}
