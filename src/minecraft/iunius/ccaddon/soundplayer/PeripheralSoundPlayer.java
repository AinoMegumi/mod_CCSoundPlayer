package iunius.ccaddon.soundplayer;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;
import net.minecraft.src.SoundManager;
import net.minecraft.src.SoundPoolEntry;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.mod_CCSoundPlayer;
import net.minecraft.src.forge.ForgeHooksClient;
import paulscode.sound.SoundSystem;
import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;
import dan200.turtle.api.ITurtlePeripheral;
import dan200.turtle.api.TurtleSide;

public class PeripheralSoundPlayer implements ITurtlePeripheral {

	private World worldObj;
	private Vec3D pos;	// for getSourceName of TileEntity Peripheral
	private Vec3D posOld;
	private ITurtleAccess turtle;
	private TurtleSide side;
	private int computerID = -1;	// for getSourceName of Turtle Peripheral

	private float soundVolume = 1.0F;
	private float oldMusicVolume = ModLoader.getMinecraftInstance().gameSettings.musicVolume;

	public PeripheralSoundPlayer() {

	}

	public PeripheralSoundPlayer(ITurtleAccess turtleAccess, TurtleSide turtleSide) {
		this.turtle = turtleAccess;
		this.side = turtleSide;
		this.worldObj =  turtleAccess.getWorld();
		this.posOld = turtleAccess.getPosition();
	}

	public void setPosition(World world, int x, int y, int z) {
		this.worldObj = world;
		this.pos = Vec3D.createVectorHelper(x, y, z);
	}

	public Vec3D getPosition(){
		if (turtle != null) {	// Turtle Peripheral
			return this.turtle.getPosition();
		}

		return this.pos;
	}

	public float getVolume() {
		return this.soundVolume;
	}

	public void setVolume(float volume) {
		this.soundVolume = volume;
	}

	public void updateVolume() {
		float musicVolume = ModLoader.getMinecraftInstance().gameSettings.musicVolume;

		if (musicVolume != this.oldMusicVolume) {
			String source = getSourceName();
			SoundManager sndManager = ModLoader.getMinecraftInstance().sndManager;
			SoundSystem sndSystem = sndManager.getSoundSystem();
			sndSystem.setVolume(source, ModLoader.getMinecraftInstance().gameSettings.musicVolume
					* this.soundVolume);
			this.oldMusicVolume = musicVolume;
		}
	}

	private String getSourceName() {
		String str = "";

		if (this.turtle != null) {	// Turtle Peripheral
			str = "CCSP@turtle#" + this.computerID
					+ (this.side == TurtleSide.Left ? "L" : "R");

		} else if (this.pos != null) {	// TileEntity Peripheral
			str = "CCSP@" + (this.pos.xCoord >= 0 ? "+" + this.pos.xCoord : this.pos.xCoord)
					+ (this.pos.yCoord >= 0 ? "+" + this.pos.yCoord : this.pos.yCoord)
					+ (this.pos.zCoord >= 0 ? "+" + this.pos.zCoord : this.pos.zCoord);
		}

		return str;
	}

