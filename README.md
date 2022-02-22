<img align="right" src="https://user-images.githubusercontent.com/90221641/154890438-7f02af72-7171-4200-938c-76fad7d1e27c.png" alt="Ballz Screenshot" width= "40%"/>

# Ballz Clone
A clone of the mobile game Ballz, which was pretty popular around 2018. I wrote this for my final project in my Junior year of High School. It's shockingly addicting.

## Installation
The simplest way to install and play the game is to download Ballz.jar from the releases tab on the right.

If you would like to compile the source code directly instead, install the JDK and run the following commands:
1. `javac *.java`
2. `java Window`

## How to Play
### Controls:
* Aim the mouse to turn the ball launcher, and click to fire. You will not be able to fire again until all of the balls return.

### Rules:
1. Each time a ball makes contact with a numbered block, the ball will bounce off and the number will decrease by 1.
2. If the number on a block hits 0, it will disappear.
3. If a ball hits a '+' block, the number of balls in the launcher will increase by one and the block will disappear.
4. Each time you click, the launcher will fire all of the balls in the launcher in the same direction.
5. After each round, the blocks will move down one row and new blocks will appear at the top.
6. The goal of the game is to avoid letting the balls hit the bottom of the screen for as long as possible.

## How It's Made
The Window class was written to manage the game. It calls the update function repeatedly, which whcih moves the balls, checks for collisions, etc.

The BallLauncher class controls the direction that the launcher is pointing at any given moment, and handles firing the balls.

The Ball class defines the behavior of the balls, such as their speed and direction.

The Block class defines the behavior of the blocks, like decreasing their health and moving down.

The BallIncrease class is a child of the Block class and defines the behavior of the increase block, pretty much just increasing the balls on contact.

### What I Learned
* How to track the mouse within the game window.
* How to calculate collisions and bounces for the balls.
* How to add slight randomness to a game while keeping it fair.

## Known Issues
* There are almost no comments. I could definitely use some comments. Apparently High School me found them unnecessary.
* The collisions are pretty janky. I think it's because the balls move in chunks at a time, not smoothly, and will skip over the collision zones.

## Planned Features
As it stands, I consider this game complete. It was only meant to be a small project. However, I may continue its development if I am truly struck with the inspiration.
