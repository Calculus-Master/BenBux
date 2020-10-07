package com.calculusmaster.benbux.commands;

import com.calculusmaster.benbux.commands.util.CooldownCommand;
import com.calculusmaster.benbux.util.Global;
import com.calculusmaster.benbux.util.Mongo;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class Work extends CooldownCommand
{
    public Work(MessageReceivedEvent event, String[] msg)
    {
        super(event, msg, "work", Global.CMD_WORK_COOLDOWN);
    }

    @Override
    protected void runLogic()
    {
        String response;
        Random r = new Random();

        int earnedAmount = r.nextInt(Global.MAX_WORK_AMOUNT);
        Mongo.changeUserBalance(userData, user, earnedAmount);
        response = "Earned " + earnedAmount + " BenBux!";

        this.embed.setTitle(this.user.getAsTag());
        this.embed.setDescription(response);
    }
}
