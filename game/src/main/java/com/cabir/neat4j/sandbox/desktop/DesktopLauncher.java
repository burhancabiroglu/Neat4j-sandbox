package com.cabir.neat4j.sandbox.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.cabir.neat4j.sandbox.core.SandboxGame;

public class DesktopLauncher {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Neat4j Sandbox");
        config.setWindowedMode(600, 400);
        config.useVsync(true);
        new Lwjgl3Application(new SandboxGame(), config);
    }
}
