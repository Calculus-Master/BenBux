package com.calculusmaster.benbux.commands;

import com.calculusmaster.benbux.commands.util.Command;
import com.calculusmaster.benbux.commands.util.GenericResponses;
import com.calculusmaster.benbux.util.Listener;
import com.calculusmaster.benbux.util.Mongo;
import com.mongodb.client.model.Filters;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONObject;

public class Pay extends Command
{
    public Pay(MessageReceivedEvent event, String[] msg)
    {
        super(event, msg);
    }

    @Override
    public Command runCommand()
    {
        if(this.msg.length != 3 || Listener.getUserIDFromMention(this.msg[1]).equals(this.user.getId()))
        {
            this.embed = GenericResponses.invalid(this.user);
            return this;
        }

        JSONObject receiverData = new JSONObject(Mongo.BenBuxDB.find(Filters.eq("userID", Listener.getUserIDFromMention(this.msg[1]))).first().toJson());
        String response;

        if(receiverData.isEmpty() || Integer.parseInt(this.msg[2]) <= 0 || Integer.parseInt(this.msg[2]) > this.userData.getInt("benbux") + this.userData.getInt("bank"))
        {
            this.embed = GenericResponses.invalid(this.user);
            return this;
        }
        else if(Integer.parseInt(this.msg[2]) > (this.userData.getInt("benbux") + this.userData.getInt("bank")) / 2)
        {
            response = "Cannot pay that much to " + Listener.getUserTagFromMention(this.msg[1]) + "!";
        }
        else
        {
            Mongo.changeUserBalance(this.userData, this.user, Integer.parseInt(this.msg[2]) * -1);
            Mongo.changeUserBalance(receiverData, Listener.getUserIDFromMention(this.msg[1]), Integer.parseInt(this.msg[2]));

            response = "Paid **" + this.msg[2] + " BenBux** to" + Listener.getUserTagFromMention(this.msg[1]) + "!";
        }

        this.embed.setTitle(this.user.getAsTag());
        this.embed.setDescription(response);
        return this;
    }
}
