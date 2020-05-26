/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.util.*;

public class FacePamphletDatabase implements FacePamphletConstants {

	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the database.
	 */
	public FacePamphletDatabase() {
		// You fill this in
	}
	
	
	/** 
	 * This method adds the given profile to the database.  If the 
	 * name associated with the profile is the same as an existing 
	 * name in the database, the existing profile is replaced by 
	 * the new profile passed in.
	 */
	public void addProfile(FacePamphletProfile profile) {
		profilesMap.put(profile.getName(), profile);// You fill this in
	}

	
	/** 
	 * This method returns the profile associated with the given name 
	 * in the database.  If there is no profile in the database with 
	 * the given name, the method returns null.
	 */
	public FacePamphletProfile getProfile(String name) {
		if (profilesMap.containsKey(name)) {
			return profilesMap.get(name);// You fill this in.  Currently always returns null.
		}else {
			return null;
		}
	}
	
	
	/** 
	 * This method removes the profile associated with the given name
	 * from the database.  It also updates the list of friends of all
	 * other profiles in the database to make sure that this name is
	 * removed from the list of friends of any other profile.
	 * 
	 * If there is no profile in the database with the given name, then
	 * the database is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		if (containsProfile(name)) {
			profilesMap.remove(name);
			profilesMapIt = profilesMap.keySet().iterator();
			while (profilesMapIt.hasNext()) {
				FacePamphletProfile nextProfile = profilesMap.get(profilesMapIt.next());
				nextProfile.removeFriend(name);	
			}
		}
	}
	
	
	/** 
	 * This method returns the name of the person with the most 
	 * friends or the joint person with the most friends
	 * 
	 * If there is no profile in the database and empty string is returned.
	 */
	public String mostFriends() {
		String mostPopular = "Nothing yet";
		profilesMapIt = profilesMap.keySet().iterator();
		System.out.println (profilesMapIt.hasNext());
		if (profilesMapIt.hasNext()) {
			FacePamphletProfile nextProfile = profilesMap.get(profilesMapIt.next());
			mostPopular = nextProfile.getName();
			System.out.println("mostFriends redefined as " + mostPopular);
			int mostFriends = nextProfile.getNumFriends();
			while (profilesMapIt.hasNext()) {
				nextProfile = profilesMap.get(profilesMapIt.next());
				if (nextProfile.getNumFriends() > mostFriends) {
					mostFriends = nextProfile.getNumFriends();
					mostPopular = nextProfile.getName();
				}
			}
		}return mostPopular;
	}
	
	
	/** 
	 * This method returns an iterator, for iterating through the profiles map.
	 */
	public HashMap <String, FacePamphletProfile> getProfileMap() {	
		return profilesMap;
	}
	
	/** 
	 * This method returns true if there is a profile in the database 
	 * that has the given name.  It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		return (profilesMap.containsKey(name));// You fill this in.  Currently always returns false.
	}

	/*private instance variables*/
	HashMap <String, FacePamphletProfile> profilesMap = new HashMap <String, FacePamphletProfile>();
	Iterator<String> profilesMapIt = profilesMap.keySet().iterator();
}
	