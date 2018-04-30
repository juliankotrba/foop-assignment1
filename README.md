# First Assignment

## Bot Racer – Network game

Design and implement a network-based game in an object-oriented programming language of your choice (but not in a language specialized for game development). The game shall correspond to the following description.
Simple autonomous bots move around in a labyrinth trying to find an exit. Each bot behaves according to a simple algorithm depending on sensor values and the contents of its memory. Sensors are quite limited: A bot can see marks on the field where the bot is currently located at as well as insurmountable wall fragments on immediately surrounding fields. Bots can remember where they have been before and what their sensors saw in the past.
Each player (real person or the computer, at least four in each game) has its own bot and supports this bot to be the first one to find an exit out of the labyrinth. Players can influence the behaviour of bots (not just their own bots, but all bots) by placing marks on fields and removing other marks. Marks can, for example, express facts like

* "stay in this area, an exit is close"

* "move away from this area, there is no exit nearby"

* "turn left/right now"

* "change the algorithm to ..."

* "clear the memory"

Such marks can be used to help the own bot and to confuse others. Each player uses his/her own computer connected to a network. The communication between the computers shall be efficient enough to avoid noticeable delays for at least four players. All players shall
get the same information at about the same time.
Please select appropriate details of the game by yourselves. Give
your fancy full scope.