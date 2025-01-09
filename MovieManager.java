/*
Written by Dev M.
Date Started: January 12th, 2022
Date Completed: January 26th, 2022
This class is used to generate a particular array of movies, and must be able to sort them as well.
*/

//Imported Packages
import java.io.*;
import java.util.LinkedList;

//MovieManager Class
public class MovieManager {

    private Movie[] movies;// crates an array of movies
    private LinkedList<User> tempList;// creates linked list for users

    public MovieManager(char sortVal) throws IOException {// constructor that takes a char argunmant for sorting type
        movies = new Movie[countLinesOfFile("MoreMovieData.csv")];// creates array of movies
        setMovies();// fills movies

        if (sortVal == 'H') {// sort Highest to lowest
            sortByAvgRating(true);
        } else if (sortVal == 'L') {// sort Lowest to highest
            sortByAvgRating(false);
        } else if (sortVal == 'R') {// shuffle movies
            this.movies = sortRandomly(this.movies);
        }

    }

    public MovieManager(char sortVal, int indexOfUser, LinkedList<User> list) throws IOException {// constructor that
                                                                                                  // specifies user
        movies = new Movie[countLinesOfFile("MoreMovieData.csv")];// creates array
        setMovies();// fills movie array

        if (sortVal == 'H') {// sort Highest to lowest
            this.movies = unrated(this.movies, indexOfUser);
            sortByAvgRating(true);
        } else if (sortVal == 'L') {// sort Lowest to highest
            this.movies = unrated(this.movies, indexOfUser);
            sortByAvgRating(false);
        }

        try {
            alignArrs(list, this.movies);// align the user rateings with the newly modiied movie rateings
        } catch (NullPointerException e) {

        }

    }

    public MovieManager(LinkedList<User> userList, int indexOfUser) throws IOException {// recomends the highest rated
                                                                                        // movies by the closest user
        movies = new Movie[countLinesOfFile("MoreMovieData.csv")];// initializes the array
        setMovies();// fills the movies array

        float product[] = new float[movies.length];// creates and unitializes similarity array
        float sum = 0;// running total; for products
        for (int i = 0; i < movies.length; i++) {// fill product array
            product[i] = movies[i].getRatings().get(1) * movies[i].getRatings().get(24);
            sum = sum + product[i];
        }

        this.tempList = userList;// coppys user list to movie manager

        User mostSimilarUser = getSimilarUser(tempList, indexOfUser);// most similar user user created and st=et to the
                                                                     // most similar user
        int index1 = searchNamFile(mostSimilarUser.getUserNam());// finds index of most similar user

        LinkedList<Movie> tempMovies = new LinkedList<Movie>();// creates movie linked list

        for (int i = 0; i < this.movies.length; i++) {// fills movies list from movies array, discarding movies watched
                                                      // by the user and not watched by the most similar user

            if (movies[i].getRatings().get(index1) != 0 && movies[i].getRatings().get(indexOfUser) == 0) {
                tempMovies.add(movies[i]);
            }
        }

        this.movies = linkedListToArray(tempMovies);// puts movies list in movies array

    }

    public void setNewMovies(Movie[] list) {// replaces the movie array with new one
        this.movies = list;
    }

    public void setMovies() throws IOException {// fills movies from file
        BufferedReader reader = new BufferedReader(new FileReader("MoreMovieData.csv"));// buffered reader to read file

        for (int i = 0; i < movies.length; i++) {
            String line = reader.readLine();// reads line from file
            String bits[] = line.split(",");// breaks up line into strings

            LinkedList<Float> rates = new LinkedList<Float>();// creates link list for rateings

            for (int j = 1; j < bits.length; j++) {
                rates.add(Float.parseFloat(bits[j]));// fills rating from bits for movie
            }

            this.movies[i] = new Movie(bits[0], rates, i);// puts movie with stats in array
        }

        reader.close();
    }

    public Movie[] getMovieArray() {// returns movie array
        return this.movies;
    }

