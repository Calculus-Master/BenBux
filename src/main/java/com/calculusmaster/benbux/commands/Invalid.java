package com.calculusmaster.benbux.commands;

import com.calculusmaster.benbux.commands.util.Command;
import com.calculusmaster.benbux.commands.util.GenericResponses;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Invalid extends Command
{
    public Invalid(MessageReceivedEvent event)
    {
        super(event, null);
    }

    @Override
    public Command runCommand()
    {
        this.embed = GenericResponses.invalid(this.user);
        return this;
    }
}
