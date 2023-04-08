package com.jose.lottery.repositories;

import com.jose.lottery.models.UserModel;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jose
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

}
