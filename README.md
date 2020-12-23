# Forbidden Island

Software Engineering (COMP 41670) 2020

Authors: Jithin James and Omar Duadu

This document outlines the following:
	1.	How to Play
	2.	Our software development process

1.	How to Play:

1)	Open up the Project and press the run button. 
2)	You will be greeted with the following welcome screen:


```     
Welcome to Forbidden Island!

Software Engineering (COMP 41670) 2020

Authors: Jithin James and Omar Duadu

Shuffling the Flood Deck.

How many people are playing? (must be between 2 and 4)
```
3)	Enter the number of players that are going to be playing in this game. Please enter a number between 2 and 4.
4)	Next, you will have to enter the names of the players:
```
Player 1...
Enter your name:
....
```
5)	After entering all the player names, you will be drawn 2 treasure cards each from the treasure deck. If a Waters Rise card is picked up, it is placed placed back into the deck and deck is shuffled:
```
Shuffling the Treasure Deck.
Waters Rise card is drawn.
Adding the discard pile back into the Treasure Deck.
Shuffling the Treasure Deck.
```
6)	Next, you will be asked to select a Game Difficulty:
```
What game difficulty would you like to play? (Must be between 1 and 4)

Novice: 1, Normal: 2, Elite: 3, Legendary: 4
```
You must input one of the numbers seen on screen. This inputted number is the water level.
Every time a Waters Rise card is drawn, this water level will increase.

7)	Finally, the setup is finished. We get the following screen:
```
Water Level is at 1

It is <Player Name>'s (Player 1) turn!.
```
8)	Press Enter here to Start playing! You get the following screen:
```
                                X   W A T   X   X   T W H   X                                 
                                X           X   X           X                                 
                                X           X   X           X                                 
                                X X X X X X X   X X X X X X X                                 
                X   I R G   X   X   C O P   X   ~   L O L   ~   X   D O D   X                 
                X           X   X           X   ~           ~   X           X                 
                X     2     X   X @         X   ~           ~   X           X                 
                X X X X X X X   X X X X X X X   ~ ~ ~ ~ ~ ~ ~   X X X X X X X                 
X   M I M   X   X   C O A   X   X   S I G   X   X   C O S   X   X   W H G   X   X   T O M   X 
X           X   X           X   X           X   X           X   X           X   X           X 
X           X   X           X   X           X   X #         X   X $         X   X £         X 
X X X X X X X   X X X X X X X   X X X X X X X   X X X X X X X   X X X X X X X   X X X X X X X 
X   B R B   X   ~   G O G   ~   ~   T I P   ~   ~   T O S   ~   X   O B S   X   ~   C R F   ~ 
X           X   ~   1       ~   ~           ~   ~           ~   X           X   ~           ~ 
X           X   ~           ~   ~ @         ~   ~ £         ~   X           X   ~           ~ 
X X X X X X X   ~ ~ ~ ~ ~ ~ ~   ~ ~ ~ ~ ~ ~ ~   ~ ~ ~ ~ ~ ~ ~   X X X X X X X   ~ ~ ~ ~ ~ ~ ~ 
                X   P H R   X   X   C O E   X   X   C O G   X   X   B R G   X                 
                X           X   X           X   X           X   X           X                 
                X           X   X #         X   X         4 X   X           X                 
                X X X X X X X   X X X X X X X   X X X X X X X   X X X X X X X                 
                                ~   F O L   ~   X   H O G   X                                 
                                ~       3   ~   X           X                                 
                                ~           ~   X $         X                                 
                                ~ ~ ~ ~ ~ ~ ~   X X X X X X X                                 
Adventurer: Navigator
WaterLevel: 1
```
9)	The above screen displays the current state of the game board. You can see that some tiles
		have been flooded, as marked by the '~' character. The dry tiles are marked by the 'X' characters.
10)	You can also see the string representation of the tiles given. For example, 'HOG' represents
		'Howling Garden' Island Tile, 'FOL' represents 'Fool's Landing' and so on.
11)	Player Pawns can be seen on the Island Tiles, represented by a number character between 1-4.
12)	Finally, the treasures can also be seen on their respective tiles. The treasure characters are £,#,$ and @.
		
	More details about these abbreviations will be explained later.

13)	You are also presented with the following options:
```
What do you want to do?
Your options are:
[1]    Move
[2]    Shore Up
[3]    Give a Treasure Card
[4]    Capture a Treasure
[5]    Use a Special Card
[6]    Show Board
[7]    Show Hand
[8]    Show Help
[9]    Team Play
[0]    End turn
```
14)	You should pick one of the options above, by inputting the corresponding number.
15)	Choosing [1] Move:
```
<Player Name> (Player 1) is on Gold Gate

Where would you like to move? (For Example: SIG)
```
16)	Enter the tile map string as seen on board and you should get a successful or fail move message.
```
Valid Island Tile

Player <Player Name> successfully moved to Cliffs of Abandon

Actions taken: 1
```
17)	You can see actions taken increments if a successful action is performed.
18)	Choosing [2] Shore Up:
```
<Player Name> (Player 1) is on Cliffs of Abandon

Which Island would you like to shore up? :
```
19)	Enter the tile map string as seen on board and you should get a successful or fail shore up message.
```
Valid Island Tile

Player a successfully Shored Up Gold Gate

Actions taken: 2
```
20)	'Actions taken' increments once again as shore up was successful.
21)	Now, the aim of the game is to survive the flooding of the island and capture treasures using the treasure cards you have in your hand.
		There are multiple options for showing the different game components:
