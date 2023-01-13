/*
Written by Dev M.
Date Started: January 12th, 2022
Date Completed: January 26th, 2022
This class is used to display a window with an interface, enabling a user to rate movies. It is dependent on whether a user is logged in, or is not.
The user is able to rate movies, search movies, and sort a particular selection of them dependent on whether they are logged in or not. 
The program will also recommend some movies to them based on the user's similarities with other users in the database.
*/

//imported packages:
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.awt.*;
import java.util.LinkedList;

//RatingScreen class --> inherits properties from JFrame and implements ActionListener, FocusListener, and KeyListener interfaces
public class RatingScreen extends JFrame implements ActionListener, FocusListener, KeyListener {

    // private Fields
    private JPanel topPanel, middlePanel;
    private JComboBox<String> helpList, sortingList;
    private JLabel titleLbl;
    private String logMessage;
    private String[] helpOptions;
    private String[] sortOptions1 = { "Randomizer", "Top Six Movies", "Bottom Six Movies" };
    private String[] sortOptions2 = { "Top Unrated Movies", "Bottom Unrated Movies", "Top Six Movies",
            "Bottom Six Movies", "Randomizer", "Recommended Movies" };
    private JTextField searchBar;
    private JPanel leftPanel, rightPanel, bottomPanel;
    private MovieManager genericManager, unratedManager, recManager;
    private Movie[] moviesList, unratedList, recommendedList;
    private String displayNam;
    private boolean isCreated;
    private int userIndex;
    private LinkedList<User> userList;

    // RatingScreen Constructor: Takes in a boolean parameter (to distinguish
    // between a logged in user or no user), a linkedlist of users, and the index of
    // a particular user if they are logged in
    public RatingScreen(boolean isUserCreated, LinkedList<User> list, int indexOfUser) {
        super("Film Craze");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1000, 875);
        setResizable(false);
        setLocationRelativeTo(null);

        topPanel = new JPanel(); // topPanel contains title label, searchbar, and 2 JComboBoxes
        topPanel.setLayout(new FlowLayout());
        topPanel.setBackground(Color.black);

        // LeftPanel, RightPanel, and BottomPanel are just used for aesthetics
        leftPanel = new JPanel();
        leftPanel.setBackground(Color.black);

