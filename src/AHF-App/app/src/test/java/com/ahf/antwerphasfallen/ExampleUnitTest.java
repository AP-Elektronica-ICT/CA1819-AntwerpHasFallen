package com.ahf.antwerphasfallen;

import android.test.mock.MockContext;

import com.ahf.antwerphasfallen.Helpers.PlayerHandler;
import com.ahf.antwerphasfallen.Model.Player;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(Theories.class)
public class ExampleUnitTest {


    @DataPoints
    public static int[] ids (){
        return new int[]{21,2,3,54,26,8,62,58,46};
    }

    @Theory(nullsAccepted = false)
    public void getPlayerFromString(int id, int gameId, int teamId){
        PlayerHandler testPlayerHandler = new PlayerHandler(new MockContext());
        Player result = testPlayerHandler.extractPlayerFromFileString("playerId:"+id + ";gameId:"+ gameId + ";teamId:"+teamId+";");

        assertEquals(id, result.getId());
        assertEquals(gameId, result.getGameId());
        assertEquals(teamId, result.getTeamId());
    }
}