package iunius.ccaddon.soundplayer;

import java.util.ArrayList;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockSoundPlayer extends BlockContainer {

	public BlockSoundPlayer(int i) {
		super(i, Material.rock);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getBlockTextureFromSide(int side) {
		switch (side) {
		case 0:
			return 1;

		case 1:
			return 2;

		default:
			return 0;
		}
	}

	public boolean blockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer) {
		if (par1World.isRemote) {
			return true;
		} else {
			return true;
		}
	}

	@Override
	public TileEntity getBlockEntity() {
		return new TileEntitySoundPlayer();
	}

	@Override
	public void addCreativeItems(ArrayList itemList) {
		itemList.add(new ItemStack(this));
	}

}
