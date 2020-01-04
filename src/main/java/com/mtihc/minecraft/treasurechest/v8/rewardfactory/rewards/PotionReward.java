package com.mtihc.minecraft.treasurechest.v8.rewardfactory.rewards;

import com.mtihc.minecraft.treasurechest.v8.rewardfactory.IReward;
import com.mtihc.minecraft.treasurechest.v8.rewardfactory.RewardInfo;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class PotionReward implements IReward {

    private RewardInfo info;

    public PotionReward(PotionEffect effect) {
        this.info = new RewardInfo("potion", new HashMap<>());
        setEffect(effect);
    }

    PotionReward(RewardInfo info) {
        this.info = info;
    }

    @Override
    public RewardInfo getInfo() {
        return info;
    }

    public String getEffectName() {
        return (String) info.getData("type");
    }

    public PotionEffectType getEffectType() {
        return PotionEffectType.getByName(getEffectName());
    }

    public PotionEffect createEffect() {
        PotionEffectType type = getEffectType();
        int duration = (Integer) info.getData("duration");
        int amplifier = (Integer) info.getData("amplifier");
        // type.getDurationModifier() is allways 1.0
        // maybe in other versions this will change
        return type.createEffect((int) (duration / type.getDurationModifier()), amplifier);
    }

    public void setEffect(PotionEffect effect) {
        info.setData("type", effect.getType().getName());
        info.setData("duration", effect.getDuration());
        info.setData("amplifier", effect.getAmplifier());
    }

    @Override
    public String getDescription() {
        return "potion effect \"" + getEffectName() + "\"";
    }

    @Override
    public void give(Player player) {
        PotionEffect e = createEffect();
        player.addPotionEffect(e);
    }

}
