package com.calculusmaster.benbux.util;

import com.calculusmaster.benbux.commands.*;
import com.calculusmaster.benbux.commands.util.Command;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.Random;

public class Listener extends ListenerAdapter
{
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event)
    {
        User user = event.getAuthor();
        if(user.isBot()) return;

        String[] msg = event.getMessage().getContentRaw().toLowerCase().trim().split("\\s+");
        JSONObject userData;
        Random r = new Random();

        if(msg[0].startsWith(Global.PREFIX))
        {
            msg[0] = msg[0].substring(Global.PREFIX.length()).toLowerCase();

            if(!Mongo.isRegistered(user))
            {
                Mongo.addUserData(user);
                Mongo.setInitialTimestamps(event);
            }

            userData = Mongo.UserInfo(user);
            Command c;

            if(!userData.getString("username").equals(user.getAsTag()))
            {
                Mongo.BenBuxDB.updateOne(Filters.eq("userID", user.getId()), Updates.set("username", user.getAsTag()));
                userData = Mongo.UserInfo(user);
            }

            //If any new database fields are added, update existing users here
            /*if(!userData.getString("ver").equals(BenBux.DB_VERSION))
            {
                System.out.println(userData.getString("username") + " has been updated to version " + BenBux.DB_VERSION + "!");
                this.onMessageReceived(event);
            }*/

            if(!userData.has("timestamp_slots"))
            {
                Mongo.updateTimestamp(user, "slots", event.getMessage().getTimeCreated().minusDays(2));
                this.onMessageReceived(event);
            }

            if(Global.CMD_WORK.contains(msg[0]) && msg.length == 1)
            {
                c = new Work(event, msg).runCommand();
            }
            else if(Global.CMD_CRIME.contains(msg[0]))
            {
                c = new Crime(event, msg).runCommand();
            }
            else if(Global.CMD_BAL.contains(msg[0]))
            {
                c = new Balance(event, msg).runCommand();
            }
            else if(Global.CMD_DEPOSIT.contains(msg[0]))
            {
                c = new Deposit(event, msg).runCommand();
            }
            else if(Global.CMD_WITHDRAW.contains(msg[0]))
            {
                c = new Withdraw(event, msg).runCommand();
            }
            else if(Global.CMD_LEADERBOARD.contains(msg[0]))
            {
                c = new Leaderboard(event, msg).runCommand();
            }
            else if(Global.CMD_STEAL.contains(msg[0]))
            {
                c = new Steal(event, msg).runCommand();
            }
            else if(Global.CMD_PAY.contains(msg[0]))
            {
                c = new Pay(event, msg).runCommand();
            }
            else if(Global.CMD_CHANGELOG.contains(msg[0]))
            {
                c = new Changelog(event, msg).runCommand();
            }
            else if(Global.CMD_VERSION.contains(msg[0]))
            {
                c = new Version(event, msg).runCommand();
            }
            else if(Global.CMD_PROST.contains(msg[0]))
            {
                c = new Prost(event, msg).runCommand();
            }
            else if(Global.CMD_SHOP.contains(msg[0]))
            {
                c = new Shop(event, msg).runCommand();
            }
            else if(Global.CMD_BRUH.contains(msg[0]))
            {
                c = new Bruh(event, msg).runCommand();
            }
            else if(Global.CMD_DICE.contains(msg[0]))
            {
                c = new Dice(event, msg).runCommand();
            }
            else if(Global.CMD_SLOTS.contains(msg[0]))
            {
                c = new Slots(event, msg).runCommand();
            }
            else if(msg[0].toLowerCase().equals(Global.CMD_RESTART))
            {
                c = new Restart(event, msg).runCommand();
            }
            else c = new Invalid(event).runCommand();

            event.getChannel().sendMessage(c.getResponseEmbed()).queue();
        }
    }

    private static MessageEmbed getReplyEmbed(String userTag, String msg)
    {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle(userTag);
        embed.setDescription(msg);
        embed.setColor(Global.getRandomColor());

        return embed.build();
    }

    public static String getUserTagFromMention(String mention)
    {
        return Mongo.BenBuxDB.find(Filters.eq("userID", getUserIDFromMention(mention))).first() != null ? Mongo.BenBuxDB.find(Filters.eq("userID", getUserIDFromMention(mention))).first().getString("username") : "";
    }

    public static String getUserIDFromMention(String mention)
    {
        return mention.substring(mention.indexOf("!") + 1, mention.lastIndexOf(">"));
    }
}
