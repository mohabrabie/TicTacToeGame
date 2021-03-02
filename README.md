# Tic Tac Toe GAME
[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/built-with-love.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/built-by-developers.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/uses-git.svg)](https://forthebadge.com)

A Client/Server Tic Tac Toe Game, these projects(client&server) were done @ ITI intake 41 by students of Cloud Platform Development (Open Source Track).

# Contents

- [Demo For The Gameplay](#demo-for-the-gameplay)
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Diagrams](#diagrams)
	 - [Class Diagram](#class-diagram)
	 - [Database Schema](#database-schema)
- [Features ](#features)
	- [Server Side](#server-side)
	- [Client Side](#client-side)

## Demo For The Gameplay
<img src="https://github.com/ayaabdo/TicTacToe/blob/master/Project-Gif.gif?raw=true"/>



## Getting Started

There are two ways to run the project.

1- First through the terminal and jar files.
open the terminal and change directory to the Server jar file directory and run:
```
java -jar ./Server.jar
```
The server dashboard will pop up and the server should now be listening to requests on the localhost port 55111

Next open another terminal and change directory to the Client jar file directory and run:
```
java -jar ./Client.jar
```
Then home screen should open and you could choose what you want to do (Login OR Sign-Up)  

Note: the client and the server both run on the same machine if you want to run them on different machines one small change should be made and it's to change the connection ip in the Client project in a class called PlayerSoc.java from 127.0.0.1 to the ip of the machine which the server is runing on.

2- Second through the projects
it's easy! 

From any IDE, you could run the Server project first then the Client project.

## Prerequisites

Java 8u111 or higher recommended

## Features

### Server Side
* View a list of all users</br> 
* View players status, e-mail, score and avatars</br>
* Start and close the server</br>
</br>

### Client Side

* login</br>
* SignUp</br>
* play with pc with 3 difficulty levels</br>
* play with online friends</br>
* chat while playing</br>
* have an avatar and score level</br>
* see who has the highest score in the game</br>
* see who is online, offline or busy (playing with someone else)</br>
</br>

## Built With

* [JFoenix](http://www.jfoenix.com/) -JavaFX Material Design Library
* [Maven](https://maven.apache.org/) - Dependency Management
* [SQLite](https://www.sqlite.org/download.html/) - Small, Fast and Reliable
* [Jackson](https://github.com/FasterXML/jackson) -  The best JSON parser for Java

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Contributors

* [Mohab Rabie](https://github.com/mohabrabie)
* [Islam Reda](https://github.com/IslamReda)
* [Aya Abdelsamie](https://github.com/ayaabdo)
* [Wesal Mekky](https://github.com/wesalEldsokey)
* [Ahmed Samy](https://github.com/Ahmedsamymahrous)
