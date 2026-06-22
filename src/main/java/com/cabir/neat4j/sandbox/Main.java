package com.cabir.neat4j.sandbox;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cabir.neat4j.sandbox.core.SandboxGame;

public class Main {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new SandboxGame(), config);
    }
}
