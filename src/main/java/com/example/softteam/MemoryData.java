package com.example.softteam;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MemoryData {
    public static void saveData(String data, Context context) {
        try (FileOutputStream fileOutputStream = context.openFileOutput("data.txt", Context.MODE_PRIVATE)) {
            fileOutputStream.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately, e.g., log it or show a message to the user
        }
    }

    public static void saveLastMsgTS(String data,String chatId, Context context) {
        try (FileOutputStream fileOutputStream = context.openFileOutput(chatId+"LastMsgTS.txt", Context.MODE_PRIVATE)) {
            fileOutputStream.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
    }

    public static String getData(Context context) {
        StringBuilder data = new StringBuilder();
        try (FileInputStream fis = context.openFileInput("data.txt");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader bfr = new BufferedReader(isr)) {
            String line;
            while ((line = bfr.readLine()) != null) {
                data.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
        return data.toString();
    }

    public static String getName(Context context) {
        StringBuilder name = new StringBuilder();
        try (FileInputStream fis = context.openFileInput("name.txt");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader bfr = new BufferedReader(isr)) {
            String line;
            while ((line = bfr.readLine()) != null) {
                name.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
        return name.toString();
    }

    public static String getLastMsgTs(String chatId,Context context) {
        StringBuilder name = new StringBuilder();
        try (FileInputStream fis = context.openFileInput("chatID.txt");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader bfr = new BufferedReader(isr)) {
            String line;
            while ((line = bfr.readLine()) != null) {
                name.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
        return name.toString();
    }

    public static void saveName(String nametxt, Register register) {
    }
}
