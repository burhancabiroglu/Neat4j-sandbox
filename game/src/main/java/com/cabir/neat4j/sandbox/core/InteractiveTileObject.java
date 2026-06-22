package com.cabir.neat4j.sandbox.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected Rectangle rect;
    protected Body body;
    protected float ppm = 20f;
    protected String name;

    protected Fixture fixture;

    public InteractiveTileObject(World world,TiledMap TiledMap,Rectangle rect,String name){
        this.world = world;
        this.map = TiledMap;
        this.rect = rect;
        this.name = name;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type =BodyDef.BodyType.StaticBody;
        bodyDef.position.set((rect.getX()+rect.getWidth()/2)/ppm,(rect.y+rect.getHeight()/2)/ppm);

        body = world.createBody(bodyDef);
        shape.setAsBox((rect.getWidth()/2)/ppm,(rect.getHeight()/2)/ppm);
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(name);

    }
}
