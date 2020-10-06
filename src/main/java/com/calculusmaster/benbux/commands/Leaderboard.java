package com.calculusmaster.benbux.commands;

import com.calculusmaster.benbux.commands.util.Command;
import com.calculusmaster.benbux.util.Global;
import com.calculusmaster.benbux.util.Mongo;
import com.mongodb.client.model.Filters;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;

public class Leaderboard extends Command
{
    public Leaderboard(MessageReceivedEvent event, String[] msg)
    {
        super(event, msg);
    }

    @Override
    public Command runCommand()
    {
        Map<Integer, String> userMap = new HashMap<>();
        Mongo.BenBuxDB.find(Filters.exists("userID")).forEach(d -> userMap.put(d.getInteger("benbux") + d.getInteger("bank"), d.getString("username")));
        StringBuilder lbDesc = new StringBuilder();

        //int[] userSort = Leaderboard.sortedBalances(userMap.keySet());

        List<Integer> balances = new ArrayList<>(userMap.keySet());
        balances.sort(Collections.reverseOrder());
        //System.out.println(balances.toString());

        int[] userSort = new int[balances.size()];
        for(int i = 0; i < balances.size(); i++) userSort[i] = balances.get(i);

        for(int i = 0; i < 10; i++)
        {
            if(i >= userSort.length) break;
            else lbDesc.append("`").append(i + 1).append("`. ").append(userMap.get(userSort[i])).append(" - *Net Worth:* **").append(userSort[i]).append(" BenBux**\n");
        }

        this.embed.setTitle("BenBux Leaderboard: ");
        this.embed.setDescription(lbDesc.toString());
        this.embed.setColor(Global.getRandomColor());
        return this;
    }

    private static int[] sortedBalances(Set<Integer> s)
    {
        List<Integer> balances = new ArrayList<>(s);
        balances.sort(Collections.reverseOrder());
        //System.out.println(balances.toString());

        int[] sortedArray = new int[balances.size()];
        for(int i = 0; i < balances.size(); i++) sortedArray[i] = balances.get(i);
        return sortedArray;
    }
}
