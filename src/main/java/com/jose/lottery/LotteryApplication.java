package com.jose.lottery;

import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.repositories.LotteryEventRepository;
import com.jose.lottery.repositories.UserRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableScheduling
@RestController
public class LotteryApplication {

    private static Logger logger = LoggerFactory.getLogger(LotteryApplication.class);
    
    public static void main(String[] args) {
        SpringApplication.run(LotteryApplication.class, args);
        
        logger.info("Lottery Serrvice started successfully!");
    }

    @GetMapping("/")
    public String alive() {
        return "Server Alive!";
    }

}
