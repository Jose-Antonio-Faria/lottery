package com.jose.lottery.winner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author jose
 */
public class JackpotKeyGeneratorTest {
    
    public JackpotKeyGeneratorTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of generateKey method, of class JackpotKeyGenerator.
     */
    @Test
    public void if_key_generated_then_key_has_5_numbers_between_1_and_10() {
        JackpotKeyGenerator jackpotKeyGenerator = new JackpotKeyGenerator();
        int[] jackpotKey = jackpotKeyGenerator.generateKey();
        then_5_numbers_between_1_and_10(jackpotKey);
    }
    
    private void then_5_numbers_between_1_and_10(int[] jackpotKey){
        boolean has_5_numbers = check_has_5_numbers(jackpotKey);
        boolean numbers_between_1_and_10 = check_numbers_between_1_and_10(jackpotKey);
        assertTrue(has_5_numbers && numbers_between_1_and_10);
    }
    
    private boolean check_has_5_numbers(int[] jackpotKey){
        return jackpotKey.length == 5;
    }
    
    private boolean check_numbers_between_1_and_10(int[] jackpotKey){
        boolean numbers_between_1_and_10 = true;
        for (int i = 0; i < jackpotKey.length; i++) {
            if(jackpotKey[i] < 1 || jackpotKey[i] > 10){
                numbers_between_1_and_10 = false;
                break;
            }
        }
        return numbers_between_1_and_10;
    }
    
}
