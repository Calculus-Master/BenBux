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
    public static final List<String> CMD_GAMBLE_DICE = Collections.singletonList("dice");
    public static final String CMD_RESTART = "restart";

    //Cooldowns are formatted as [0] = Day, [1] = Hour, [2] = Minute, [3] = Second
    public static final int[] CMD_WORK_COOLDOWN = {0, 1, 0, 0};
    public static final int[] CMD_CRIME_COOLDOWN = {0, 0, 30, 0};
    public static final int[] CMD_STEAL_COOLDOWN = {0, 2, 0, 0};
    public static final int[] CMD_PROST_COOLDOWN = {2, 0, 0, 0};
    public static final int[] CMD_GAMBLE_DICE_COOLDOWN = {0, 3, 0, 0};
    public static final int[] CMD_RESTART_COOLDOWN = {3, 0, 0, 0};

    public static final List<String> CAN_RESTART = Arrays.asList("762770750351999046");

    //Config Settings
    public static final int MAX_WORK_AMOUNT = 500;
    public static final int MAX_CRIME_AMOUNT = 1000;

    /*Changelogs
    private static final ChangelogEntry CL_V1_0 = new ChangelogEntry("1.0").addChange("Initial Release").addChange("Added b!work");
    private static final ChangelogEntry CL_V1_1 = new ChangelogEntry("1.1").addChange("Added b!crime");
    private static final ChangelogEntry CL_V1_2 = new ChangelogEntry("1.2").addChange("Added b!bux");
    private static final ChangelogEntry CL_V1_3 = new ChangelogEntry("1.3").addChange("Split money into cash and bank").addChange("Added b!deposit").addChange("Added b!withdraw");
    private static final ChangelogEntry CL_V1_4 = new ChangelogEntry("1.4").addChange("Refactored code to allow for command aliases");
    private static final ChangelogEntry CL_V1_5 = new ChangelogEntry("1.5").addChange("Added b!leaderboard");
    private static final ChangelogEntry CL_V1_6 = new ChangelogEntry("1.6").addChange("Added b!pay (WIP)").addChange("Added new feature to b!bux that allows users to check another user's balance").addChange("Changed b!bux to an Embed showing more detailed info").addChange("Added b!changelog").addChange("Added b!version");
    private static final ChangelogEntry CL_V1_7 = new ChangelogEntry("1.7").addChange("Added feature to b!crime: If you successfully commit a crime, there is no cooldown").addChange("Slightly changed the formatting of the leaderboard").addChange("Restricted access to b!restart").addChange("Finished b!pay");
    private static final ChangelogEntry CL_V1_8 = new ChangelogEntry("1.8").addChange("Added embed messages for b!deposit and b!withdraw").addChange("Fixed b!pay").addChange("Fixed b!changelog for older versions").addChange("Changed cooldowns to Embeds").addChange("Added a ton of Invalid Command Embeds when needed").addChange("Changed misc responses to embeds");
    private static final ChangelogEntry CL_V1_9 = new ChangelogEntry("1.9").addChange("Added b!cock").addChange("Added b!shop (WIP)").addChange("Added b!bruh").addChange("Added b!dice gambling");
    private static final ChangelogEntry CL_V2_0 = new ChangelogEntry("2.0").addChange("Added cooldown to b!restart").addChange("Changed a few cooldowns");
    public static final List<ChangelogEntry> changelogs = Arrays.asList(CL_V1_0, CL_V1_1, CL_V1_2, CL_V1_3, CL_V1_4, CL_V1_5, CL_V1_6, CL_V1_7, CL_V1_8, CL_V1_9);
    */
    public static Color getRandomColor()
    {
        Random r = new Random();
        return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
    }
}
