package movie_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String FILE_PATH = "movies.json";
    private Gson gson = new Gson();

    public List<Movie> loadMovies() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            // TypeToken used to handle generic type deserialization
            return gson.fromJson(reader, new TypeToken<List<Movie>>(){}.getType());
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveMovies(List<Movie> movies) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(movies, writer);
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }

  /*  private static Connection conn;
    public static Connection connect() {
        conn= null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:myDB.db");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return conn;


    }

    public static Connection getConnection() {
        return conn;
    }  public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void createTable() {
        try {
            Statement stmt = conn.createStatement();
            String sqlMovies = "CREATE TABLE IF NOT EXISTS movies " +
                    "(id INTEGER PRIMARY KEY, " +
                    "druh VARCHAR(255), " +
                    "meno VARCHAR(255), " +
                    "reziser VARCHAR(255), " +
                    "rok INT, " +
                    "hodnotenie INT, " +
                    "comment VARCHAR(255), " +
                    "vek INT)";
            stmt.executeUpdate(sqlMovies);

            String sqlHerci = "CREATE TABLE IF NOT EXISTS herci " +
                    "(id INTEGER PRIMARY KEY, " +
                    "meno VARCHAR(255), " +
                    "herec VARCHAR(255))";
            stmt.executeUpdate(sqlHerci);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }*/
}


