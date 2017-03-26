package net.minecraft.src;

import java.util.HashSet;
import java.util.Set;

import dan200.turtle.api.TurtleAPI;
import iunius.ccaddon.soundplayer.BlockSoundPlayer;
import iunius.ccaddon.soundplayer.SaveEventHandler;
import iunius.ccaddon.soundplayer.SoundHandler;
import iunius.ccaddon.soundplayer.TileEntitySoundPlayer;
import iunius.ccaddon.soundplayer.TurtleSoundPlayer;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.MinecraftForgeClient;

public class mod_CCSoundPlayer extends BaseMod {

	@MLProp(info="The Turtle upgrade ID for Sound-Player Turtle", min=64, max=255, name="TurtleSoundPlayerID")
	public static int idTurtleSoundPlayer = 75;
	@MLProp(info="The Block ID for Sound Player Block", min=0, max=4095, name="SoundPlayerBlockID")
	public static int idBlockSoundPlayer = 1759;
	public static Block blockSoundPlayer;
	public final String blockTextureFilePath = "/terrain/ccsoundplayer.png";

	public static Set<String> sourceNames = new HashSet<String>();

	@Override
	public String getVersion() {
		return "0.2.2";
	}

	@Override
	public void load() {
		initBlock();
		MinecraftForgeClient.registerSoundHandler(new SoundHandler());
		MinecraftForge.registerSaveHandler(new SaveEventHandler());
		TurtleAPI.registerUpgrade(new TurtleSoundPlayer());
	}

	public void initBlock() {
		MinecraftForgeClient.preloadTexture(blockTextureFilePath);

		blockSoundPlayer = new BlockSoundPlayer(idBlockSoundPlayer).setBlockName("block_sound_player");
		blockSoundPlayer.setTextureFile(blockTextureFilePath);
		ModLoader.registerBlock(blockSoundPlayer);
		ModLoader.addName(blockSoundPlayer, "Sound Player Block");

		ModLoader.registerTileEntity(TileEntitySoundPlayer.class, "tile_sound_player");

		ModLoader.addRecipe(new ItemStack(blockSoundPlayer, 1), new Object[] { "SRS", "SNS", "SSS",
			Character.valueOf('S'), Block.stone,
			Character.valueOf('N'),Block.music,
			Character.valueOf('R'), Item.redstone});
	}

	@Override
	public String getPriorities() {
		return "after:mod_CCTurtle";
	}

}
