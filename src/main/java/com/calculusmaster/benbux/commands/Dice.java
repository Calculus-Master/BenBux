package com.calculusmaster.benbux.commands;

import com.calculusmaster.benbux.commands.util.CooldownCommand;
import com.calculusmaster.benbux.commands.util.GenericResponses;
import com.calculusmaster.benbux.util.Global;
import com.calculusmaster.benbux.util.Mongo;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dice extends CooldownCommand
{
    public Dice(MessageReceivedEvent event, String[] msg)
    {
        super(event, msg, "dice", Global.CMD_DICE_COOLDOWN);
    }

    @Override
    protected void runLogic()
    {
        if(this.msg.length != 4 || (!this.msg[1].chars().allMatch(Character::isDigit) || !this.msg[2].chars().allMatch(Character::isDigit) || !this.msg[3].chars().allMatch(Character::isDigit)))
        {
            this.embed = GenericResponses.invalid(this.user);
            return;
        }

        //msg[1] is the wager, msg[2] is the guess, msg[3] is the number of dice
        int wager = Integer.parseInt(this.msg[1]);
        int guess = Integer.parseInt(this.msg[2]);
        int dice = Integer.parseInt(this.msg[3]);
        int earning = 0;

        if((wager < 0 || wager > this.userData.getInt("benbux") + this.userData.getInt("bank")) || (guess < 1 || guess > 6) || dice < 0)
        {
            this.embed = GenericResponses.invalid(this.user);
            return;
        }

        double specificWager = (double)wager / (double)dice;

        List<Double> accEarnings = new ArrayList<>();
        for(int i = 0; i < dice; i++) accEarnings.add(this.getSpecificEarning(specificWager, guess));
        earning = (int)accEarnings.stream().mapToDouble(x -> x).sum();
        int totalChange = earning - wager;

        Mongo.changeUserBalance(this.userData, this.user, totalChange);

        this.embed.setTitle(this.user.getAsTag());
        this.embed.setDescription("Earned " + earning + " BenBux with a wager of " + wager + " BenBux" + "\nNet " + (totalChange < 0 ? " Loss " : " Gain") + " of " + totalChange + " BenBux!");
    }

    private double getSpecificEarning(double specificWager, int guess)
    {
        int diceRoll = new Random().nextInt(6) + 1;
        int deviation = Math.abs(diceRoll - guess);

        switch(deviation)
        {
            case 0: return specificWager * 5.0;
            case 1: return specificWager * 3.0;
            case 2: return specificWager * 1.0;
            case 3: return specificWager * 0.5;
            case 4: return specificWager * -3.0;
            case 5: return specificWager * -5.0;
            default: return 0;
        }
    }
}
