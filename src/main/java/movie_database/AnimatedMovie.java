package movie_database;

import java.util.ArrayList;

/**
 * The AnimatedMovie class represents an animated movie, extending the Movie class.
 * It includes additional properties specific to animated movies.
 */
public class AnimatedMovie extends Movie {

    private int vek;

    /**
     * Constructs an AnimatedMovie object.
     *
     * @param meno        The name of the movie.
     * @param reziser     The director of the movie.
     * @param rok         The year of release.
     * @param rating      The rating of the movie.
     * @param comment     A comment about the movie.
     * @param vek         The recommended age for the movie.
     * @param zoznamHercov The list of animators involved in the movie.
     */
    public AnimatedMovie(String meno, String reziser, int rok, int rating, String comment, int vek, ArrayList<String> zoznamHercov) {
        super("Animated movie", meno, reziser, rok, comment, zoznamHercov);
        this.actorsList = zoznamHercov;
        this.rating = rating;
        this.vek = vek;
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
     * @param vek The recommended age to set.
     */
    public void setVek(int vek) {
        this.vek = vek;
    }

    /**
     * Gets the recommended age for the movie.
     *
     * @return The recommended age for the movie.
     */
    public int getVek() {
        return vek;
    }

    /**
     * Returns a string representation of the animated movie.
     *
     * @return A string containing the details of the animated movie.
     */
    @Override
    public String toString() {
        return super.toString() + "\nAnimatori: " + actorsList + "\nHodnotenie: " + rating + "\nOdporuceny vek: " + vek;
    }
}
