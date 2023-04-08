/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jose.lottery.services;

import com.jose.lottery.models.UserModel;
import com.jose.lottery.repositories.UserRepository;
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
    
}
