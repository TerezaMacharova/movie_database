package movie_database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * MovieManeger class provides methods to manage a collection of movies, including adding, editing, removing, and listing movies.
 * It supports both animated and live-action movies.
 */
public class MovieManager {
    public static Map<String, Movie> MovieMap;


    /**
     * Constructs an MovieManager object.
     * Initializes the MovieMap by loading movies from the database.
     */
    public MovieManager() {
        Database jsonDatabase = new Database();
        MovieMap = jsonDatabase.loadMovies();
        if (MovieMap == null) {
            MovieMap = new HashMap<>();
        }
    }


    /**
     * Adds or updates a movie in the MovieMap.
     *
     * @param type          The type of the movie (animated or live-action).
     * @param name          The name of the movie.
     * @param director      The director of the movie.
     * @param rating        The rating of the movie.
     * @param comment       A comment about the movie.
     * @param year          The year of release.
     * @param age           The recommended age for the movie (for animated movies).
     * @param actorsList    The list of actors or animators involved in the movie.
     * @return True if the movie was added successfully, otherwise false.
     */
    public boolean setFilm(String type, String name, String director, int rating, String comment, int year, int age, ArrayList<String> actorsList) {
        if (type.equals("Live action movie")) {
            if (MovieMap.put(name, new Live_action_movie(name, director, year, rating, comment, actorsList)) == null) {
                Movie film = MovieMap.get(name);
                film.setRating(rating);
                film.setComment(comment);
                return true;
            }
        } else if (type.equals("Animated movie")) {
            if (MovieMap.put(name, new AnimatedMovie(name, director, year, rating, comment, age, actorsList)) == null) {
                Movie movie = MovieMap.get(name);
                movie.setRating(rating);
                movie.setComment(comment);
                return true;
            }
        }
        return false;
    }


    /**
     * Adds a new movie based on user input.
     *
     * @return A message indicating the result of the operation.
     */
    public String addMovie() {
        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the type of the movie (1 for live action, 2 for animated):");
        int filmType = input.nextInt();
        input.nextLine();

        System.out.println("Enter the name of the movie:");
        String name = input.nextLine();

        System.out.println("Enter the name of the director:");
        String director = input.nextLine();

        System.out.println("Enter the year of release:");
        int year = Play.onlyWholeNumber(sc);

        Movie movie;

        if (filmType == 1) {
            ArrayList<String> actorsList = new ArrayList<>();
            System.out.println("How many actors do you want to add?");
            int number = Play.onlyWholeNumber(sc);

            System.out.println("Enter actor names: ");
            for (int i = 0; i < number; i++) {
                String actor = input.nextLine();
                actorsList.add(actor);
            }

            System.out.println("Do you want to leave a comment? (yes/no):");
            String choice = input.nextLine();
            String comment = null;
            if (choice.equalsIgnoreCase("yes")) {
                System.out.println("Enter the comment:");
                comment = input.nextLine();
            }

            System.out.println("Enter viewer rating (1-10):");
            int rating = input.nextInt();
            input.nextLine();

            movie = new Live_action_movie(name, director, year, rating, comment, actorsList);
            boolean success = movie.setRating(rating);

            if (!success) {
                System.out.println("Invalid rating. Rating must be between 1 and 10.");
                return name;
            }
            movie = new Live_action_movie(name, director, year, rating, comment, actorsList);
        } else if (filmType == 2) {
            ArrayList<String> actorsList = new ArrayList<>();
            System.out.println("How many animators do you want to add?");
            int number = Play.onlyWholeNumber(sc);

            System.out.println("Enter animator names: ");
            for (int i = 0; i < number; i++) {
                String actor = input.nextLine();
                actorsList.add(actor);
            }

            System.out.println("Enter recommended age:");
            int age = input.nextInt();
            input.nextLine();
            System.out.println("Do you want to leave a comment? (yes/no):");
            String choice = input.nextLine();

            String comment = null;

            if (choice.equalsIgnoreCase("yes")) {
                System.out.println("Enter comment:");
                comment = input.nextLine();
            }

            System.out.println("Enter viewer rating (1-10):");
            int rating = input.nextInt();
            input.nextLine();

            movie = new AnimatedMovie(name, director, year, rating, comment, age, actorsList);
            boolean success = movie.setRating(rating);
            if (!success) {
                System.out.println("Invalid rating. Rating must be between 1 and 10.");
                return name;
            }

            movie = new AnimatedMovie(name, director, year, rating, comment, age, actorsList);
        } else {
            System.out.println("Invalid movie type.");
            return name;
        }

        MovieMap.put(name, movie);
        new Database().saveMovies(MovieMap);
        return "Movie was added successfully.";
    }

