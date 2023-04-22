package com.jose.lottery.repositories;

import com.jose.lottery.models.LotteryEventModel;
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
public interface LotteryEventRepository extends JpaRepository<LotteryEventModel, UUID> {

    public boolean existsByDate(LocalDate date);

    public Optional<LotteryEventModel> findByDate(LocalDate date);

    @Query("SELECT l FROM LotteryEventModel l "
            + "WHERE l.date <= :date "
            + "AND l.status = 0 "
            + "ORDER BY l.date ")
    public List<LotteryEventModel> findOpenEventsBeforeOrEqualDate(LocalDate date);

}
