package com.calculusmaster.benbux.commands;

import com.calculusmaster.benbux.util.Global;
import com.calculusmaster.benbux.util.Mongo;
import com.mongodb.client.model.Filters;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.*;
import java.util.List;

public class Leaderboard
{
    public static MessageEmbed getLeaderboard()
    {
        EmbedBuilder leaderboard = new EmbedBuilder();
        Map<Integer, String> userMap = new HashMap<>();
        Mongo.BenBuxDB.find(Filters.exists("userID")).forEach(d -> userMap.put(d.getInteger("benbux") + d.getInteger("bank"), d.getString("username")));
        StringBuilder lbDesc = new StringBuilder();

        int[] userSort = sortedBalances(userMap.keySet());

        for(int i = 0; i < 10; i++)
        {
            if(i >= userSort.length) break;
            else lbDesc.append("`").append(i + 1).append("`. ").append(userMap.get(userSort[i])).append(" - *Net Worth:* **").append(userSort[i]).append(" BenBux**\n");
        }

        leaderboard.setTitle("BenBux Leaderboard: ");
        leaderboard.setDescription(lbDesc.toString());
        leaderboard.setColor(Global.getRandomColor());
        return leaderboard.build();
    }

    private static int[] sortedBalances(Set<Integer> s)
    {
        List<Integer> balances = new ArrayList<>(s);
        balances.sort(Collections.reverseOrder());
        System.out.println(balances.toString());

        int[] sortedArray = new int[balances.size()];
        for(int i = 0; i < balances.size(); i++) sortedArray[i] = balances.get(i);
        return sortedArray;
    }
}
