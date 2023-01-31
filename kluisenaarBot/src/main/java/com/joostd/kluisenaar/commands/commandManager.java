package com.joostd.kluisenaar.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class commandManager extends ListenerAdapter {
    String[] owners = new String[]{"admin1", "admin#7", "admin#3"};
    String responseChanel;




    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {


        if(event.getName().equals("open")){
            User user = event.getUser();

            if(Arrays.toString(owners).contains(user.getAsTag())){ //if admin
                event.reply("Oke! admin: " + user.getName()).addActionRow(
                        Button.primary ("esp32", "Open")).queue(message -> message.deleteOriginal().queueAfter(15, TimeUnit.SECONDS));
                return;
            }else{ //not admin
                event.reply("You are not(yet) allowed to open lockers");

            }
        }

    }
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<CommandData>();
        commandData.add(Commands.slash("open", "opens locker"));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }


    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {

        if(Arrays.toString(owners).contains(event.getUser().getAsTag())){ // if admin
            event.getChannel().sendMessage("Opened: " + " " + openLocker(event.getComponentId())).queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

    }

    public static String openLocker(String locker)   {
        URL url = null;
        try {
            url = new URL("http://"+ locker);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            return e.toString();
        }


        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            return e.toString();
        }

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try {
            connection.getInputStream().read();
        } catch (IOException e) {
            return e.toString();
        }


// close the connection
        connection.disconnect();
        return locker;

    }
    public static void main(String[] args){
        System.out.println(openLocker("esp32"));

    }
}
