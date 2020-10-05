package com.calculusmaster.benbux.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class Shop
{
    public static MessageEmbed getShop()
    {
        EmbedBuilder shop = new EmbedBuilder();

        shop.setTitle("BenBux Shop");
        shop.setDescription("Unimplemented");

        return shop.build();
    }
}