    private int countLinesOfFile(String fileNam) {// counts the number of lines in a file
        int lineNum = 0;// line counter
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileNam));// bufferedreader for the file to count
                                                                                // lines out of
            String ted = reader.readLine();// ted is a goldfish so he can only remember one line
            while (ted != null) {// until ted stops talki
                lineNum++;
                ted = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR. FILE NOT FOUND");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineNum;
    }

    // method to swap information in array
    public void swap(boolean whichOrder, int beginningIndex, int endIndex) {
        String tempNam = this.movies[beginningIndex].getMovieNam();
        this.movies[beginningIndex].setMovieNam(this.movies[endIndex].getMovieNam());
        this.movies[endIndex].setMovieNam(tempNam);

        LinkedList<Float> tempRatings = this.movies[beginningIndex].getRatings();
        this.movies[beginningIndex].setRatings(this.movies[endIndex].getRatings());
        this.movies[endIndex].setRatings(tempRatings);

        String tempPath = this.movies[beginningIndex].getFileString();
        this.movies[beginningIndex].setFileString(this.movies[endIndex].getFileString());
        this.movies[endIndex].setFileString(tempPath);

        float tempAvg = this.movies[beginningIndex].getAvgRating();
        this.movies[beginningIndex].setAvgRating(this.movies[endIndex].getAvgRating());
        this.movies[endIndex].setAvgRating(tempAvg);
    }

    // method which sorts the average ratings for each movie from highesr to lowest
    // & lowest to highest
    public void sortByAvgRating(boolean highestToLowest) {
        for (int i = 0; i < this.movies.length; i++) {
            for (int j = 0; j < this.movies.length - 1; j++) {
                // if variable equals true, sort average rating from highest to lowest
                if (highestToLowest == true && (this.movies[j].getAvgRating() < this.movies[j + 1].getAvgRating())) {
                    swap(highestToLowest, j, j + 1);
                    // else if variable is false, sort average rating from lowest to highest
                } else if (highestToLowest == false
                        && (this.movies[j].getAvgRating() > this.movies[j + 1].getAvgRating())) {
                    swap(highestToLowest, j, j + 1);
                }
            }
        }
    }

    // method which randomizes movie to be displayed on movie panel later
    public Movie[] sortRandomly(Movie[] movies) {
        Movie[] outputArr = new Movie[movies.length];
        Movie[] leftovers = movies;
        for (int i = 0; i < outputArr.length; i++) {
            int randIndex = (int) (Math.random() * (leftovers.length - 1));
            outputArr[i] = leftovers[randIndex];
            Movie[] temp = leftovers;
            leftovers = new Movie[temp.length - 1];
            for (int x = 0; x < temp.length; x++) {
                if (x < randIndex) {
                    leftovers[x] = temp[x];
                } else if (x > randIndex) {
                    leftovers[x - 1] = temp[x];
                }
            }
        }
        return outputArr;
    }

    // method which puts linkedlist info into array
    public Movie[] linkedListToArray(LinkedList<Movie> list) {
        Movie[] outArr = new Movie[list.size()];
        for (int i = 0; i < outArr.length; i++) {
            outArr[i] = list.get(i);
        }
        return outArr;
    }

    // method which finds unrated movies
    public Movie[] unrated(Movie[] movies, int userIndex) {
        LinkedList<Movie> unratedMovies = new LinkedList<Movie>();
        for (int i = 0; i < movies.length; i++) {
            if (movies[i].getRatings().get(userIndex) == 0) {
                unratedMovies.add(movies[i]);
            }
        }
        Movie[] outArr = linkedListToArray(unratedMovies);
        return outArr;
    }

    // methdo which sawps 2 users depdning on index passed through
    public void swapUsers(LinkedList<User> uList, int currentUser, int otherUser) {
        User tempUser = uList.get(currentUser);
        uList.set(currentUser, uList.get(otherUser));
        uList.set(otherUser, tempUser);
    }

    // method which uses dot product to get similar user (recomendation algorithm)
    public User getSimilarUser(LinkedList<User> userList, int indexOfUser) {
        LinkedList<User> newLinkedList = userList;
        alignArrs(newLinkedList, this.movies);
        User current = newLinkedList.get(indexOfUser);

        // if user is not equal to 0, swap user
        if (indexOfUser != 0) {
            swapUsers(newLinkedList, indexOfUser, 0);
        }

        newLinkedList.remove(0);
        float[] dotProducts = new float[newLinkedList.size()];

        // compares logged in user to everyone else and their rating to get similarity
        // value
        for (int i = 0; i < newLinkedList.size(); i++) {

            float sum = 0F;

            float[] tempProduct = new float[newLinkedList.get(0).getRatings().size()];

            for (int j = 0; j < tempProduct.length; j++) {
                tempProduct[j] = (current.getRatings().get(j)) * (newLinkedList.get(i).getRatings().get(j));
            }

            for (int x = 0; x < tempProduct.length; x++) {
                sum += tempProduct[x];
            }

            dotProducts[i] = sum;

        }

        sortDotProducts(dotProducts, newLinkedList);

        return (newLinkedList.get(0));

    }

    // method which sorts the similarity values for each user
    public void sortDotProducts(float[] array, LinkedList<User> uList) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1; j++) {
                if (array[j] < array[j + 1]) {
                    float temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapUsers(uList, j, j + 1);
                }
            }
        }
    }

    // method which makes sure the user linkelist & the movie array contain the same
    // data
    public void alignArrs(LinkedList<User> users, Movie[] movies) {
        for (int j = 0; j < users.size(); j++) {
            users.get(j).setRatings(new LinkedList<Float>());
            for (int i = 0; i < movies.length; i++) {
                users.get(j).addRating(movies[i].getRatings().get(j));
            }
        }
    }

    // method which searches the "NamAndPass.csv" file for a specific username
    public int searchNamFile(String userNam) {
        int loc = -1;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("NamAndPass.csv"));
            int lines = countLinesOfFile("NamAndPass.csv");
            for (int i = 0; i < lines; i++) {
                String line = reader.readLine();
                String[] bits = line.split(",");
                if (userNam.equals(bits[0])) {
                    loc = i;
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loc;
    }

    // method which searhces for a specific movie name from the movie array
    public int searchMovieNams(Movie[] list, String nam) {
        int loc = -1;
        for (int i = 0; i < list.length; i++) {
            if (nam.equalsIgnoreCase(list[i].getMovieNam())) {
                loc = i;
                break;
            }
        }
        return loc;
    }

}
