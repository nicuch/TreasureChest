package com.mtihc.minecraft.treasurechest.v8.rewardfactory.rewards;

import com.mtihc.minecraft.treasurechest.v8.rewardfactory.IReward;
import com.mtihc.minecraft.treasurechest.v8.rewardfactory.RewardInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class BroadcastReward implements IReward {

    private RewardInfo info;

    public BroadcastReward(List<String> messages) {
        this.info = new RewardInfo("broadcast", new HashMap<>());
        setMessages(messages);
    }

    BroadcastReward(RewardInfo info) {
        this.info = info;
    }

    @SuppressWarnings("unchecked")
    public List<String> getMessages() {
        return (List<String>) info.getData("messages");
    }

    public void setMessages(List<String> messages) {
        info.setData("messages", messages);
    }

    @Override
    public RewardInfo getInfo() {
        return info;
    }

    @Override
    public String getDescription() {
        return "broadcast a message";
    }

    @Override
    public void give(Player player) {
        List<String> msg = getMessages();
        String playerName = player.getDisplayName();
        for (String line : msg) {
            Bukkit.getServer().broadcastMessage(ChatColor.GOLD + line.replace("%player%", playerName + ChatColor.GOLD).replace("@p", playerName + ChatColor.GOLD));
        }
    }

}
