package movie_database;

import java.util.ArrayList;

public class Live_action_movie extends Movie {
    private int hodnotenie;


    public Live_action_movie(String meno, String reziser, int rok, int hodnotenie,String comment,ArrayList<String> zoznamHercov) {
        super("Hrany film", meno, reziser, rok,comment, zoznamHercov);

        this.hodnotenie = hodnotenie;
        //this.comment=comment;
    }


    public int getRatings() {
        return hodnotenie;
    }


    @Override
    public boolean setRatings(int newRating) {
        if (hodnotenie >= 1 && hodnotenie <= 5) {
            this.hodnotenie = newRating;
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return super.toString() + "\nHerci: " + zoznamHercov+ "\nHodnotenie: "+hodnotenie;
    }
}
