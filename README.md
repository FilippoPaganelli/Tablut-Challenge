# Tablut-Challenge
## Team Tonkatsu

This is our code for the Tablut challenge, course "Fondamenti di Intelligenza Artificiale M" (A.Y. 2020-2021), University of Bologna.
The code was modified from [Andrea Galassi's repo](https://github.com/AGalassi/TablutCompetition).

### The game
Tablut is an ancient board game on a 9x9 grid. We implemented our AI agent based on [Ashton version](https://www.heroicage.org/issues/13/ashton.php) of the game. 

### Client
This is a Java client implementing Minimax with Alpha-Beta pruning for choosing the best move each turn. At startup, it is possible to choose between "white" and "black" players also specifing a timeout for waiting the opponent's move and a server IP address.

### Server
The server manages the connection between white and black players, keeping an updated state version. Once a player sends its move to the server, it forwards the state to the other player waiting for its reply. In case any player doesn't answer in due time the former loses.

### Installation on Ubuntu/Debian
Run these commands:
```sudo apt update
sudo apt install openjdk-8-jdk -y
sudo apt install ant -y
```
### Building the project
After cloning the repo:
```cd Tablut-Challenge/Tablut
ant clean
ant compile
```

### Run
Run server first:
```
ant server
```

Run each player in different terminal windows:
```
./Tonkatsu white timeout serverIP
./Tonkatsu black timeout serverIP
```
