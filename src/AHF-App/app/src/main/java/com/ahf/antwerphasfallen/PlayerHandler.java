package com.ahf.antwerphasfallen;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Jorren on 5/12/2018.
 */

public class PlayerHandler {
    private static final String TAG = "PlayerHandler";
    private final String SAVED_PLAYER = "savedPlayer";

    private Context context;
    private static PlayerHandler playerHandler;

    public PlayerHandler(Context context){
        this.context = context;
    }

    public static PlayerHandler getInstance(Context context){
        if(playerHandler == null)
            playerHandler = new PlayerHandler(context);
        return playerHandler;
    }

    public Player checkPlayer() {
        checkFile();
        String playerInfo = getPlayerInfo();
        if(playerInfo.contains("playerId"))
            if(playerInfo.contains("gameId"))
                if (playerInfo.contains("teamId"))
                    return extractPlayerFromFileString(playerInfo);
        return null;
    }

    public void checkFile() {
        final File file;
        boolean found = false;
        for(int i=0; i<context.fileList().length; i++){
            if(context.fileList()[i] == SAVED_PLAYER){
                found = true;
                break;
            }
        }
        if(!found) file = new File(context.getFilesDir(), SAVED_PLAYER);
    }

    public String getPlayerInfo(){
        FileInputStream fis = null;
        InputStreamReader reader = null;
        try{
            fis = context.openFileInput(SAVED_PLAYER);
            reader = new InputStreamReader(fis);

            char[] buffer = new char[100];
            int bytesRead;
            String playerInfo = "";

            while ((bytesRead=reader.read(buffer)) > 0){
                String read = String.copyValueOf(buffer, 0, bytesRead);
                playerInfo += read;
            }
            return playerInfo;
        }catch (IOException e){
            e.printStackTrace();
            return "";
        }finally {
            try {
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Player extractPlayerFromFileString(String info){
        Player player = new Player();
        player.setId(extractIdFromString(info, "playerId:"));
        player.setGameId(extractIdFromString(info, "gameId:"));
        player.setTeamId(extractIdFromString(info, "teamId:"));

        return player;
    }

    private int extractIdFromString(String s, String name){
        return Integer.valueOf(s.substring(s.indexOf(name) + name.length(), s.indexOf(';', s.indexOf(name))));
    }
}
