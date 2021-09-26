# Tenebris

![Tenebris banner](https://raw.githubusercontent.com/BambooPyanda/Tenebris/master/res/tenebris_banner.png)

## Introduction

Tenebris is a game engine written in Java which uses no external libraries and has no dependencies. 

## How to install

Above the file list, click the button named 'Use this template'. Follow the instructions presented by Github,
including adding a name to your repository selecting its visibility and adding a description. Click
'Create repository from template', and you're good to go!

## Configuration/How to use

Tenebris is extremely customisable. In `EngineSettings.java` you can find
configurable options which you can change to suit your requirements. Feel free to take your time to look
around the engine and check out some of how it works.

To begin creating your first game, simply head over to `GameState.java`. Immediately
you will be greeted by a few methods. Notably `init()`, `update()`, and `render()`.

The init() function runs when the game first starts. 
The update() function is where your main code goes. This is run at around 60 times per second(unless there is lag). 
The render() function runs every frame and everything must be redrawn per frame.

To draw "Hello, world!" to the screen, go to your render function, and type: 
`r.drawText("Hello, world!", 0, 0, 0xFFFFFFFF);`. This draws the text "Hello, world!" at pixel (0,0) in a
white colour. Run your code and now you should see that "Hello, world!" is drawn in the top-left of the
screen.

The documentation is still a work-in-progress, however you can check it out on this project's wiki [here](https://github.com/BambooPyanda/Tenebris/wiki)

## Features implemented
 - [x] Main engine (input, rendering, audio etc.)
 - [x] Engine utilities and simple customisability
 - [x] Game states
 - [x] GameObjects, and an Entity Component System
 - [x] More rendering functionality
 - [ ] Better window functionality

## Licensing
This project is licensed under the MIT license. Read the LICENSE file for more.
