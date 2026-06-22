package com.cabir.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cabir.algo.NEAT;
import com.cabir.core.annotation.ActivationAnnotation;
import com.cabir.skeleton.LayerSkeleton;
import com.cabir.skeleton.NetworkSkeleton;


public class PlayScreen implements Screen {

    private final GrumpRun game;
    public Viewport viewport;
    public OrthographicCamera camera;
    private final OrthogonalTiledMapRenderer renderer;
    private final TiledMap tiledMap;
    private final TextureAtlas atlas;

    public int WIDTH = 600;
    public int HEIGHT = 400;
    public int ppm = 20;

    // box2d variables
    private final World world;
    private final Box2DDebugRenderer box2DDebugRenderer;

    public static Mario player;
    public static NEAT evoBrain;

    public static int counter = 0;
    ShapeRenderer sr = new ShapeRenderer();


    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    public PlayScreen(GrumpRun game){

        NetworkSkeleton skeleton = new NetworkSkeleton();
        skeleton.add(new LayerSkeleton(1,3));
        skeleton.add(new LayerSkeleton(ActivationAnnotation.ReLU));
        skeleton.add(new LayerSkeleton(3,8));
        skeleton.add(new LayerSkeleton(ActivationAnnotation.ReLU));
        skeleton.add(new LayerSkeleton(8,4));
        skeleton.add(new LayerSkeleton(ActivationAnnotation.ReLU));
        skeleton.add(new LayerSkeleton(4,3));
        skeleton.add(new LayerSkeleton(ActivationAnnotation.ReLU));
        skeleton.add(new LayerSkeleton(3,2));

        evoBrain = new NEAT(skeleton,10,0.1,100);

        // Declaring Game and Camera
        this.game = game;
        atlas = new TextureAtlas("Mario_and_Enemies.pack");
        TmxMapLoader tmxMapLoader = new TmxMapLoader();
        tiledMap = tmxMapLoader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap,0.05f);
        camera = new OrthographicCamera();
        viewport = new FitViewport(WIDTH/ppm,HEIGHT/ppm,camera);
        viewport.setScreenSize(WIDTH/ppm,HEIGHT/ppm);
        camera.position.set(viewport.getScreenWidth() /2,viewport.getScreenHeight()/2,0);
        world = new World(new Vector2(0,-100),true);
        box2DDebugRenderer = new Box2DDebugRenderer();
        // Declaring end

        // Declaring World
        new Box2DCreator(world,tiledMap);
        // Declaring End



        player = new Mario(world,this,evoBrain.getPopulation().get(counter),"mario");

        world.setContactListener(new WorldContactListener());
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    public void update(float delta){

        camera.update();
        world.step(1/60f,6,2);
        if(!player.isDead){
            player.update(delta);
        }
        renderer.setView(camera);
        if(!player.isDead) {
            camera.position.x = player.body.getPosition().x;
        }
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        box2DDebugRenderer.render(world,camera.combined);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();


        if(!player.isDead){
            player.draw(game.batch);
        }
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.dispose();
        tiledMap.dispose();
        world.dispose();
    }
}
