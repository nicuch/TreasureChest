package com.mtihc.minecraft.treasurechest.v8.rewardfactory.rewards;

import com.mtihc.minecraft.treasurechest.v8.rewardfactory.IReward;
import com.mtihc.minecraft.treasurechest.v8.rewardfactory.RewardInfo;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class FlyReward implements IReward {

    private RewardInfo info;
    private FlyRewardFactory factory;

    public FlyReward(FlyRewardFactory factory, int seconds) {
        this.factory = factory;

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("seconds", seconds);

        this.info = new RewardInfo("fly", data);
    }

    protected FlyReward(FlyRewardFactory factory, RewardInfo info) {
        this.factory = factory;
        this.info = info;
    }

    public int getSeconds() {
        return (Integer) info.getData("seconds");
    }

    public void setSeconds(int value) {
        this.info.setData("seconds", value);
    }

    @Override
    public RewardInfo getInfo() {
        return info;
    }

    @Override
    public String getDescription() {
        return "fly for " + getSeconds() + " seconds";
    }

    @Override
    public void give(Player player) {
        factory.startFlight(player, this);
    }

}
