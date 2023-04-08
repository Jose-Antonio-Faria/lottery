package com.jose.lottery.controllers;

import com.jose.lottery.dtos.UserDto;
import com.jose.lottery.models.UserModel;
import com.jose.lottery.services.UserService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jose
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto userDto){
        if(userService.existsByName(userDto.getName())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Name is already in use!");
        }
        if(userService.existsByIdentificationDocumentNumber(userDto.getIdentificationDocumentNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Identification Document Number is already in use!");
        }
        if(userService.existsByTaxIdentificationNumber(userDto.getTaxIdentificationNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Tax Identification Number is already in use!");
        }
        if(userService.existsByEmail(userDto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Email is already in use!");
        }
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
    }
}
