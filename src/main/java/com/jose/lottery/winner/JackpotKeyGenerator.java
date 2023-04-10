package com.jose.lottery.winner;

import java.util.Random;

/**
 *
 * @author jose
 */
public class JackpotKeyGenerator implements IRandomKeyGenerator{

    @Override
    public int[] generateKey() {
        
        int[] key = new int[5];
        Random rand = new Random();
        for (int i = 0; i < key.length; i++) {
            int num = rand.nextInt(10) + 1;
            key[i] = num;
        }
        return key;
    }
    
}
