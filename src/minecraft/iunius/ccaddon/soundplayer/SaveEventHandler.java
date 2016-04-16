package iunius.ccaddon.soundplayer;

import net.minecraft.src.Chunk;
import net.minecraft.src.GuiIngameMenu;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.SoundManager;
import net.minecraft.src.World;
import net.minecraft.src.mod_CCSoundPlayer;
import net.minecraft.src.forge.ISaveEventHandler;
import paulscode.sound.SoundSystem;

public class SaveEventHandler implements ISaveEventHandler {

	// Worldロード時とディメンション移動時にストリーミング再生を全停止・解放
	@Override
	public void onWorldLoad(World world) {
		SoundManager sndManager = ModLoader.getMinecraftInstance().sndManager;
		SoundSystem sndSystem = sndManager.getSoundSystem();
		System.out.println("[mod_CCSoundPlayer][SaveEventHandler] onWorldLoad: Source release");

		synchronized (mod_CCSoundPlayer.sourceNames) {
			for (String source : mod_CCSoundPlayer.sourceNames) {
				if (sndSystem.playing(source)) {
					sndSystem.stop(source);
				}

				sndSystem.removeSource(source);
			}

			mod_CCSoundPlayer.sourceNames.clear();
		}
	}

	// タイトル画面へ戻ったとき（ゲームメニュー画面からのワールドセーブ時）にストリーミング再生を全停止・解放
	@Override
	public void onWorldSave(World world) {
		if (ModLoader.getMinecraftInstance().currentScreen instanceof GuiIngameMenu) {
			SoundManager sndManager = ModLoader.getMinecraftInstance().sndManager;
			SoundSystem sndSystem = sndManager.getSoundSystem();
			System.out.println("[mod_CCSoundPlayer][SaveEventHandler] onWorldSave: Source release");

			synchronized (mod_CCSoundPlayer.sourceNames) {
				for (String source : mod_CCSoundPlayer.sourceNames) {
					if (sndSystem.playing(source)) {
						sndSystem.stop(source);
					}

					sndSystem.removeSource(source);
				}

				mod_CCSoundPlayer.sourceNames.clear();
			}
		}
	}

	@Override
	public void onChunkLoad(World world, Chunk chunk) {

	}

	@Override
	public void onChunkUnload(World world, Chunk chunk) {

	}

	@Override
	public void onChunkSaveData(World world, Chunk chunk, NBTTagCompound data) {

	}

	@Override
	public void onChunkLoadData(World world, Chunk chunk, NBTTagCompound data) {

	}

}
