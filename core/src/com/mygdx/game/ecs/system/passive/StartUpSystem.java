package com.mygdx.game.ecs.system.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;

public class StartUpSystem extends EntitySystem {

    private com.mygdx.game.ecs.system.passive.EntityFactorySystem factory;

    public StartUpSystem() {
        setProcessing(false);   // it doesn't call update
    }

    @Override
    public void addedToEngine(Engine engine) {
        factory = engine.getSystem(com.mygdx.game.ecs.system.passive.EntityFactorySystem.class);
        onStartUp();
    }

    private void onStartUp() {
        factory.createApple();
        factory.createMonkey();
    }
}
