package movie_database;

import java.util.ArrayList;

public class Live_action_movie extends Movie {
    //protected int rating;


    public Live_action_movie(String meno, String director, int rok, int rating, String comment, ArrayList<String> listOfActors) {
        super("Live action movie", meno, director, rok,comment, listOfActors);
        this.rating = rating;
        //this.comment=comment;
    }


    public int getRatings() {
        return rating;
    }



    public boolean setRatings(int newRating) {
        if (newRating >= 1 && newRating <= 10) {
            this.rating = newRating;
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return super.toString() + "\nActors: " + actorsList + "\nRating: "+ rating;
    }
}
