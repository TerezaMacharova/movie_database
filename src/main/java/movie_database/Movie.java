package movie_database;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Movie {
    protected String type;
    protected String name;
    protected String director;
    protected int year;
    protected int rating;
    protected String comment;
    protected ArrayList<String> actorsList;

    public Movie(String type, String name, String director, int year, String comment, ArrayList<String> actorsList) {
        this.type = type;
        this.name = name;
        this.director = director;
        this.year = year;
        this.comment = comment;
        this.actorsList = actorsList;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getActorsList() {
        return actorsList;
    }

    public String getDirector() {
        return director;
    }

    public String getComment() {
        return comment;
    }
    public int getYear() {
        return year;
    }

    public abstract int getRatings();

    public boolean setRating(int rating) {
        if (rating < 1 || rating > 10) {
            throw new IllegalArgumentException("Ratings must be between 1 and 10");
        }
        this.rating = rating;
        return true;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public void setComment(String comment) {
        this.comment=comment;
    }

    public void promptForComment() {
        Scanner input = new Scanner(System.in);
        System.out.println("Do you want to leave comment? (yes/no):");
        String choice = input.nextLine();

        if (choice.equalsIgnoreCase("yes")) {
            System.out.println("Enter comment:");
            String comment = input.nextLine();
            setComment(comment);
        }
    }

    @Override
    public String toString() {
        return "Type: " + type + "\nName: " + name + "\nDirector: " + director + "\nYear: " + year + "\nComment: " + comment ;
    }
}
