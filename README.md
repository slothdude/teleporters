Trying it Yourself
===============

Copy your testing input into ./input.txt or
change this line in
![TeleporterRunner.java](./src/main/java/TeleporterRunner.java)
to point to your file:

`sc = new Scanner(new File("input.txt"));`

Assuming you have maven installed, run

```
mvn verify
mvn exec:java
```

to build with maven and automatically run TeleporterRunner.java


Prompt
==========

## Problem 1: Teleportation System

You have discovered the secrets of teleportation and have several teleportation
routes up and running. Each route allows instantaneous travel from one city to
another. All routes are two way: if you can teleport from city A to city B, you
can also teleport from city B to city A. You want to create a system to make it
easier for you to answer specific questions about your network. You should
assume anyone using your network wants to travel only by teleportation.
Questions you must be able to answer:
1. What cities can I reach from city X with a maximum of N jumps?
2. Can someone get from city X to city Y?
3. Starting in city X, is it possible to travel in a loop (leave the city on
one route and return on another, without traveling along the same route twice)?

## Assumptions I made

1. If you ask "Can I teleport from <city1> to <city2>?" I automatically return
`true` if they are the same value, instead of trying the loop.

2. I assume that all input will be presented in the same format that was given
in the format above, case matters on certain words but not cities

Implementation
======

The most basic class is a city, which just has a name and a list of neighbors.
I thought it would be easier to work with strings when searching however, so I
made a `HashMap<String, City>` that links the city name with the actual city
object that has the neighbors and such. Each city name has only one
corresponding object. The main method is in
![TeleporterRunner.java](./src/main/java/TeleporterRunner.java)
