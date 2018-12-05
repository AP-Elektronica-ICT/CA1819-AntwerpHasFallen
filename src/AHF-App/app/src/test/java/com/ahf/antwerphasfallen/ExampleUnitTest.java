package com.ahf.antwerphasfallen;

import org.junit.Test;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getPlayerFromString(){
        MainActivity testMain = new MainActivity();
        testMain.extractPlayerFromFileString("playerId:1;gameId:2;teamId:3");
    }
}