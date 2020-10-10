package com.calculusmaster.benbux.commands;

import com.calculusmaster.benbux.commands.util.Command;
import com.calculusmaster.benbux.commands.util.CooldownCommand;
import com.calculusmaster.benbux.commands.util.GenericResponses;
import com.calculusmaster.benbux.util.Global;
import com.calculusmaster.benbux.util.Mongo;
import com.calculusmaster.benbux.util.TimeUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class Crime extends CooldownCommand
{
    private int amount;

    public Crime(MessageReceivedEvent event, String[] msg)
    {
        super(event, msg, "crime", Global.CMD_CRIME_COOLDOWN);
    }

    @Override
    protected void runLogic()
    {
        Random r = new Random();
        String response;
        int amount = r.nextInt(Global.MAX_CRIME_AMOUNT) * (r.nextInt(10) < 6 ? -1 : 1);
        Mongo.changeUserBalance(this.userData, this.user, amount);

        this.amount = amount;
        response = (amount < 0 ? " Lost **" : " Earned **") + Math.abs(amount) + "** BenBux!";

        this.embed.setTitle(this.user.getAsTag());
        this.embed.setDescription(response);
    }

    @Override
    public Command runCommand()
    {
        if(!TimeUtils.isOnCooldown(this.userData, this.event, this.cooldown, this.cmdName))
        {
            this.runLogic();
            if(this.amount < 0) Mongo.updateTimestamp(this.user, this.cmdName, this.event.getMessage().getTimeCreated());
        }
        else this.embed = GenericResponses.onCooldown(this.user, this.userData, this.event, this.cooldown, this.cmdName);

        return this;
    }
}
