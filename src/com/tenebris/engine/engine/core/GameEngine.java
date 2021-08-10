package com.tenebris.engine.engine.core;

import com.tenebris.engine.engine.states.Game;
import com.tenebris.engine.engine.util.EngineSettings;
import com.tenebris.engine.engine.util.terminal.Console;

import java.awt.event.WindowEvent;

public class GameEngine implements Runnable {

    public int fps = 0;

    protected Renderer renderer;
    protected Window window;
    protected Input input;
    protected Game game;
    protected EngineSettings settings;
    protected EngineAPI api = new EngineAPI();
    private Thread thread;

    private int clearColour = 0xFF000000;
    private boolean running = false;

    public GameEngine(Game game, EngineSettings settings) {
        this.game = game;
        this.settings = settings;
    }

    public void run() {

        Console.println("<purple><Engine>: Info - <reset>Engine initialised");

        running = true;

        boolean render;
        double firstTime;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0;

        game.setAPI(api);
        game.init(api);

        while (running) {
            render = !settings.isLockFPS(); // Change to uncap frame-rate

            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while (unprocessedTime >= settings.getUpdateCap()) {
                unprocessedTime -= settings.getUpdateCap();
                render = true;

                if (frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                }
                Console.update(api, (float) settings.getUpdateCap());
                game.getState().update(api, (float) settings.getUpdateCap());
                window.update();
                input.update();
            }

            if (render) { // Render the game
                frames++;
                renderer.clear();
                game.getState().render(api, renderer);
                renderer.process();
                Console.render(api, renderer);
            } else {
                try {
                    Thread.sleep(1); // Allow the thread to sleep
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void start() {
        window = new Window(this);
        renderer = new Renderer(this);
        input = new Input(this);

        Console.init(api);

        thread = new Thread(this);
        thread.start();

        getWindow().getFrame().addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                stop();
            }
        });

        window.setVisible(true);
    }

    public void stop() {
        if (this.running) {
            this.running = false;
            game.dispose();
            game.getState().dispose(api);
            window.getFrame().dispatchEvent(new WindowEvent(window.getFrame(), WindowEvent.WINDOW_CLOSING));
        }
    }

    public void forceStop() {
        if (this.running) {
            this.running = false;
            thread.stop();
            window.getFrame().dispatchEvent(new WindowEvent(window.getFrame(), WindowEvent.WINDOW_CLOSING));
        }
    }


    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public EngineSettings getSettings() {
        return settings;
    }

    public void setSettings(EngineSettings settings) {
        this.settings = settings;
    }

    public EngineAPI getAPI() {
        return api;
    }

    public void setAPI(EngineAPI api) {
        this.api = api;
    }

    public int getClearColour() {
        return clearColour;
    }

    public void setClearColour(int clearColour) {
        this.clearColour = clearColour;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }
}
