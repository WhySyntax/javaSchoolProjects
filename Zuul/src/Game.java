import java.util.ArrayList;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

class Game 
{
    private Parser parser;
    private Room currentRoom;
    Room escaped, outside, theatre,pub,backRoom,office,oneTwenty,teachersLounge,janitorRoom,custodialHall,oneHall,twoHall,threeHall,overworkedTeachersOffice,homeroom,theGreatBlackInescapableVoidSouthOfThreeHall;
    Items mainKey,custodialKey,detentionSlip,computingPass,dragonMask,loungeKey,pint,coffee,unfinishedHomework,skeleton;
    ArrayList<Items> playerInventory = new ArrayList<Items>();
    Boolean finished = false;
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }
    
    public static void main(String[] args) {
    	Game myGame = new Game();
    	myGame.play();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // create the items
    	mainKey=new Items("MKey","it may get you out of the building");
    	custodialKey=new Items("CKey","it can open the door to the janitors closet");
    	detentionSlip=new Items("Detention","What Have You Done, Go to the office and we'll call your parents");
    	computingPass=new Items("Pass","allows you to finish your homework in 1-20");
    	dragonMask=new Items("Mask","Just a prop for the spring play, though it may be able to scare an interdimensional creature if those exist");
    	loungeKey=new Items("LKey","it can get you into the teachers lounge, a heaven of coffee and snacks, but only if you're careful");
    	pint=new Items("Pint","Who in their right mind would give this to a minor, though it may be able to help take someones mind off of all the work they have to do");
    	coffee=new Items("Coffee","the drink of the gods according to some, though not as strong as a pint");
    	unfinishedHomework=new Items("Homework","if you take this to class they may let you finish it elsewhere");
    	skeleton=new Items("Skeleton","proof that you arent the first to venture into the inescapable void below 3-hall, though you wont be the last either");
    	
        // create the rooms
    	escaped = new Room("outside the school, you have escaped");
    	outside = new Room("in the outer common area of the school");
    	theatre = new Room("in the theatre, they're hosting a fall play in a few weeks");
    	pub = new Room("the campus pub, why do we have one again");
    	backRoom = new Room("the backroom of the pub, a teacher is sitting here doing their work");
    	office = new Room("in the front office, you're almost there");
    	oneTwenty = new Room("in 1-20, the coolest place in the world");
    	teachersLounge = new Room("in the teachers lounge, or as I like to call it, supposed heaven");
    	janitorRoom = new Room("in the janitors closet, it is very clean");
    	custodialHall = new Room("in a small mini-hall that only leads to the custodial office");
    	oneHall = new Room("in 1-hall");
    	twoHall = new Room("in 2-hall");
    	threeHall = new Room("in 3-hall, dont go south");
    	overworkedTeachersOffice = new Room("in the office of an overworked teacher");
    	homeroom = new Room("in homeroom");
    	theGreatBlackInescapableVoidSouthOfThreeHall = new Room("in a inescapable void under 3-hall, I told you not to go here, but you just had to ignore me, didn't ya");
        
        // initialise room exits and set up which keys are used in which rooms
        //outer commons
    	outside.setExit("east", theatre,true);
        outside.setExit("north", oneTwenty,false);
        outside.setExit("west", pub,true);
        outside.setExit("south", oneHall, true);
        outside.setRoomKey(computingPass,"You're only allowed in 1-20 if you have a pass to do you're homework there");

        //theater
        theatre.setExit("west", outside,true);
        theatre.setItem(dragonMask,"\nthere is a dragon mask in the room, I think it was for the musical last spring\nwrite 'get Mask' to pickup");

        //pub
        pub.setExit("east", outside,true);
        pub.setExit("west", backRoom, false);
        pub.setItem(pint, "\nSomeone left a pint on the table, maybe pick it up?\nwrite 'get Pint' to pickup");
        pub.setRoomKey(coffee, "you hear some muttering behind the door, bring me coffee if you want to get in here, I need caffeine");
        
        //1-20
        oneTwenty.setExit("south", outside,true);
        oneTwenty.setExit("north", office, false);
        oneTwenty.setRoomKey(detentionSlip,"you can only go in there if you have detention or if you have an urgent matter to speak\nabout, neither of those apply right now");

        //pub backroom
        backRoom.setExit("east", pub, true);
        backRoom.setItem(loungeKey, "\nTheres a teacher in the room muttering to themselves, they left their key to the teachers lounge unattended, pick it up?\nwrite 'get LKey' to pickup");
        
        //front office
        office.setExit("south", oneTwenty, true);
        office.setExit("west", escaped, false);
        office.setRoomKey(mainKey, "Its the door to the front of the school, of course its locked, you need a key for this");
        office.setItem(custodialKey, "\nThe key to the janitors office is here, pick it up?\nwrite 'get CKey' to pickup");
        
        //void
        theGreatBlackInescapableVoidSouthOfThreeHall.setExit("unlisted", escaped, false);
        theGreatBlackInescapableVoidSouthOfThreeHall.setRoomKey(dragonMask, "the only exit to the void is guarded by a multi-dimensional being, perhaps it is afraid of dragons");
        theGreatBlackInescapableVoidSouthOfThreeHall.setItem(skeleton, "\nTheres a strange skeleton in the room, perhaps you can pick it up?\nwrite 'get Skeleton' to pick it up");
        
        //Janitors closet
        janitorRoom.setExit("east", custodialHall, true);
        janitorRoom.setItem(mainKey, "\nout of the corner of you eye you see the key out of the main office on one of the shelves, pick it up?\nwrite 'get MKey' to pickup");
        
        //janitor hall
        custodialHall.setExit("west",janitorRoom,false);
        custodialHall.setExit("east", oneHall, true);
        custodialHall.setRoomKey(custodialKey, "this room is locked, perhaps if you have the custodial key you could get in");
        
        //1 hall
        oneHall.setExit("north", outside, true);
        oneHall.setExit("south", twoHall, true);
        oneHall.setExit("east", overworkedTeachersOffice, false);
        oneHall.setExit("west", custodialHall, true);
        oneHall.setRoomKey(pint, "You hear a teacher yelling 'YOU CAN'T GET IN HERE UNLESS YOU BRING ME SOMETHING STRONGER THAN THIS COFFEE'");
        
        //OTO
        overworkedTeachersOffice.setExit("west", oneHall, true);
        overworkedTeachersOffice.setItem(coffee, "\nwith the teacher happily drinking their pint they have forgotten all about their coffee, pick it up?\nwrite 'get Coffee' to pick up'");
        
        //2 hall
        twoHall.setExit("north", oneHall, true);
        twoHall.setExit("south", threeHall, true);
        twoHall.setExit("west", teachersLounge, false);
        twoHall.setRoomKey(loungeKey, "thats the teachers lounge, you can't get in there unless you steal a key from an unaware teacher");
        
        //teachers lounge
        teachersLounge.setExit("east", twoHall, true);
        teachersLounge.setItem(detentionSlip, "there seems to be a pad so that students can give themselves a detention if they come here, do you want to write one?\nwrite 'get Detention' to pick it up");
        
        //3 hall
        threeHall.setExit("north", twoHall, true);
        threeHall.setExit("south", theGreatBlackInescapableVoidSouthOfThreeHall, true);
        threeHall.setExit("east", homeroom, false);
        threeHall.setRoomKey(unfinishedHomework, "You can only go back into homeroom if you have unfinished homework to do, try checking your backpack and unlocking the door if you have what you need");
        
        //homeroom
        homeroom.setExit("west", threeHall, true);
        homeroom.setItem(computingPass, "theres a pass to use the computer lab on the desk, pick it up?\nwrite 'get Pass' to pick up");
        
        
        //giving the player homework
        playerInventory.add(unfinishedHomework);
        
        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Adventure!");
        System.out.println("Adventure is a new, incredibly boring adventure game.");
        System.out.println("Your goal is to start at the outdoor commons of the school and try to leave the campus,\n"
        		+ "some doos may be locked, but you can get through them if you know how\n"
        		+ "Just remember to NEVER go below three hall");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("list")) {
        	checkToList(command);
        }
        else if (commandWord.equals("get")) {
        	getItem(command);
        }
        else if (commandWord.equals("unlock")) {
        	unlockLockedDoor(command,currentRoom);
        }
        return wantToQuit;
    }


	// implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university, trying to get out.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go unless the player is in void, there are no directions in the void
        	if (!currentRoom.equals(theGreatBlackInescapableVoidSouthOfThreeHall)) {
        		System.out.println("Go where?");
            	return;
        	} else {
        		command.manualSetWordTwo("unlisted");
        	}
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction,currentRoom);

        if (nextRoom == null)
            System.out.println("There is no door!");
        else if (currentRoom.equals(escaped)) {
        	System.out.println("You have escaped the school, congratulations on winning");
        	finished = true;
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    private void checkToList(Command command) {
    	if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("List what?");
            return;
        }
    	
    	String listed = command.getSecondWord(); 
    	if (listed.equals("inventory")) {
    		Items item = new Items("","");
    		String inventoryString = "";
    		for (int i=0;i<playerInventory.size();i++) {
    			item = playerInventory.get(i);
    			inventoryString+=item.name+": "+item.description;
    			if (i!=playerInventory.size()-1) {
    				inventoryString+=", ";
    			}
    		}
    		System.out.println(inventoryString);
    	}
    }
    
    private void getItem(Command command) {
		if (!command.hasSecondWord()) {
			System.out.println("Get what?");
			return;
		}
		String toGet = command.getSecondWord();
		
		Items item = currentRoom.getItem(toGet);
		if (item==null) {
			System.out.println("The item you are looking for is not here");
		} else {
			playerInventory.add(item);
			currentRoom.removeItem(toGet);
			System.out.println("You picked up a: "+item.name);
		}
	}
    
    private void unlockLockedDoor(Command command,Room room) {
		Items item = currentRoom.lockedRoomKey;
		if (item.name.equals("")) {
			System.out.println("There are no locked rooms here, or all rooms have been unlocked");
		} else if (playerInventory.contains(item)) {
			currentRoom.unlockLockedDoor();
			playerInventory.remove(item);
		} else {
			System.out.println("You dont have the proper key for the locked room");
		}
	}
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else
            return true;  // signal that we want to quit
    }
}
