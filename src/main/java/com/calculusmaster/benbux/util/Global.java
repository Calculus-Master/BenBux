package com.calculusmaster.benbux.util;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Global
{
    public static final int STARTING_BALANCE = 1000;
    public static final String PREFIX = "b!";

    public static final List<String> CMD_WORK = Arrays.asList("work", "w");
    public static final List<String> CMD_CRIME = Arrays.asList("crime", "c");
    public static final List<String> CMD_BAL = Arrays.asList("bux", "bal", "bank");
    public static final List<String> CMD_DEPOSIT = Arrays.asList("deposit", "dep", "d");
    public static final List<String> CMD_WITHDRAW = Arrays.asList("withdraw", "with", "w");
    public static final List<String> CMD_LEADERBOARD = Arrays.asList("leaderboard", "lb", "lead");
    public static final List<String> CMD_STEAL = Arrays.asList("rob", "steal", "s");
    public static final List<String> CMD_PAY = Arrays.asList("pay", "give", "donate", "p");
    public static final List<String> CMD_CHANGELOG = Arrays.asList("changelog", "changes");
    public static final List<String> CMD_VERSION = Arrays.asList("version", "ver", "v");
    public static final List<String> CMD_PROST = Arrays.asList("prostitute", "bjc", "cock");
    public static final List<String> CMD_SHOP = Arrays.asList("shop", "store");
    public static final List<String> CMD_BRUH = Collections.singletonList("bruh");
    public static final List<String> CMD_DICE = Collections.singletonList("dice");
    public static final List<String> CMD_SLOTS = Arrays.asList("slots", "slotmachine");
    public static final String CMD_RESTART = "restart";

    //Cooldowns are formatted as [0] = Day, [1] = Hour, [2] = Minute, [3] = Second
    public static final int[] CMD_WORK_COOLDOWN = {0, 1, 0, 0};
    public static final int[] CMD_CRIME_COOLDOWN = {0, 0, 30, 0};
    public static final int[] CMD_STEAL_COOLDOWN = {0, 2, 0, 0};
    public static final int[] CMD_PROST_COOLDOWN = {2, 0, 0, 0};
    public static final int[] CMD_DICE_COOLDOWN = {0, 3, 0, 0};
    public static final int[] CMD_SLOTS_COOLDOWN = {0, 2, 0, 0};
    public static final int[] CMD_RESTART_COOLDOWN = {3, 0, 0, 0};

    //Config Settings
    public static final int MAX_WORK_AMOUNT = 500;
    public static final int MAX_CRIME_AMOUNT = 1000;

    public static Color getRandomColor()
    {
        Random r = new Random();
        return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
    }
}
