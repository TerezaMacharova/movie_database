package movie_database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class AnimatedMovies
{
    public static Map<String, Movie> MovieMap;

    public AnimatedMovies()
    {
        MovieMap = new HashMap<>();
    }
    public boolean setFilm(String druh, String meno, String reziser, int hodnotenie,String comment, int rok, int vek, ArrayList<String> zoznamHercov)
    {
        if (druh.equals("Hrany film"))
        {
            if (MovieMap.put(meno, new Live_action_movie(meno, reziser, rok, hodnotenie,comment,zoznamHercov)) == null)
            {
                Movie film = MovieMap.get(meno);
                film.setRatings(hodnotenie);
                film.setComment(comment);
                return true;
            }
        } else if (druh.equals("Animovaný film"))
        {
            if (MovieMap.put(meno, new AnimatedMovie(meno, reziser, rok, hodnotenie,comment, vek, zoznamHercov)) == null)
            {
                Movie movie = MovieMap.get(meno);
                movie.setRatings(hodnotenie);
                movie.setComment(comment);
                return true;
            }
        } return false;
    }


    public void addFilm() {Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        System.out.println("Vyberte druh filmu (1 pre hraný film, 2 pre animovaný film):");
        int filmType = input.nextInt();
        input.nextLine();
        System.out.println("Zadajte názov filmu:");
        String meno = input.nextLine();

        System.out.println("Zadajte režiséra filmu:");
        String reziser = input.nextLine();

        System.out.println("Zadajte rok vydania filmu:");

        int rok = Play.pouzeCelaCisla(sc);

        Movie movie ;

        if (filmType == 1)
        {
            ArrayList<String> zoznamHercov = new ArrayList<String>();
            System.out.println("Zadajte koľko hercov chcete zadať: ");
            int pocet = Play.pouzeCelaCisla(sc);

            System.out.println("Zadajte jednotlivých hercov : ");
            for (int i = 0; i < pocet; i++)
            {
                String herec = input.nextLine();
                zoznamHercov.add(herec);
            }

            System.out.println("Chcete zanechať komentár? (ano/nie):");
            String choice= input.nextLine();
            String comment=null;
            if (choice.equalsIgnoreCase("ano"))
            {
                System.out.println("Zadajte komentár:");
            }
            comment = input.nextLine();


            System.out.println("Zadajte hodnotenie divákov (1-5):");
            int hodnotenie = input.nextInt();

            input.nextLine();
            movie = new Live_action_movie(meno, reziser, rok, hodnotenie,comment, zoznamHercov);
            boolean success = ((Live_action_movie) movie).setRatings(hodnotenie);

            if (!success)
            {
                System.out.println("Neplatné hodnotenie divákov. Hodnotenie musí byť v rozmedzí 1-5.");
                return;
            }
             movie = new Live_action_movie(meno, reziser,  rok, hodnotenie,comment, zoznamHercov);

        } else if (filmType == 2)
        {
            ArrayList<String> zoznamHercov = new ArrayList<String>();
            System.out.println("Zadajte koľko animátorov chcete zadať: ");
            int pocet = Play.pouzeCelaCisla(sc);

            System.out.println("Zadajte jednotlivých animátorov: ");

            for (int i = 0; i < pocet; i++)
            {
                String herec = input.nextLine();
                zoznamHercov.add(herec);
            }

            System.out.println("Zadajte doporučený vek diváka:");
            int vek = input.nextInt();
            input.nextLine();
            System.out.println("Chcete zanechať komentár? (ano/nie):");
            String choice = input.nextLine();

            String comment = null;

            if (choice.equalsIgnoreCase("ano")) {
                System.out.println("Zadajte komentár:");
                comment = input.nextLine();
            } else {
                System.out.println("Žiadny komentár nebol zadaný.");
            }



            System.out.println("Zadajte hodnotenie divákov (1-10):");
            int hodnotenie = input.nextInt();
            input.nextLine();
            movie = new AnimatedMovie(meno, reziser, rok, hodnotenie,comment, vek, zoznamHercov);
            boolean success = ((AnimatedMovie) movie).setRatings(hodnotenie);
            if (!success) {
                System.out.println("Neplatné hodnotenie divákov. Hodnotenie musí byť v rozmedzí 1-10.");
                return;
            }



            movie = (Movie) new AnimatedMovie(meno, reziser, rok, hodnotenie,comment, vek, zoznamHercov);
        } else {
            System.out.println("Neplatný výber typu filmu.");
            return;
        }

        MovieMap.put(meno, movie);
        System.out.println("Film " + meno + " bol úspešne pridaný.");
    }


    public String editFilm() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Zadajte meno filmu, ktorý chceš zmeniť: ");
        String meno = scanner.nextLine();
        Movie movie = getFilm(meno);

        if (movie == null) {
            return "Film sa nenašiel.";
        }

        System.out.println("Čo chceš zmeniť?");
        System.out.println("1. Meno");
        System.out.println("2. Režisér");
        System.out.println("3. Rok");
        System.out.println("4. Hodnotenie");
        System.out.println("5. Koment");
        System.out.println("6. Odporúčaný vek");
        System.out.print("Zadaj svoj výber: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Nové meno: ");
                String newMeno = scanner.nextLine();
                movie.setMeno(newMeno);
                MovieMap.remove(meno);
                MovieMap.put(newMeno, movie);
                return "Vytvoril si nové meno.\n" + movie.toString();
            case 2:
                System.out.print("Zadaj režiséra: ");
                String newReziser = scanner.nextLine();
                movie.setReziser(newReziser);
                return "Zmenil si režiséra.\n" + movie.toString();
            case 3:
                System.out.print("Zadaj nový rok: ");
                int newRok = scanner.nextInt();
                movie.setRok(newRok);
                return "Zmenil si rok.\n" + movie.toString();
            case 4:
                System.out.print("Zadaj nové hodnotenie: ");
                int newHodnotenie = scanner.nextInt();
                movie.setRatings(newHodnotenie);
                return "Zmenil si hodnotenie.\n" + movie.toString();
            case 5:
                System.out.print("Zadaj koment: ");
                String newKoment = scanner.nextLine();
                movie.setComment(newKoment);
                return "Zmenil si koment.\n" + movie.toString();
            case 6:
                if (!(movie instanceof AnimatedMovie)) {
                    return "Tento film nie je animovaný";
                }
                System.out.print("Zadaj nový odporúčaný vek: ");
                int newVek = scanner.nextInt();
                ((AnimatedMovie) movie).setVek(newVek);
                return "Zmenil si odporúčaný vek.\n" + movie.toString();
            default:
                return "Chyba.";
        }
    }



    public Movie getFilm(String meno)
    {
        return MovieMap.get(meno);

    }

    public boolean setHodnotenie(String meno, int hodnoceni) {
        Movie movie = MovieMap.get(meno);
        if (movie == null) {
            return false;
        }
        return movie.setRatings(hodnoceni);
    }


    public boolean vymazFilm(String meno)
    {
        if (MovieMap.remove(meno)!=null)
            return true;
        return false;
    }

    public void vypisFilmov() {
        for (Movie movie : MovieMap.values()) {
            System.out.println("Názov filmu: " + movie.getMeno());
            System.out.println("Režisér: " + movie.getReziser());

            if (movie instanceof Live_action_movie) {
                Live_action_movie live_action_movie = (Live_action_movie) movie;
                System.out.println("Zoznam hercov: " + live_action_movie.getZoznamHercov());
            } else if (movie instanceof Live_action_movie) {
                AnimatedMovie animated_movie = (AnimatedMovie) movie;
                System.out.println("Zoznam animátorov: " + animated_movie.getZoznamHercov());
                System.out.println("Doporučený vek diváka: " + animated_movie.getVek());
            }

            System.out.println("Hodnotenie divákov: " + movie.getRatings());
            System.out.println("Hodnotenie divákov-komentár: " + movie.getComment());
            System.out.println("Rok vydania: " + movie.getRok());
            System.out.println();
        }
    }

    public static void HerecvJednomFilme (String meno) {
        try {
            for (Movie movie : MovieMap.values()) {
                if (movie.getZoznamHercov().contains(meno)) {
                    System.out.println(movie.getMeno());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void HerecVoViacejFilmochMetoda() {
        HashMap<String, ArrayList<Movie>> HerecVoViacejFilmoch = new HashMap<>();


        for (Movie movie : MovieMap.values()) {

            for (String herec : movie.getZoznamHercov()) {
                String meno = herec;

                if (HerecVoViacejFilmoch.containsKey(meno)) {
                    ArrayList<Movie> priradenie = HerecVoViacejFilmoch.get(meno);
                    priradenie.add(movie);
                    HerecVoViacejFilmoch.put(meno, priradenie);
                } else {

                    ArrayList<Movie> priradenie = new ArrayList<>();
                    priradenie.add(movie);
                    HerecVoViacejFilmoch.put(meno, priradenie);
                }
            }
        }


        for (String meno : HerecVoViacejFilmoch.keySet()) {
            ArrayList<Movie> priradenie = HerecVoViacejFilmoch.get(meno);
            if (priradenie.size() > 1) {
                System.out.println("Herec: " + meno);
                System.out.println("Filmy:");
                for (Movie film : priradenie) {
                    System.out.println("- " + film.getMeno() + " (" + film.getRok() + ")");
                }
            }
        }
    }

    public static void HerecVoViacejFilmochMetoda2() {
        System.out.println("Zoznam hercov,animátorov, ktorý sa podielali na viacerých filmoch:");
        HerecVoViacejFilmochMetoda();
    }



    public void ulozDoSuboru(String meno) {
        Movie movie = MovieMap.get(meno);
        if (movie != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(meno + ".txt"))) {
                writer.write("Druh: " + movie.getDruh());
                writer.newLine();
                writer.write("Meno: " + movie.getMeno());
                writer.newLine();
                writer.write("Režisér: " + movie.getReziser());
                writer.newLine();
                writer.write("Rok: " + movie.getRok());
                writer.newLine();
                writer.write("Hodnotenie: " + movie.getRatings());
                writer.newLine();
                writer.write("Hodnotenie-komentár: " + movie.getComment());
                writer.newLine();
                if (movie instanceof AnimatedMovie) {
                    AnimatedMovie animated_movie = (AnimatedMovie) movie;
                    writer.write("Odporúčaný vek: " + animated_movie.getVek());
                    writer.newLine();
                }
                //writer.write("Seznam osob:");
                writer.newLine();
                for (String herec : movie.getZoznamHercov()) {
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





    public static Movie nacitajZoSuboru(String meno) {
        try (BufferedReader reader = new BufferedReader(new FileReader(meno + ".txt"))) {
            String druh = reader.readLine().substring(6);
            String nazov = reader.readLine().substring(6);
            String reziser = reader.readLine().substring(9);
            int rok = Integer.parseInt(reader.readLine().substring(5));
            int hodnotenie = Integer.parseInt(reader.readLine().substring(12));
            String comment = reader.readLine().substring(8);
            Movie movie;
            if (druh.equals("Animovaný film")) {
                int vek = Integer.parseInt(reader.readLine().substring(14));
                movie = new AnimatedMovie(nazov, reziser, rok, hodnotenie,comment, vek, new ArrayList<>());
            } else {
                movie = new Live_action_movie(nazov, reziser, rok, hodnotenie,comment, new ArrayList<>());
            }

            String line;
            ArrayList<String> zoznamHercov = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                zoznamHercov.add(line);
            }
            movie.getZoznamHercov().addAll(zoznamHercov);
            MovieMap.put(meno, movie);

            return movie;
        } catch (IOException e) {
            System.out.println("Chyba pri čítaní zo súboru.");
        }
        return null;
    }




    public static void insertFilm(Connection conn)  throws SQLException {

        for(Movie movie : MovieMap.values()) {
            if(movie instanceof Live_action_movie) {
                String sql = "INSERT INTO movies (druh, meno, reziser,rok,hodnotenie,comment) VALUES (?, ?, ?, ?, ?,?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, "hranyFilm");
                stmt.setString(2, movie.getMeno());
                stmt.setString(3, movie.getReziser());
                stmt.setInt(4, movie.getRok());
                stmt.setInt(5,movie.getRatings());
                stmt.setString(6,movie.getComment());
                stmt.executeUpdate();
                for (String herec: movie.getZoznamHercov()) {
                    sql="INSERT INTO herci(meno,herec) VALUES (?,?)";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1,movie.getMeno());
                    stmt.setString(2,herec);
                    stmt.executeUpdate();

                }
            }

            else if (movie instanceof AnimatedMovie){   String Database ="INSERT INTO movies (druh, meno, reziser,  rok,hodnotenie,comment,vek)"+" VALUES (?, ?, ?, ?, ?,?,?)";
                PreparedStatement stmt = conn.prepareStatement(Database);
                stmt.setString(1, "animovanyFilm");
                stmt.setString(2, movie.getMeno());
                stmt.setString(3, movie.getReziser());
                stmt.setInt(4, movie.getRok());
                stmt.setInt(5,movie.getRatings());
                stmt.setString(6,movie.getComment());
                stmt.setInt(7, ((AnimatedMovie) movie).getVek());
                stmt.executeUpdate();
                for (String herec: movie.zoznamHercov) {
                    Database = "INSERT INTO herci(meno,herec) VALUES (?,?)";
                    stmt = conn.prepareStatement(Database);
                    stmt.setString(1,movie.getMeno());
                    stmt.setString(2,herec);
                    stmt.executeUpdate();

                }}}}




    public static void loadMovie(Connection conn)throws SQLException{

        Statement stmt=conn.createStatement();
        String query="SELECT * FROM movies";
        ResultSet rs=stmt.executeQuery(query);
        while(rs.next()) {
            if (rs.getString("druh").equals("hranyFilm")) {
                String meno = rs.getString("meno");

                ArrayList <String>zoznam = uploadZoznam(meno,conn);

                Movie movie = new Live_action_movie(rs.getString("meno"), rs.getString("reziser"),rs.getInt("rok"),rs.getInt("hodnotenie"),rs.getString("comment"),zoznam);
                Statement state = conn.createStatement();

                MovieMap.put(meno, movie);

            }else {
                String meno=rs.getString("meno");
                ArrayList<String>zoznam= uploadZoznam(meno,conn);
                Movie movie = new AnimatedMovie(rs.getString("meno"), rs.getString("reziser"),rs.getInt("rok"),rs.getInt("hodnotenie"),rs.getString("comment"),rs.getInt("vek"),zoznam);
                Statement state = conn.createStatement();
                MovieMap.put(meno, movie);

            }}}




    static  ArrayList<String> uploadZoznam(String meno,Connection conn) throws SQLException
    {
        ArrayList<String> zoznam= new ArrayList<String>();

        Statement stmt = conn.createStatement();
        ResultSet herec = stmt.executeQuery("SELECT * FROM herci WHERE meno = '"+meno+"'");
        while (herec.next())
        {
            zoznam.add(herec.getString("meno"));
        }
        MovieMap.putAll((Map<? extends String, ? extends Movie>) herec);
        return zoznam;
    }
}
