package movie_database;

import java.util.ArrayList;


/**
 * Movie class is an abstract representation of a movie.
 * It includes common properties and methods for both animated and live-action movies.
 */
public abstract class Movie {
    protected String type;
    protected String name;
    protected String director;
    protected int year;
    protected int rating;
    protected String comment;
    protected ArrayList<String> actorsList;


    /**
     * Constructs a Movie object.
     *
     * @param type       The type of the movie (e.g., animated or live-action).
     * @param name       The name of the movie.
     * @param director   The director of the movie.
     * @param year       The year of release.
     * @param comment    A comment about the movie.
     * @param actorsList The list of actors involved in the movie.
     */
    public Movie(String type, String name, String director, int year, String comment, ArrayList<String> actorsList) {
        this.type = type;
        this.name = name;
        this.director = director;
        this.year = year;
        this.comment = comment;
        this.actorsList = actorsList;
    }


    /**
     * Gets the type of the movie.
     *
     * @return The type of the movie.
     */
    public String getType() {
        return type;
    }


    /**
     * Gets the name of the movie.
     *
     * @return The name of the movie.
     */
    public String getName() {
        return name;
    }


    /**
     * Gets the list of actors involved in the movie.
     *
     * @return The list of actors.
     */
    public ArrayList<String> getActorsList() {
        return actorsList;
    }


    /**
     * Gets the director of the movie.
     *
     * @return The director of the movie.
     */
    public String getDirector() {
        return director;
    }


    /**
     * Gets the comment about the movie.
     *
     * @return The comment about the movie.
     */
    public String getComment() {
        return comment;
    }


    /**
     * Gets the year of release of the movie.
     *
     * @return The year of release.
     */
    public int getYear() {
        return year;
    }


    /**
     * Gets the rating of the movie.
     *
     * @return The rating of the movie.
     */
    public abstract int getRatings();

    /**
     * Sets the rating of the movie.
     *
     * @param rating The rating to set.
     * @return True if the rating is within the valid range (1-10), otherwise false.
     */
    public boolean setRating(int rating) {
        if (rating < 1 || rating > 10) {
            throw new IllegalArgumentException("Ratings must be between 1 and 10");
        }
        this.rating = rating;
        return true;
    }


    /**
     * Sets the name of the movie.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Sets the director of the movie.
     *
     * @param director The director to set.
     */
    public void setDirector(String director) {
        this.director = director;
    }


    /**
     * Sets the year of release of the movie.
     *
     * @param year The year to set.
     */
    public void setYear(int year) {
        this.year = year;
    }


    /**
     * Sets the comment about the movie.
     *
     * @param comment The comment to set.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }


    /**
     * Returns a string representation of the movie.
     *
     * @return A string containing the details of the movie.
     */
    @Override
    public String toString() {
        return "Type: " + type +
                "\nName: " + name +
                "\nDirector: " + director +
                "\nYear: " + year +
                "\nComment: " + comment;
    }
}