	private void play(String soundName, double x, double y, double z, int soundType, int isStreaming, float pitch)
			throws Exception {
		String name = soundName;

		if (soundType == 0) {
			String saveDir = this.worldObj.getSaveHandler().getSaveDirectoryName();
			File file = new File(Minecraft.getMinecraftDir(), "saves/" + saveDir + "/computer/" + soundName);
			try {
				name = file.getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (isStreaming == 0) {
			this.worldObj.playSoundEffect(x, y, z, name, 3.0F, pitch);
			return;
		}

		this.playStreaming(name, (float) x, (float) y, (float) z, 4.0F);
	}

	private void playStreaming(String soundName, float x, float y, float z, float range) throws Exception {
		String source = this.getSourceName();

		if (source.length() == 0) {
			return;
		}

		SoundManager sndManager = ModLoader.getMinecraftInstance().sndManager;
		SoundSystem sndSystem = sndManager.getSoundSystem();

		this.stopStreaming(sndSystem, source);

		SoundPoolEntry entry = sndManager.getStreamingPool().getRandomSoundFromSoundPool(soundName);

		if (entry == null) {
			entry = sndManager.getMusicPool().getRandomSoundFromSoundPool(soundName);
		}

		entry = ForgeHooksClient.onPlayStreaming(sndManager, entry, soundName, x, y, z);

		if (entry != null && range > 0.0F) {
			File file = new File(entry.soundUrl.toURI());

			if (!file.exists()) {
				System.out.println("[mod_CCSoundPlayer][PeripheralSoundPlayer] playStreaming: FILE NOT FOUND "
						+ entry.soundUrl.toString() + ", " + source);
				return;
			}

			if (sndSystem.playing("BgMusic")) {
				sndSystem.stop("BgMusic");
			}

			synchronized (mod_CCSoundPlayer.sourceNames) {
				sndSystem.newStreamingSource(true, source, entry.soundUrl, entry.soundName, false, x, y, z, 2,
						range * 16.0F);
				sndSystem.setVolume(source, ModLoader.getMinecraftInstance().gameSettings.musicVolume
						* this.getVolume());
				sndSystem.play(source);
				mod_CCSoundPlayer.sourceNames.add(source);
			}

			System.out.println("[mod_CCSoundPlayer][PeripheralSoundPlayer] playStreaming: PLAY "
					+ entry.soundUrl.toString() + ", " + source);
		}
	}

	private void stopStreaming(SoundSystem sndSystem, String source) {
		synchronized (mod_CCSoundPlayer.sourceNames) {
			if (sndSystem.playing(source)) {
				sndSystem.stop(source);
			}

			if (mod_CCSoundPlayer.sourceNames.contains(source)) {
				sndSystem.removeSource(source);
				mod_CCSoundPlayer.sourceNames.remove(source);
			}
		}
	}

	@Override
	public String getType() {
		return "soundPlayer";
	}

	@Override
	public String[] getMethodNames() {
		return new String[] {
				"play",
				"stop",
				"volume",
				"isPlaying" };
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, int method,
			Object[] arguments) throws Exception {
		SoundManager sndManager = ModLoader.getMinecraftInstance().sndManager;
		SoundSystem sndSystem = sndManager.getSoundSystem();
		String source = this.getSourceName();

		switch (method) {
		case 0: // string play( string, boolean, number, boolean )
			String soundName;
			int soundType = 0;
			int isStreaming = 0;
			float pitch = 1.0F;

			// soundName <- arguments[0]
			if (arguments.length < 1 || arguments[0] == null || !(arguments[0] instanceof String)) {
				throw new Exception("Expacted string, optional: boolean, number");
			}

			soundName = (String) arguments[0];

			// isStreaming <- arguments[1]
			if (arguments.length > 1 && arguments[1] != null && arguments[1] instanceof Boolean) {
				isStreaming = (byte) ((Boolean) arguments[1] ? 1 : 0);
			} else {
				isStreaming = 0;
			}

			// pitch/volume <- arguments[3]

			if (arguments.length > 2 && arguments[2] != null && arguments[2] instanceof Double) {
				float f = ((Double) arguments[2]).floatValue();

				if (isStreaming == 0) {
					// pitch
					if (f < 0.0F) {
						f = 0.0F;
					}

					if (f > 24.0F) {
						f = 24.0F;
					}

					pitch = (float) Math.pow(2.0D, (double) (f - 12) / 12.0D);
				} else {
					// volume
					if (f < 0.0F) {
						f = 0.0F;
					}

					if (f > 1.0F) {
						f = 1.0F;
					}

					this.soundVolume = f;
				}
			}

			// soundType <- arguments[3]
			if (arguments.length > 3 && arguments[3] != null && arguments[3] instanceof Boolean) {
				soundType = (byte) ((Boolean) arguments[3] ? 1 : 0);
			} else {
				soundType = 0;
			}

			if (soundType == 0 && soundName.length() > 0) {
				File path = new File(computer.getID() + "/" + (String) arguments[0]);
				String[] pathElements = path.getPath().split(Matcher.quoteReplacement(File.separator));
				String secureName = "";

				for (String s : pathElements) {
					if (!"..".equals(s)) {
						if (secureName.length() > 0) {
							secureName += File.separator + s;
						} else {
							secureName = s;
						}
					}
				}

				soundName = secureName;
			}

			Vec3D v = this.getPosition();

			if (v != null) {
				this.play(soundName, (double) v.xCoord + 0.5D, (double) v.yCoord + 0.5D, (double) v.zCoord + 0.5D,
						soundType, isStreaming, pitch);
				this.posOld = v;
			} else {
				soundName = null;
			}

			return new Object[] { soundName };

		case 1: // stop()
			this.stopStreaming(sndSystem, source);
			return null;

		case 2: // number volume( number )
			if (arguments.length > 0 && arguments[0] != null && arguments[0] instanceof Double) {
				float f = ((Double) arguments[0]).floatValue();

				if (f < 0.0F) {
					f = 0.0F;
				} else if (f > 1.0F) {
					f = 1.0F;
				}

				this.soundVolume = f;
			}

			sndSystem.setVolume(source, ModLoader.getMinecraftInstance().gameSettings.musicVolume * this.soundVolume);
			return new Object[] { Float.valueOf(this.soundVolume) };

		case 3: // boolean isPlaying()
			return new Object[] { Boolean.valueOf(sndSystem.playing(source)) };

		}

		return null;
	}

	@Override
	public boolean canAttachToSide(int side) {
		return true;
	}

	@Override
	public void attach(IComputerAccess computer, String computerSide) {
		this.computerID = computer.getID();
	}

	@Override
	public void detach(IComputerAccess computer) {
		SoundManager sndManager = ModLoader.getMinecraftInstance().sndManager;
		SoundSystem sndSystem = sndManager.getSoundSystem();
		String source = this.getSourceName();

		this.stopStreaming(sndSystem, source);
	}

	@Override
	public void update() {
		this.updateVolume();

		Vec3D v = turtle.getPosition();

		if (v.xCoord != this.posOld.xCoord || v.yCoord != this.posOld.yCoord || v.zCoord != this.posOld.zCoord) {
			SoundManager sndManager = ModLoader.getMinecraftInstance().sndManager;
			SoundSystem sndSystem = sndManager.getSoundSystem();
			String source = this.getSourceName();

			if (sndSystem.playing(source)) {
				sndSystem.setPosition(source, (float)v.xCoord + 0.5F, (float)v.yCoord + 0.5F, (float)v.zCoord + 0.5F);
			}

			this.posOld = v;
		}


	}

}
