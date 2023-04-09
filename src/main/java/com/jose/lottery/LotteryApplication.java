package com.jose.lottery;

import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.repositories.LotteryEventRepository;
import com.jose.lottery.repositories.UserRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public static void main(String[] args) {
        SpringApplication.run(LotteryApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner demo(LotteryEventRepository lotteryEventRepository) {
//        return (args) -> {
//        };
//    }

    @GetMapping("/")
    public String alive() {
        return "Server Alive!";
    }

}
