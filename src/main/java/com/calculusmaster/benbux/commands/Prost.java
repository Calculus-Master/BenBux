package com.calculusmaster.benbux.commands;

import com.calculusmaster.benbux.commands.util.CooldownCommand;
import com.calculusmaster.benbux.util.Global;
import com.calculusmaster.benbux.util.Mongo;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class Prost extends CooldownCommand
{
    public Prost(MessageReceivedEvent event, String[] msg)
    {
        super(event, msg, "prost", Global.CMD_PROST_COOLDOWN);
    }

    @Override
    protected void runLogic()
    {
        double rating = (new Random().nextInt(101)) / 10.0;
        double earnings = (rating / 5.0) * Math.pow(this.userData.getInt("benbux") + this.userData.getInt("bank"), rating / 10.0);
        Mongo.changeUserBalance(this.userData, this.user, (int)earnings);

        this.embed.setTitle(this.user.getAsTag());
        this.embed.setDescription("Your c0ck received a rating of **" + rating + "**.\n Earned **" + (int)earnings + "** BenBux");
    }
}
