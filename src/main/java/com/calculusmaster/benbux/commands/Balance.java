package com.calculusmaster.benbux.commands;

import com.calculusmaster.benbux.commands.util.Command;
import com.calculusmaster.benbux.commands.util.GenericResponses;
import com.calculusmaster.benbux.util.Listener;
import com.calculusmaster.benbux.util.Mongo;
import com.mongodb.client.model.Filters;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONObject;

public class Balance extends Command
{
    public Balance(MessageReceivedEvent event, String[] msg)
    {
        super(event, msg);
    }

    @Override
    public Command runCommand()
    {
        JSONObject targetData = null;
        if(this.msg.length > 1) targetData = new JSONObject(Mongo.BenBuxDB.find(Filters.eq("userID", Listener.getUserIDFromMention(this.msg[1]))).first().toJson());

        if(this.msg.length > 1 && targetData.isEmpty())
        {
            this.embed = GenericResponses.invalid(this.user);
            return this;
        }

        int cash = this.msg.length > 1 ? targetData.getInt("benbux") : this.userData.getInt("benbux");
        int bank = this.msg.length > 1 ? targetData.getInt("bank") : this.userData.getInt("bank");

        this.embed.setTitle(this.msg.length > 1 ? targetData.getString("username") : this.user.getAsTag());
        this.embed.setDescription("Cash: **" + cash + "** BenBux\nBank: **" + bank + "** BenBux");
        return this;
    }
}
