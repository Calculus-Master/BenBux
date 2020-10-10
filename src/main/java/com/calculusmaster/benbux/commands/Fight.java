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

        int winning = new Random(System.currentTimeMillis()).nextInt(Integer.parseInt(this.msg[2]));

        if(winning > Global.MAX_FIGHT_AMOUNT) winning = Global.MAX_FIGHT_AMOUNT;

        String flavorText;
        String earnText;

        if(userWin)
        {
            Mongo.changeUserBalance(this.userData, this.user, winning * 2);
            Mongo.changeUserBalance(opponentData, opponentData.getString("userID"), winning / -2);

            flavorText = this.user.getAsTag() + " " + chosenFight.replaceAll("\\?", opponentData.getString("username"));
            earnText = "You earned **" + (winning * 2) + "** BenBux!\n" + opponentData.getString("username") + " lost **" + (winning / 2) + "** BenBux!";
        }
        else
        {
            Mongo.changeUserBalance(this.userData, this.user, winning * -1);
            Mongo.changeUserBalance(opponentData, opponentData.getString("userID"), winning);

            flavorText = opponentData.getString("username") + " " + chosenFight.replaceAll("\\?", this.user.getAsTag());
            earnText = opponentData.getString("username") + " earned **" + winning + "** BenBux!\nYou lost **" + winning + "** BenBux!";
        }

        this.embed.setTitle(user.getAsTag());
        this.embed.setDescription(flavorText + "!\n" + earnText);
    }
}
