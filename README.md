# Rubics Race User Documentation
## Introduction
The goal of Rubics Race is to rearange squares on the board (5x5) by moving them into the empty spot, so that the center of that board matches a generated goal state (3x3). Usually this game is played by two players, but this is a one player version, where you compete to solve the race in the least amount of time possible. 

## Game Flow
After starting the game you will be prompted to give a nickname, under which you will be competing. Finalize your choice by pressing `Ready to Race` button. This will initialize the real game, starting right away. Move squares on the bigger board and try to create the same pattern in the center of the playing board as is shown in the smaller board. If you want to start with the same goal state again, press the `Reset` button. If you want to generate a new goal state as well, press the `New` button. When you are confident you have solved the puzzle, `Evaluate` the game. If you were succesfull, you will be shown the leaderboard with your name and time added. `Back to business` will bring you back to playing the game.

## Running
The Program is compiled and ran using Maven, writing these commands in the root folder, where pom.xml is located:
```bash
    $ mvn compile
    $ mvn exec:java
```

## Generating documentation
You can generate a documentation from the source code comments using Maven and javadoc:
```bash
    $mvn javadoc:javadoc
```
The generated documentation can be found under `target/site/apidocs/index.html`.
