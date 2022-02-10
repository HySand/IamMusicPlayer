package dev.felnull.imp;

import dev.felnull.imp.block.IMPBlocks;
import dev.felnull.imp.blockentity.IMPBlockEntitys;
import dev.felnull.imp.inventory.IMPMenus;
import dev.felnull.imp.item.IMPItems;
import dev.felnull.imp.networking.IMPPackets;
import dev.felnull.imp.server.data.IMPWorldData;
import dev.felnull.imp.server.handler.ServerHandler;
import dev.felnull.imp.server.handler.ServerMusicHandler;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

public class IamMusicPlayer {
    public static final IMPConfig CONFIG = AutoConfig.register(IMPConfig.class, Toml4jConfigSerializer::new).getConfig();
    public static final String MODID = "iammusicplayer";

    public static void init() {
        IMPPackets.init();
        IMPItems.init();
        IMPBlocks.init();
        IMPBlockEntitys.init();
        IMPMenus.init();
        IMPWorldData.init();
        ServerMusicHandler.init();
        ServerHandler.init();
    }
}