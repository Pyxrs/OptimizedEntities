package com.simplycmd.optimized_entities;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanRule;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
	public static final String MOD_ID = "optimized_entities";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Random RANDOM = new Random();

	public static boolean optimize = true;
	public static float factor = 3.0f;
	public static float max = 1.0f;
	public static float min = 0.0f;

	public static final GameRules.Key<BooleanRule> OPTIMIZE_ENTITIES = GameRuleRegistry.register("optimizeEntities", GameRules.Category.MOBS,
		GameRuleFactory.createBooleanRule(optimize, (server, rule) -> optimize = rule.get()
	));
	public static final GameRules.Key<DoubleRule> OPTIMIZE_ENTITY_FALLOFF = GameRuleRegistry.register("optimizeEntityFalloff", GameRules.Category.MOBS,
		GameRuleFactory.createDoubleRule(factor, 0.0, 5.0, (server, rule) -> factor = (float) rule.get()
	));
	public static final GameRules.Key<DoubleRule> OPTIMIZE_ENTITY_MAX = GameRuleRegistry.register("optimizeEntityMax", GameRules.Category.MOBS,
		GameRuleFactory.createDoubleRule(max, 0.0, 1.0, (server, rule) -> max = (float) rule.get()
	));
	public static final GameRules.Key<DoubleRule> OPTIMIZE_ENTITY_MIN = GameRuleRegistry.register("optimizeEntityMin", GameRules.Category.MOBS,
		GameRuleFactory.createDoubleRule(min, 0.0, 1.0, (server, rule) -> min = (float) rule.get()
	));

	@Override
	public void onInitialize() {
		LOGGER.info("Optimized entities initializing.");
	}
}
