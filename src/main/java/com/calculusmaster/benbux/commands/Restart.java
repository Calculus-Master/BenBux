package com.calculusmaster.benbux.commands;

import com.calculusmaster.benbux.commands.util.CooldownCommand;
import com.calculusmaster.benbux.util.Global;
import com.calculusmaster.benbux.util.Mongo;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Restart extends CooldownCommand
{
    public Restart(MessageReceivedEvent event, String[] msg)
    {
        super(event, msg, "restart", Global.CMD_RESTART_COOLDOWN);
    }

    @Override
    protected void runLogic()
    {
        Mongo.removeUser(this.user);

        this.embed.setTitle(this.user.getAsTag());
        this.embed.setDescription("Successfully Restarted!\nStarting Balance: " + Global.STARTING_BALANCE);
    }
}
