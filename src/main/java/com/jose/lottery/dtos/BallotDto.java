package com.jose.lottery.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

/**
 *
 * @author jose
 */
public class BallotDto {
    
    @NotNull
    private String email;
    
    @Min(value = 1)
    @Max(value = 10)
    private int number1;
    @Min(value = 1)
    @Max(value = 10)
    private int number2;
    @Min(value = 1)
    @Max(value = 10)
    private int number3;
    @Min(value = 1)
    @Max(value = 10)
    private int number4;
    @Min(value = 1)
    @Max(value = 10)
    private int number5;

    private boolean winner;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    public int getNumber3() {
        return number3;
    }

    public void setNumber3(int number3) {
        this.number3 = number3;
    }

    public int getNumber4() {
        return number4;
    }

    public void setNumber4(int number4) {
        this.number4 = number4;
    }

    public int getNumber5() {
        return number5;
    }

    public void setNumber5(int number5) {
        this.number5 = number5;
    }

    public int[] getNumbers() {
        int[] numbers = new int[5];
        numbers[0] = number1;
        numbers[1] = number2;
        numbers[2] = number3;
        numbers[3] = number4;
        numbers[4] = number5;
        return numbers;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
 
}
