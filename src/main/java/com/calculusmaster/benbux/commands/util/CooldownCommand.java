package com.calculusmaster.benbux.commands.util;

import com.calculusmaster.benbux.util.Mongo;
import com.calculusmaster.benbux.util.TimeUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class CooldownCommand extends Command
{
    protected String cmdName;
    protected int[] cooldown;

    public CooldownCommand(MessageReceivedEvent event, String[] msg, String cmdName, int... cooldown)
    {
        super(event, msg);
        this.cmdName = cmdName;
        this.cooldown = cooldown;
    }

    @Override
    public Command runCommand()
    {
        if(!TimeUtils.isOnCooldown(this.userData, this.event, this.cooldown, this.cmdName))
        {
            this.runLogic();
            Mongo.updateTimestamp(this.user, this.cmdName, this.event.getMessage().getTimeCreated());
        }
        else this.embed = GenericResponses.onCooldown(this.user, this.userData, this.event, this.cooldown, this.cmdName);

        return this;
    }

    protected abstract void runLogic();
}
