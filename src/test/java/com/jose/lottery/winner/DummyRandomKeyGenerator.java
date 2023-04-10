package com.jose.lottery.winner;

/**
 *
 * @author jose
 */
public class DummyRandomKeyGenerator implements IRandomKeyGenerator{

    @Override
    public int[] generateKey() {
        return new int[]{2,4,6,8,10};
    }
    
}
