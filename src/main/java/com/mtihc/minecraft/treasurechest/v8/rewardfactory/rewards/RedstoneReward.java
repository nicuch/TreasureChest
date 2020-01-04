package com.mtihc.minecraft.treasurechest.v8.rewardfactory.rewards;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.mtihc.minecraft.treasurechest.v8.rewardfactory.IReward;
import com.mtihc.minecraft.treasurechest.v8.rewardfactory.RewardException;
import com.mtihc.minecraft.treasurechest.v8.rewardfactory.RewardInfo;

public class RedstoneReward implements IReward {

    private RewardInfo info;

    public RedstoneReward(Block attachedBlock, BlockFace facing) {
        Map<String, Object> data = new HashMap<>();
        this.info = new RewardInfo("redstone", data);

        setAttachedBlock(attachedBlock.getLocation());
        setAttachedBlockType(attachedBlock.getType());
        setFacingDirection(facing);
    }

    RedstoneReward(RewardInfo info) {
        this.info = info;
    }

    @Override
    public RewardInfo getInfo() {
        return info;
    }

    public Location getAttachedBlock() {
        World world = Bukkit.getWorld((String) info.getData("world"));
        Vector coords = (Vector) info.getData("coords");
        return coords.toLocation(world);
    }

    public void setAttachedBlock(Location location) {
        info.setData("world", location.getWorld().getName());
        info.setData("coords", location.toVector());
    }

    public Material getAttachedBlockType() {
        return Material.getMaterial(info.getData("block-type").toString());
    }

    public void setAttachedBlockType(Material type) {
        info.setData("block-type", type);
    }

    public BlockFace getFacingDirection() {
        return BlockFace.valueOf((String) info.getData("facing"));
    }

    public void setFacingDirection(BlockFace facing) {
        info.setData("facing", facing.name());
    }

    @Override
    public String getDescription() {
        Location loc = getAttachedBlock();
        return "attach a redstone torch at " + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + " in " + loc.getWorld().getName();
    }

    @Override
    public void give(Player player) {
        Location loc = getAttachedBlock();
        BlockFace facing = getFacingDirection();
        Block attachedBlock = loc.getBlock();
        attachedBlock.setType(getAttachedBlockType());
        Block torchBlock = attachedBlock.getRelative(facing);
        torchBlock.setType(Material.REDSTONE_WALL_TORCH);
        Directional torch = (Directional) torchBlock.getBlockData();
        torch.setFacing(facing);
        torchBlock.setBlockData(torch);
    }

}