22)	To see your hand, choose option [7] Show Hand:
```
[1] : Helicopter Lift 
[2] : The Earth Stone
```
23)	You should see a similar output. If you want to see the board again, choose option [6] Show Board.
24)	If you want to check what the different abbreviations stand for, choose option [8] Show Help:
```
Choose from the options below:

[1] Tiles 
[2] Tile States 
[3] Treasures 
[4] Return
```
25)	This will give you the following options to choose from. Tiles will display the tile strings corresponding to the Island Tiles.
26)	Tile States gives you the character representing the different states of the tile:
```
[X] : Dry 
[~] : Flooded 
[ ] : Sunk
```
27)	Treasure gives you the character representing the different treasures available as seen on the game board.
```
[£] : The Earth Stone 
[$] : The Statue of the Wind 
[#] : The Crystal of Fire 
[@] : The Ocean's Chalice
```
28)	Finally, it allows you to return to the main options by choosing option [4]. You return to the main options:
```
Actions taken: 2

What do you want to do?
Your options are:
[1]    Move
[2]    Shore Up
[3]    Give a Treasure Card
[4]    Capture a Treasure
[5]    Use a Special Card
[6]    Show Board
[7]    Show Hand
[8]    Show Help
[9]    Team Play
[0]    End turn
```
29)	Finally, the current player's turn ends by choosing the option [0] End turn or if 3 successful actions are taken.
```
Your turn has ended.

It is <Player Name>'s (Player 2) turn!.
```
30)	At the end of each turn, you will draw 2 treasure cards from the treasure deck and a number of flood cards from the flood deck
		corresponding to the water level.
31)	There are a few other options that was not mentioned above.
32)	If the player is a Messenger or if the player is on the same Island Tile as another
		player, then they can choose to give a treasure card (option [3]).
33)	If the player is on the treasure tile and he/she has a corresponding matching treasure card set, then they can capture the treasure.
34)	You can also use a Special card like Sandbags and Helicopter Lift, which will not count as an action taken.
35)	Finally, since this is a co-operative board game, you should use option [9] Team Play:
```
Choose one of the following:
[1]    View the hands of your team members
[2]    Allow a team member to use a Special Card
[3]    View the captured treasures
[0]    Return to Main Options
```
36)	It gives you 4 options. The first option allows you to see the hands of your team members. This can help you when you want to give another
		player a treasure card. You will not modify their deck by doing this. It only lets you to see their hands.
37)	There is also an option to allow a team member to play their special card if they have any. This is necessary since special cards can be played
		at any time during the game by any player.
38)	The third option lets you see the captured treasures of the team as a whole. This gives you an indication of the progress of the game.
39)	Finally, you can return to main options.

If you capture all the treasures, get all the players to Fools Landing and use a Helicopter Lift to fly all the players of the Island, you win:
```
 _____  _____  _____  _____    _ _ _  _____  _____ 
|   __||  _  ||     ||   __|  | | | ||_   _||   | |
|  |  ||     || | | ||   __|  | | | | _| |_ | | | |
|_____||__|__||_|_|_||_____|  |_____||_____||_|___|
```
However, if one of the below:
1. Both Treasure Tiles sink
2. Fools Landing sinks
3. Any player sinks
4. Water level reaches Skull and Bones i.e 10

is true, then you lose the game and game ends:
```
_____  _____  _____  _____    _____  _____  _____  _____
|   __||  _  ||     ||   __|  |     ||  |  ||   __|| __  |
|  |  ||     || | | ||   __|  |  |  ||  |  ||   __||    -|
|_____||__|__||_|_|_||_____|  |_____| \___/ |_____||__|__|
```
2.	Our software development process:

1)	At the start, we created a Project in Github where we created issues of classes to create, features to work on, etc.
	
2)	We evenly divided the work amongst ourselves and if any issues arose, we would contact the other team member and discuss and create a plan of action.
	
3)	The commits we create would be detailed but not too long. We used pull requests which would enable us to check each other's code and help spot any bugs
		earlier in the flow. This reduced the need for major refactoring later on.

4)	Commits were pushed up regularly and in small increments where possible.

5)	All classes, packages, methods and variables were given descriptive names.

6)	Design patterns such as Singleton, Facade, Observer and Model-View-Controller were used.

7)	We did not test getters and setters, methods whose only function is to call another method,
		or the Observer/View/Controller methods, where all the logic of the methods were tested in other classes.

8)	UML diagrams were created using Papyrus and ObjectAid tools.
	


		

