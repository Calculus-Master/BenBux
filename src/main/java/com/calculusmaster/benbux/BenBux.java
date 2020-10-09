package com.calculusmaster.benbux;

import com.calculusmaster.benbux.util.Listener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class BenBux
{
    public static final String VERSION = "2.2";
    public static final String DB_VERSION = "1.2";

    public static void main(String[] args) throws LoginException
    {
        JDABuilder builder = JDABuilder.createDefault(BotToken.BOT_TOKEN);
        builder.addEventListeners(new Listener());
        builder.setActivity(Activity.watching("the economy crash"));
        builder.build();
    }
}
