# TEngine

My very first 2D game engine to support my very first game, snek! To read more about on the `TEngine` project, see the GitHub repo
[here](https://github.com/tessapower/tengine).

`TEngine` (pronounced _"ten-gin"_, short for _"Tessa's Engine"_) developed out of the game engine
supplied by the lecturers of the 159.261 Games Programming course at Massey University. I
fleshed out the original lone little `GameEngine.java` into what you see here, and used it to
build the games that I submitted for course assignments.

`TEngine` was built using Java 17 and uses Java Swing for window management (based on requirement
for the course). The structure of game objects (called `TActor`s or simply actors) is loosely 
based on the ECS model. There is support for actors and actor management, level and world
management, audio, composite primitive containers, transforms, text, animated sprites and sprite 
sequences, and basic broad-phase collision detection and collision event notifications.

## Why is `TEngine` included as a module?

`TEngine` is included in this project as a module to make building the entire game easier and
preserve the state of the `TEngine` project as it was used when `snek!` was finished.

> **Note**
>
> At the time, `TEngine` was a work in progress, and the state of the code in this module
> reflects that. To see the most recent and more polished version of `TEngine`, visit the
> [GitHub page](https://github.com/tessapower/tengine).

## Requirements

- Minimum Java SE 17+
- IntelliJ, Eclipse, or your favourite Java editor
- Maven (install locally for command-line-only builds)

## How to Build `TEngine`

The `snek!` project was developed using the Maven build system. You can either build and package 
the project straight from the command line or using your favourite Java IDE that has support for 
Maven. You shouldn't need to build the `TEngine` separately - that will be handled by the 
parent project.

A file called `TEngine.jar` will be created in the `out/artifacts/TEngine_jar/` dir. This `.jar` can
be included in external projects in the usual way you link to external libraries.
