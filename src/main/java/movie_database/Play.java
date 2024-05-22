package movie_database;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Play {

    public static int onlyWholeNumber(Scanner sc) {
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                sc.nextLine(); // clear the invalid input
                System.out.println("Zadaj prosim cele cislo");
            }
        }
    }

    public static float onlyNums(Scanner sc) {
        float cislo;
        try {
            cislo = sc.nextFloat();
        } catch (Exception e) {
            System.out.println("Nastala vynimka typu " + e.toString());
            System.out.println("zadaj prosim cislo ");
            sc.nextLine();
            cislo = onlyNums(sc);
        }
        return cislo;
    }

    public static void main(String[] args) {
        AnimatedMovies movies = new AnimatedMovies();
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
            System.out.println("9 -  list actors, animators, that have been in more movies");
            System.out.println("10 - list movies with an actor");
            System.out.println("0 - exit");

            try {
                if (input.hasNextInt()) {
                    int volba = input.nextInt();
                    input.nextLine(); // Clear the buffer

                    switch (volba) {
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
                                input.nextLine(); // Clear the buffer
                                result = movies.setRanking(name, ratings);
                                if (result) {
                                    System.out.println("Rating has been set.");
                                } else {
                                    System.out.println("Movie wasn't found.");
                                }
                            } else {
                                input.nextLine(); // Clear the buffer
                                System.out.println("Invalid input for rating.");
                            }
                            break;
                        case 4:
                            System.out.println("Zadajte name filmu:");
                            name = input.nextLine();
                            result = movies.removeMovie(name);
                            if (result) {
                                System.out.println("Film bol úspešne vymazaný.");
                            } else {
                                System.out.println("Film nebol nájdený.");
                            }
                            break;
                        case 5:
                            movies.movieListing();
                            break;
                        case 6:
                            System.out.println("Enter the name of the movie");
                            name = input.nextLine();
                            movies.saveToFile(name);
                            break;
                        case 7:
                            System.out.println("Enter the name of the file:");
                            String nacistSoubor = input.nextLine();
                            Movie loadedMovie = AnimatedMovies.readFromFile(nacistSoubor);
                            if (loadedMovie != null) {
                                System.out.println("Film bol načítaný zo súboru " + nacistSoubor);
                            } else {
                                System.out.println("Chyba pri čítaní zo súboru.");
                            }
                            break;
                        case 8:
                            movies.editMovie();
                            break;
                        case 9:
                            AnimatedMovies.actorMoreMoviesPrint();
                            break;
                        case 10:
                            System.out.println("Enter the name of the actor or animator:");
                            String actorName = input.nextLine();
                            //input.nextLine();
                            AnimatedMovies.actorOneMovie(actorName);
                            break;
                        case 0:
                            System.exit(0);
                        default:
                            System.out.println("Neplatná volba. Zvolte prosím číslo od 0 do 10.");
                            break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number from 0 to 10.");
                    input.nextLine(); // Clear the buffer
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number from 0 to 10.");
                input.nextLine(); // Clear the invalid input
            } catch (NoSuchElementException e) {
                System.out.println("Unexpected end of input.");
                input.nextLine(); // Clear the buffer
                input = new Scanner(System.in); // Reset the scanner

            }
        }
    }
}
