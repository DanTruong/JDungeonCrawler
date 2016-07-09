# jDungeonCrawler
Dan Truong  
June 29, 2016

jDungeonCrawler is a text adventure game engine written in Java (much in the
vein of Zork). It takes in user input and parses text response to the player.

### Running the game

At this time, a GUI client is yet to be developed for the game. To run this
game, execute the Java executable (**\jDungeonCrawler\dist\jDungeonCrawler.jar**) from a terminal/command line environment.
Below is an example running in a Windows PowerShell environment:

```
PS C:\jDungeonCrawler\dist> java -jar ".\jDungeonCrawler.jar"
Enter a command (type "help" for a list of commands):
```

### Commands

Commands are case-sensitive

- **north** - Moves your character to the Sector in the North
- **south** - Moves your character to the Sector in the South
- **east** - Moves your character to the Sector in the East
- **west** - Moves your character to the Sector in the West
- **heat** - Makes the current Sector hotter
- **cool** - Makes the current Sector cooler
- **look** - Look at current information about the Sector
- **help** - Display the help commands
- **quit** - Quit the game
