package com.calculusmaster.benbux.util;

import com.calculusmaster.benbux.BenBux;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bson.Document;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Mongo
{
    //Setup
    private static final ConnectionString MongoDB_CONNECT = new ConnectionString("mongodb+srv://BenBuxCEO:BenBuxPOG@pokemondata.jemcp.gcp.mongodb.net/test");
    private static final MongoClientSettings MongoDB_SETTINGS = MongoClientSettings.builder()
            .applyConnectionString(MongoDB_CONNECT)
            .retryWrites(true)
            .build();
    private static final MongoClient MongoDB_CLIENT = MongoClients.create(MongoDB_SETTINGS);
    public static final MongoDatabase BENBUX = MongoDB_CLIENT.getDatabase("BenBux");

    public static final MongoCollection<Document> BenBuxDB = BENBUX.getCollection("UserData");

    public static JSONObject UserInfo(User u)
    {
        return new JSONObject(BenBuxDB.find(Filters.eq("userID", u.getId())).first().toJson());
    }

    public static boolean isRegistered(User u)
    {
        return BenBuxDB.find(Filters.eq("userID", u.getId())).first() != null;
    }

    public static void addUserData(User u)
    {
        Document userInfo = new Document("userID", u.getId()).append("ver", BenBux.DB_VERSION).append("benbux", Global.STARTING_BALANCE).append("bank", 0).append("username", u.getAsTag());
        BenBuxDB.insertOne(userInfo);
    }

    public static void setInitialTimestamps(MessageReceivedEvent event)
    {
        Mongo.updateTimestamp(event.getAuthor(), "work", event.getMessage().getTimeCreated().minusDays(Global.CMD_WORK_COOLDOWN[0] + 1));
        Mongo.updateTimestamp(event.getAuthor(), "crime", event.getMessage().getTimeCreated().minusDays(Global.CMD_CRIME_COOLDOWN[0] + 1));
        Mongo.updateTimestamp(event.getAuthor(), "steal", event.getMessage().getTimeCreated().minusDays(Global.CMD_STEAL_COOLDOWN[0] + 1));
        Mongo.updateTimestamp(event.getAuthor(), "prost", event.getMessage().getTimeCreated().minusDays(Global.CMD_PROST_COOLDOWN[0] + 1));
        Mongo.updateTimestamp(event.getAuthor(), "dice", event.getMessage().getTimeCreated().minusDays(Global.CMD_DICE_COOLDOWN[0] + 1));
    }

    public static void addItem(User user, String itemName)
    {
        BenBuxDB.updateOne(Filters.eq("userID", user.getId()), Updates.push("items", itemName));
    }

    public static void removeItem(User user, String itemName)
    {
        JSONObject j = new JSONObject(BenBuxDB.find(Filters.eq("userID", user.getId())).first().toJson());
        List<String> l = new ArrayList<>();

        for(int i = 0; i < j.getJSONArray("items").length(); i++) l.add(j.getJSONArray("items").getString(i));
        l.remove(itemName);

        BenBuxDB.updateOne(Filters.eq("userID", user.getId()), Updates.unset("items"));
        for (String s : l) addItem(user, s);
    }

    public static void removeUser(User u)
    {
        BenBuxDB.deleteOne(Filters.eq("userID", u.getId()));
    }

    public static void changeUserBalance(JSONObject info, User u, int amount)
    {
        BenBuxDB.updateOne(Filters.eq("userID", u.getId()), Updates.set("benbux", info.getInt("benbux") + amount));
    }

    public static void changeUserBalance(JSONObject info, String uID, int amount)
    {
        BenBuxDB.updateOne(Filters.eq("userID", uID), Updates.set("benbux", info.getInt("benbux") + amount));
    }

    public static void changeUserBank(JSONObject info, User u, int amount)
    {
        BenBuxDB.updateOne(Filters.eq("userID", u.getId()), Updates.set("bank", info.getInt("bank") + amount));
    }

    public static void depositBank(JSONObject info, User u, int amount)
    {
        changeUserBalance(info, u, amount * -1);
        changeUserBank(info, u, amount);
    }

    public static void withdrawBank(JSONObject info, User u, int amount)
    {
        changeUserBalance(info, u, amount);
        changeUserBank(info, u, amount * -1);
    }

    public static void updateTimestamp(User u, String cmd, OffsetDateTime time)
    {
        updateTimestamp(u, cmd, time.getYear(), time.getMonthValue(), time.getDayOfYear(), time.getHour(), time.getMinute(), time.getSecond(), time.getNano(), time.getOffset().getId());
    }

    public static void updateTimestamp(User u, String cmd, int yr, int mon, int day, int hr, int min, int sec, int nano, String offset)
    {
        String timestamp = yr + " " + mon + " " + day + " " + hr + " " + min + " " + sec + " " + nano + " " + offset;
        BenBuxDB.updateOne(Filters.eq("userID", u.getId()), Updates.set("timestamp_" + cmd.toLowerCase(), timestamp));
    }

}
