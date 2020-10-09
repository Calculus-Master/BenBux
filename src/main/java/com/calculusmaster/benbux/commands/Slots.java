package com.calculusmaster.benbux.commands;

import com.calculusmaster.benbux.commands.util.CooldownCommand;
import com.calculusmaster.benbux.commands.util.GenericResponses;
import com.calculusmaster.benbux.util.Global;
import com.calculusmaster.benbux.util.Mongo;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;
import java.util.stream.Collectors;

public class Slots extends CooldownCommand
{
    private List<SlotLevel> levels = new ArrayList<>();

    public Slots(MessageReceivedEvent event, String[] msg)
    {
        super(event, msg, "slots", Global.CMD_SLOTS_COOLDOWN);

        levels.add(new SlotLevel(1, 250, 3000));
        levels.add(new SlotLevel(2, 1000, 10000));
        levels.add(new SlotLevel(3, 2500, 50000));
    }

    @Override
    protected void runLogic()
    {
        if(this.msg.length != 2)
        {
            this.embed = GenericResponses.invalid(this.user);
            return;
        }

        StringBuilder response = new StringBuilder();

        if(this.msg[1].toLowerCase().equals("info"))
        {
            for(SlotLevel l : this.levels) response.append("**Level ").append(l.level).append(":** Costs **").append(l.cost).append("** BenBux, with **").append(l.slots).append("** Slots and a Jackpot of **").append(l.jackpot).append("** BenBux!\n");
        }
        else if(!this.msg[1].chars().allMatch(Character::isDigit) || !isValidSlotLevel(Integer.parseInt(this.msg[1])) || this.userData.getInt("benbux") + this.userData.getInt("bank") < this.getSlotLevel(Integer.parseInt(this.msg[1])).cost)
        {
            this.embed = GenericResponses.invalid(this.user);
        }
        else
        {
            SlotLevel level = this.getSlotLevel(Integer.parseInt(this.msg[1]));

            int[] rollResults = level.roll();
            int numMatches = level.numMatches(rollResults);
            int mode = level.getMode(rollResults);
            int earnings = level.valueEarned(rollResults);

            Mongo.changeUserBalance(this.userData, this.user, earnings - level.cost);

            response.append("**Slots** ").append("(Level: **").append(level.level).append("**, Jackpot: **").append(level.jackpot).append("**, Cost: **").append(level.cost).append("**):\n**[ ");
            for(int res : rollResults) response.append(res).append(" - ");
            response.delete(response.length() - 2, response.length()).append("]**");
            response.append("\nChecking for Matches to ").append(mode).append("!");
            response.append("\nNumber of Matches: ").append(numMatches).append("\n Earned **").append(earnings).append("** BenBux!");
        }

        this.embed.setTitle(this.msg[1].toLowerCase().equals("info") ? "**Slot Machine Levels:** \n" : this.user.getAsTag());
        this.embed.setDescription(response.toString());
    }

    private boolean isValidSlotLevel(int lvl)
    {
        return this.levels.stream().filter(L -> L.level == lvl).count() == 1;
    }

    private SlotLevel getSlotLevel(int lvl)
    {
        return this.levels.stream().filter(L -> L.level == lvl).collect(Collectors.toList()).get(0);
    }

    public static class SlotLevel
    {
        protected int level;
        protected int cost;
        protected int jackpot;
        protected int slots;

        public SlotLevel(int level, int cost, int jackpot)
        {
            this.level = level;
            this.cost = cost;
            this.jackpot = jackpot;
            this.slots = 3 + 2 * (level - 1);
        }

        public int[] roll()
        {
            int[] rollResults = new int[this.slots];

            for(int i = 0; i < this.slots; i++) rollResults[i] = new Random().nextInt(10);

            return rollResults;
        }

        public int numMatches(int[] rollResults)
        {
            List<Integer> list = new ArrayList<>();
            for(int i = 0; i < rollResults.length; i++) list.add(rollResults[i]);

            return (int)list.stream().mapToInt(x -> x).filter(r -> r == this.getMode(rollResults)).count();
        }

        private int getMode(int[] arr)
        {
            Map<Integer, Integer> arrMap = new HashMap<>();
            for(int i : arr) arrMap.put(i, arrMap.containsKey(i) ? arrMap.get(i) + 1 : 1);

            int modeCount = 0;
            for(int i : arrMap.values()) if(i > modeCount) modeCount = i;

            for(int i : arrMap.keySet()) if(arrMap.get(i) == modeCount) return i;
            return -1;
        }

        public int valueEarned(int[] rollResults)
        {
            double num = this.numMatches(rollResults);

            return (int)(Math.pow(num / this.slots, 2) * this.jackpot);
        }
    }
}
