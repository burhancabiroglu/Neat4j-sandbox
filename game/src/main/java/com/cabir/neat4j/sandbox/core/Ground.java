package com.cabir.neat4j.sandbox.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Ground extends InteractiveTileObject {
    public Ground(World world, TiledMap TiledMap, Rectangle rect,String name) {
        super(world, TiledMap, rect,name);
    }
    public void isCollision(){
        System.out.println(super.name+" hit");
    }
}
