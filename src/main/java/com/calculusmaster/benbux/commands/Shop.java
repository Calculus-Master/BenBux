package com.calculusmaster.benbux.commands;

import com.calculusmaster.benbux.commands.util.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Shop extends Command
{
    public Shop(MessageReceivedEvent event, String[] msg)
    {
        super(event, msg);
    }

    @Override
    public Command runCommand()
    {
        this.embed.setTitle("BenBux Shop");
        this.embed.setDescription("Unimplemented");
        /*
        Ideas:
            BenBux Booster: 1hr, 2hr, 6hr
            Bank Security Levels (in combination with bank robbery): 1, 2, 3, 4, 5
         */
        return this;
    }

}
