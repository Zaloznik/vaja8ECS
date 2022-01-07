package com.mygdx.game.ecs.system.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.ecs.component.AppleComponent;
import com.mygdx.game.ecs.component.BananaComponent;
import com.mygdx.game.ecs.component.BoundsComponent;
import com.mygdx.game.ecs.component.CleanUpComponent;
import com.mygdx.game.ecs.component.DimensionComponent;
import com.mygdx.game.ecs.component.MonkeyComponent;
import com.mygdx.game.ecs.component.MovementComponent;
import com.mygdx.game.ecs.component.PositionComponent;
import com.mygdx.game.ecs.component.TextureComponent;
import com.mygdx.game.ecs.component.WorldWrapComponent;
import com.mygdx.game.ecs.component.ZOrderComponent;


public class EntityFactorySystem extends EntitySystem {

    private static final int APPLE_Z_ORDER = 1;
    private static final int BANANA_Z_ORDER = 2;
    private static final int MONKEY_Z_ORDER = 3;

    private final AssetManager assetManager;

    private PooledEngine engine;
    private TextureAtlas gamePlayAtlas;

    public EntityFactorySystem(AssetManager assetManager) {
        this.assetManager = assetManager;
        setProcessing(false);   // passive system
        init();
    }

    private void init() {
        gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = (PooledEngine) engine;
    }

    public void createMonkey() {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = GameConfig.WIDTH / 2f - GameConfig.MONKEY_WIDTH / 2f;
        position.y = 20;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.MONKEY_WIDTH;
        dimension.height = GameConfig.MONKEY_HEIGHT;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        MovementComponent movement = engine.createComponent(MovementComponent.class);

        MonkeyComponent monkey = engine.createComponent(MonkeyComponent.class);

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.MONKEY);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = MONKEY_Z_ORDER;

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movement);
        entity.add(monkey);
        entity.add(worldWrap);
        entity.add(texture);
        entity.add(zOrder);
        engine.addEntity(entity);
    }

    public void createApple() {
        PositionComponent position = engine.createComponent(PositionComponent.class);

        position.x = MathUtils.random(0, GameConfig.WIDTH - GameConfig.APPLE_WIDTH);
        position.y = GameConfig.HEIGHT;

        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        movementComponent.xSpeed = -GameConfig.APPLE_SPEED_X_MIN * MathUtils.random(-1f, 1f);
        movementComponent.ySpeed = -GameConfig.APPLE_SPEED_X_MIN * MathUtils.random(1f, 2f);
        movementComponent.rSpeed = MathUtils.random(-1f, 1f);

        float randFactor = MathUtils.random(1f, 4f);
        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.APPLE_WIDTH * randFactor;
        dimension.height = GameConfig.APPLE_HEIGHT * randFactor;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        AppleComponent appleComponent = engine.createComponent(AppleComponent.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.APPLE);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = APPLE_Z_ORDER;

        // WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);

        CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movementComponent);
        entity.add(appleComponent);
        entity.add(texture);
        entity.add(zOrder);
        // entity.add(worldWrap);
        entity.add(cleanUp);
        engine.addEntity(entity);
    }

    public void createBanana() {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = MathUtils.random(0, GameConfig.WIDTH - GameConfig.BANANA_SIZE);
        position.y = GameConfig.HEIGHT;

        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        movementComponent.xSpeed = GameConfig.APPLE_SPEED_X_MIN * MathUtils.random(-0.1f, 0.1f);
        movementComponent.ySpeed = -GameConfig.APPLE_SPEED_X_MIN * MathUtils.random(1f, 2f);
        movementComponent.rSpeed = MathUtils.random(-1f, 1f);

        BananaComponent banana = engine.createComponent(BananaComponent.class);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.BANANA_SIZE;
        dimension.height = GameConfig.BANANA_SIZE;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.BANANA);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = BANANA_Z_ORDER;

        CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movementComponent);
        entity.add(banana);
        entity.add(texture);
        entity.add(zOrder);
        entity.add(cleanUp);
        engine.addEntity(entity);
    }
}
