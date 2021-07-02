package com.game.engine.engine;

import com.game.engine.engine.util.EngineUtilities;
import com.game.engine.engine.util.Logger;
import com.game.engine.game.GameManager;

public class GameContainer implements Runnable {

    private Thread thread;
    private Renderer renderer;
    public Window window;
    private Input input;
    private AbstractGame game;
    private EngineSettings settings = new EngineSettings();
    private Logger logger = new Logger();

    public int clearColour = 0xFF000000;

    private boolean running = false;
    public int fps = 0;

    public GameContainer(AbstractGame game) {
        this.game = game;
    }

    public void start(GameManager gm) {
        window = new Window(this);
        renderer = new Renderer(this, gm);
        input = new Input(this);

        thread = new Thread(this);
        thread.start();
    }

    public void stop() { this.running = false; }

    public void run() {
        running = true;

        boolean render;
        double firstTime;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0;

        game.init(this);

        while(running) {
            render = !settings.isLockFPS(); // Change from FALSE to TRUE to uncap frame-rate

            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while(unprocessedTime >= settings.getUpdateCap()) {
                unprocessedTime -= settings.getUpdateCap();
                render = true;

                if(frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                }

                game.update(this,(float)settings.getUpdateCap());
                window.update();
                input.update();
            }

            if(render) {
                frames++;
                renderer.clear();
                game.render(this,renderer);
                renderer.process();
            }else{
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getWidth() {
        return settings.getWidth();
    }

    public void setWidth(int width) {
        settings.setWidth(width);
    }

    public int getHeight() {
        return settings.getHeight();
    }

    public void setHeight(int height) {
        settings.setHeight(height);
    }

    public float getScale() {
        return settings.getScale();
    }

    public String getTitle() {
        return settings.getTitle();
    }

    public void setTitle(String title) {
        settings.setTitle(title);
    }

    public Window getWindow() {
        return window;
    }

    public Input getInput() {
        return input;
    }

    public int getFps() {
        return fps;
    }

    public Renderer getRenderer() {
        return renderer;
    }
}
