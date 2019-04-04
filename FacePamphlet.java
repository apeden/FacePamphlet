/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;

import java.awt.Canvas;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		
		profileDatabase = new FacePamphletDatabase();
		add(profileCanvas);
		nameField = new JTextField(TEXT_FIELD_SIZE);
		statusField = new JTextField(TEXT_FIELD_SIZE);
		statusField.addActionListener(this);
		pictureField = new JTextField(TEXT_FIELD_SIZE);
		pictureField.addActionListener(this);
		friendField = new JTextField(TEXT_FIELD_SIZE);
		friendField.addActionListener(this);
		add(new JButton("Display Everyone"), NORTH);
		add(new JLabel("Name"), NORTH);
		add(nameField, NORTH);
		add(new JButton("Add"), NORTH);
		add(new JButton("Delete"), NORTH);
		add(new JButton("Lookup"), NORTH);
		add(statusField, WEST);
		add(new JButton("Change Status"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(pictureField, WEST);
		add(new JButton("Change Picture"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(friendField, WEST);
		add(new JButton("Add Friend"), WEST);
		
		addActionListeners();
		
		// You fill this in
		
    }
   
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
    	String cmd = e.getActionCommand();
		if ((cmd.equals("Add")) && (checkJTextField(nameField))) {
			String name = nameField.getText();
			if (profileDatabase.containsProfile(name)) {
				currentProfile = profileDatabase.getProfile(name);
				messageString = "A profile already exists for " + name;		
			}else {		
				FacePamphletProfile newProfile = new FacePamphletProfile(name);
				profileDatabase.addProfile(newProfile);
				currentProfile = newProfile;
				messageString = "";
			}
			
		}else if ((cmd.equals("Delete")) && (checkJTextField(nameField))) {
			currentProfile = null;
			String name = nameField.getText();
			if (profileDatabase.containsProfile(name)) {
				profileDatabase.deleteProfile(name);
				currentProfile = null;
				messageString = "Profile for " + name + " was deleted.";			
			}
			
		}else if ((cmd.equals("Lookup")) && (checkJTextField(nameField))) {
			String name = nameField.getText();
			if (profileDatabase.containsProfile(name)) {
				FacePamphletProfile profile = profileDatabase.getProfile(name);
				currentProfile = profile;
				messageString = name+" has "+ Integer.toString(profile.getNumFriends()) +" friends";
				
			} else {
				currentProfile = null;
				messageString = "The profile for " + name + " does not exist.";
			}
			
		}else if (cmd.equals("Display Everyone")) {
			if (profileDatabase.getProfileMap().size() > 24) {
				messageString = "Too many people to assign to tents";
			}
			currentProfile = null;
			messageString = "The most popular person is "+ profileDatabase.mostFriends();
			println("Display everyone button depressed");
			print(profileDatabase.getProfileMap().toString());
			profileCanvas.displayAllPersons(profileDatabase.getProfileMap());//passed a hashmap
		
		}else if (currentProfile == null) {
			messageString = "Please choose a profile in 'name' before adding a status, "
					+ "picture or friend.";
			
    	}else if ((cmd.equals("Change Status"))||(e.getSource() == statusField)) {
    		if (checkJTextField(statusField)) { 			
    			currentProfile.setStatus(statusField.getText());
    			messageString = currentProfile.getName() + " is" +statusField.getText();
    		}
    		
		}else if ((cmd.equals("Change Picture"))||(e.getSource() == pictureField)) {
    		if  (checkJTextField(pictureField)) {
    			GImage image = null;
    			try {
    				image = new GImage(pictureField.getText());
    				currentProfile.setImage(image);
    				messageString = "";
    				
    			}catch (ErrorException ex) {
    				messageString = "Sorry, we cannot find and image with this name";
    			}// Code that is executed if the filename cannot be opened.
    		}
    		
		}else if ((cmd.equals("Add Friend"))||(e.getSource() == friendField)) {
			println("Add friend depressed");
			if (friendField.getText().equalsIgnoreCase(currentProfile.getName())){
					messageString = "You can't add yourself as a friend!!";
			}else if  (checkJTextField(friendField)) {
				if (profileDatabase.containsProfile(friendField.getText())) {
					currentProfile.addFriend(friendField.getText());
					messageString = "";
					profileDatabase.getProfile(friendField.getText()).addFriend(currentProfile.getName());
				}else {
					messageString = "A profile does not yet exist for " + friendField.getText();
				}
				
			}	
		}
		println("Message displayed is " + messageString);
		profileCanvas.showMessage(messageString);
		if (!cmd.equals("Display Everyone")) {
			profileCanvas.displayProfile(currentProfile);
			profileCanvas.showMessage(messageString);
		}
	}
			
	public boolean checkJTextField(JTextField field) {
		String regex = "[a-zA-Z0-9]+";
		for (int i = 0; i < field.getText().length(); i ++) {
			if (field.getText().substring(i, i+1).matches(regex)) {
				return true;
			}
		}return false;
	}
	
    	
    	// You fill this in as well as add any additional methods
    /*private instance variables*/
    JTextField nameField;
    JTextField statusField;
    JTextField pictureField;
    JTextField friendField;
    String messageString = "";
    FacePamphletProfile currentProfile;
    FacePamphletDatabase profileDatabase;
    FacePamphletCanvas profileCanvas = new FacePamphletCanvas();
	
}
