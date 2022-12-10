# `snek!`

A simple remake of the classic game Snake, built in Java.

## Requirements

- Java SE 17 or above
- IntelliJ (or Maven for command-line-only builds)

---

## How to run `snek!`

### Run with IntelliJ

This project was developed using the Maven build system. When first opening the project, you'll see the project 
plugins and dependencies being downloaded. It may take a minute for the project to process and index all the build 
files.

> **Warning**
>
> Please do **not** delete the `.idea` directory—it contains the "Run Game" configuration 
> necessary for you to build and run the game.

When the project has loaded, you will find the Run Game configuration in the menu bar.

!["Run Game" run configuration](docs/images/run-game-config.png)

### Run from Commandline

To build from the command-line only, you can use Maven, if you have it installed, and then run the `.JAR` 
directly. Enter the following commands in the project root:

```shell
mvn install
java -jar snake/target/snek.jar
```

---

## Playing `snek!`

A window should appear for you to play the game:

![`snek!` running](docs/images/main-menu.png)

The rules are simple:

- Use the arrow keys to move snek around the world
- Eat the apples, but watch out for the poisonous ones! 
- Try not to hit the walls, rocks, or your tail, or it's game over

### `snek!` Features

- Main menu that lets the player select the game mode
- Infinite mode: let the snake tail grow forever
- Avoid poisonous apples or lose a life!
- H.U.D. shows the player(s) apple count and lives remaining
- DOOM-inspired H.U.D. avatars that respond when players eat apples
- In-game pause function
- Listen out for cool sound effects:
  - Satisfying crunch when an apple is eaten
  - Disgusted noise when a poisonous apple is eaten
  - Death scream when the player loses the game
  - Menu navigation and selection
- Epic music to set the scene during gameplay
- Game over screen that lets the player play again or go to the main menu
- Two player mode: play against a blue friend, fight for the highest score!

![`snek!` two-player mode](docs/images/two-player-mode.png)

### Future Improvements

- Track high-scores and high-score submenu in the main menu
- ~~Player Two Blue H.U.D. Avatar~~
- ~~Randomly placed obstacles, like rocks~~
- Infinite-walls mode: go through walls and appear on the opposite side
- Power-ups, like extra lives, or to make snek faster

---

## Architecture

### Overview

`snek` was developed with the minimum version of OpenJDK 17, and uses Maven to build the project including its 
dependencies, and package it into an executable `.JAR`. The game engine used to develop Snek is my external 
`TEngine` project, which has been included locally as a module to simplify the build process. 

### Project Overview and Maven

The project is organised to work with the Maven build system—you can find all the source code in `./snake/src/main/java/`.

Within the source files, there are the following packages:

![`snek!` package diagram](docs/images/package-diagram.png)

### `TEngine`

The `TEngine` is my personal rebuild of the Massey GameEngine, loosely based on the ECS software architecture pattern. 
It's a work in progress, so the following is only a brief summary:

- Graphics Engine: Supported ✅
  - Drawing primitives: ovals, rectangles
  - Compound primitive containers
  - Text
  - Sprites
  - Animated Sprites & Sprite Sequences
  - Transforms
- Physics Engine: WIP ⚠️
- Audio: Supported ✅
- Actors & Actor Management: Supported ✅
- World Management: Supported ✅

### The `snek!` Game Actor

As the `TEngine` is loosely based on ECS, the `Snek` Actor is also structured this way—we separate out the logic 
from the graphical representation, and this decoupled structure offers more flexibility throughout development. The 
graphical representation is further broken down into two separate components, the head and the tail. The logical
representation is also further separated out and encapsulated in the Player class, which you can see in the class
diagram below:

![The `snek!` Game Actor](docs/images/snek-actor.png)

This makes it quite easy to have each aspect of the `Snek` Actor manage itself and update its different components 
separately.

The `Snek` Actor largely manages itself, including growing its tail as necessary, rotating its head to face the right 
direction, and handling key events. The only parts that need to be coordinated from the outside are responding to
collisions and needing to be told to eat an apple. A higher level `GameWorld` manages the interactions between the actors.

### The `GameWorld`

The `GameWorld` coordinates the gameplay, which is grid based, and manages the interactions between actors depending on 
the game configuration (one or two player, infinite or normal mode).

![The `GameWorld`](docs/images/game-world.png)

On a given tick, the `GameWorld` will check if the `Snek` actors have collided with the walls, with themselves,
or with each other. It then checks if an `Apple` has been eaten, and passes that to the appropriate `snek` player,
which will know what to do with it. The `GameWorld` will then organise removing the `Apple` from the world, 
and placing a new one at a random location. We can see this interaction fleshed out below:

![`GameWorld` managing interactions](docs/images/world-management.png)

### Screen Management

At a higher level than the `GameWorld` is the `PlayGameScreen`, which is, as the name suggests, the screen that is 
loaded when the user starts playing the game. We also have the `MenuScreen`, which internally is made up of 
smaller `Menu`s, and the `GameOverScreen`. To manage moving between all of these screens at a higher level, we have the 
`Game` class, which extends the `TEngine` game engine, and is the entry point for the program. The `Game` is where we
initially set up everything needed for `Snek`, and then it manages loading and unloading each of these screens. It then
listens for callbacks from each screen to know when to transition and which screen to load next.

![`GameWorld` managing interactions](docs/images/screen-management.png)

The first screen loaded is the `MenuScreen`, which lets the player select the game configuration and makes 
that available to the `PlayGameScreen` through `Settings`. While the `PlayGameScreen` is loaded, it updates the 
`GameState` so that when the game is over and the `GameOverScreen` is loaded, it can be passed the `GameState` and 
display the results.

## Attributions

- [Animated Snake by Calciumtrice](https://opengameart.org/content/animated-snake), Creative Commons Attribution 3.0 license.
- [Retro Gaming Font by Daymarius](https://www.dafont.com/retro-gaming.font), free for personal and commercial use.
- [Music by Steven Melin](https://stevenmelin.com), free for personal and commercial use.
- [Sound Effects by Juhani Junkala](https://juhanijunkala.com/), free for personal and commercial use.

![`snek`](docs/images/snake-idle.gif)
