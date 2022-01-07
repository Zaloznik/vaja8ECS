package com.mygdx.game.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.ecs.component.AppleComponent;
import com.mygdx.game.ecs.component.BananaComponent;
import com.mygdx.game.ecs.component.BoundsComponent;
import com.mygdx.game.ecs.component.MonkeyComponent;
import com.mygdx.game.ecs.system.passive.SoundSystem;

public class CollisionSystem extends EntitySystem {

    private static final Family MONKEY_FAMILY = Family.all(MonkeyComponent.class, BoundsComponent.class).get();
    private static final Family APPLE_FAMILY = Family.all(AppleComponent.class, BoundsComponent.class).get();
    private static final Family BANANA_FAMILY = Family.all(BananaComponent.class, BoundsComponent.class).get();

    private SoundSystem soundSystem;

    public CollisionSystem() {
    }

    @Override
    public void addedToEngine(Engine engine) {
        soundSystem = engine.getSystem(SoundSystem.class);
    }

    @Override
    public void update(float deltaTime) {
        if (GameManager.INSTANCE.isGameOver()) return;

        ImmutableArray<Entity> monkeys = getEngine().getEntitiesFor(MONKEY_FAMILY);
        ImmutableArray<Entity> apples = getEngine().getEntitiesFor(APPLE_FAMILY);
        ImmutableArray<Entity> bananas = getEngine().getEntitiesFor(BANANA_FAMILY);

        for (Entity monkeyEntity : monkeys) {
            BoundsComponent monkeyBounds = Mappers.BOUNDS.get(monkeyEntity);

            // check collision with apples
            for (Entity appleEntity : apples) {
                AppleComponent apple = Mappers.APPLE.get(appleEntity);

                if (apple.hit) {
                    continue;
                }

                BoundsComponent appleBounds = Mappers.BOUNDS.get(appleEntity);

                if (Intersector.overlaps(monkeyBounds.rectangle, appleBounds.rectangle)) {
//                    apple.hit = true;
                    GameManager.INSTANCE.damage();
                    soundSystem.pick();
                }
            }

            // check collision with bananas
            for (Entity bananaEntity : bananas) {
                BananaComponent banana = Mappers.BANANAS.get(bananaEntity);

                if (banana.hit) {
                    continue;
                }

                BoundsComponent bananaBounds = Mappers.BOUNDS.get(bananaEntity);

                if (Intersector.overlaps(monkeyBounds.rectangle, bananaBounds.rectangle)) {
                    banana.hit = true;
                    GameManager.INSTANCE.incResult();
                    soundSystem.pick();
                    getEngine().removeEntity(bananaEntity);
                }
            }
        }
    }
}