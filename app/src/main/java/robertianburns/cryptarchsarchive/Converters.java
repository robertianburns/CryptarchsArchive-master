package robertianburns.cryptarchsarchive;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.nio.charset.StandardCharsets;

/**
 * Converts things needed for the app to work. This is needed for ManifestDefinition and to save
 * fields into database.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class Converters {
    private static final JsonParser parser = new JsonParser();

    /**
     * Parses a string as a JSON object.
     * <p>
     * This method parses a string (from bytes) and gets the specified string as a JSON object.
     * JSON object consists of name-value pairs where books are strings, and values are any other
     * type of JSON element. This allows for the creation of a tree of JSON elements. The member
     * elements of this object are maintained in order they were added.
     *
     * @param bytes The bytes from which a string is parsed from.
     * @return Parsed string as a JSON object.
     * @version 1.0.0
     * @since 1.0.0
     */
    @TypeConverter
    public static JsonObject fromJson(byte[] bytes) {
        String string = new String(bytes);
        return parser.parse(string.substring(0, string.length() - 1)).getAsJsonObject();
    }

    /**
     * Needed to save fields into database.
     * <p>
     * This method converts a JSON object to a byte array using eight-bit UCS Transformation Format
     * encoding. 'toString()' gives the textual String representation (text) of the JSON, and
     * 'getBytes()' encodes that String into a byte array.
     *
     * @param jObject The JSON object being converted to a byte array.
     * @return The byte array (byte []) of the JSON object.
     * @version 1.0.0
     * @since 1.0.0
     */
    @TypeConverter
    public static byte[] toJson(JsonObject jObject) {
        return jObject.toString().getBytes(StandardCharsets.UTF_8);
    }
}
