# Death Swap Plugin

This code is for a death swap plugin for a spigot server in Minecraft 1.15.2 using the Bukkit library.
It was written by James Darby for 3 players (works with 2) during May 2020 based on the idea
originally conceived by Sethbling, who created the game using Minecraft command blocks.

In the game, players try to eliminate each other through indirect means each time they swap.

## Setup

To use you need to put a jar file of all the code, the most up to date one should be in the repository,
and put this in the plugin folder of the server. 

## Commands

```
/deathswap player1_name player2_name player3_name
/swap player1_name player2_name player3_name
```
/deathswap will swap up to three players maximum with a timed delay between continuous swaps

It will put all players in survival and start a countdown based
on server ticks, giving a 30 sec, 10sec, and 5-1 second warning before the next swap.

/swap will do the swap instantly and is mostly for testing purposes

### Swap Information

During the swap the players are teleported to other's positions and effects are applied to compensate for possible slow chunk loading.
These effects include blindness, slowness, damage invulnerability and the inability to lose air levels.
