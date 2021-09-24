import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/*
 * Class Room - a room in an adventure game.
 *
 * This class is the main class of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

class Room 
{
    private String description;
    private HashMap<String,Room> exits;        // stores exits of this room.
    private HashMap<String,Boolean> unlockedExits = new HashMap<String,Boolean>();
    private ArrayList<String> exitKeys = new ArrayList<String>();
    ArrayList<Items> items = new ArrayList<Items>();
    public Items lockedRoomKey = new Items("","");
    private String roomItemString = "";
    private String lockedRoomMessage = "";

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "in a kitchen" or "in an open court 
     * yard".
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap();
    }

    /**
     * Define an exit from this room.
     */
    public void setExit(String direction, Room neighbor, boolean opened) 
    {
        exits.put(direction, neighbor);
        unlockedExits.put(direction, opened);
        exitKeys.add(direction);
    }

    public void setRoomKey(Items key, String lockedMessage) {
    	lockedRoomKey = key;
    	lockedRoomMessage = lockedMessage;
    }
    /**
     * Return the description of the room (the one that was defined in the
     * constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a long description of this room, in the form:
     *     You are in the kitchen.
     *     Exits: north west
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set keys = exits.keySet();
        for(Iterator iter = keys.iterator(); iter.hasNext(); )
            returnString += " " + iter.next();
        returnString +=roomItemString;
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     */
    public Room getExit(String direction, Room currentRoom) 
    {
    	if (!exits.containsKey(direction)) {
    		return null;
    	}
        if (unlockedExits.get(direction)) {
        	return (Room)exits.get(direction);
        } else {
        	System.out.println(lockedRoomMessage);
        	return currentRoom;
        }
    }
    
    //get item from room
    public Items getItem(int index) {
    	return items.get(index);
    }
    public Items getItem(String itemName) {
    	@SuppressWarnings("null")
		Items toReturn = null;
    	for (int i=0;i<items.size();i++) {
    		if (items.get(i).name.equals(itemName)) {
    			toReturn = items.get(i);
    		}
    	}
    	return toReturn;
    }
    
    //remove an item from the room
    public void removeItem(String itemName) {
    	items.remove(getItem(itemName));
    	roomItemString = "";
    }
    
    //set the items in the room and the message saying whats there
    public void setItem(Items newItem, String itemString) {
    	items.add(newItem);
    	roomItemString=itemString;
    	return;
    }
    
    //list room items was deleted because I accidentally made it irrelevent
    
    //this is a function to unlock the rooms locked door
    public void unlockLockedDoor() {
    	lockedRoomKey=new Items("","");
    	for (int i=0;i<exitKeys.size();i++) {
    		if (!unlockedExits.get(exitKeys.get(i))) {
    			unlockedExits.put(exitKeys.get(i),true);
    			return;
    		}
    	}
    }
}

