package movie_database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private static final String FILE_PATH = "movies.json";
    private Gson gson = new Gson();

    public Database() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Movie.class, new MovieTypeAdapter())
                .create();
    }

    public Map<String, Movie> loadMovies() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            System.out.println("Debug: JSON file does not exist or is empty.");
            return new HashMap<>();
        }


        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<Map<String, Movie>>() {}.getType();
            Map<String, Movie> movieMap = gson.fromJson(reader, type);
            if (movieMap == null) {
                movieMap = new HashMap<>();
            }
            // Check for and remove any null values in the map
           // movieMap.values().removeIf(movie -> movie == null);
            System.out.println("Debug: Loaded " + movieMap.size() + " movies from JSON.");
            return movieMap;
        } catch (IOException e) {
            System.out.println("An error occurred while loading movies: " + e.getMessage());
            return new HashMap<>();
        }
    }


    public void saveMovies(Map<String, Movie> movies) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(movies, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


