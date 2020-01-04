package com.mtihc.minecraft.treasurechest.v8.core;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.ItemStack;

/**
 * Class representing two blocks with a single inventory. Like a double chest.
 * <p>The location is always the left side's location.</p>
 *
 * @author Mitch
 */
public class DoubleBlockInventory implements IBlockInventory {

    protected IBlockInventory leftSide;
    protected IBlockInventory rightSide;

    /**
     * Constructor
     *
     * @param doubleChest the double chest
     */
    public DoubleBlockInventory(DoubleChest doubleChest) {
        DoubleChestInventory inv = (DoubleChestInventory) doubleChest.getInventory();
        BlockState leftBlock = (BlockState) doubleChest.getLeftSide();
        BlockState rightBlock = (BlockState) doubleChest.getRightSide();
        leftSide = new BlockInventory(leftBlock.getLocation(), inv.getLeftSide());
        rightSide = new BlockInventory(rightBlock.getLocation(), inv.getRightSide());
    }

    /**
     * Deserialization constructor.
     *
     * @param values the serialized values
     */
    public DoubleBlockInventory(Map<String, Object> values) {
        this.leftSide = (IBlockInventory) values.get("left-side");
        this.rightSide = (IBlockInventory) values.get("right-side");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> values = new LinkedHashMap<>();
        values.put("left-side", getLeftSide());
        values.put("right-side", getRightSide());
        return values;
    }

    @Override
    public Location getLocation() {
        return getLeftSide().getLocation();
    }

    @Override
    public InventoryType getType() {
        return InventoryType.CHEST;
    }

    @Override
    public int getSize() {
        return InventoryType.CHEST.getDefaultSize() * 2;
    }

    @Override
    public ItemStack[] getContents() {
        int size = InventoryType.CHEST.getDefaultSize();

        ItemStack[] left = getLeftSide().getContents();
        ItemStack[] right = getRightSide().getContents();
        ItemStack[] combined = new ItemStack[size * 2];

        if (size >= 0) System.arraycopy(left, 0, combined, 0, size);
        if (size >= 0) System.arraycopy(right, 0, combined, size, size);

        return combined;
    }

    @Override
    public void setContents(ItemStack[] contents) {
        int size = InventoryType.CHEST.getDefaultSize();

        ItemStack[] left = new ItemStack[size];
        ItemStack[] right = new ItemStack[size];

        System.arraycopy(contents, 0, left, 0, size);
        System.arraycopy(contents, size, right, 0, size);

        getLeftSide().setContents(left);
        getRightSide().setContents(right);
    }

    /**
     * Returns the object that represents the block on the left side.
     *
     * @return the left side
     */
    public IBlockInventory getLeftSide() {
        return leftSide;
    }

    /**
     * Returns the object that represents the block on the right side.
     *
     * @return the right side
     */
    public IBlockInventory getRightSide() {
        return rightSide;
    }

}
