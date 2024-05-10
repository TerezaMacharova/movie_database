package movie_database;


import java.util.ArrayList;

public class AnimatedMovie extends Movie {

    private int ratings;
    private int vek;

    public AnimatedMovie(String meno, String reziser, int rok , int hodnotenie, String comment, int vek, ArrayList<String> zoznamHercov) {
        super("Animated movie", meno, reziser, rok,comment, zoznamHercov);
        this.zoznamHercov=zoznamHercov;
        this.ratings = hodnotenie;
        this.vek = vek;
    }


    public int getRatings() {
        return ratings;
    }

    @Override
    public boolean setRatings(int ratings) {
        if (ratings >= 1 && ratings <= 10) {
            this.ratings = (int) ratings;
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
        return super.toString() + "\nAnimatori: " + zoznamHercov + "\nHodnotenie: " + ratings +  "\nOdporuceny vek: " + vek;
    }}