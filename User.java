/*
Written by Kai Olsen
Date Started and Completed: January 10th, 2022
This class is an object-oriented class containing the following encapsulated properties of one User:
username --> username
password --> password
userRatings --> LinkedList of float ratings given by a particular user
This class can also replace a particular rating for a movie, which is useful for the MoviePanel class
*/

//Imported Packages
import java.util.LinkedList;

//User Class
public class User {

    // creates fields
    private String username, password;
    private LinkedList<Float> userRatings;

    // default constructor
    public User() {
        this.username = "username";
        this.password = "password";
    }

    // constructor to set username and password for specific user
    public User(String userNam, String password) {
        this.username = userNam;
        this.password = password;
        // declares userRating linkedlist
        userRatings = new LinkedList<Float>();
    }

    // setter for username
    public void setUserNam(String username) {
        // if username is empty, set default
        if (username.equals("")) {
            this.username = "user name";
        } else {
            // else set actual username
            this.username = username;
        }
    }

    // setter for password
    public void setPassword(String password) {
        // if password is empty, set default
        if (password.equals("")) {
            this.password = "password";
        } else {
            // else set actual password
            this.password = password;
        }
    }

    // getter for username
    public String getUserNam() {
        return this.username;
    }

    // getter for password
    public String getPassword() {
        return this.password;
    }

    // getter for formatted username and password
    public String toString() {
        return this.username + "," + this.password;
    }

    // method that adds specific rating to linkedlist
    public void addRating(float rating) {
        this.userRatings.add(rating);
    }

    // setter for ratings
    public void setRatings(LinkedList<Float> tempList) {
        this.userRatings = tempList;
    }

    // method that is used to replace a rating from the linkedlist
    public void replaceRating(float tempNum, int index) {
        // removes rating off sepcified index
        this.userRatings.remove(index);
        // adds rating at specifc index
        this.userRatings.set(index, tempNum);

        try {
            this.userRatings.set(index, tempNum);
        } catch (IndexOutOfBoundsException e) {
            // ignore
        }
    }

    // getter for ratings
    public LinkedList<Float> getRatings() {
        return this.userRatings;
    }

}
