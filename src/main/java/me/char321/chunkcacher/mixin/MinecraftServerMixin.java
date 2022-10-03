package me.char321.chunkcacher.mixin;

import me.char321.chunkcacher.WorldCache;
import net.minecraft.class_4070;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.SaveHandler;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(method = "method_20316", at = @At("HEAD"))
    public void isGenerating(SaveHandler saveHandler, class_4070 arg, LevelProperties levelProperties, LevelInfo levelInfo, CallbackInfo ci) {
        WorldCache.isGenerating = true;

        WorldCache.checkGeneratorOptions(levelInfo);
    }

    @Inject(method = "method_20320", at = @At("TAIL"))
    public void isNotGenerating(CallbackInfo ci) {
        WorldCache.isGenerating = false;
    }
}
