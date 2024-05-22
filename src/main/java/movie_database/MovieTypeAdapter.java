package movie_database;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MovieTypeAdapter implements JsonSerializer<Movie>, JsonDeserializer<Movie> {

    @Override
    public JsonElement serialize(Movie src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", src.getType());
        obj.add("name", new JsonPrimitive(src.getName()));
        obj.add("director", new JsonPrimitive(src.getDirector()));
        obj.add("year", new JsonPrimitive(src.getYear()));
        obj.add("rating", new JsonPrimitive(src.getRatings()));
        obj.add("comment", src.getComment() == null ? JsonNull.INSTANCE : new JsonPrimitive(src.getComment()));
        obj.add("actorsList", context.serialize(src.getActorsList()));
        if (src instanceof AnimatedMovie) {
            obj.add("vek", new JsonPrimitive(((AnimatedMovie) src).getVek()));
        }
        return obj;
    }

    @Override
    public Movie deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String type = obj.get("type").getAsString();

        System.out.println("Debug: Deserializing type: " + type);

        switch (type) {
            case "Animated movie":
                return deserializeAnimatedMovie(obj, context);
            case "Live action movie":
                return deserializeLiveActionMovie(obj, context);
            default:
                throw new JsonParseException("Unknown element type: " + type);
        }
    }

    private AnimatedMovie deserializeAnimatedMovie(JsonObject json, JsonDeserializationContext context) {
        String name = json.has("name") ? json.get("name").getAsString() : null;
        String director = json.has("director") ? json.get("director").getAsString() : null;
        int year = json.has("year") ? json.get("year").getAsInt() : 0;
        int rating = json.has("rating") ? json.get("rating").getAsInt() : 0;
        String comment = json.has("comment") ? json.get("comment").getAsString() : null;
        int vek = json.has("vek") ? json.get("vek").getAsInt() : 0;
        ArrayList<String> actorsList = json.has("actorsList") ? context.deserialize(json.get("actorsList"), new TypeToken<ArrayList<String>>(){}.getType()) : new ArrayList<>();

        return new AnimatedMovie(name, director, year, rating, comment, vek, actorsList);
    }

    private Live_action_movie deserializeLiveActionMovie(JsonObject json, JsonDeserializationContext context) {
        String name = json.has("name") ? json.get("name").getAsString() : null;
        String director = json.has("director") ? json.get("director").getAsString() : null;
        int year = json.has("year") ? json.get("year").getAsInt() : 0;
        int rating = json.has("rating") ? json.get("rating").getAsInt() : 0;
        String comment = json.has("comment") ? json.get("comment").getAsString() : null;
        ArrayList<String> actorsList = json.has("actorsList") ? context.deserialize(json.get("actorsList"), new TypeToken<ArrayList<String>>(){}.getType()) : new ArrayList<>();

        return new Live_action_movie(name, director, year, rating, comment, actorsList);
    }
}
