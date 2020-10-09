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

        boolean isJaran = this.user.getIdLong() == 423602074811367434L;

        int earnedAmount = r.nextInt(Global.MAX_WORK_AMOUNT);
        Mongo.changeUserBalance(this.userData, this.user, isJaran ? -1 * earnedAmount : earnedAmount);
        response = "Earned " + (isJaran ? -1 * earnedAmount : earnedAmount) + " BenBux!";

        this.embed.setTitle(this.user.getAsTag());
        this.embed.setDescription(response);
    }
}
