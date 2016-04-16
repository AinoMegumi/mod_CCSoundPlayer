package iunius.ccaddon.soundplayer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import net.minecraft.src.Entity;
import net.minecraft.src.SoundManager;
import net.minecraft.src.SoundPoolEntry;
import net.minecraft.src.forge.ISoundHandler;

public class SoundHandler implements ISoundHandler {

	@Override
	public void onSetupAudio(SoundManager soundManager) {

	}

	@Override
	public void onLoadSoundSettings(SoundManager soundManager) {

	}

	@Override
	public SoundPoolEntry onPlayBackgroundMusic(SoundManager soundManager,
			SoundPoolEntry entry) {
		return entry;
	}

	@Override
	public SoundPoolEntry onPlayStreaming(SoundManager soundManager,
			SoundPoolEntry entry, String soundName, float x, float y, float z) {
		// サウンドがMinecraftのリソースでなかった場合の処理
		if (entry == null && soundName != null) {
			File file = null;
			String filename = null;
			URL url = null;

			try {
				file = new File(soundName);
				filename = file.getName();
				url = (file).toURI().toURL();
			} catch (MalformedURLException e) {
				e.printStackTrace();
				filename = soundName;
			}

			entry = new SoundPoolEntry(filename, url);
			System.out.println("[mod_CCSoundPlayer][SoundHandler] onPlayStreaming: " + entry.soundUrl.toString() + " "
					+ entry.soundName + " @ " + x + ", " + y + ", " + z);
		}

		return entry;
	}

	@Override
	public SoundPoolEntry onPlaySound(SoundManager soundManager,
			SoundPoolEntry entry, String soundName, float x, float y, float z,
			float volume, float pitch) {
		// サウンドがMinecraftのリソースでなかった場合の処理
		if (entry == null && soundName != null) {
			File file = null;
			String filename = null;
			URL url = null;

			try {
				file = new File(soundName);
				filename = file.getName();
				url = (file).toURI().toURL();
			} catch (MalformedURLException e) {
				e.printStackTrace();
				filename = soundName;
			}

			entry = new SoundPoolEntry(filename, url);
			System.out.println("[mod_CCSoundPlayer][SoundHandler] onPlaySound: " + entry.soundUrl.toString() + " "
					+ entry.soundName + " @ " + x + ", " + y + ", " + z);
		}

		return entry;
	}

	@Override
	public SoundPoolEntry onPlaySoundEffect(SoundManager soundManager,
			SoundPoolEntry entry, String soundName, float volume, float pitch) {
		return entry;
	}

	@Override
	public String onPlaySoundAtEntity(Entity entity, String soundName,
			float volume, float pitch) {
		return soundName;
	}

}
