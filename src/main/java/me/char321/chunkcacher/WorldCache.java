package me.char321.chunkcacher;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.level.LevelInfo;

import java.util.concurrent.CompletableFuture;

public class WorldCache {
    public static boolean isGenerating = false;
    public static LevelInfo lastGeneratorOptions = null;
    public static final Long2ObjectLinkedOpenHashMap<CompletableFuture<?>> cache = new Long2ObjectLinkedOpenHashMap<>();

    public static void addChunk(ChunkPos chunkPos, CompletableFuture<?> completableFuture) {
        cache.put(chunkPos.method_16281(), completableFuture);
    }

    public static boolean shouldCache() {
        return isGenerating;
    }

    public static CompletableFuture<?> getChunk(ChunkPos chunkPos) {
        return cache.get(chunkPos.method_16281());
    }

    /**
     * Checks if the generator options have changed, if so, clear the cache
     * dude github copilot is so cool it auto generated these comments
     */
    public static void checkGeneratorOptions(LevelInfo generatorOptions) {
        if (lastGeneratorOptions == null ||
                lastGeneratorOptions.getSeed() != generatorOptions.getSeed() ||
                lastGeneratorOptions.hasStructures() != generatorOptions.hasStructures() ||
                lastGeneratorOptions.hasBonusChest() != generatorOptions.hasBonusChest() ||
//TODO: different superflat presets for example are not detected, so the cache is not cleared and the world is not generated correctly
                lastGeneratorOptions.getGeneratorType() != generatorOptions.getGeneratorType()) {
            cache.clear();
            lastGeneratorOptions = generatorOptions;
        }
    }
}
