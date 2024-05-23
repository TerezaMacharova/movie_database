package movie_database;

import java.util.ArrayList;

/**
 * AnimatedMovie class represents an animated movie, extending the Movie class.
 * It includes additional properties specific to animated movies.
 */
public class AnimatedMovie extends Movie {

    private int age;

    /**
     * Constructs an AnimatedMovie object.
     *
     * @param name         The name of the movie.
     * @param director     The director of the movie.
     * @param year         The year of release.
     * @param rating       The rating of the movie.
     * @param comment      A comment about the movie.
     * @param age          The recommended age for the movie.
     * @param actorsList   The list of animators involved in the movie.
     */
    public AnimatedMovie(String name, String director, int year, int rating, String comment, int age, ArrayList<String> actorsList) {
        super("Animated movie", name, director, year, comment, actorsList);
        this.actorsList = actorsList;
        this.rating = rating;
        this.age = age;
    }


    /**
     * Gets the rating of the movie.
     *
     * @return The rating of the movie.
     */
    public int getRatings() {
        return rating;
    }


    /**
     * Sets the recommended age for the movie.
     *
     * @param age The recommended age to set.
     */
    public void setAge(int age) {
        this.age = age;
    }


    /**
     * Gets the recommended age for the movie.
     *
     * @return The recommended age for the movie.
     */
    public int getAge() {
        return age;
    }


    /**
     * Returns a string representation of the animated movie.
     *
     * @return A string containing the details of the animated movie.
     */
    @Override
    public String toString() {
        return super.toString()
                + "\nAnimators: " + actorsList
                + "\nRating: " + rating
                + "\nAge: " + age;
    }
}
