package com.jose.lottery.repositories;

import com.jose.lottery.models.LotteryEventModel;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jose
 */
@Repository
public interface LotteryEventRepository extends JpaRepository<LotteryEventModel, UUID>{
    
    public boolean existsByDate(LocalDateTime date);
    public Optional<LotteryEventModel> findByDate(LocalDateTime date);
}
