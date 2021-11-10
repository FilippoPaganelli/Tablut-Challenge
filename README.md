# Tablut-Challenge
## Team 002 - Tonkatsu

### - The team
#### [Filippo Paganelli](https://github.com/FilippoPaganelli)
#### [Ginevra Fabrizio](https://github.com/lamebanana)

This is our code for the Tablut competition, course __Fondamenti di Intelligenza Artificiale M__ (AY 2020-2021), Master's Programme in Computer Engineering, University of Bologna.
The code was modified from and inspired by [Andrea Galassi's repo](https://github.com/AGalassi/TablutCompetition).

### - VM for the challenge
For this challenge, we had to test our agent inside a Debian VM with the following technical specifications:
- 8 GB of RAM
- 30 GB of storage
- 4 CPU cores maximum
- no Internet or external drives access

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
*credits for this: textart.sh/topic/noodle*

----------

### - The game
Tablut is an ancient board game on a 9x9 grid. We implemented our AI agent based on [Ashton version](https://www.heroicage.org/issues/13/ashton.php) of the game. 

### - Game state
Game state is represented as a matrix of characters where __"B"__ is black pawn, __"W"__ is white pawn and __"K"__ is the king. Every time the state is updated (i.e. a move is performed on the board) it's printed as follows:

```java
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
_(this is the initial state as for the Ashton version's rules)_

### - Client
This is a Java client implementing __Minimax with Alpha-Beta pruning__ for choosing the best move each turn. At startup, it is possible to choose between __"white"__ and __"black"__ players, specifing a __timeout__ for waiting the opponent's move and a __server IP address__.
The actual implementation of our client is just a bash script and a JAR file for each player.

_(defaults for timeout and serverIP are 60s and localhost respectively)_

### - Server (*)
The server manages the connection between white and black players, keeping an updated version of the state. Once a player sends its move to the server, it forwards the state to the opponent and waits for its reply. In case any of the players doesn't answer in due time, the former loses.


### - Run (*)
Run server first:
```
ant server
```

Run each player in different terminal windows:
```
./Tonkatsu.sh white timeout serverIP
./Tonkatsu.sh black timeout serverIP
```
Note: our Bash script is case-insensitive about role argument ("white"/"black") but wants it as a full word.

 (*) *this version only implements the client's code, server's will be added soon. Meanwhile, you can use the server at [Andrea Galassi's repo](https://github.com/AGalassi/TablutCompetition).*
