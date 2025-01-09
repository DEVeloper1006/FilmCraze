/*
Written by Dev M.
Date Started & Completed: January 12th, 2022
This class is an object-oriented class containing the following encapsulated properties for one movie:
movieNam --> movie name
ratings --> LinkedList of Float ratings for one particular movie
avgRating --> average rating of a movie
filePath --> File Path to gain image of a movie
This class can calculate an average rating and set an image path when given a particular index
*/

//imported packages
import java.io.*;
import java.util.LinkedList;

public class Movie {

    // Creates fields
    private String movieNam;
    private LinkedList<Float> ratings;
    private float avgRating;
    private String filePath;

    // Overloaded constrcutors

    // This constructor takes in movie name, linkedlist of rating, and index and
    // uses it to set data
    public Movie(String nam, LinkedList<Float> rates, int index) throws IOException {
        setMovieNam(nam);
        setRatings(rates);
        // calculates average rating of movie and assigns it to variable
        this.avgRating = calcAvgRating(this.ratings);
        setFilePath(index);
    }

    // This movie constructor takes in average rating, movie name, name of poster
    // file path, and linkedlist of ratings
    public Movie(String nam, LinkedList<Float> rates, float avgRating, String filePath) {
        // sets average rating
        setAvgRating(avgRating);
        // sets movie name
        setMovieNam(nam);
        // set file path
        setFileString(filePath);
        // sets ratings
        setRatings(rates);
    }

    // setter for movie name
    public void setMovieNam(String nam) {
        this.movieNam = nam;
    }

    // getter for movie name
    public String getMovieNam() {
        return this.movieNam;
    }

    // setter for linkedlist of ratinsg
    public void setRatings(LinkedList<Float> rates) {
        this.ratings = rates;
    }

    // getter for Linkedlist of ratings
    public LinkedList<Float> getRatings() {
        return this.ratings;
    }

    // getter for the average rating
    public float getRating() {
        return this.avgRating;
    }

    // method which replaces the rating in a linkedlist
    public void replaceRating(float rating, int index) {
        this.ratings.remove(index);
        this.ratings.add(index, rating);
    }

    // method that calculates the average rating for the movie
    public float calcAvgRating(LinkedList<Float> ratings) {
        // creates and declares float variables
        float sum = 0;
        float numbers = 0;
        float avg;
        // for loop to collect a sum of ratings discluding 0's (unrated movies)
        for (int i = 0; i < ratings.size(); i++) {
            if (ratings.get(i) != 0) {
                sum += ratings.get(i);
                numbers++;
            }
        }
        // average rating is sum divided by rated movies
        avg = sum / numbers;
        return avg;
    }

    // setter for average rating
    public void setAvgRating(float avg) {
        this.avgRating = avg;
    }

    // getter for average rating
    public float getAvgRating() {
        return this.avgRating;
    }

    // setter for the movie poster file name/path
    public void setFileString(String imagePath) {
        this.filePath = imagePath;
    }

    // setter for movie poster file name/path
    public void setFilePath(int index) throws IOException {
        // creates Bufferedreader and filereader to read "movieFilePath.csv" file
        BufferedReader kb = new BufferedReader(new FileReader("movieFilePath.csv"));
        // for loop to find the index of the movie index passed in
        for (int i = 0; i <= index; i++) {
            // reads line of file and temporaly asisgns it to temp variable
            String temp = kb.readLine();
            // if the i value is equal to index then filePath variable is equal to name of
            // specific file
            if (i == index) {
                this.filePath = temp;
            }
        }
        // close Bufferedreader
        kb.close();
    }

    // getter for filePath
    public String getFileString() {
        return this.filePath;
    }

}
