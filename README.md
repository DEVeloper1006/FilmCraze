# FilmCraze - Movie Recommendation System

## Overview
FilmCraze is a Java-based desktop application designed to provide a personalized movie recommendation experience. Users can rate movies, explore top-rated and recommended movies, and manage their preferences through an intuitive interface. The application leverages collaborative filtering to recommend movies based on user similarity.

---

## Features
- **User Authentication**: Secure sign-up and login functionality with username and password management.
- **Movie Rating**: Rate movies on a scale of -2 (Hate it) to 2 (Really good).
- **Sorting and Filtering**:
  - Randomized movie lists.
  - Top and bottom-rated movies.
  - Unwatched movies sorted by average rating.
- **Recommendations**: Suggests movies based on collaborative filtering (user similarity).
- **Search**: Quickly search movies by name.

---

## File Descriptions

### **Core Classes**
1. **Main.java**: Entry point of the application. Launches the `RatingScreen` for initial interaction.
2. **LoginScreen.java**:
   - Handles user authentication.
   - Reads and updates user credentials from `NamAndPass.csv`.
3. **RatingScreen.java**:
   - Provides the main interface for rating and exploring movies.
   - Manages movie recommendations and sorting logic.
4. **MoviePanel.java**:
   - Displays movie details, including the poster, name, average rating, and a dropdown to rate.
   - Updates movie data dynamically when ratings change.
5. **MovieManager.java**:
   - Manages movie data, including loading, sorting, and recommending movies.
   - Implements collaborative filtering for recommendations.
6. **UserManager.java**:
   - Handles user-related data operations like adding new users, reading user credentials, and syncing user ratings.
7. **User.java**:
   - Represents a user object with encapsulated properties like username, password, and ratings.

---

## Dependencies
- **CSV Files**:
  - **`NamAndPass.csv`**: Stores usernames and passwords.
  - **`MoreMovieData.csv`**: Contains movie names and user ratings.

---

## Setup and Usage
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/DEVeloper1006/filmcraze.git
   ```
2. **Compile the Java Files**:
   ```bash
   javac *.java
   ```
3. **Run the Application**:
   ```bash
   java Main
   ```
4. **Ensure Required Files**:
   - Place `NamAndPass.csv` and `MoreMovieData.csv` in the project root directory.

---

## How It Works
1. **Sign Up / Log In**:
   - Use `LoginScreen` to sign up for a new account or log in to an existing one.
   - Credentials are securely stored in `NamAndPass.csv`.
2. **Rate Movies**:
   - Use `RatingScreen` to view, rate, and explore movies.
   - Ratings are updated dynamically in `MoreMovieData.csv`.
3. **Receive Recommendations**:
   - Collaborative filtering identifies similar users and recommends their favorite movies.
4. **Search Movies**:
   - Type movie names in the search bar to find specific titles.

---

## Future Enhancements
- **Enhanced Recommendation Algorithms**: Include content-based filtering.
- **Database Integration**: Migrate CSV storage to a relational database.
- **Cloud Sync**: Enable remote access and synchronization of user data.
- **Modern UI**: Transition to a more visually appealing interface using JavaFX or a web-based front end.

---
