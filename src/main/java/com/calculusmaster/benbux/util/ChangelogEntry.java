package com.calculusmaster.benbux.util;

import com.calculusmaster.benbux.BenBux;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChangelogEntry
{
    private String ver;
    private List<String> changes = new ArrayList<>();

    public ChangelogEntry(String ver)
    {
        this.ver = ver;
    }

    public ChangelogEntry addChange(String change)
    {
        changes.add(change);
        return this;
    }

    public String getFullChangelog()
    {
        StringBuilder changelog = new StringBuilder();
        for(String s : changes) changelog.append("- ").append(s).append("\n");
        return changelog.toString();
    }

    public String getVersion()
    {
        return this.ver;
    }

    public static String getLatest()
    {
        return Global.changelogs.stream().filter(cl -> cl.getVersion().equals(BenBux.VERSION.substring(0, 3))).collect(Collectors.toList()).get(0).getFullChangelog();
    }
}
