# game.engine

## Introduction

game.engine is a game engine written in Java which uses no external libraries and has no dependencies. 

## How to use

Either clone this project using `git clone https://github.com/BambooPyanda/game.engine` or download the project and import it into your IDE.

## Configuration

game.engine is extremely customisable. In `com.game.engine.engine.util.EngineSettings.java` you can find configurable options which you can change to suit your requirements. Feel free to take your time to look around some of the engine and check out some of how it works.

To begin creating your first game, simply head over to `com.game.engine.game.GameManager.java`. Immediately you will be greeted by a few methods. Notably `init()`, `update()`, and `render()`.

The init() function runs when the game first starts. 
The update() function is where your main code goes. This is run at around 60 times per second (unless there is lag). 
The render() function runs every frame and everything must be redrawn per frame.

To draw "Hello, world!" to the screen, go to your render function, and type: `r.drawText("Hello, world!", 0, 0, 0xFFFFFFFF);`. This draws the text "Hello, world!" at pixel (0,0) in a white colour. Run your code and now you should see that "Hello, world!" is drawn in the top-left of the screen.

## Features implemented
 - [x] Main engine
 - [x] Customisability
 - [x] Image layering with alpha
 - [ ] More rendering functionality
 - [ ] GameObjects and a physics engine
