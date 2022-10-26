package dev.felnull.imp.server.music.ringer;

import dev.felnull.imp.networking.IMPPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MusicRingManager {
    private static final MusicRingManager INSTANCE = new MusicRingManager();
    private final Map<ServerLevel, MusicRing> MUSIC_RINGS = new HashMap<>();

    public static MusicRingManager getInstance() {
        return INSTANCE;
    }

    public void tick(ServerLevel level) {
        var ring = MUSIC_RINGS.get(level);
        if (ring == null) {
            ring = new MusicRing(level);
            MUSIC_RINGS.put(level, ring);
        }
        ring.tick();
    }

    public Map<ServerLevel, MusicRing> getMusicRingers() {
        return MUSIC_RINGS;
    }

    public void restartRinger(ServerLevel level, UUID uuid) {
        var ring = MUSIC_RINGS.get(level);
        if (ring != null)
            ring.restart(uuid);
    }

    public void addRinger(ServerLevel level, IMusicRinger ringer) {
        var ring = MUSIC_RINGS.get(level);
        if (ring != null)
            ring.addRinger(ringer);
    }

    public boolean isWaitRinger(UUID uuid, ServerLevel level) {
        var ring = MUSIC_RINGS.get(level);
        if (ring != null)
            return ring.isWaitRinger(uuid);
        return false;
    }

    public void onUpdate(ServerPlayer player, UUID uuid, UUID waitUUID, IMPPackets.MusicRingResponseStateType state) {
        var ring = MUSIC_RINGS.get(player.getLevel());
        if (ring != null)
            ring.onUpdate(player, uuid, waitUUID, state);
    }

    public void addReadyPlayer(ServerPlayer player, UUID uuid, UUID waitUUID, boolean result, boolean retry, long elapsed) {
        var ring = MUSIC_RINGS.get(player.getLevel());
        if (ring != null)
            ring.addReadyPlayer(player, uuid, waitUUID, result, retry, elapsed);
    }

    public MusicRing getMusicRing(ServerLevel level) {
        return getMusicRingers().get(level);
    }

    public boolean hasRinger(UUID uuid) {
        for (ServerLevel serverLevel : MUSIC_RINGS.keySet()) {
            if (hasRinger(serverLevel, uuid))
                return true;
        }
        return false;
    }

    public boolean hasRinger(ServerLevel level, UUID uuid) {
        var ring = MUSIC_RINGS.get(level);
        return ring != null && ring.hasRinger(uuid);
    }

    public IMusicRinger getRinger(UUID uuid) {
        for (ServerLevel serverLevel : MUSIC_RINGS.keySet()) {
            var mr = MUSIC_RINGS.get(serverLevel);
            if (mr != null) {
                var r = mr.getRingers().get(uuid);
                if (r != null)
                    return r;
            }
        }
        return null;
    }

    @Nullable
    public ServerLevel getLevel(MusicRing ring) {
        for (Map.Entry<ServerLevel, MusicRing> entry : MUSIC_RINGS.entrySet()) {
            if (entry.getValue() == ring)
                return entry.getKey();
        }
        return null;
    }

    public void pause() {
        MUSIC_RINGS.forEach((n, m) -> m.pause());
    }

    public void resume() {
        MUSIC_RINGS.forEach((n, m) -> m.resume());
    }

    public void clear() {
        MUSIC_RINGS.forEach((n, m) -> m.depose());
        MUSIC_RINGS.clear();
    }
}
