package com.jose.lottery.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

/**
 *
 * @author jose
 */
public class BallotDto {
    
    @NotNull
    private UUID user_id;
    @NotNull
    private UUID lottery_id;
    @Size(min = 5, max = 5)
    private int[] numbers;

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public UUID getLottery_id() {
        return lottery_id;
    }

    public void setLottery_id(UUID lottery_id) {
        this.lottery_id = lottery_id;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public void setNumbers(int[] numbers) {
        this.numbers = numbers;
    }
 
}
