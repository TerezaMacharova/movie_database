package movie_database;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Play class provides the main entry point for the application, allowing users to interact with the movie database.
 */
public class Play {

    /**
     * Prompts the user to enter a whole number, handling invalid input appropriately.
     *
     * @param sc The Scanner object to read input from.
     * @return The whole number entered by the user.
     */
    public static int onlyWholeNumber(Scanner sc) {
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                sc.nextLine(); // clear the invalid input
                System.out.println("Please enter a whole number.");
            }
        }
    }


    /**
     * Prompts the user to enter a float number, handling invalid input appropriately.
     *
     * @param sc The Scanner object to read input from.
     * @return The float number entered by the user.
     */
    public static float onlyNums(Scanner sc) {
        float number;
        try {
            number = sc.nextFloat();
        } catch (Exception e) {
            System.out.println("Exception:  " + e.toString());
            System.out.println("Enter a number please.");
            sc.nextLine();
            number = onlyNums(sc);
        }
        return number;
    }


    /**
     * The main method providing a menu-driven interface for the movie database application.
     */
    public static void main(String[] args) {
        MovieManager movies = new MovieManager();
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1 - add movie");
            System.out.println("2 - show movie");
            System.out.println("3 - add ratings");
            System.out.println("4 - delete movie");
            System.out.println("5 - list all movies");
            System.out.println("6 - save to file");
            System.out.println("7 - load from file");
            System.out.println("8 - edit movie");
            System.out.println("9 - list actors, animators, that have been in more movies");
            System.out.println("10 - list movies with an actor");
            System.out.println("0 - exit");

            try {
                if (input.hasNextInt()) {
                    int choice = input.nextInt();
                    input.nextLine();

                    switch (choice) {
                        case 1:
                            movies.addMovie();
                            break;
                        case 2:
                            System.out.println("Enter name of the movie:");
                            String name = input.nextLine();
                            Movie movie = movies.getMovie(name);
                            if (movie == null) {
                                System.out.println("Movie wasn't found.");
                            } else {
                                System.out.println(movie);
                            }
                            break;
                        case 3:
                            System.out.println("Enter name of the movie:");
                            name = input.nextLine();
                            System.out.println("Enter new rating for the movie:");
                            boolean result;
                            if (input.hasNextInt()) {
                                int ratings = input.nextInt();
                                input.nextLine();
                                result = movies.setRating(name, ratings);
                                if (result) {
                                    System.out.println("Rating has been set.");
                                } else {
                                    System.out.println("Movie wasn't found.");
                                }
                            } else {
                                input.nextLine();
                                System.out.println("Invalid input for rating.");
                            }
                            break;
                        case 4:
                            System.out.println("Enter the name of the movie:");
                            name = input.nextLine();
                            result = movies.removeMovie(name);
                            if (result) {
                                System.out.println("Movie was successfully deleted.");
                            } else {
                                System.out.println("Movie wasn't found.");
                            }
                            break;
                        case 5:
                            movies.movieListing();
                            break;
                        case 6:
                            System.out.println("Enter the name of the movie:");
                            name = input.nextLine();
                            movies.saveToFile(name);
                            break;
                        case 7:
                            System.out.println("Enter the name of the file:");
                            String loadFile = input.nextLine();
                            Movie loadedMovie = MovieManager.readFromFile(loadFile);
                            if (loadedMovie != null) {
                                System.out.println("Movie was loaded from file " + loadFile);
                            } else {
                                System.out.println("An error occurred when trying to read from a file.");
                            }
                            break;
                        case 8:
                            System.out.println(movies.editMovie());
                            break;
                        case 9:
                            MovieManager.actorMoreMoviesPrint();
                            break;
                        case 10:
                            System.out.println("Enter the name of the actor or animator:");
                            String actorName = input.nextLine();
                            MovieManager.actorOneMovie(actorName);
                            break;
                        case 0:
                            System.exit(0);
                        default:
                            System.out.println("Invalid choice. Please enter a valid number (0-10).");
                            break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid number (0-10).");
                    input.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number (0-10).");
                input.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Unexpected end of input.");
                input.nextLine();
                input = new Scanner(System.in);
            }
        }
    }

}
