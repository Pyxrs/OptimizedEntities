package com.simplycmd.optimized_entities.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simplycmd.optimized_entities.Main;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
	@Inject(at = @At("HEAD"), method = "tickEntity", cancellable = true)
	private void tickEntity(Entity entity, CallbackInfo info) {
		var dist = 100000.0;
		for (var player : ((ServerWorld) (Object) this).getPlayers()) {
			var e = squaredHorizontalDistanceTo(player, entity);
			if (e < dist) dist = e;
		}
		if (Main.optimize)
			if (entity instanceof MobEntity e) {
				final var random = Main.RANDOM.nextFloat();
				if (e.isPersistent() || e instanceof AnimalEntity) {
					if (random < MathHelper.clamp(dist / (Main.factor * 1000.0), Main.min, Main.max)) {
						info.cancel();
					}
				} else {
					if (random < MathHelper.clamp(dist / (Main.factor * 1000.0), Main.min, Main.max / 2)) {
						info.cancel();
					}
				}
			}
	}

	private static double squaredHorizontalDistanceTo(Vec3d point1, Vec3d point2) {
		double d = point1.x - point2.x;
		double f = point1.z - point2.z;
		return d * d + f * f;
	}

	private static double squaredHorizontalDistanceTo(Entity entity1, Entity entity2) {
		return squaredHorizontalDistanceTo(entity1.getPos(), entity2.getPos());
	}
}
