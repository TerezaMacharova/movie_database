package movie_database;


import java.util.ArrayList;

public class AnimatedMovie extends Movie {

    //private int rating;
    private int vek;

    public AnimatedMovie(String meno, String reziser, int rok , int rating, String comment, int vek, ArrayList<String> zoznamHercov) {
        super("Animated movie", meno, reziser, rok,comment, zoznamHercov);
        this.actorsList =zoznamHercov;
        this.rating = rating;
        this.vek = vek;
    }


    public int getRatings() {
        return rating;
    }


    public boolean setRatings(int rating) {
        if (rating >= 1 && rating <= 10) {
            this.rating = (int) rating;
            return true;
        }
        return false;
    }

    public void setVek(int vek) {
        this.vek = vek;
    }

    public int getVek() {
        return vek;
    }


    @Override
    public String toString() {
        return super.toString() + "\nAnimatori: " + actorsList + "\nHodnotenie: " + rating +  "\nOdporuceny vek: " + vek;
    }}