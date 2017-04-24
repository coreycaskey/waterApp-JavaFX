/*
 Created by Corey Caskey on 4/24/2017
 */

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class UserList {

	private static Map<String, User> userList = new HashMap<>();
    public static User currentUser;

    public static boolean addUser(User user) {
        Set<String> keys = userList.keySet();

        if (keys.contains(user.getEmail())) {
        	return false;
        }
        userList.put(user.getEmail(), user);
        return true;
    }

    public static Map<String, User> getUserList() {
        return userList;
    }

    public static User getUser(String email) {
        return userList.get(email);
    }

    public static ArrayList<User> getUserListValues() {
    	ArrayList<User> users = new ArrayList<>();
    	for (User user : userList.values()) {
    		users.add(user);
    	}
        return users;
    }

    public static ArrayList<String> getPasswordList() {
        ArrayList<String> passwords = new ArrayList<>();
        for (User user : userList.values()) {
            passwords.add(user.getPassword());
        }
        return passwords;
    }

    public static void setUserList(Map<String, User> userMap) {
        userList = userMap;
    }

    public static ArrayList<String> getUserListKeys() {
		ArrayList<String> stringArr = new ArrayList<>();
    	for (String email : userList.keySet()) {
    		stringArr.add(email);
    	}
		return stringArr;
    }

    public static String getValue(String email) {
        return userList.get(email).getPassword();
    }

    public static void setCurrentUser(String email) {
        currentUser = getUser(email);
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