    /**
     * Edits an existing movie based on user input.
     *
     * @return A message indicating the result of the operation.
     */
    public String editMovie() {
        Scanner scanner = new Scanner(System.in);
        try  {
            System.out.print("Enter the name of a movie you want to change: ");
            String name = scanner.nextLine();
            Movie movie = getMovie(name);

            if (movie == null) {
                return "The movie was not found.";
            }

            System.out.println("What do you want to change?");
            System.out.println("1. Name\n2. Director\n3. Year\n4. Rating\n5. Comment\n6. Recommended age");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("New name: ");
                    String newName = scanner.nextLine();
                    movie.setName(newName);
                    MovieMap.remove(name);
                    MovieMap.put(newName, movie);
                    break;
                case 2:
                    System.out.print("Enter new director: ");
                    String newDirector = scanner.nextLine();
                    movie.setDirector(newDirector);
                    break;
                case 3:
                    System.out.print("Enter new year: ");
                    int newYear = scanner.nextInt();
                    scanner.nextLine();
                    movie.setYear(newYear);
                    break;
                case 4:
                    System.out.print("Enter new rating: ");
                    int newRating = scanner.nextInt();
                    scanner.nextLine();
                    if (!movie.setRating(newRating)) {
                        return "Invalid rating. Please enter a number within the valid range (1-10).";
                    }
                    break;
                case 5:
                    System.out.print("Enter new comment: ");
                    String newComment = scanner.nextLine();
                    movie.setComment(newComment);
                    break;
                case 6:
                    if (!(movie instanceof AnimatedMovie)) {
                        return "This film is not animated.";
                    }
                    System.out.print("Enter new recommended age: ");
                    int newAge = scanner.nextInt();
                    scanner.nextLine();
                    ((AnimatedMovie) movie).setAge(newAge);
                    break;
                default:
                    return "Invalid choice.";
            }

            new Database().saveMovies(MovieMap);
            return "Changes saved successfully.\n" + movie.toString();
        } catch (InputMismatchException e) {
            return "Invalid input. Please enter a valid number.";
        } catch (Exception e) {
            return "An error occurred: " + e.getMessage();
        }
    }


    /**
     * Retrieves a movie from the MovieMap.
     *
     * @param name The name of the movie to retrieve.
     * @return The Movie object if found, otherwise null.
     */
    public Movie getMovie(String name) {
     //   System.out.println("Debug: Retrieving movie with name: " + name);
        Movie movie = MovieMap.get(name);
        if (movie == null) {
          System.out.println("Debug: Movie not found.");
        } return movie;
    }


    /**
     * Sets the rating of a movie.
     *
     * @param name       The name of the movie.
     * @param rating     The rating to set.
     * @return True if the rating was set successfully, otherwise false.
     */
    public boolean setRating(String name, int rating) {
        Movie movie = MovieMap.get(name);
        if (movie == null) {
            return false;
        }
        boolean success = movie.setRating(rating);
        if (success) {
            new Database().saveMovies(MovieMap);
        }
        return success;
    }


    /**
     * Removes a movie from the MovieMap.
     *
     * @param name The name of the movie to remove.
     * @return True if the movie was removed successfully, otherwise false.
     */
    public boolean removeMovie(String name) {
        if (MovieMap.remove(name) != null) {
            new Database().saveMovies(MovieMap);
            return true;
        }
        return false;
    }


    /**
     * Lists all movies in the MovieMap.
     */
    public void movieListing() {
      //  System.out.println("Debug: MovieMap size is " + MovieMap.size());

        for (Movie movie : MovieMap.values()) {
            if (movie == null) {
             //   System.out.println("Debug: Found a null movie entry.");
                continue;
            }

          //  System.out.println("Debug: Processing movie - " + movie.getName());

            System.out.println("Movie name: " + movie.getName());
            System.out.println("Director: " + movie.getDirector());

            if (movie instanceof Live_action_movie) {
                Live_action_movie live_action_movie = (Live_action_movie) movie;
                System.out.println("List of actors: " + live_action_movie.getActorsList());
            } else if (movie instanceof AnimatedMovie) {
                AnimatedMovie animated_movie = (AnimatedMovie) movie;
                System.out.println("List of animators: " + animated_movie.getActorsList());
                System.out.println("Recommended age: " + animated_movie.getAge());
            }

            System.out.println("Rating: " + movie.getRatings());
            System.out.println("Comment: " + movie.getComment());
            System.out.println("Year of release: " + movie.getYear());
            System.out.println();
        }
    }


    /**
     * Lists movies that include a specific actor or animator.
     *
     * @param name The name of the actor or animator.
     */
    public static void actorOneMovie(String name) {
      //  System.out.println("Debug: Listing movies with actor/animator: " + name);
        boolean found = false;
        try {
            for (Movie movie : MovieMap.values()) {
                if (movie.getActorsList().contains(name)) {
                    System.out.println("Movie name: " + movie.getName());
                    System.out.println("Director: " + movie.getDirector());
                    System.out.println("Year: " + movie.getYear());
                    System.out.println("Comment: " + movie.getComment());
                    System.out.println("Actors/Animators: " + movie.getActorsList());
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No movies found with actor/animator: " + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Lists actors or animators who have participated in multiple movies.
     */
    public static void actorMoreMovies() {
        HashMap<String, ArrayList<Movie>> actorMoreMovies = new HashMap<>();

        for (Movie movie : MovieMap.values()) {
            for (String actor : movie.getActorsList()) {
                String name = actor;
                if (actorMoreMovies.containsKey(name)) {
                    ArrayList<Movie> assignments = actorMoreMovies.get(name);
                    assignments.add(movie);
                    actorMoreMovies.put(name, assignments);
                } else {
                    ArrayList<Movie> assignments = new ArrayList<>();
                    assignments.add(movie);
                    actorMoreMovies.put(name, assignments);
                }
            }
        }

        for (String name : actorMoreMovies.keySet()) {
            ArrayList<Movie> assignments = actorMoreMovies.get(name);
            if (assignments.size() > 1) {
                System.out.println("Actor: " + name);
                System.out.println("Movies:");
                for (Movie movie : assignments) {
                    System.out.println("- " + movie.getName() + " (" + movie.getYear() + ")");
                }
            }
        }
    }


    /**
     * Prints a list of actors and animators who participated in multiple movies.
     */
    public static void actorMoreMoviesPrint() {
        System.out.println("List of actors and animators who participated in multiple movies:");
        actorMoreMovies();
    }


    /**
     * Saves a movie's details to a text file.
     *
     * @param name The name of the movie to save.
     */
    public void saveToFile(String name) {
        Movie movie = MovieMap.get(name);
        if (movie != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(name + ".txt"))) {
                writer.write("Type: " + movie.getType());
                writer.newLine();
                writer.write("Name: " + movie.getName());
                writer.newLine();
                writer.write("Director: " + movie.getDirector());
                writer.newLine();
                writer.write("Year: " + movie.getYear());
                writer.newLine();
                writer.write("Rating: " + movie.getRatings());
                writer.newLine();
                writer.write("Comment: " + movie.getComment());
                writer.newLine();
                if (movie instanceof AnimatedMovie) {
                    AnimatedMovie animated_movie = (AnimatedMovie) movie;
                    writer.write("Age: " + animated_movie.getAge());
                    writer.newLine();
                }
                writer.newLine();
                for (String actor : movie.getActorsList()) {
                    writer.write(actor);
                    writer.newLine();
                }
                System.out.println("Movie " + name + " was saved to file.");
            } catch (IOException e) {
                System.out.println("An error occurred when saving movie to a file.");
            }
        } else {
            System.out.println("Movie " + name + " doesn't exist.");
        }
    }


    /**
     * Reads a movie's details from a text file and adds it to the MovieMap.
     *
     * @param fileName The name of the file to read from.
     * @return The Movie object if read successfully, otherwise null.
     */
    public static Movie readFromFile(String fileName) {
       // System.out.println("Debug: Reading from file " + fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String type = null;
            String name = null;
            String director = null;
            int year = 0;
            int rating = 0;
            String comment = null;
            int age = 0;
            ArrayList<String> actorsList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Trim the line to remove any leading/trailing whitespace
              //  System.out.println("Debug: Processing line - " + line);

                if (line.startsWith("Type: ")) {
                    type = line.substring(6).trim();
                    System.out.println("Debug: Parsed type - " + type);
                } else if (line.startsWith("Name: ")) {
                    name = line.substring(6).trim();
                    System.out.println("Debug: Parsed name - " + name);
                } else if (line.startsWith("Director: ")) {
                    director = line.substring(9).trim();
                    System.out.println("Debug: Parsed reziser - " + director);
                } else if (line.startsWith("Year: ")) {
                    year = Integer.parseInt(line.substring(5).trim());
                    System.out.println("Debug: Parsed rok - " + year);
                } else if (line.startsWith("Rating: ")) {
                    rating = Integer.parseInt(line.substring(12).trim());
                    System.out.println("Debug: Parsed rating - " + rating);
                } else if (line.startsWith("Comment: ")) {
                    comment = line.substring(10).trim();
                    System.out.println("Debug: Parsed comment - " + comment);
                } else if (line.startsWith("Age: ")) {
                    age = Integer.parseInt(line.substring(5).trim());
                    System.out.println("Debug: Parsed vek - " + age);
                } else if (!line.isEmpty() && !line.startsWith("Type: ")
                        && !line.startsWith("Name: ")
                        && !line.startsWith("Director: ")
                        && !line.startsWith("Year: ")
                        && !line.startsWith("Rating: ")
                        && !line.startsWith("Comment: ")
                        && !line.startsWith("Age: ")) {
                    actorsList.add(line);
                    System.out.println("Debug: Added to actorsList - " + line);
                }
            }

            Movie movie;
            if ("Animated movie".equals(type)) {
                movie = new AnimatedMovie(name, director, year, rating, comment, age, actorsList);
            } else {
                movie = new Live_action_movie(name, director, year, rating, comment, actorsList);
            }

            MovieMap.put(name, movie);
            new Database().saveMovies(MovieMap);
            //System.out.println("Debug: Successfully read movie " + name + " from file.");
            return movie;
        } catch (IOException e) {
            System.out.println("Error occurred when tried reading from file.");
            e.printStackTrace();
        }
        return null;
    }

}
