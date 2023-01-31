package com.joostd.kluisenaar;

import com.joostd.kluisenaar.commands.commandManager;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class Kluisenaar {
    private final ShardManager shardManager;

    public ShardManager getShardManager() {
        return shardManager;
    }

    public Kluisenaar(){
        String token = "token";
        DefaultShardManagerBuilder builder =  DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("je locker"));
        shardManager = builder.build();
        shardManager.addEventListener(new commandManager());
    }
    public static void main(String[] args){
        Kluisenaar bot = new Kluisenaar();

    }
}
