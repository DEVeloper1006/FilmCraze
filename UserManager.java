//Written By: Dev Mody
//Date Started and Completed: January 11, 2022
//This class is used to read, add, and modify the different files while also changing data within the user linked list  

//Imported Packages
import java.io.*;
import java.util.LinkedList;

public class UserManager {

    public void readUserFile(LinkedList<User> list) {
        // surrounds everything with try catch
        try {
            // creates Bufferedreader and Filereader to read "NamAndPass.csv" file
            BufferedReader reader = new BufferedReader(new FileReader("NamAndPass.csv"));
            // creates and assigns integer variable to the number of lines in
            // "NamAndPass.csv" file
            // calls countLinesOfFile method which returns number of lines
            int lines = countLinesOfFile("NamAndPass.csv");

            // for loop that loops for the number of lines
            for (int i = 0; i < lines; i++) {
                // reads line of "NamAndPass.csv" file and assigns to variable
                String line = reader.readLine();
                // splits the line with regex "," and assigns it to array
                String bits[] = line.split(",");
                // adds the two bits of info (which is the user's username and password) to the
                // linkedlist
                list.add(new User(bits[0], bits[1]));
            }
            // close Bufferedreader
            reader.close();

            // catch exception and print error messages
        } catch (FileNotFoundException e) {
            System.out.println("ERROR. FILE NOT FOUND");
        } catch (IOException e) {
            System.out.println("ERROR. UNKNOWN ERROR");
        }
    }

    // method to seach usernames which takes in the linkedlist of user and search
    // key
    public int searchUserNams(LinkedList<User> uArray, String searchKey) {
        // default location equals -1
        int loc = -1;
        // for loop that loops for the size of the user array
        for (int i = 0; i < uArray.size(); i++) {
            // if the username is equal to the search key
            if (uArray.get(i).getUserNam().equalsIgnoreCase(searchKey)) {
                // location equal to index of for loop
                loc = i;
                break;
            }
        }
        // return location
        return loc;
    }

    // method whcih adds a user (new user most of the time) to the "NamAndPass.csv"
    // file. Takes in username and password
    public void addNamPass(String username, String password) {

        // surround eveything with try catch
        try {
            // creates Printwriter and Filewriter to write to "NamAndPass.csv" file
            PrintWriter writer = new PrintWriter(new FileWriter("NamAndPass.csv", true));
            // writes the properly formatted username and password to the the file
            writer.print("\n" + username + "," + password);
            // close Printwriter
            writer.close();
            // catch IOException and print error message
        } catch (IOException e) {
            System.out.println("UNKNOWN ERROR");
        }
    }

    // method which adds new data/ratings to "MoreMovieData.csv" file
    public void addNewData() {
        // surround with try catch
        try {
            // creates Bufferedreader and Filereader to read "MoreMovieData.csv" file
            BufferedReader br = new BufferedReader(new FileReader("MoreMovieData.csv"));
            // calls method to count lines of "MoreMovieData.csv" file and assigns it to
            // integer variable
            int lines = countLinesOfFile("MoreMovieData.csv");
            // creates a temporary string array with a length of the number of lines from
            // "MoreMovieData.csv"
            String[] tempArray = new String[lines];
            // for loop that loops for the number of lines that are in "MoreMovieData.csv"
            for (int i = 0; i < lines; i++) {
                // assigns specific index of the temporary array to the line of the
                // "MoreMovieData.csv" file
                tempArray[i] = br.readLine();
            }
            // close Bufferedreader
            br.close();
            // creates Printwriter and Filewirter to write to "MoreMovieData.csv" file
            PrintWriter out = new PrintWriter(new FileWriter("MoreMovieData.csv"));
            // for loop that loops for the number of lines that are in "MoreMovieData.csv"
            for (int i = 0; i < lines; i++) {
                // if i does not equal the number of lines minus 1
                if (i != lines - 1) {
                    // print line tempArray on "MoreMovieData.csv" with a added ",0"
                    out.println(tempArray[i] + ",0");
                } else {
                    // else print (not new line) tempArray on "MoreMovieData.csv" with a added ",0"
                    out.print(tempArray[i] + ",0");
                }
            }
            // close Printwriter
            out.close();
            // catch IOException and print error message
        } catch (IOException e) {
            System.out.println("UNKNOWN ERROR.");
        }
    }

    // method which counts the lines of the file name which is passed through
    private int countLinesOfFile(String fileNam) {

        int lineNum = 0;
        // surround everything with try catch
        try {
            // create Bufferedreader and Filereader to read file which is passed through as
            // a parameter
            BufferedReader reader = new BufferedReader(new FileReader(fileNam));
            // reads line of file and assigns it to String variable "ted"
            String ted = reader.readLine();
            // while ted is not null
            while (ted != null) {
                // increase/incriment counter by 1
                lineNum++;
                // ted is now equal to another line from the file
                ted = reader.readLine();
            }
            // close Bufferedreader
            reader.close();
            // catch exceptions and pritn error message
        } catch (FileNotFoundException e) {
            System.out.println("ERROR. FILE NOT FOUND");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return number of lines
        return lineNum;
    }

    // method which adds user ratings to linkedlist
    public void addUserRatings(LinkedList<User> uList) {
        try {
            // creates Bufferedreader and Filereader
            BufferedReader reader = new BufferedReader(new FileReader("MoreMovieData.csv"));
            // gets number of lines of "MoreMovieData.csv" and assigns it to integer
            // variabel "lines"
            int lines = countLinesOfFile("MoreMovieData.csv");
            // for loop runs for the number of lines
            for (int i = 0; i < lines; i++) {
                // reads line of file and assigns it to String variabel "line"
                String line = reader.readLine();
                // creates array and assigns the infomration from "MoreMovieData.csv" split by
                // ","
                String[] bits = line.split(",");
                // for loop runs for the length of previous array
                for (int j = 1; j < bits.length - 1; j++) {
                    // adds rating to linkedlist
                    uList.get(j - 1).addRating(Float.parseFloat(bits[j]));
                }
            }
            // close Bufferedreader
            reader.close();
            // catch IOException and print error message
        } catch (IOException e) {
            System.out.println("UNKNOWN ERROR");
        }
    }

    // method that adds new rating to linkedlist
    public void addNewRatings(LinkedList<User> list) {
        // creates temporary float linkedlist
        LinkedList<Float> tempList = new LinkedList<Float>();
        // for loop that loops for the number of lines in "MoreMovieData.csv"
        for (int i = 0; i < countLinesOfFile("MoreMovieData.csv"); i++) {
            // adds a rating of 0 to the temporary linkedlist
            tempList.add(0F);
        }
        // gets the last value on the linkedlist and sets it to number of new ratings
        list.getLast().setRatings(tempList);
    }

}
