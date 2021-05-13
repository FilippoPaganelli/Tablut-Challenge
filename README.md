# Tablut-Challenge
## Team 002 - Tonkatsu

### - The team
#### [Filippo Paganelli](https://github.com/FilippoPaganelli)
#### [Ginevra Fabrizio](https://github.com/lamebanana)

This is our code for the Tablut challenge, course "Fondamenti di Intelligenza Artificiale M" (AY 2020-2021), Master's Programme in Computer Engineering, University of Bologna.
The code was modified from and inspired by [Andrea Galassi's repo](https://github.com/AGalassi/TablutCompetition).

### - VM for the challenge!
[link](https://drive.google.com/drive/folders/1q-TeF79NsoUTj0XvAmVQowjVssYT3Ipp?usp=sharing) to a Debian VM necessary for the competition.

----------
```
  ▒▒▒▒░░                                                                                  
                                                                                          
                                                                                          
                            ████                                                          
                      ████  ██▒▒██                                                        
                      ██▓▓████▓▓▓▓██                                                      
                      ██▒▒▒▒████▒▒▒▒██                                                    
          ░░            ██▓▓▓▓████▓▓▓▓██░░░░░░░░░░░░░░                                    
              ░░          ██▓▓▓▓████▓▓░░░░░░░░░░░░░░░░░░░░                                
                            ██▒▒▓▓████░░░░▒▒░░░░░░░░░░░░░░░░░░░░░░                        
                              ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                      
                            ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                    
                          ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                    
                        ████████████████████████████████████████████████                  
                        ██  ▒▒  ▒▒                                  ░░██                  
                        ▓▓▒▒▒▒▒▒▒▒▒▒      ▒▒      ▒▒      ▒▒      ▒▒░░██                  
                        ██▒▒▒▒▒▒▒▒▒▒              ░░      ░░    ░░▒▒░░██                  
                        ██  ▒▒▒▒▒▒    ▒▒      ▒▒      ▒▒      ▒▒░░░░░░██                  
                        ██    ▒▒                            ░░░░░░░░░░██                  
                        ████████████████████████████████████████████████                  
                          ██                                  ░░░░░░██                    
                          ██      ████                    ████░░░░░░██                    
                            ██  ▓▓██  ██              ░░██  ████░░██                      
                            ██  ████████            ░░░░████████░░██                      
                              ██  ████          ░░░░░░░░░░████░░██                        
                                ██▒▒▒▒      ░░░░░░░░░░░░░░▒▒▒▒██                          
                                ████░░░░░░░░░░░░░░░░░░░░░░░░████                          
                                  ████████████████████████████                            
```
----------

### - The game
Tablut is an ancient board game on a 9x9 grid. We implemented our AI agent based on [Ashton version](https://www.heroicage.org/issues/13/ashton.php) of the game. 

### - Game state
The game state is represented as a matrix of characters where "B" is black pawn, "W" is white pawn and "K" is the king. Every time the state is updated (i.e. a move is performed on the board) it's printed as follows:

```
     [java] Current state:
     [java] O O O B B B O O O 
     [java] O O O O B O O O O 
     [java] O O O O W O O O O 
     [java] B O O O W O O O B 
     [java] B B W W K W W B B 
     [java] B O O O W O O O B 
     [java] O O O O W O O O O 
     [java] O O O O B O O O O 
     [java] O O O B B B O O O
```

### - Client
This is a Java client implementing Minimax with Alpha-Beta pruning for choosing the best move each turn. At startup, it is possible to choose between "white" and "black" players also specifing a timeout for waiting the opponent's move and a server IP address.
(defaults for timeout and serverIP are 60s and localhost respectively)

### - Server
The server manages the connection between white and black players, keeping an updated state version. Once a player sends its move to the server, it forwards the state to the other player waiting for its reply. In case any player doesn't answer in due time the former loses.

### - Installation on Ubuntu/Debian
Run these commands:
```
sudo apt update
sudo apt install openjdk-8-jdk -y
sudo apt install ant -y
```
### - Building the project
After cloning the repo:
```
cd Tablut-Challenge/tablut
ant clean
ant compile
```

### - Run
Run server first:
```
ant server
```

Run each player in different terminal windows:
```
./Tonkatsu.sh white timeout serverIP
./Tonkatsu.sh black timeout serverIP
```
