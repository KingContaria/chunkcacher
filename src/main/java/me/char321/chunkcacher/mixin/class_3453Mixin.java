package me.char321.chunkcacher.mixin;

import me.char321.chunkcacher.WorldCache;
import net.minecraft.class_3452;
import net.minecraft.class_3455;
import net.minecraft.class_3786;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Mixin(class_3452.class_3453.class)
public class class_3453Mixin {

    @Shadow @Final private Object field_16646;

    @Inject(method = "method_15503", at = @At("HEAD"), cancellable = true)
    private void loadFromCache(class_3455<?, ?> chunkStatus, Map<?, CompletableFuture<?>> map, CompletableFuture<?> completableFuture, class_3455<?, ?> arg2, CallbackInfoReturnable<CompletableFuture<?>> cir) {
        if (WorldCache.shouldCache() && chunkStatus == class_3786.LIQUID_CARVED) {
            CompletableFuture<?> cachedFuture = WorldCache.getChunk((ChunkPos) this.field_16646);
            if (cachedFuture != null) {
                cir.setReturnValue(cachedFuture);
                cir.cancel();
            }
        }
    }

    @Inject(method = "method_15503", at = @At("TAIL"))
    private void addToCache(class_3455<?, ?> chunkStatus, Map<?, CompletableFuture<?>> map, CompletableFuture<?> completableFuture, class_3455<?, ?> arg2, CallbackInfoReturnable<CompletableFuture<?>> cir) {
        if (WorldCache.shouldCache() && completableFuture.isDone() && chunkStatus == class_3786.LIQUID_CARVED) {
            WorldCache.addChunk((ChunkPos) this.field_16646, cir.getReturnValue());
        }
    }
}
