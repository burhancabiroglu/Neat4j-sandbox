package com.cabir.neat4j.sandbox.core;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.cabir.math.Matrix;
import com.cabir.network.NeatNetwork;


public class Mario extends Sprite{


    public double start;
    public double end;
    public double score;

    public enum State {FALLING,JUMPING,STANDING,RUNNING};
    public String marioName;
    public State currentState;
    public State previousState;
    public World world;
    public Body body;
    private PlayScreen screen;
    private TextureRegion marioStand;
    private Animation marioJump;
    private Animation marioRun;
    public boolean isOnGround = true;

    public boolean runningRight;

    public float stateTimer;

    public double fitness = 0;

    public boolean isDead = false;

    public NeatNetwork brain;



    public Mario(World world, PlayScreen screen, NeatNetwork brain, String marioName){
        super(screen.getAtlas().findRegion("little_mario"));
        this.brain = brain;
        this.marioName = marioName;
        this.world = world;
        this.screen = screen;
        start=0;
        score=0;
        end=0;
        currentState = State.STANDING;
        previousState = State.STANDING;

        stateTimer = 0;
        runningRight = true;


        Array<TextureRegion> frames = new Array<>();

        marioStand = new TextureRegion(getTexture(),1,11,16,16);

        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(),1+i*16,11,16,16));
        }

        marioRun = new Animation(0.1f,frames);
        frames.clear();
        for (int i = 4; i < 6; i++) {
            frames.add(new TextureRegion(getTexture(),1+i*16,11,16,16));

        }
        marioJump = new Animation(0.1f,frames);
        setBounds(0,0,1,1);
        setRegion(marioStand);
        defineMario();

    }
    public void update(float dt){
        if(!isDead){
            setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
            setRegion(getFrame(dt));

            // Always Run Feature
            if(body.getLinearVelocity().x<=7){
                body.applyLinearImpulse(new Vector2(5f,0),body.getWorldCenter(),true);
            }

        }
        world.rayCast(new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {

                    Matrix m = brain.forward(new Matrix(new double[][]{{Math.abs(fraction)}}));
                    if(m.get(0,1)<m.get(0,0)&& isOnGround){
                        double res=m.get(0,0)-m.get(0,1);
                        body.applyLinearImpulse(new Vector2(0,  (float) res*10),body.getWorldCenter(), true);
                    }
        return 0;
            }
        }, new Vector2(body.getPosition().x+1,body.getPosition().y+1), new Vector2(body.getPosition().x + 5, body.getPosition().y+1));

    }

    public TextureRegion getFrame(float delta){
        currentState = getState();

        TextureRegion region = new TextureRegion();

        switch (currentState){
            case JUMPING:
                region= (TextureRegion) marioJump.getKeyFrame(stateTimer);
                isOnGround = false;
                break;
            case RUNNING:
                region = (TextureRegion) marioRun.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
            case STANDING:
                region = marioStand;
        }
        if((body.getLinearVelocity().x<0|| !runningRight)&&!region.isFlipX()){
            region.flip(true,false);

            body.setTransform(new Vector2(body.getPosition().x,body.getPosition().y),3.102f);


            runningRight = false;
        }
        else  if((body.getLinearVelocity().x>0|| runningRight)&&region.isFlipX()){

            body.setTransform(new Vector2(body.getPosition().x,body.getPosition().y),0.02f);

            region.flip(true,false);

            runningRight = true;
        }

        stateTimer = currentState==previousState?stateTimer+delta:0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(body.getLinearVelocity().y>0){
            return State.JUMPING;
        }
        if(body.getLinearVelocity().y<0){
            return State.FALLING;
        }
        if(body.getLinearVelocity().x!=0){
            return State.RUNNING;

        }
        else{
            return State.STANDING;
        }
    }

    public void defineMario(){
        // defining body
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        bodyDef.position.set(10,10);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.5f);
        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef).setUserData(marioName);
        // <!-- body end -->


        // right sensor
        /*
        FixtureDef sensorFixtureDef = new FixtureDef();
        EdgeShape edgeShape= new EdgeShape();
        edgeShape.set(new Vector2(0,0),new Vector2(6f,0));
        sensorFixtureDef.shape = edgeShape;
        sensorFixtureDef.isSensor = true;

        Fixture fixture = body.createFixture(sensorFixtureDef);
        fixture.setUserData("sensor");*/
        //<!-- right sensor end -->




        // bottom sensor//


        FixtureDef bottomSensor = new FixtureDef();
        EdgeShape edgeShape1 = new EdgeShape();
        edgeShape1.set(new Vector2(0,-12f),new Vector2(0,0));
        bottomSensor.shape =edgeShape1;
        bottomSensor.isSensor = true;

        Fixture fixture1 = body.createFixture(bottomSensor);
        fixture1.setUserData("bottom");
        // bottom sensor//




    }

    public void reset(NeatNetwork brain){

        this.brain = brain;
        start=0;
        score=0;
        end=0;
        currentState = State.STANDING;
        previousState = State.STANDING;
        isDead = false;
        setBounds(0,0,1,1);
        setRegion(marioStand);
        defineMario();

    }





}
