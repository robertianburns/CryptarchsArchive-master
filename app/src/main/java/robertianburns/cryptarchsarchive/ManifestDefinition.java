package robertianburns.cryptarchsarchive;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.JsonObject;

/**
 * The type Manifest definition.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class ManifestDefinition {
    
    /**
     * The ID.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    @PrimaryKey
    protected int id;

    /**
     * The JSON.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    protected JsonObject json;

    /**
     * Gets the ID.
     *
     * @return The ID being got.
     * @version 1.0.0
     * @since 1.0.0
     */
    public int getID() {
        return id;
    }

    /**
     * Gets the JSON object.
     *
     * @return The JSON object being got.
     * @version 1.0.0
     * @since 1.0.0
     */
    public JsonObject getJson() {
        return json;
    }

    /**
     * Sets the JSON object.
     *
     * @param json The JSON object being set.
     * @version 1.0.0
     * @since 1.0.0
     */
    public void setJson(JsonObject json) {
        this.json = json;
    }
}
