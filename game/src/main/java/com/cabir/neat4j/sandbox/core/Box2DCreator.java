package com.cabir.neat4j.sandbox.core;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DCreator {
    public Box2DCreator(World world, TiledMap tiledMap){



        for(MapObject object: tiledMap.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new Ground(world,tiledMap,rect,"ground");
        }
        for(MapObject object: tiledMap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new Cylinder(world,tiledMap,rect,"cylinder");
        }

    }
}
