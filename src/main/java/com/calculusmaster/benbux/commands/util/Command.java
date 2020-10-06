package com.calculusmaster.benbux.commands.util;

import com.calculusmaster.benbux.util.Mongo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONObject;

public abstract class Command
{
    protected MessageReceivedEvent event;
    protected JSONObject userData;
    protected User user;
    protected String[] msg;

    protected EmbedBuilder embed;
    protected boolean isInit;

    public Command(MessageReceivedEvent event, String[] msg)
    {
        this.event = event;
        this.userData = Mongo.UserInfo(event.getAuthor());
        this.user = event.getAuthor();
        this.msg = msg;

        this.embed = new EmbedBuilder();
        this.isInit = false;
    }

    public abstract Command runCommand();

    public MessageEmbed getResponseEmbed()
    {
        return this.embed.build();
    }
}
