package com.calculusmaster.benbux.commands;

import com.calculusmaster.benbux.commands.util.Command;
import com.calculusmaster.benbux.commands.util.GenericResponses;
import com.calculusmaster.benbux.util.Mongo;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Withdraw extends Command
{
    public Withdraw(MessageReceivedEvent event, String[] msg)
    {
        super(event, msg);
    }

    @Override
    public Command runCommand()
    {
        String response;

        if(msg.length != 2)
        {
            this.embed = GenericResponses.invalid(this.user);
            return this;
        }
        else if(this.userData.getInt("bank") == 0)
        {
            this.embed = GenericResponses.invalid(this.user);
            return this;
        }

        if(this.msg[1].toLowerCase().equals("all"))
        {
            response = "Withdrew all money from your bank! (**" + this.userData.getInt("bank") + "** BenBux)";
            Mongo.withdrawBank(this.userData, this.user, this.userData.getInt("bank"));
        }
        else if(this.msg[1].chars().allMatch(Character::isDigit) && Integer.parseInt(this.msg[1]) <= this.userData.getInt("bank") && Integer.parseInt(this.msg[1]) > 0)
        {
            response = "Withdrew **" + this.msg[1] + "** BenBux from your bank!";
            Mongo.withdrawBank(this.userData, this.user, Integer.parseInt(this.msg[1]));
        }
        else
        {
            this.embed = GenericResponses.invalid(this.user);
            return this;
        }

        this.embed.setTitle(this.user.getAsTag());
        this.embed.setDescription(response);
        return this;
    }
}
