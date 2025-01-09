//Written By: Dev M.
// Janurary 16, 2022
//Class which creates a movie panel which contains the poster, average rating, movie name, and a JComboBox to update the rating.
//This class also update the movie data when ratings are updated 

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.awt.*;
import java.io.*;

public class MoviePanel extends JPanel implements ActionListener {

    // creates fields
    private JLabel posterHolder;
    private JPanel rightPanel;
    private JLabel nameOfMovie;
    private JLabel avgRating;
    private JComboBox<String> raterBox;
    private final String[] ratings = { "-2", "-1", "Give Rating", "1", "2" };
    private NumberFormat formatter = NumberFormat.getInstance();
    private LinkedList<User> userList;
    private int indexOfUser;
    private boolean isCreated;
    private Movie[] movieList;
    private int indexOfMovie;

    // constructor which makes the panel and it's components
    public MoviePanel(Movie[] movie, int indexOfMovie, LinkedList<User> list, int index, boolean isUserCreated) {
        super();
        setLayout(null);
        setBackground(Color.black);

        // creates and sets a red border
        Border redline = BorderFactory.createLineBorder(Color.red);
        setBorder(redline);

        // JLabel to hold poster picture
        posterHolder = new JLabel(
                new ImageIcon(new ImageIcon(movie[indexOfMovie].getFileString()).getImage().getScaledInstance(100, 200,
                        Image.SCALE_DEFAULT)));
        // sets size of poster and creates red border
        posterHolder.setBounds(25, 30, 100, 200);
        posterHolder.setBorder(redline);

        // creates a panel on the right side of the movie panel
        rightPanel = new JPanel();
        rightPanel.setBackground(Color.black);
        rightPanel.setLayout(new GridLayout(3, 1));
        rightPanel.setBounds(135, 30, 300, 200);
        rightPanel.setBorder(redline);

        // creates JLabel to hold the name of the movie
        nameOfMovie = new JLabel(movie[indexOfMovie].getMovieNam());
        nameOfMovie.setFont(new Font("Arial", Font.BOLD, 35));
        nameOfMovie.setHorizontalAlignment(JLabel.CENTER);
        nameOfMovie.setBackground(Color.black);
        nameOfMovie.setForeground(Color.white);

        // sets the number of decimals for the average rating to 2
        formatter.setMaximumFractionDigits(2);

        // creates JLabel to hold average rating
        avgRating = new JLabel("Rating: " + formatter.format(movie[indexOfMovie].getAvgRating()));
        avgRating.setFont(new Font("Arial", Font.BOLD, 35));
        avgRating.setHorizontalAlignment(JLabel.CENTER);
        avgRating.setBackground(Color.black);
        avgRating.setForeground(Color.cyan);

        // Formats the text on JComboBox to the center
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);

        // creates JComboBox which allows user to rate movies
        raterBox = new JComboBox<String>(ratings);

        // if the user is logged in
        if (isUserCreated == true) {
            // if the user rated the movie a -2, the JComboBox will dipslay -2
            if (movie[indexOfMovie].getRatings().get(index) == -2) {
                raterBox.setSelectedIndex(0);
            }
            // if the user rated the movie a -1, the JComboBox will dipslay -1
            else if (movie[indexOfMovie].getRatings().get(index) == -1) {
                raterBox.setSelectedIndex(1);
            }
            // if the user rated the movie a 0, the JComboBox will dipslay "Give Rating"
            else if (movie[indexOfMovie].getRatings().get(index) == 0) {
                raterBox.setSelectedIndex(2);
            }
            // if the user rated the movie a 1, the JComboBox will dipslay 1
            else if (movie[indexOfMovie].getRatings().get(index) == 1) {
                raterBox.setSelectedIndex(3);
            }
            // if the user rated the movie a 2, the JComboBox will dipslay 2
            else if (movie[indexOfMovie].getRatings().get(index) == 2) {
                raterBox.setSelectedIndex(4);
            }
        }
        // else if the user is not logged in
        else if (isUserCreated == false) {
            // The JComboBox will only show "Give Rating"
            raterBox.setSelectedIndex(2);
        }

        raterBox.setRenderer(listRenderer);
        raterBox.addActionListener(this);

        // add items to rightPanel
        rightPanel.add(nameOfMovie);
        rightPanel.add(avgRating);
        rightPanel.add(raterBox);

        // add poster picture and the right panel to the parent panel
        add(posterHolder);
        add(rightPanel);

