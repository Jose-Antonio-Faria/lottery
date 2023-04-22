package com.jose.lottery.winner;

import java.util.Random;

/**
 *
 * @author jose
 */
public class RandomNumberGenerator implements IRandomNumberGenerator{

    private final Random rand = new Random();
    
    @Override
    public int generateNumber(int upperLimit) {
        return rand.nextInt(upperLimit);
    }
    
}
