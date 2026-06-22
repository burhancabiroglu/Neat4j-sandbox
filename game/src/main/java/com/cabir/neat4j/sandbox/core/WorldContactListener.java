package com.cabir.neat4j.sandbox.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

import static com.cabir.neat4j.sandbox.core.PlayScreen.*;


public class WorldContactListener implements ContactListener {
    static int generation = 0;
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();


        if(player.body.getPosition().y<=0f || player.body.getPosition().x>150){
            player.end = player.body.getPosition().x;
            player.isDead = true;

            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    if(counter<evoBrain.getPopulation().size()-1){
                        evoBrain.getPopulation().get(counter).fitness=(player.end/10)+player.score;
                        player.world.destroyBody(player.body);
                        System.out.println(counter+" . PLAYER dead.  Fitness: " +( evoBrain.getPopulation().get(counter).fitness));
                        counter++;
                        player.reset(evoBrain.getPopulation().get(counter));


                    }
                    else{
                        generation++;
                        System.out.println(generation+" . GENERATION created");
                        player.world.destroyBody(player.body);
                        evoBrain.createNewGenerations();
                        counter=0;
                        player.reset(evoBrain.getPopulation().get(counter));
                    }
                }
            });
        }





        if((fixA.getUserData().equals("ground")&&fixB.getUserData().equals("mario"))||(fixA.getUserData().equals("mario")&&fixB.getUserData().equals("ground"))){
            player.isOnGround = true;
        }

        if((fixA.getUserData().equals("mario")&&fixB.getUserData().equals("cylinder"))||(fixA.getUserData().equals("cylinder")&&fixB.getUserData().equals("mario"))){



            player.end = player.body.getPosition().x;
            player.isDead = true;

            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    if(counter<evoBrain.getPopulation().size()-1){
                        evoBrain.getPopulation().get(counter).fitness=player.end/10+player.score;
                        player.world.destroyBody(player.body);

                        System.out.println(counter+" . Mario dead.  Fitness: " +( evoBrain.getPopulation().get(counter).fitness));
                        counter++;

                        player.reset(evoBrain.getPopulation().get(counter));


                    }
                    else{
                        System.out.println("new generation created");
                        player.world.destroyBody(player.body);
                        evoBrain.createNewGenerations();
                        counter=0;
                        player.reset(evoBrain.getPopulation().get(counter));
                    }
                }
            });


        }


        if((fixA.getUserData().equals("bottom")&&fixB.getUserData().equals("cylinder"))||(fixA.getUserData().equals("cylinder")&&fixB.getUserData().equals("bottom"))){


                player.score+=5;

        }


        /*
        if((fixA.getUserData().equals("sensor")&&fixB.getUserData().equals("cylinder"))||(fixA.getUserData().equals("cylinder")&&fixB.getUserData().equals("sensor"))){
            Matrix m = player.brain.getOutput(new double[][]{{Math.abs(fixA.getBody().getPosition().x-fixB.getBody().getPosition().x)}});
            if(m.get(0,1)<m.get(0,0)&& player.isOnGround){
                Double res=m.get(0,0)-m.get(0,1);

                player.body.applyLinearImpulse(new Vector2(0, 40*res.floatValue()), player.body.getWorldCenter(), true);
                player.score+=(5*res);
            }
            m.show();
        }*/
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