        // assigns variables
        this.isCreated = isUserCreated;
        this.indexOfUser = index;
        this.userList = list;
        this.movieList = movie;
        this.indexOfMovie = indexOfMovie;

    }

    public static void main(String[] args) {

    }

    public void actionPerformed(ActionEvent e) {
        // if the user uses the JComboBox to rate
        if (e.getSource() == raterBox) {
            // creates new JComboBox and finds what the user has selected
            JComboBox<String> cb = (JComboBox<String>) e.getSource();
            // retrieves what the user has selected and assigns it to String variable "msg"
            String msg = (String) cb.getSelectedItem();
            // if user is not logged in
            if (this.isCreated == false) {
                // displays error message telling us that they are not logged in
                JOptionPane.showMessageDialog(null, "USER NOT LOGGED IN", "ERROR", JOptionPane.ERROR_MESSAGE);
                // resets the JComboBox to display "Give Rating"
                this.raterBox.setSelectedIndex(2);

            }
            // else if the user is logged in
            else if (this.isCreated == true) {
                // creates temporary float variable "tempNum"
                float tempNum;
                // if the user has selected "give rating"
                if (!msg.equals("Give Rating")) {
                    tempNum = Float.parseFloat(msg);
                }
                // else tempNum equals 0
                else {
                    tempNum = 0;
                }

                int num = 0;
                // if the user's index is not equal to 0
                if (indexOfUser != 0) {
                    // num equal index of user minus 1 to prevent out of bounds excpetion
                    num = indexOfUser - 1;
                }

                // replace the rating for a movie from a user
                userList.get(num).replaceRating(tempNum, indexOfMovie);
                // replace the rating for a movie
                movieList[indexOfMovie].replaceRating(tempNum, indexOfUser);
                // finds the movie and assigns it to integer variable "loc"
                int loc = searchMovies(movieList[indexOfMovie].getMovieNam());
                // calls method which updates the movie data
                updateMovieData(loc);
                // sets the average rating of of the specified movie
                movieList[indexOfMovie]
                        .setAvgRating(movieList[indexOfMovie].calcAvgRating(movieList[indexOfMovie].getRatings()));
                // gets the average rating of a specified movie and assigns it to float variable
                // "avg"
                float avg = movieList[indexOfMovie].getAvgRating();
                // updates the average rating on the movie panel
                this.avgRating.setText("Rating: " + avg);
            }
        }
    }

    // method which searches movie
    public int searchMovies(String movieNam) {
        int loc = -1;
        // surround with try catch
        try {
            // creates Bufferedreader and Filereader for "MoreMovieData.csv" file
            BufferedReader reader = new BufferedReader(new FileReader("MoreMovieData.csv"));
            // for loop runs 24 times which is the number of movies there are
            for (int i = 0; i < 25; i++) {
                // reads line
                String line = reader.readLine();
                // splits line by ","
                String bits[] = line.split(",");
                // if the first bit of data (movie name) equals the movie name being searched
                if (bits[0].equals(movieNam)) {
                    // location equals for loop index
                    loc = i;
                    break;
                }
            }
            // close Bufferedreader
            reader.close();
            // catch IOException and print error message
        } catch (IOException e) {
            System.out.println("UNKNOWN ERROR");
        }
        // return location of searched movie
        return loc;
    }

    // method which updates movie data
    public void updateMovieData(int loc) {
        // surround with try catch
        try {
            // creates Bufferedreader and Filereader for "MoreMovieData.csv"
            BufferedReader reader = new BufferedReader(new FileReader("MoreMovieData.csv"));
            // creates new linkedlist
            LinkedList<String> fileInfo = new LinkedList<String>();
            // for loop runs 24 times which is number of movies
            for (int i = 0; i < 25; i++) {
                // adds info from "MoreMovieData.csv" to linkedlist
                fileInfo.add(reader.readLine());
            }
            // closes Bufferedreader
            reader.close();

            // gets each line of the file, splits it into a String array from the number of
            // ",", and updates a user's rating
            String line = fileInfo.get(loc);
            String[] bits = line.split(",");
            bits[this.indexOfUser + 1] = String.valueOf(this.movieList[indexOfMovie].getRatings().get(indexOfUser));

            // updates the line of the file
            line = "";
            for (int i = 0; i < bits.length; i++) {
                if (i != bits.length - 1) {
                    line += bits[i] + ",";
                } else {
                    line += bits[i];
                }
            }
            // updates the linkedlist
            fileInfo.remove(loc);
            fileInfo.add(loc, line);

            // creates Printwriter and Filewriter for "MoreMovieData.csv"
            PrintWriter outputter = new PrintWriter(new FileWriter("MoreMovieData.csv"));

            // prints out an updated line for a particular movie and/or user's rating
            for (int i = 0; i < 25; i++) {
                if (i != 24) {
                    outputter.println(fileInfo.get(i));
                } else {
                    outputter.print(fileInfo.get(i));
                }
            }

            // close Printwriter
            outputter.close();
        } catch (IOException e) {
            System.out.println("UNKNOWN ERROR");
        }
    }

}
