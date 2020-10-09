package com.calculusmaster.benbux.commands;

import com.calculusmaster.benbux.commands.util.CooldownCommand;
import com.calculusmaster.benbux.commands.util.GenericResponses;
import com.calculusmaster.benbux.util.Global;
import com.calculusmaster.benbux.util.Listener;
import com.calculusmaster.benbux.util.Mongo;
import com.mongodb.client.model.Filters;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bson.Document;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Fight extends CooldownCommand
{
    List<String> fights = Arrays.asList(
            "defeated ? in battle",
            "cast a spell on ?",
            "beat ? in Fortnite",
            "ate ?",
            "was not the Imposter. ? was the Imposter",
            "tked ?",
            "caused ? to have fatal explosive diarrhea",
            "was less gay than ?"
    );

    public Fight(MessageReceivedEvent event, String[] msg)
    {
        super(event, msg, "fight", Global.CMD_FIGHT_COOLDOWN);
    }

    @Override
    protected void runLogic()
    {
        if(msg.length != 3)
        {
            this.embed = GenericResponses.invalid(this.user);
            return;
        }

        Document opponent = Mongo.BenBuxDB.find(Filters.eq("userID", Listener.getUserIDFromMention(this.msg[1]))).first();
        JSONObject opponentData = new JSONObject(opponent == null ? "" : opponent.toJson());

        if(opponent == null || opponentData.isEmpty() || !this.msg[2].chars().allMatch(Character::isDigit) || this.userData.getInt("benbux") + this.userData.getInt("bank") < Integer.parseInt(this.msg[2]) || Integer.parseInt(this.msg[2]) < 50)
        {
            this.embed = GenericResponses.invalid(this.user);
            return;
        }

        String chosenFight = this.fights.get(new Random().nextInt(this.fights.size()));
        boolean userWin = new Random().nextInt(2) == 1;
        String[] order = {userWin ? this.user.getAsTag() : opponentData.getString("username"), userWin ? opponentData.getString("username") : this.user.getAsTag()};

        if(userWin) Mongo.changeUserBalance(this.userData, this.user, Integer.parseInt(this.msg[2]) * 2);
        else
        {
            Mongo.changeUserBalance(this.userData, this.user, Integer.parseInt(this.msg[2]) * -1);
            Mongo.changeUserBalance(opponentData, opponentData.getString("userID"), Integer.parseInt(this.msg[2]));
        }

        String flavorText = order[0] + " " + chosenFight.replaceAll("\\?", order[1]);
        String earnText = userWin ? "You earned " + (2 * Integer.parseInt(this.msg[2])) + " BenBux! " : "You lost " + Integer.parseInt(this.msg[2]) + " BenBux! Instead, " + opponentData.getString("username") + " earned " + Integer.parseInt(this.msg[2]) + " BenBux!";

        this.embed.setTitle(user.getAsTag());
        this.embed.setDescription(flavorText + "!\n" + earnText);
    }
}