        rightPanel = new JPanel();
        rightPanel.setBackground(Color.black);

        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.black);

        // titleLbl is Jlabel in topPanel
        titleLbl = new JLabel("Film Craze");
        titleLbl.setFont(new Font("Arial", Font.BOLD, 50));
        titleLbl.setBackground(Color.black);
        titleLbl.setForeground(Color.red);

        // searchBar is the textfield in topPanel enabling a user to search for movies
        searchBar = new JTextField("Search a Movie");
        searchBar.setColumns(25);
        searchBar.setFont(new Font("Arial", Font.PLAIN, 15));
        searchBar.addFocusListener(this);
        searchBar.addKeyListener(this);

        // if a user is logged in, one of the messages in one of the JComboBoxes will
        // inform them to log out, and the reverse if a user is not logged in
        if (isUserCreated == true) {
            logMessage = "Log Out";
        } else {
            logMessage = "Log In";
        }

        // listRenderer enables text to be in the middle of JComboBox elements
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);

        // if a user is logged in, display their username; otherwise, display "Username"
        if (isUserCreated == false) {
            displayNam = "Username";
        } else {
            displayNam = list.get(indexOfUser).getUserNam();
        }

        // helpOptions is a string array that is display in the helpList JComboBox
        helpOptions = new String[] { displayNam, logMessage, "Help", "Quit" };

        // helpList is a JComboBox which displays the user's username, login messages,
        // help options, and a quit option
        helpList = new JComboBox<String>(helpOptions);
        helpList.addActionListener(this);
        helpList.setRenderer(listRenderer);

        // if a user is logged in, they have more sorting options available (different
        // String arrays are used)
        if (isUserCreated == true) {
            sortingList = new JComboBox<String>(sortOptions2);
        } else {
            sortingList = new JComboBox<String>(sortOptions1);
        }

        sortingList.addActionListener(this);
        sortingList.setRenderer(listRenderer);

        topPanel.add(titleLbl);
        topPanel.add(searchBar);
        topPanel.add(helpList);
        topPanel.add(sortingList);

        middlePanel = new JPanel();
        middlePanel.setPreferredSize(getPreferredSize());
        middlePanel.setLayout(new GridLayout(3, 2));
        middlePanel.setBackground(Color.black);
        try {
            genericManager = new MovieManager('R');
        } catch (IOException e) {
            System.out.println("UNKNOWN ERROR");
        } // default is the randomized sorting

        moviesList = genericManager.getMovieArray(); // moviesList is a normal array of movies supplied by
                                                     // genericManager

        // if a user is not created, print 6 movies from the moviesList
        if (isUserCreated == false) {
            for (int i = 0; i < 6; i++) {
                middlePanel.add(new MoviePanel(moviesList, i, list, indexOfUser, isUserCreated));
            }
        } else { // otherwise, ...
            try {
                unratedManager = new MovieManager('H', indexOfUser, list);
            } catch (IOException e) {
                System.out.println("UNKNOWN ERROR");
            } // unratedManager is another MovieManager object
              // that creates an array of movies that a user
              // hasn't watched, and can be sorted
            unratedList = unratedManager.getMovieArray(); // unratedList is another list of movies that is generated
                                                          // from unratedManager

            int size = 6; // if the unratedManager has a length of less than 6, display that many movies;
                          // otherwise, display 6 movies
            if (unratedList.length < 6) {
                size = unratedList.length;
            }

            for (int i = 0; i < size; i++) {
                middlePanel.add(new MoviePanel(unratedList, i, list, indexOfUser, isUserCreated));
            }

            try {
                recManager = new MovieManager(list, indexOfUser);
            } catch (IOException e) {
                System.out.println("UNKNOWN ERROR");
            } // recManager is another MovieManager object that creates
              // an array of movies that is sorted specifically to the
              // user's similarity with other users
        }

        add(topPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);

        this.isCreated = isUserCreated; // isCreated is a field to store a boolean value if a user is logged in
        this.userIndex = indexOfUser; // userIndex is a field to store the index of the user in the linkedlist of
                                      // users, and the ratings linkedlist of the array of movies
        this.userList = list; // userList is a field to store a linkedlist of users

    }

    // focusGained: If the user gains focus on the searchBar, the text will be
    // eliminated from the searchBox
    public void focusGained(FocusEvent e) {
        if (e.getSource() == searchBar) {
            searchBar.setText("");
        }
    }

    // focusLost: if the user loses focus on the searchBar, the text will revert
    // back to its default format
    public void focusLost(FocusEvent e) {
        if (e.getSource() == searchBar) {
            if (searchBar.getText().equals("")) {
                searchBar.setText("Search a Movie");
            }
        }
    }

    // actionPerformed:
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == helpList) { // if the user clicks the helpList JComboBox, ...
            JComboBox<String> cb = (JComboBox<String>) e.getSource();
            String msg = (String) cb.getSelectedItem();
            if (msg.equals("Quit")) { // if the user selects QUIT, the program will terminate
                System.exit(0);
            } else if (msg.equals("Help")) { // if the user selects HELP, the program will display a JOptionPane
                                             // containing a particular help message - dependent to if the user is
                                             // logged in or not
                String message = "";
                if (this.isCreated == false) {
                    message = "Type Movie into SEARCH BAR and click ENTER KEY to SEARCH\n\nSelect \"LOG IN\" in the ComboBox to Log In\n\n(YOU CAN ONLY RATE MOVIES WHEN LOGGED IN)\n\nSORT the movies by RANDOM ORDER, by HIGHEST RATING, or by LOWEST RATING\n\nSelect \"QUIT\" to EXIT PROGRAM";
                } else {
                    message = "Type Movie into SEARCH BAR and click ENTER KEY to SEARCH\n\nSelect \"LOG OUT\" in the ComboBox to Log Out\n\nRate any of these movies with the following context:\n-2 --> HATE IT\n-1 --> OK\n0 --> Haven't Watched It\n1 --> DECENT\n2 --> REALLY GOOD\n\nSORT the movies (YOU HAVEN'T WATCHED) in ascending/descending order of AVG. RATING or by DATABASE RECOMMENDATIONS\n\nSelect \"QUIT\" to EXIT PROGRAM";
                }
                JOptionPane.showMessageDialog(null, message, "HELP", JOptionPane.INFORMATION_MESSAGE);
            } else if (msg.equals(logMessage)) { // if the user selects LOG IN or LOG OUT, the user will be sent to the
                                                 // login screen
                new LoginScreen();
                dispose();
            }
        } else if (e.getSource() == sortingList) { // if the user clicks on the sortingList to sort the arrays
            JComboBox<String> cb = (JComboBox<String>) e.getSource();
            String msg = (String) cb.getSelectedItem();

            // remove all components of middlePanel
            middlePanel.removeAll();
            middlePanel.revalidate();
            middlePanel.repaint();

            int size = 6;
            if (msg.equals("Randomizer")) { // if the user clicks on randomizer (if they are logged in or not),
                                            // moviesList will be sorted randomly and 6 movies from it will be displayed
                this.moviesList = genericManager.sortRandomly(moviesList);
                updateMovieData(moviesList, size);
            } else if (msg.equals("Top Six Movies") || msg.equals("Bottom Six Movies")) {
                if (msg.equals("Top Six Movies")) { // if the user clicks on Top Six Movies, moviesList will be sorted
                                                    // from highest to lowest and 6 movies from it will be displayed
                    genericManager.sortByAvgRating(true);
                } else { // if the user clicks on Bottom Six Movies, moviesList will be sorted from
                         // lowest to highest and 6 movies from it will be displayed
                    genericManager.sortByAvgRating(false);
                }
                this.moviesList = genericManager.getMovieArray();
                updateMovieData(moviesList, 6); // 6 movies displayed
            } else if (msg.equals("Top Unrated Movies") || msg.equals("Bottom Unrated Movies")) {
                if (msg.equals("Top Unrated Movies")) { // if the user clicks on Top Unrated Movies, unratedList will be
                                                        // sorted from highest to lowest and will be displayed
                    unratedManager.sortByAvgRating(true);
                } else { // if the user clicks on Bottom Unrated Movies, unratedList will be sorted from
                         // Lowest to Highest and will be displayed
                    unratedManager.sortByAvgRating(false);
                }
                this.unratedList = unratedManager.getMovieArray();
                genericManager.alignArrs(this.userList, this.unratedList); // arrays are alligned due to possible
                                                                           // confusion

                if (unratedList.length < 6) {
                    size = unratedList.length;
                }

                updateMovieData(unratedList, size); // displayed

            } else if (msg.equals("Recommended Movies")) { // if the user clicks on Recommended Movies, recManager will
                                                           // generate recommendedList which will be displayed

                recManager.sortByAvgRating(true);
                this.recommendedList = recManager.getMovieArray();
                genericManager.alignArrs(this.userList, this.recommendedList);

                if (recommendedList.length < 6) {
                    size = recommendedList.length;
                }

                updateMovieData(recommendedList, size); // displayed

            }

        }

    }

    public void keyTyped(KeyEvent e) {
        // Passed
    }

    // KeyPressed:
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) { // if the user clicks the ENTER key to search a movie
            // middle panel will be erased
            middlePanel.removeAll();
            middlePanel.revalidate();
            middlePanel.repaint();

            String movieNam = this.searchBar.getText();
            if (movieNam.equals("")) { // if the user enters nothing, an error message is displayed
                JOptionPane.showMessageDialog(null, "Movie Not Entered", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                // the location of this movie is found from the search method in genericManager
                int loc = genericManager.searchMovieNams(this.moviesList, movieNam);
                if (loc >= 0) { // if the movie is found, add it to the middlePanel
                    middlePanel
                            .add(new MoviePanel(this.moviesList, loc, this.userList, this.userIndex, this.isCreated));
                    this.searchBar.setText("");
                } else { // if not, display an error message
                    JOptionPane.showMessageDialog(null, "UNKNOWN MOVIE", "ERROR", JOptionPane.ERROR_MESSAGE);
                    this.searchBar.setText("");
                }
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Passed
    }

    // updateMovieData: Displays a set of movies - depending on the size you want
    // displayed - onto middlePanel
    public void updateMovieData(Movie[] movies, int size) {
        for (int i = 0; i < size; i++) {
            middlePanel.add(new MoviePanel(movies, i, userList, userIndex, isCreated));
        }
    }

    // self testing main method:
    public static void main(String[] args) {
        new RatingScreen(false, null, -1);
    }

}