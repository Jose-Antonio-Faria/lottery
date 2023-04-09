package com.jose.lottery.repositories;

import com.jose.lottery.models.BallotModel;
import com.jose.lottery.models.UserModel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jose
 */
@Repository
public interface BallotRepository extends JpaRepository<BallotModel, UUID>{
    
    public List<BallotModel> findByUser(UserModel user);
    
}
