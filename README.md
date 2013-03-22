Moonlight
=========
Game design - Moonlight.

----------Contents----------
1.Plot
  1.1 Background
	1.2 Aims of main char
	1.3 What happens (spoilers!)
2.Gameplay
	2.1 Loading Screen
	2.2 Main menu
	2.3 Gameplay
		2.3.1 Moving
		2.3.2 Talking
		2.3.3 Interacting with Objects
			2.3.3.1 Lever
			2.3.3.2 'Armour'
	2.4 Death
3.Music
	3.1 Loading Screen
	3.2 Main menu
	3.3 Gameplay
		3.3.1 Moving
		3.3.2 Talking
	3.4 Death
4.Loading/Saving
	4.1 Loading
	4.2 Saving
5.Art
	5.1 Main character
		5.1.1 Walking animations
		5.1.2 'Armour' animations
		5.1.3 'Death' animations
	5.2 Background
		5.2.1 Tiled map creations
	5.3 Menu system
		5.3.1 Main menu
		5.3.2 Loading screen

----------/Contents----------

----------PLOT----------

1.1 - BACKGROUND:
Ye Oldy England (though not actually set on Earth, just earthlike). Main character is a young boy who's civilisation is struggling to cope with never-ending darkness - for some reason, the sun's not risen for 3 days streight. Crops are failing, everyone's getting pretty miserable, and nobody really knows what to do.
1.2 - OBJECTIVE: 
Your characters aim is to solve the mystery of the missing sun/find a way to bring it back. Only trouble is your civilisation has always been afraid of the moonlight - makes people go crazy.
1.3 - SPOILERVILLE:
You find out that your civilisation used to worship the sun in grand temples,  but now doesn't. This 'Angered the Sun God' who has just decided to leave you alone. The only way that you can think to get the sun back is to make a human sacrifice - you (maybe give the player an option to sacrifice someone else instead?). You do this without knowing if there even is a sun God but see it as the only way to try and save your civilisation.

----------/PLOT----------

----------GAMEPLAY----------

2.1 - LOADING SCREEN:
Loads all of the character sheets, music, maps - the full works. Maybe play some background music/do something to keep the player interested whilst they wait.
2.2 - MAIN MENU:
Give the option to Play, Load from checkpoint, Quit the game
2.3 - GAMEPLAY

2.3.1- MOVING:

Movement Keys - PC - Arrow Keys.

Interaction Key - PC - Spacebar.

Movement keys: move the character 1 tile in +-x/y coordiantes. Main character will change facing directions when buttons are pushed, regardless of collision detection preventing movement into adjacent tile.	Collision detection will prevent moving through impassible objects. Movement between tiles will be animated.

Interaction key: will be used to interact with objects in the game (see below). Inteaction button will have animation matching facing direction.

2.3.2 - TALKING:

It will be possible to talk/read information from signs when facing the right direction and using the interaction key.

2.3.3 - OBJECTS:

Interaction with objects through facing right direction+using interaction key will then use the object.

2.3.3.1 - Lever - Will deploy cover from moonlight in selected area.

2.3.3.2 - 'Armour' - Will provide temporary cover from moonlight that degrades over time.

2.3.4 - DEATH:
When the player steps into the moonlight, they die, respawning them the nearest checkpoint.

----------/GAMEPLAY----------

----------MUSIC----------

3.1 - LOADING SCREEN:
No music will be played whilst loading.
3.2 - MAIN MENU:
Main menu music will be played in main menu, looping after completion.
3.3.1 - MOVING:
Each different environment will have its own music.
3.3.2 - TALKING/INTERACTING:
Music volume will be dimmed when interacting.
3.3.4 - DEATH:
Death music played upon death.

----------/MUSIC----------

----------LOADING/SAVING----------

4.1 - After player death, load from correct checkpoint.
4.2 - Update checkpoint after reaching it.

----------/(LOADING/SAVING)----------

----------ART----------
5.1 - See character art in src/data folder.
5.2 -
5.3 - 
----------/ART----------





--------------------/Game Design Document--------------------
