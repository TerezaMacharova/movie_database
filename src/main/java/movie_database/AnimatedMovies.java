package movie_database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class AnimatedMovies {
    public static Map<String, Movie> MovieMap;


    public AnimatedMovies() {
        Database jsonDatabase = new Database();
        MovieMap = jsonDatabase.loadMovies();
        if (MovieMap == null) {
            MovieMap = new HashMap<>();
        }
    }


    public boolean setFilm(String druh, String meno, String reziser, int hodnotenie, String comment, int rok, int vek, ArrayList<String> zoznamHercov) {
        if (druh.equals("Live action movie")) {
            if (MovieMap.put(meno, new Live_action_movie(meno, reziser, rok, hodnotenie, comment, zoznamHercov)) == null) {
                Movie film = MovieMap.get(meno);
                film.setRating(hodnotenie);
                film.setComment(comment);
                return true;
            }
        } else if (druh.equals("Animated movie")) {
            if (MovieMap.put(meno, new AnimatedMovie(meno, reziser, rok, hodnotenie, comment, vek, zoznamHercov)) == null) {
                Movie movie = MovieMap.get(meno);
                movie.setRating(hodnotenie);
                movie.setComment(comment);
                return true;
            }
        }
        return false;
    }

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
                System.out.println("Enter the comment:");
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

    public String editMovie() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the name of a movie you want to change: ");
            String name = scanner.nextLine();
            Movie movie = getMovie(name);

            if (movie == null) {
                return "The film was not found.";
            }

            System.out.println("What do you want to change?");
            System.out.println("1. Name\n2. Director\n3. Year\n4. Rating\n5. Comment\n6. Recommended age");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("New name: ");
                    String newName = scanner.nextLine();
                    movie.setName(newName);
                    MovieMap.remove(name);  // Remove old entry
                    MovieMap.put(newName, movie);  // Add new entry with new key
                    break;
                case 2:
                    System.out.print("Enter new director: ");
                    String newDirector = scanner.nextLine();
                    movie.setDirector(newDirector);
                    break;
                case 3:
                    System.out.print("Enter new year: ");
                    int newYear = scanner.nextInt();
                    //MovieMap.remove();  // Remove old entry
                    movie.setYear(newYear);
                    break;
                case 4:
                    System.out.print("Enter new rating: ");
                    int newRating = scanner.nextInt();
                    if (!movie.setRating(newRating)) {
                        return "Invalid rating. Please enter a number within the valid range.";
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
                    ((AnimatedMovie) movie).setVek(newAge);
                    break;
                default:
                    return "Invalid choice.";
            }

            new Database().saveMovies(MovieMap);  // Save changes to JSON file
            return "Changes saved successfully.\n" + movie.toString();
        } catch (InputMismatchException e) {
            return "Invalid input. Please enter a valid number.";
        } catch (Exception e) {
            return "An error occurred: " + e.getMessage();
        }
    }


    public Movie getMovie(String name) {
        System.out.println("Debug: Retrieving movie with name: " + name);
        Movie movie = MovieMap.get(name);
        if (movie == null) {
            System.out.println("Debug: Movie not found.");
        } else {
            System.out.println("Debug: Movie found: " + movie);
        }
        return movie;
    }

    public boolean setRanking(String name, int ranking) {
        Movie movie = MovieMap.get(name);
        if (movie == null) {
            return false;
        }
        boolean success = movie.setRating(ranking);
        if (success) {
            new Database().saveMovies(MovieMap);
        }
        return success;
    }


    public boolean removeMovie(String name) {
        if (MovieMap.remove(name) != null) {
            new Database().saveMovies(MovieMap);
            return true;
        }
        return false;
    }


    public void movieListing() {
        System.out.println("Debug: MovieMap size is " + MovieMap.size()); // Debug statement

        for (Movie movie : MovieMap.values()) {
            if (movie == null) {
                System.out.println("Debug: Found a null movie entry.");
                continue;
            }

            System.out.println("Debug: Processing movie - " + movie.getName());

            System.out.println("Movie name: " + movie.getName());
            System.out.println("Director: " + movie.getDirector());

            if (movie instanceof Live_action_movie) {
                Live_action_movie live_action_movie = (Live_action_movie) movie;
                System.out.println("List of actors: " + live_action_movie.getActorsList());
            } else if (movie instanceof AnimatedMovie) {
                AnimatedMovie animated_movie = (AnimatedMovie) movie;
                System.out.println("List of animators: " + animated_movie.getActorsList());
                System.out.println("Recommended age: " + animated_movie.getVek());
            }

            System.out.println("Audience rating: " + movie.getRatings());
            System.out.println("Audience rating comment: " + movie.getComment());
            System.out.println("Year of release: " + movie.getYear());
            System.out.println();
        }
    }



    public static void actorOneMovie(String name) {
        System.out.println("Debug: Listing movies with actor/animator: " + name);
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


    public static void actorMoreMovies() {
        HashMap<String, ArrayList<Movie>> actorMoreMovies = new HashMap<>();

        for (Movie movie : MovieMap.values()) {
            for (String herec : movie.getActorsList()) {
                String meno = herec;
                if (actorMoreMovies.containsKey(meno)) {
                    ArrayList<Movie> assignments = actorMoreMovies.get(meno);
                    assignments.add(movie);
                    actorMoreMovies.put(meno, assignments);
                } else {
                    ArrayList<Movie> assignments = new ArrayList<>();
                    assignments.add(movie);
                    actorMoreMovies.put(meno, assignments);
                }
            }
        }

        for (String meno : actorMoreMovies.keySet()) {
            ArrayList<Movie> assignments = actorMoreMovies.get(meno);
            if (assignments.size() > 1) {
                System.out.println("Herec: " + meno);
                System.out.println("Filmy:");
                for (Movie film : assignments) {
                    System.out.println("- " + film.getName() + " (" + film.getYear() + ")");
                }
            }
        }
    }


    public static void actorMoreMoviesPrint() {
        System.out.println("List of actors and animators who participated in multiple movies:");
        actorMoreMovies();
    }


    public void saveToFile(String meno) {
        Movie movie = MovieMap.get(meno);
        if (movie != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(meno + ".txt"))) {
                writer.write("Druh: " + movie.getType());
                writer.newLine();
                writer.write("Meno: " + movie.getName());
                writer.newLine();
                writer.write("Režisér: " + movie.getDirector());
                writer.newLine();
                writer.write("Rok: " + movie.getYear());
                writer.newLine();
                writer.write("Hodnotenie: " + movie.getRatings());
                writer.newLine();
                writer.write("Komentár: " + movie.getComment());
                writer.newLine();
                if (movie instanceof AnimatedMovie) {
                    AnimatedMovie animated_movie = (AnimatedMovie) movie;
                    writer.write("Odporúčaný vek: " + animated_movie.getVek());
                    writer.newLine();
                }
                writer.newLine();
                for (String herec : movie.getActorsList()) {
                    writer.write(herec);
                    writer.newLine();
                }
                System.out.println("Film " + meno + " bol uloženy do súboru.");
            } catch (IOException e) {
                System.out.println("Chyba pri ukladaní do súboru.");
            }
        } else {
            System.out.println("Film s menom " + meno + " neexistuje.");
        }
    }


    public static Movie readFromFile(String fileName) {
        System.out.println("Debug: Reading from file " + fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String druh = null;
            String nazov = null;
            String reziser = null;
            int rok = 0;
            int rating = 0;
            String comment = null;
            int vek = 0;
            ArrayList<String> zoznamHercov = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Trim the line to remove any leading/trailing whitespace
                if (line.startsWith("Druh: ")) {
                    druh = line.substring(6).trim();
                } else if (line.startsWith("Meno: ")) {
                    nazov = line.substring(6).trim();
                } else if (line.startsWith("Režisér: ")) {
                    reziser = line.substring(9).trim();
                } else if (line.startsWith("Rok: ")) {
                    rok = Integer.parseInt(line.substring(5).trim());
                } else if (line.startsWith("Hodnotenie: ")) {
                    rating = Integer.parseInt(line.substring(12).trim());
                } else if (line.startsWith("Komentár: ")) {
                    comment = line.substring(10).trim();
                } else if (line.startsWith("Vek: ")) {
                    vek = Integer.parseInt(line.substring(15).trim());
                } else if (!line.isEmpty() && !line.startsWith("Druh: ") && !line.startsWith("Meno: ") && !line.startsWith("Režisér: ") && !line.startsWith("Rok: ") && !line.startsWith("Hodnotenie: ") && !line.startsWith("Komentár: ") && !line.startsWith("Odporúčaný vek: ")) {
                    zoznamHercov.add(line);
                }
            }

            Movie movie;
            if ("Animated movie".equals(druh)) {
                movie = new AnimatedMovie(nazov, reziser, rok, rating, comment, vek, zoznamHercov);
            } else {
                movie = new Live_action_movie(nazov, reziser, rok, rating, comment, zoznamHercov);
            }

            MovieMap.put(nazov, movie);
            new Database().saveMovies(MovieMap);
            System.out.println("Debug: Successfully read movie " + nazov + " from file.");
            return movie;
        } catch (IOException e) {
            System.out.println("Chyba pri čítaní zo súboru.");
            e.printStackTrace();
        }
        return null;
    }
}