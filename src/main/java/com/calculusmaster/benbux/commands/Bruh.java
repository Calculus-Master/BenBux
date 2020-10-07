package com.calculusmaster.benbux.commands;

import com.calculusmaster.benbux.commands.util.Command;
import com.calculusmaster.benbux.util.Mongo;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class Bruh extends Command
{
    public Bruh(MessageReceivedEvent event, String[] msg)
    {
        super(event, msg);
    }

    @Override
    public Command runCommand()
    {
        String bruh;
        int r = new Random().nextInt(10000);

        if(r == new Random().nextInt(10000)) bruh = "BRUH BRUH BRUH BRUH POG";
        else if(r == 69 || r == 420 || r == 690) bruh = "BRUH BRUH BRUH";
        else if(r % 13 == 0) bruh = "BRUH BRUH";
        else bruh = "bruh";

        if(!bruh.equals("bruh")) Mongo.changeUserBalance(this.userData, this.user, (int)Math.pow(10, bruh.split("\\s").length - 1) - 1);

        this.embed.setTitle(this.user.getAsTag());
        this.embed.setDescription(bruh);
        return this;
    }
}
