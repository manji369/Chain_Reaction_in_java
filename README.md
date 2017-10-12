# Chain Reaction in Java using JFrame

## Story:

I really like to play this mobile multiplayer board kind of game ChainReaction for Android. After a lot of unsuccessful attempts, I could not find a version of it for windows. So in 2013, I decided to make a chain reaction game for windows. The only programming environment I was familiar back then was Autoit - a windows automation software. So I ended up making it in Autoit which was posted on my hobbyist blog. Years later in 2016, since I have learnt many programming languages, I wanted to make it again but this time, using Java. And hence this project came into existence.

## Logic implementation and UI
I implemented the same logic code I used when I programmed it using Autoit. Various blocks have their own memory to store the count of reactions and the color of reaction. When a block exceeds its maximum number of reactions, it explodes and propagates the reaction leading to chain reaction. JFrame was used with 81 buttons to serve as UI.
