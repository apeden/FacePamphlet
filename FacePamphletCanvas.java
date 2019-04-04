/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {


	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		// You fill this in
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {	
		if (message != null) {
			remove(message);
		}message = new GLabel (msg);
		message.setFont(MESSAGE_FONT);
		message.setLocation((APPLICATION_WIDTH-message.getWidth())/2, 
							APPLICATION_HEIGHT-BOTTOM_MESSAGE_MARGIN);
		add(message);
	}
		
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		if (profile != null) {
			addName(profile.getName());
			if (profile.getImage() != null) {
				addImage(profile.getImage());
			}else {
				addEmptyRec();
			}addStatus(profile.getStatus());
			addFriends(profile.getFriends());
		}
	}		
	

	public void displayAllPersons(HashMap <String, FacePamphletProfile> profileDatabaseMap) {
		Iterator<String> profileDatabaseMapIt = profileDatabaseMap.keySet().iterator();
		System.out.println("display all persons called");
		removeAll();
		addTentRec(4);
		addFriends(profileDatabaseMapIt);////passed a string iterator
	}
	
	
	
	private void addEmptyRec() {
		add(new GRect(LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN, 
										IMAGE_WIDTH, 
										IMAGE_HEIGHT));
		addLabel("No Image", 
					PROFILE_IMAGE_FONT, 
					LEFT_MARGIN + (IMAGE_WIDTH/2), 
					TOP_MARGIN + (IMAGE_HEIGHT/2),
					true,
					Color.black);
	}
	
	private void addTentRec(int numTents) {
		for (int i = 0; i < numTents; i++) {
			add(new GRect(LEFT_MARGIN + IMAGE_WIDTH + (LEFT_MARGIN+TENT_WIDTH)*(i%2),
					TOP_MARGIN + IMAGE_MARGIN + (TOP_MARGIN + TENT_HEIGHT)*(i/2), 
					TENT_WIDTH, 
					TENT_HEIGHT));
		}
		addLabel("Tent", 
					PROFILE_IMAGE_FONT, 
					LEFT_MARGIN + IMAGE_WIDTH + (TENT_WIDTH/2), 
					TOP_MARGIN + (TENT_HEIGHT/2),
					true,
					Color.black);
	}
	
	private void addStatus(String status) {		
		String statusForDisplay = "No current status";
		if (!status.isEmpty()) {
			statusForDisplay = status;
		}addLabel(statusForDisplay, 
			PROFILE_STATUS_FONT, 
			LEFT_MARGIN, 
			TOP_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN,
			false,
			Color.black);
	}
	
	private void addName(String name) {
		addLabel(name, 
				PROFILE_NAME_FONT, 
				LEFT_MARGIN, 
				TOP_MARGIN,
				false,
				Color.blue);
	}	
	
	private void addImage(GImage image) {
		image.setLocation(LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN);
		image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
		add(image);	
	}	
	
	public void addFriends(Iterator<String> getFriendsIt) {
		addLabel("Friends:", 
				PROFILE_FRIEND_LABEL_FONT, 
				APPLICATION_WIDTH/2, 
				TOP_MARGIN, 
				false, 
				Color.black);
		double count = 1.0;
		while (getFriendsIt.hasNext()){ 
			String friend = getFriendsIt.next();
			GLabel friendLabel = new GLabel(friend); 
			friendLabel.setFont(PROFILE_FRIEND_FONT);
			friendLabel.setLocation(
									LEFT_MARGIN + IMAGE_WIDTH, 
									TOP_MARGIN + IMAGE_MARGIN + (count * friendLabel.getHeight()));
			count += 1.0;
			add(friendLabel);
		}
	}				
		
	private void addLabel(String label, String font, 
			double xleftLocation, double ytopLocation, boolean centre, Color color) {
		GLabel newLabel = new GLabel (label);
		newLabel.setFont(font);
		newLabel.setColor(color);
		if (centre) {
			newLabel.setLocation(xleftLocation - newLabel.getWidth()/2, ytopLocation + newLabel.getHeight()/2);
		} else  
			newLabel.setLocation(xleftLocation, ytopLocation);	
		add(newLabel);
	}
	
	
	/*private instance variables*/
	GLabel message;
}

