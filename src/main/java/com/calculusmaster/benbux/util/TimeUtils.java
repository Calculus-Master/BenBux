package com.calculusmaster.benbux.util;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.util.Arrays;

public class TimeUtils
{
    public static String timeLeft(JSONObject userData, MessageReceivedEvent e, int[] cooldown, String cmd)
    {
        String[] timestamp = userData.getString("timestamp_" + cmd).split("\\s");

        //Day, Hour, Minute, Second
        int[] now = {e.getMessage().getTimeCreated().getDayOfYear(), e.getMessage().getTimeCreated().getHour(), e.getMessage().getTimeCreated().getMinute(), e.getMessage().getTimeCreated().getSecond()};
        int[] ts = {Integer.parseInt(timestamp[2]), Integer.parseInt(timestamp[3]), Integer.parseInt(timestamp[4]), Integer.parseInt(timestamp[5])};
        int[] end = {ts[0] + cooldown[0], ts[1] + cooldown[1], ts[2] + cooldown[2], ts[3] + cooldown[3]};

        int totalDiffSec = (end[0] * 3600 * 24 + end[1] * 3600 + end[2] * 60 + end[3]) - (now[0] * 3600 * 24 + now[1] * 3600 + now[2] * 60 + now[3]);

        int sec = totalDiffSec % 60;
        totalDiffSec /= 60;
        int min = totalDiffSec % 60;
        totalDiffSec /= 60;
        int hr = totalDiffSec % 24;
        totalDiffSec /= 24;
        int day = totalDiffSec;

        return day + " D " + hr + " HR " + min + " MIN " + sec + " SEC";
    }

    public static boolean isOnCooldown(JSONObject userData, MessageReceivedEvent e, int[] cooldown, String cmd)
    {
        String[] ts = userData.getString("timestamp_" + cmd).split("\\s");

        //Day, Hour, Minute, Second
        int[] currentTime = {e.getMessage().getTimeCreated().getDayOfYear(), e.getMessage().getTimeCreated().getHour(), e.getMessage().getTimeCreated().getMinute(), e.getMessage().getTimeCreated().getSecond()};
        int[] timestampTime = {Integer.parseInt(ts[2]), Integer.parseInt(ts[3]), Integer.parseInt(ts[4]), Integer.parseInt(ts[5])};

        //System.out.println("Current Time: " + Arrays.toString(currentTime));
        //System.out.println("Timestamp (" + e.getAuthor().getName() + "): " + Arrays.toString(timestampTime));

        int currentTimeSEC = currentTime[0] * 3600 * 24 + currentTime[1] * 3600 + currentTime[2] * 60 + currentTime[3];
        int timestampSEC = timestampTime[0] * 3600 * 24 + timestampTime[1] * 3600 + timestampTime[2] * 60 + timestampTime[3];

        int difference = cooldown[0] * 3600 * 24 + cooldown[1] * 3600 + cooldown[2] * 60 + cooldown[3];

        return difference >= (currentTimeSEC - timestampSEC);
    }
}
