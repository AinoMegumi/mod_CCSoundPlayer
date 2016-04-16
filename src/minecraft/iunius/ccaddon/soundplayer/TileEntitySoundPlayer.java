package iunius.ccaddon.soundplayer;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IPeripheral;

public class TileEntitySoundPlayer extends TileEntity implements IPeripheral {

	PeripheralSoundPlayer soundPlayer = new PeripheralSoundPlayer();

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (this.soundPlayer.getPosition() == null) {
			this.soundPlayer.setPosition(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		}

		this.soundPlayer.updateVolume();
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);

		this.soundPlayer.setVolume(par1nbtTagCompound.getFloat("vol"));
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);

		par1nbtTagCompound.setFloat("vol", this.soundPlayer.getVolume());
	}

	@Override
	public String getType() {
		return this.soundPlayer.getType();
	}

	@Override
	public String[] getMethodNames() {
		return this.soundPlayer.getMethodNames();
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, int method, Object[] arguments) throws Exception {
		return this.soundPlayer.callMethod(computer, method, arguments);
	}

	@Override
	public boolean canAttachToSide(int side) {
		return this.soundPlayer.canAttachToSide(side);
	}

	@Override
	public void attach(IComputerAccess computer, String computerSide) {
		this.soundPlayer.attach(computer, computerSide);
	}

	@Override
	public void detach(IComputerAccess computer) {
		this.soundPlayer.detach(computer);
	}



}
