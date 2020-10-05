package com.calculusmaster.benbux;

import com.calculusmaster.benbux.util.Listener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class BenBux
{
    private static final String BOT_TOKEN = "NzYyMTc3NDE5OTc2OTAwNjA4.X3lXCQ.aEGEp3rgokDT41Dku2AVVyoaNXM";
    public static final String VERSION = "1.8";

    public static void main(String[] args) throws LoginException
    {
        JDABuilder builder = JDABuilder.createDefault(BOT_TOKEN);
        builder.setToken(BOT_TOKEN);
        builder.addEventListeners(new Listener());
        builder.setActivity(Activity.watching("the economy crash"));
        builder.build();
    }
}
