/*
Written by Dev M.
Date Started: January 10th, 2022
Date Completed: January 11th, 2022
This class is used to display a window, in a which a user can either sign up or log in. It can be called
from the rating screen and to the rating screen, but it can also run independently.
*/

//Imported Classes
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.LinkedList;

//LoginScreen class --> inherits properties from JFrame and implements the ActionListener & FocusListener interfaces
public class LoginScreen extends JFrame implements ActionListener, FocusListener {

    // Private Fields
    private JPanel topPanel, btnPanel, middlePanel, bottomPanel;
    private JButton helpBtn, exitBtn, resetBtn;
    private JLabel logoLbl;
    private JLabel titleLbl, userNamLbl, passwordLbl;
    private JTextField userNamField, passwordField;
    private JButton clickToLoginBtn, clickToSignUpBtn;
    private JButton getStartedBtn;
    private boolean isUserCreated;
    public LinkedList<User> userArray;
    private UserManager manager;

    // LoginScreen Constructor:
    public LoginScreen() {
        super("FilmCraze Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 975);
        setResizable(false);
        setLocationRelativeTo(null);
        setBackground(Color.black);
        setLayout(new BorderLayout());

        topPanel = new JPanel(); // topPanel is the panel at the top --> Contains a GIF and a set of buttons
        topPanel.setLayout(new FlowLayout());
        topPanel.setBackground(Color.black);

        logoLbl = new JLabel(new ImageIcon("FilmCraze.gif")); // logoLbl contains an ImageIcon

        btnPanel = new JPanel(); // btnPanel is the panel with 3 rows and 1 column containing assistive buttons
        btnPanel.setLayout(new GridLayout(3, 1));
        btnPanel.setBackground(Color.black);

        exitBtn = new JButton("Click to Exit"); // exitBtn is one of the buttons in btnPanel that allows to exit program
        exitBtn.setForeground(Color.RED);
        exitBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        exitBtn.addActionListener(this);

        helpBtn = new JButton("Click for Help"); // helpBtn is one of the buttons in btnPanel that prints out
                                                 // instructions
        helpBtn.setForeground(Color.BLACK);
        helpBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        helpBtn.addActionListener(this);

        resetBtn = new JButton("Click to Reset"); // resetBtn is one of the buttons in btnPanel that enables user to
                                                  // reset text when needed
        resetBtn.setForeground(Color.BLUE);
        resetBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        resetBtn.addActionListener(this);

        btnPanel.add(exitBtn);
        btnPanel.add(helpBtn);
        btnPanel.add(resetBtn);

        topPanel.add(logoLbl);
        topPanel.add(btnPanel);

        middlePanel = new JPanel(); // middlePanel is the panel in the middle which contains titles, buttons, and
                                    // textfields for the user to type in their info
        middlePanel.setLayout(null);
        middlePanel.setBackground(Color.black);

        titleLbl = new JLabel("Welcome! Please login to continue!"); // titleLbl is the label at the top of middlePanel
        titleLbl.setFont(new Font("Arial", Font.ITALIC, 35));
        titleLbl.setBackground(Color.black);
        titleLbl.setForeground(Color.green);
        titleLbl.setHorizontalAlignment(JLabel.CENTER);
        titleLbl.setBounds(225, 0, 550, 35);

        clickToLoginBtn = new JButton("Click to Login INSTEAD"); // clickToLoginBtn is a button in middlePanel to enable
                                                                 // user to begin login processes
        clickToLoginBtn.setFont(new Font("Arial", Font.BOLD, 15));
        clickToLoginBtn.setBounds(375, 60, 225, 35);
        clickToLoginBtn.addActionListener(this);
        clickToLoginBtn.setVisible(false); // initially invisible

        clickToSignUpBtn = new JButton("Click to Sign-Up"); // clickToSignUpBtn is a button in middlePanel to enable
                                                            // user to begin signup processes
        clickToSignUpBtn.setFont(new Font("Arial", Font.BOLD, 15));
        clickToSignUpBtn.setBounds(375, 60, 225, 35);
        clickToSignUpBtn.addActionListener(this);
        clickToSignUpBtn.setVisible(true); // initially visible

        userNamLbl = new JLabel("Username"); // userNamLbl is a label to indicate the user to enter a username
        userNamLbl.setFont(new Font("Arial", Font.PLAIN, 25));
        userNamLbl.setBackground(Color.black);
        userNamLbl.setForeground(Color.cyan);
        userNamLbl.setBounds(250, 225, 125, 35);

        userNamField = new JTextField("Enter your username"); // userNamField is a textfield enabling a user to enter
                                                              // their username
        userNamField.setFont(new Font("Arial", Font.PLAIN, 15));
        userNamField.addFocusListener(this);
        userNamField.setHorizontalAlignment(JTextField.CENTER);
        userNamField.setBounds(390, 225, 350, 35);

        passwordLbl = new JLabel("Password"); // passwordLbl is a label to indicate the user to enter a password
        passwordLbl.setFont(new Font("Arial", Font.PLAIN, 25));
        passwordLbl.setBackground(Color.black);
        passwordLbl.setForeground(Color.magenta);
        passwordLbl.setBounds(250, 275, 125, 35);

        passwordField = new JTextField("Enter your password"); // passwordField is a textfield enabling a user to enter
                                                               // their password
        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordField.addFocusListener(this);
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.setBounds(390, 275, 350, 35);

        middlePanel.add(titleLbl);
        middlePanel.add(clickToSignUpBtn);
        middlePanel.add(clickToLoginBtn);
        middlePanel.add(userNamLbl);
        middlePanel.add(userNamField);
        middlePanel.add(passwordLbl);
        middlePanel.add(passwordField);

        bottomPanel = new JPanel(); // bottomPanel is the panel at the bottom containing a button
        bottomPanel.setBackground(Color.black);

        getStartedBtn = new JButton("Click to Get Started"); // getStartedBtn helps to transition the user from the
                                                             // login screen to a rating screen
        getStartedBtn.setFont(new Font("Arial", Font.PLAIN, 25));
        getStartedBtn.addActionListener(this);

        bottomPanel.add(getStartedBtn);

        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);

        isUserCreated = true; // isUserCreated is a check boolean value that checks to see if someone is
                              // signing up or logging in (True = login & False = signup)
        manager = new UserManager(); // creates new UserManager to create a linkedlist of users to pass through
        this.userArray = new LinkedList<User>(); // userArray ==> linkedlist of Users
        manager.readUserFile(this.userArray); // reads through the "NamAndPass.csv" file to add usernames and passwords
                                              // to the linkedlist
        manager.addUserRatings(this.userArray);// reads through the "MoreMovieData.csv" file to add ratings to each user

    }

