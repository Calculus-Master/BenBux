package com.calculusmaster.benbux.commands;

import com.calculusmaster.benbux.BenBux;
import com.calculusmaster.benbux.commands.util.Command;
import com.calculusmaster.benbux.commands.util.GenericResponses;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Changelog extends Command
{
    private static final ChangelogEntry CL_V1_0 = new ChangelogEntry("1.0").addChange("Initial Release").addChange("Added b!work");
    private static final ChangelogEntry CL_V1_1 = new ChangelogEntry("1.1").addChange("Added b!crime");
    private static final ChangelogEntry CL_V1_2 = new ChangelogEntry("1.2").addChange("Added b!bux");
    private static final ChangelogEntry CL_V1_3 = new ChangelogEntry("1.3").addChange("Split money into cash and bank").addChange("Added b!deposit").addChange("Added b!withdraw");
    private static final ChangelogEntry CL_V1_4 = new ChangelogEntry("1.4").addChange("Refactored code to allow for command aliases");
    private static final ChangelogEntry CL_V1_5 = new ChangelogEntry("1.5").addChange("Added b!leaderboard");
    private static final ChangelogEntry CL_V1_6 = new ChangelogEntry("1.6").addChange("Added b!pay (WIP)").addChange("Added new feature to b!bux that allows users to check another user's balance").addChange("Changed b!bux to an Embed showing more detailed info").addChange("Added b!changelog").addChange("Added b!version");
    private static final ChangelogEntry CL_V1_7 = new ChangelogEntry("1.7").addChange("Added feature to b!crime: If you successfully commit a crime, there is no cooldown").addChange("Slightly changed the formatting of the leaderboard").addChange("Restricted access to b!restart").addChange("Finished b!pay");
    private static final ChangelogEntry CL_V1_8 = new ChangelogEntry("1.8").addChange("Added embed messages for b!deposit and b!withdraw").addChange("Fixed b!pay").addChange("Fixed b!changelog for older versions").addChange("Changed cooldowns to Embeds").addChange("Added a ton of Invalid Command Embeds when needed").addChange("Changed misc responses to embeds");
    private static final ChangelogEntry CL_V1_9 = new ChangelogEntry("1.9").addChange("Added b!cock").addChange("Added b!shop (WIP)").addChange("Added b!bruh").addChange("Added b!dice gambling");
    private static final ChangelogEntry CL_V2_0 = new ChangelogEntry("2.0").addChange("Added cooldown to b!restart").addChange("Changed a few cooldowns").addChange("Converted all commands to objects").addChange("Added b!slots").addChange("Changed b!dice payouts");
    private static final ChangelogEntry CL_V2_1 = new ChangelogEntry("2.1").addChange("Fixed cooldowns being reapplied when the user inputs an invalid message");
    public static final List<ChangelogEntry> changelogs = Arrays.asList(CL_V1_0, CL_V1_1, CL_V1_2, CL_V1_3, CL_V1_4, CL_V1_5, CL_V1_6, CL_V1_7, CL_V1_8, CL_V1_9, CL_V2_0, CL_V2_1);

    public Changelog(MessageReceivedEvent event, String[] msg)
    {
        super(event, msg);
    }

    @Override
    public Command runCommand()
    {
        if(this.msg.length == 1 || this.msg[1].equals("latest")) this.embed.setDescription(ChangelogEntry.getLatest());
        else if(Changelog.changelogs.stream().noneMatch(cl -> cl.getVersion().equals(this.msg[1]))) this.embed = GenericResponses.invalid(this.user);
        else this.embed.setDescription(Changelog.changelogs.stream().filter(cl -> cl.getVersion().equals(this.msg[1])).collect(Collectors.toList()).get(0).getFullChangelog());

        this.embed.setTitle(this.user.getAsTag());
        return this;
    }

    public static class ChangelogEntry
    {
        private final String ver;
        private final List<String> changes;

        public ChangelogEntry(String ver)
        {
            this.ver = ver;
            this.changes = new ArrayList<>();
        }

        public ChangelogEntry addChange(String change)
        {
            this.changes.add(change);
            return this;
        }

        public String getFullChangelog()
        {
            StringBuilder changelog = new StringBuilder().append("**").append(this.ver).append(":**\n");
            for(String s : changes) changelog.append("- ").append(s).append("\n");
            return changelog.toString();
        }

        public String getVersion()
        {
            return this.ver;
        }

        public static String getLatest()
        {
            return Changelog.changelogs.stream().filter(cl -> cl.getVersion().equals(BenBux.VERSION.substring(0, 3))).collect(Collectors.toList()).get(0).getFullChangelog();
        }
    }
}
