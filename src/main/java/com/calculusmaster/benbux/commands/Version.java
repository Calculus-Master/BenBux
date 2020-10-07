package com.calculusmaster.benbux.commands;

import com.calculusmaster.benbux.BenBux;
import com.calculusmaster.benbux.commands.util.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Version extends Command
{
    public Version(MessageReceivedEvent event, String[] msg)
    {
        super(event, msg);
    }

    @Override
    public Command runCommand()
    {
        this.embed.setTitle(this.user.getAsTag());
        this.embed.setDescription("Current Version: v" + BenBux.VERSION);
        return this;
    }
}
