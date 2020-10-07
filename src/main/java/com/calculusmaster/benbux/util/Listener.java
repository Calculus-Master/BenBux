package com.calculusmaster.benbux.util;

import com.calculusmaster.benbux.BenBux;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
            if(user.getIdLong() != 309135641453527040L) reply(event, getReplyEmbed(user.getAsTag(), "Not authorized to use the bot at this time"));

            msg[0] = msg[0].substring(Global.PREFIX.length()).toLowerCase();

            if(!Mongo.isRegistered(user))
            {
                Mongo.addUserData(user);
                Mongo.updateTimestamp(user, "work", event.getMessage().getTimeCreated().minusDays(Global.CMD_WORK_COOLDOWN[0] + 1));
                Mongo.updateTimestamp(user, "crime", event.getMessage().getTimeCreated().minusDays(Global.CMD_CRIME_COOLDOWN[0] + 1));
                Mongo.updateTimestamp(user, "steal", event.getMessage().getTimeCreated().minusDays(Global.CMD_STEAL_COOLDOWN[0] + 1));
                Mongo.updateTimestamp(user, "prost", event.getMessage().getTimeCreated().minusDays(Global.CMD_PROST_COOLDOWN[0] + 1));
                Mongo.updateTimestamp(user, "gamble_dice", event.getMessage().getTimeCreated().minusDays(Global.CMD_GAMBLE_DICE_COOLDOWN[0] + 1));
            }
            userData = Mongo.UserInfo(user);
            boolean isNoob = userData.getInt("benbux") == Global.STARTING_BALANCE;

            Command c;

            if(!userData.getString("username").equals(user.getAsTag()))
            {
                Mongo.BenBuxDB.updateOne(Filters.eq("userID", user.getId()), Updates.set("username", user.getAsTag()));
                userData = Mongo.UserInfo(user);
            }

            if(!userData.has("timestamp_steal"))
            {
                Mongo.updateTimestamp(user, "steal", event.getMessage().getTimeCreated().minusDays(Global.CMD_STEAL_COOLDOWN[0] + 1));
                reply(event, getReplyEmbed(user.getAsTag(), "Try that command again"));
                return;
            }

            if(!userData.has("timestamp_prost"))
            {
                Mongo.updateTimestamp(user, "prost", event.getMessage().getTimeCreated().minusDays(Global.CMD_PROST_COOLDOWN[0] + 1));
                reply(event, getReplyEmbed(user.getAsTag(), "Try that command again"));
                return;
            }

            if(!userData.has("timestamp_gamble_dice"))
            {
                Mongo.updateTimestamp(user, "gamble_dice", event.getMessage().getTimeCreated().minusDays(Global.CMD_GAMBLE_DICE_COOLDOWN[0] + 1));
                reply(event, getReplyEmbed(user.getAsTag(), "Try that command again"));
                return;
            }

            if(Global.CMD_WORK.contains(msg[0]) && msg.length == 1)
            {
                c = new Work(event, msg).runCommand();
                reply(event, c.getResponseEmbed());
            }
            else if(Global.CMD_CRIME.contains(msg[0]))
            {
                c = new Crime(event, msg).runCommand();
                reply(event, c.getResponseEmbed());
            }
            else if(Global.CMD_BAL.contains(msg[0]))
            {
                c = new Balance(event, msg).runCommand();
                reply(event, c.getResponseEmbed());
            }
            else if(Global.CMD_DEPOSIT.contains(msg[0]))
            {
                c = new Deposit(event, msg).runCommand();
                reply(event, c.getResponseEmbed());
            }
            else if(Global.CMD_WITHDRAW.contains(msg[0]))
            {
                c = new Withdraw(event, msg).runCommand();
                reply(event, c.getResponseEmbed());
            }
            else if(Global.CMD_LEADERBOARD.contains(msg[0]))
            {
                c = new Leaderboard(event, msg).runCommand();
                reply(event, c.getResponseEmbed());
            }
            else if(Global.CMD_STEAL.contains(msg[0]))
            {
                c = new Steal(event, msg).runCommand();
                reply(event, c.getResponseEmbed());
            }
            else if(Global.CMD_PAY.contains(msg[0]))
            {
                c = new Pay(event, msg).runCommand();
                reply(event, c.getResponseEmbed());
            }
            else if(Global.CMD_CHANGELOG.contains(msg[0]))
            {
                c = new Changelog(event, msg).runCommand();
                reply(event, c.getResponseEmbed());
            }
            else if(Global.CMD_VERSION.contains(msg[0]))
            {
                c = new Version(event, msg).runCommand();
                reply(event, c.getResponseEmbed());
            }
            else if(Global.CMD_PROST.contains(msg[0]))
            {
                c = new Prost(event, msg).runCommand();
                reply(event, c.getResponseEmbed());
            }
            else if(Global.CMD_SHOP.contains(msg[0]))
            {
                c = new Shop(event, msg).runCommand();
                reply(event, c.getResponseEmbed());
            }
            else if(Global.CMD_BRUH.contains(msg[0]))
            {
                c = new Bruh(event, msg).runCommand();
                reply(event, c.getResponseEmbed());
            }
            else if(Global.CMD_GAMBLE_DICE.contains(msg[0]))
            {
                if(isNoob || !TimeUtils.isOnCooldown(userData, event, Global.CMD_GAMBLE_DICE_COOLDOWN, "gamble_dice"))
                {
                    if(msg.length != 4)
                    {
                        reply(event, getReplyEmbed(user.getAsTag()));
                        return;
                    }
                    else if(!msg[1].chars().allMatch(Character::isDigit) || !msg[2].chars().allMatch(Character::isDigit) || !msg[3].chars().allMatch(Character::isDigit))
                    {
                        reply(event, getReplyEmbed(user.getAsTag()));
                        return;
                    }

                    //msg[1] is the wager, msg[2] is the guess, msg[3] is the number of dice
                    int wager = Integer.parseInt(msg[1]);
                    int guess = Integer.parseInt(msg[2]);
                    int dice = Integer.parseInt(msg[3]);
                    int earning = 0;

                    if((wager < 0 || wager > userData.getInt("benbux") + userData.getInt("bank")) || (guess < 1 || guess > 6) || dice < 0)
                    {
                        reply(event, getReplyEmbed(user.getAsTag()));
                        return;
                    }

                    List<Integer> deviations = new ArrayList<>();
                    for(int i = 0; i < dice; i++) deviations.add(Math.abs((new Random().nextInt(6) + 1) - guess));
                    double averageDeviation = deviations.stream().mapToInt(x -> x).sum() / (double)deviations.size();

                    if(averageDeviation == 0) averageDeviation = 1.0 / dice;
                    earning = (int)((1 / averageDeviation + 0.3) * wager);

                    int totalChange = earning - wager;

                    Mongo.changeUserBalance(userData, user, totalChange);

                    reply(event, getReplyEmbed(user.getAsTag(), "Earned " + earning + " BenBux with a wager of " + wager + " BenBux" + "\nNet " + (totalChange < 0 ? " Loss " : " Gain") + " of " + totalChange + " BenBux!"));
                    Mongo.updateTimestamp(user, "gamble_dice", event.getMessage().getTimeCreated());
                }
                else reply(event, getCooldownEmbed(user.getAsTag(), TimeUtils.timeLeft(userData, event, Global.CMD_GAMBLE_DICE_COOLDOWN, "gamble_dice")));
            }
            else if(msg[0].toLowerCase().equals(Global.CMD_RESTART) && Global.CAN_RESTART.contains(user.getId()))
            {
                Mongo.removeUser(user);
                reply(event, getReplyEmbed(user.getAsTag(), "You restarted!"));
            }
            else reply(event, getReplyEmbed(user.getAsTag()));
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

    private static MessageEmbed getCooldownEmbed(String userTag, String timeLeft)
    {
        return getReplyEmbed(userTag, "You can use this command again in\n" + timeLeft + "");
    }

    private static MessageEmbed getReplyEmbed(String userTag)
    {
        return getReplyEmbed(userTag, "Invalid Command");
    }

    private static void reply(MessageReceivedEvent e, MessageEmbed embed)
    {
        e.getChannel().sendMessage(embed).queue();
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
