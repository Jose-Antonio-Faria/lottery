package com.jose.lottery.services;

import com.jose.lottery.models.UserModel;
import com.jose.lottery.repositories.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jose
 */
@Service
public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }
    
    public boolean existsByName(String name){
        return userRepository.existsByName(name);
    }
    
    public boolean existsByIdentificationDocumentNumber(String identificationDocumentNumber){
        return userRepository.existsByIdentificationDocumentNumber(identificationDocumentNumber);
    }
    
    public boolean existsByTaxIdentificationNumber(String taxIdentificationNumber){
        return userRepository.existsByTaxIdentificationNumber(taxIdentificationNumber);
    }
    
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
    
    public Page<UserModel> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    
    public Optional<UserModel> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<UserModel> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    
    @Transactional
    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }
    
}
