package com.calculusmaster.benbux.commands;

import com.calculusmaster.benbux.commands.util.CooldownCommand;
import com.calculusmaster.benbux.commands.util.GenericResponses;
import com.calculusmaster.benbux.util.Global;
import com.calculusmaster.benbux.util.Listener;
import com.calculusmaster.benbux.util.Mongo;
import com.mongodb.client.model.Filters;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.util.Random;

public class Steal extends CooldownCommand
{
    public Steal(MessageReceivedEvent event, String[] msg)
    {
        super(event, msg, "steal", Global.CMD_STEAL_COOLDOWN);
    }

    @Override
    protected void runLogic()
    {
        if(this.msg.length != 2 || Listener.getUserIDFromMention(this.msg[1]).equals(this.user.getId()))
        {
            this.embed = GenericResponses.invalid(this.user);
            return;
        }

        String response;
        JSONObject victimData = new JSONObject(Mongo.BenBuxDB.find(Filters.eq("userID", Listener.getUserIDFromMention(this.msg[1]))).first().toJson());
        boolean canSteal = new Random().nextInt(100) < 40;

        if(canSteal && victimData.getInt("benbux") > 20)
        {
            int stolenAmount = new Random().nextInt(victimData.getInt("benbux"));
            response = "Stole " + stolenAmount + " BenBux from " + victimData.getString("username");
            Mongo.changeUserBalance(this.userData, this.user, stolenAmount);
            Mongo.changeUserBalance(victimData, Listener.getUserIDFromMention(this.msg[1]), stolenAmount * -1);
        }
        else
        {
            boolean lost = new Random().nextInt(5) < 3 && this.userData.getInt("benbux") + this.userData.getInt("bank") > 20;
            int lostAmount = 0;
            if(lost)
            {
                lostAmount = new Random().nextInt((this.userData.getInt("bank") + this.userData.getInt("benbux")) / 4) + 1;
                Mongo.changeUserBalance(this.userData, this.user, lostAmount * -1);
            }
            response = "Robbery failed! You lost " + (lost ? lostAmount + " BenBux :(" : "nothing :)");
        }

        this.embed.setTitle(this.user.getAsTag());
        this.embed.setDescription(response);
    }
}
