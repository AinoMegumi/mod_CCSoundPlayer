package iunius.ccaddon.soundplayer;

import net.minecraft.src.ItemStack;
import net.minecraft.src.mod_CCSoundPlayer;
import dan200.turtle.api.ITurtleAccess;
import dan200.turtle.api.ITurtlePeripheral;
import dan200.turtle.api.ITurtleUpgrade;
import dan200.turtle.api.TurtleSide;
import dan200.turtle.api.TurtleUpgradeType;
import dan200.turtle.api.TurtleVerb;

public class TurtleSoundPlayer implements ITurtleUpgrade {

	public ItemStack craftingItem = new ItemStack(mod_CCSoundPlayer.blockSoundPlayer);

	@Override
	public int getUpgradeID() {
		return mod_CCSoundPlayer.idTurtleSoundPlayer;
	}

	@Override
	public String getAdjective() {
		return "Sound-Player";
	}

	@Override
	public TurtleUpgradeType getType() {
		return TurtleUpgradeType.Peripheral;
	}

	@Override
	public ItemStack getCraftingItem() {
		return craftingItem;
	}

	@Override
	public boolean isSecret() {
		return false;
	}

	@Override
	public String getIconTexture(ITurtleAccess turtle, TurtleSide side) {
		return craftingItem.getItem().getTextureFile();
	}

	@Override
	public int getIconIndex(ITurtleAccess turtle, TurtleSide side) {
		return craftingItem.getIconIndex();
	}

	@Override
	public ITurtlePeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
		return new PeripheralSoundPlayer(turtle, side);
	}

	@Override
	public boolean useTool(ITurtleAccess turtle, TurtleSide side, TurtleVerb verb, int direction) {
		return false;
	}

}
