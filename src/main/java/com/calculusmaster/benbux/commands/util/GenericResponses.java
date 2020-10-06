package com.calculusmaster.benbux.commands.util;

import com.calculusmaster.benbux.util.TimeUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONObject;

public class GenericResponses
{
    public static EmbedBuilder invalid(User user)
    {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle(user.getAsTag());
        embed.setDescription("Invalid Command!");

        return embed;
    }

    public static EmbedBuilder onCooldown(User user, JSONObject userData, MessageReceivedEvent event, int[] cooldown, String cmd)
    {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle(user.getAsTag());
        embed.setDescription("You can use this command again in:\n" + TimeUtils.timeLeft(userData, event, cooldown, cmd));

        return embed;
    }
}