    // actionPerformed:
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exitBtn) { // if the user clicks the exit button, the program terminates
            System.exit(0);
        } else if (e.getSource() == helpBtn) { // if the user clicks the help button, a JOptionPane displays a message,
                                               // which is dependent to if the user is signing up or logging in
            String msg = "Do you want to start finding movies for that awesome movie night you have planned? Well, don’t worry! FilmCraze has gotten you covered! Start by either logging in or signing up to begin the fun! If you don’t have an account yet, click the button that states “Click to Sign Up.” If you’ve clicked and wish to go back, you can always click the button that states “Click to Log in.” To do either, enter your information in the designated text boxes below. If you feel that your information is invalid or your username is too embarrassing, there is always a RESET button to reset your progress. If you feel your information is ready to go, click the “GET STARTED” button to enter your account. Finally, once you’ve felt satisfied with your ratings and movies, you can click the QUIT button to terminate.";
            JOptionPane.showMessageDialog(null, msg, "HELP", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == resetBtn) { // if the reset button is clicked, all the textfields are set to their
                                                // default texts
            userNamField.setText("Enter your username");
            passwordField.setText("Enter your password");
        } else if (e.getSource() == clickToLoginBtn) { // if the clickToLogIn button is clicked, the following
                                                       // components are reset for signing up purposes, and
                                                       // isUserCreated is set to false
            titleLbl.setText("Welcome! Please login to continue!");
            clickToLoginBtn.setVisible(false);
            clickToSignUpBtn.setVisible(true);
            isUserCreated = true;
            userNamField.setText("Enter your username");
            passwordField.setText("Enter your password");
        } else if (e.getSource() == clickToSignUpBtn) { // if the clickToSignUp button is clicked, the following
                                                        // components are reset for logging in purposes, and
                                                        // isUserCreated is set to false
            titleLbl.setText("Welcome! Sign up to get started!");
            clickToLoginBtn.setVisible(true);
            clickToSignUpBtn.setVisible(false);
            isUserCreated = false;
            userNamField.setText("Enter your username");
            passwordField.setText("Enter your password");
        } else if (e.getSource() == getStartedBtn) { // if the get started button is clicked, the user is sent to the
                                                     // rating screen
            if (isUserCreated == false) { // if user is signing up,...
                if (userNamField.getText().equals("Enter your username") // if the textfields aren't filled out, an
                                                                         // error message will display
                        || passwordField.getText().equals("Enter your password")) {
                    JOptionPane.showMessageDialog(null, "FILL IN ALL TEXT BOXES", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else { // otherwise, ...
                    if (manager.searchUserNams(this.userArray, userNamField.getText()) < 0) {// if the username entered
                                                                                             // is completely unique,
                                                                                             // the username and
                                                                                             // password will be added
                                                                                             // to the NamAndPass File,
                                                                                             // MoreMovieData file, and
                                                                   // the User LinkedList
                        manager.addNamPass(userNamField.getText(), passwordField.getText());
                        manager.addNewData();
                        this.userArray.add(new User(userNamField.getText(), passwordField.getText()));
                        manager.addNewRatings(this.userArray);
                        new RatingScreen(true, this.userArray, this.userArray.size() - 1); // calls RatingScreen with
                                                                                           // new user logged in
                        dispose();
                    } else { // if username is already in the database, error message pops up and textfields
                             // are reverted
                        JOptionPane.showMessageDialog(null,
                                "THIS USERNAME IS ALREADY TAKEN.\nPLEASE ENTER ANOTHER USERNAME", "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                        userNamField.setText("Enter your username");
                        passwordField.setText("Enter your password");
                    }
                } // if user is logging in
            } else {
                if (userNamField.getText().equals("Enter your username") // if the textfields aren't filled out, an
                                                                         // error message pops up
                        || passwordField.getText().equals("Enter your password")) {
                    JOptionPane.showMessageDialog(null, "FILL IN ALL TEXT BOXES", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else { // otherwise, ...
                    int loc = manager.searchUserNams(this.userArray, userNamField.getText()); // searches username in
                                                                                              // NamAndPass
                    if (loc >= 0) { // if username is found
                        if (userArray.get(loc).getPassword().equals(passwordField.getText())) { // if password is
                                                                                                // correct
                            new RatingScreen(true, this.userArray, loc); // call RatingScreen with user logged in
                            dispose();
                        } else { // if the password is incorrect, display error message and reset textfields
                            JOptionPane.showMessageDialog(null, "INCORRECT PASSWORD", "ERROR",
                                    JOptionPane.ERROR_MESSAGE);
                            userNamField.setText("Enter your username");
                            passwordField.setText("Enter your password");
                        }
                    } else { // if username is not found, display error message and reset textfields
                        JOptionPane.showMessageDialog(null, "USERNAME NOT FOUND", "ERROR", JOptionPane.ERROR_MESSAGE);
                        userNamField.setText("Enter your username");
                        passwordField.setText("Enter your password");
                    }
                }
            }
        }

    }

    // focusGained: When the user clicks onto the textfields, the text should
    // immediately erase to type new text
    public void focusGained(FocusEvent e) {
        if (e.getSource() == userNamField) {
            userNamField.setText("");
        } else if (e.getSource() == passwordField) {
            passwordField.setText("");
        }
    }

    // focusLost: When the user clicks off of the textfields and nothing is written,
    // the text should reverse to the default text
    public void focusLost(FocusEvent e) {
        if (e.getSource() == userNamField && userNamField.getText().equals("")) {
            userNamField.setText("Enter your username");
        } else if (e.getSource() == passwordField && passwordField.getText().equals("")) {
            passwordField.setText("Enter your password");
        }
    }

    // Self-Testing Main;
    public static void main(String[] args) {
        new LoginScreen();
    }

}
