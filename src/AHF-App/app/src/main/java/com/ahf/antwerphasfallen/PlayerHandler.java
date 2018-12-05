package com.ahf.antwerphasfallen;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Jorren on 5/12/2018.
 */

public class PlayerHandler {
    private static final String TAG = "PlayerHandler";
    public static final String SAVED_PLAYER = "savedPlayer.txt";

    private Context context;
    private static PlayerHandler playerHandler;

    public PlayerHandler(Context context) {
        this.context = context;
    }

    public static PlayerHandler getInstance(Context context) {
        if (playerHandler == null)
            playerHandler = new PlayerHandler(context);
        return playerHandler;
    }

    public Player checkPlayer() {
        checkFile();
        String playerInfo = getPlayerInfo();
        if (playerInfo.contains("playerId"))
            if (playerInfo.contains("gameId"))
                if (playerInfo.contains("teamId"))
                    return extractPlayerFromFileString(playerInfo);
        return null;
    }

    public void checkFile() {
        final File file;
        String path = context.getFilesDir().getAbsolutePath() + "/" + SAVED_PLAYER;
        file = new File(path);
        if (!file.exists())
            createFile(file.getName());
    }

    private void createFile(String filename) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write("test".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteFile(String filename){
        File file = new File(context.getFilesDir(), filename);
        if(file.exists()) file.delete();
    }

    public String getPlayerInfo() {
        String playerInfo = "";
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(SAVED_PLAYER);

            byte[] buffer = new byte[100];
            int bytesRead = fis.read(buffer);
            if(bytesRead > 0){
                byte[] readBuffer = new byte[bytesRead];
                for (int i = 0; i < bytesRead; i++) {
                    readBuffer[i] = buffer[i];
                }
                String read = new String(readBuffer);
                playerInfo += read;
            }
            return playerInfo;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void putPlayerInfo(Player player) {
        checkFile();
        String playerInfo = playerToFileString(player);
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(SAVED_PLAYER, Context.MODE_PRIVATE);
            fos.write(playerInfo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Player extractPlayerFromFileString(String info) {
        Player player = new Player();
        player.setId(extractIdFromString(info, "playerId:"));
        player.setGameId(extractIdFromString(info, "gameId:"));
        player.setTeamId(extractIdFromString(info, "teamId:"));

        return player;
    }

    private String playerToFileString(Player player) {
        String s = "playerId:";
        s += player.getId() + ";";
        s += "gameId:";
        s += player.getGameId() + ";";
        s += "teamId:";
        s += player.getTeamId() + ";";
        return s;
    }

    private int extractIdFromString(String s, String name) {
        return Integer.valueOf(s.substring(s.indexOf(name) + name.length(), s.indexOf(';', s.indexOf(name))));
    }
}
