package com.game.engine.engine;

import com.game.engine.engine.util.EngineSettings;
import com.game.engine.engine.util.Logger;

import java.awt.event.WindowEvent;

public class GameEngine implements Runnable {

    private Thread thread;
    private Renderer renderer;
    private Window window;
    private Input input;
    private Game game;
    private EngineSettings settings;
    private Logger logger = new Logger();

    private EngineAPI api = new EngineAPI();

    private int clearColour = 0xFF000000;

    private boolean isRunning = false;
    public int fps = 0;

    public GameEngine(Game game, EngineSettings settings) {
        this.game = game;
        this.settings = settings;
    }

    public void run() {
        isRunning = true;

        float divisor = (float)0x3B9ACA00; // Divisor to convert nanoTime to ms

        boolean render;
        double first;
        double last = System.nanoTime() / divisor;
        double passed;
        double unprocessed = 0;

        double frameTime = 0;
        int frames = 0;

        game.init(); // Run the init function for our game

        while(isRunning) {
            render = !settings.isLockFPS(); // Change to uncap frame-rate

            first = System.nanoTime() / divisor;
            passed = first - last;
            last = first;

            unprocessed += passed;
            frameTime += passed;

            while(unprocessed >= settings.getUpdateCap()) {
                unprocessed -= settings.getUpdateCap();
                render = true; // Only render when we update

                if(frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                }
                game.getState().update(api,(float)settings.getUpdateCap());
                window.update(this);
                input.update();
            }

            if(render) { // Render the game
                frames++;
                renderer.clear();
                game.getState().render(api, renderer);
                renderer.process();
            }else{
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
        logger.init(settings.getTitle());

        thread = new Thread(this);
        thread.start();

        getWindow().getFrame().addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                stop();
            }
        });
        window.setVisible(true);
        Logger.log(Logger.ENGINE_INFO, "Engine initialised");
    }

    public void stop() {
        if(this.isRunning) {
            this.isRunning = false;
            game.dispose();
            game.getState().dispose();
            window.getFrame().dispatchEvent(new WindowEvent(window.getFrame(), WindowEvent.WINDOW_CLOSING));
        }
    }

    public void forceStop() {
        if(this.isRunning) {
            this.isRunning = false;
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

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
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
