package com.jose.lottery.controllers;

import com.jose.lottery.models.LotteryEventModel;
import com.jose.lottery.models.UserModel;
import com.jose.lottery.services.LotteryEventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jose
 */

@RestController
@RequestMapping("/lottery")
public class LotteryEventController {
    private final LotteryEventService lotteryEventService;

    public LotteryEventController(LotteryEventService lotteryEventService) {
        this.lotteryEventService = lotteryEventService;
    }
    
    @GetMapping
    public ResponseEntity<Page<LotteryEventModel>> getAllLotteryEvents(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(lotteryEventService.findAll(pageable));
    }
}
