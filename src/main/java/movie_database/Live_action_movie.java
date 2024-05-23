package movie_database;

import java.util.ArrayList;

/**
 * Live_action_movie class represents a live-action movie.
 * It extends the abstract Movie class.
 */
public class Live_action_movie extends Movie {

    /**
     * Constructs a Live_action_movie object.
     *
     * @param name            The name of the movie.
     * @param director        The director of the movie.
     * @param year            The year of release.
     * @param rating          The rating of the movie.
     * @param comment         A comment about the movie.
     * @param actorsList      The list of actors involved in the movie.
     */
    public Live_action_movie(String name, String director, int year, int rating, String comment, ArrayList<String> actorsList) {
        super("Live action movie", name, director, year, comment, actorsList);
        this.rating = rating;
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
     * Returns a string representation of the Live_action_movie object.
     *
     * @return A string representation of the Live_action_movie object.
     */
    @Override
    public String toString() {
        return super.toString() + "\nActors: " + actorsList + "\nRating: " + rating;
    }
}
