package robertianburns.cryptarchsarchive;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.huma.room_for_asset.RoomAsset;

/**
 * This marks DataAccessObject as classes of RoomDatabase. As Cryptarch's Archive manages
 * significant amounts of structured data, it benefits from persisting that data locally. Relevant
 * pieces of data (Grimoire and Lore) are cached, so when the device cannot access the network, the
 * user can still browse that content while they are offline.
 * <p>
 * ManifestDatabase databaseInstance is volatile so the value of a field becomes visible to all
 * readers (other threads) after a completed write operation.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
@Database(entities = {DataAccessObject_LoreBookSelectionDefinition.class, DataAccessObject_LoreEntrySelectionDefinition.class, DataAccessObject_LoreEntryDefinition.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class ManifestDatabase extends RoomDatabase {
    private static volatile ManifestDatabase databaseInstance;

    /**
     * Gets the database instance. The database is initialised if there is no instance.
     *
     * @param context The context.
     * @return The database databaseInstance.
     * @version 1.0.0
     * @since 1.0.0
     */
    public static ManifestDatabase getInstance(Context context) {
        if (databaseInstance == null) {
            initialiseDatabase(context);
        }
        return databaseInstance;
    }

    /**
     * Builds the Destiny 2 lore database. The lore database is deleted and recreated if the
     * application version doesn't match up with the build configuration version.
     * <p>
     * This accesses and modifies preference data. This is so no matter the set of preferences,
     * there is a single preference instance that all users share. Objects that are returned from
     * this are treated as immutable by Cryptarch's Archive.
     * <p>
     * While using expensive operations which might slow down the application, this provides strong
     * consistency guarantees.
     *
     * @param context The context.
     * @version 1.0.0
     * @since 1.0.0
     */
    private static void initialiseDatabase(Context context) {
        synchronized (ManifestDatabase.class) {
            if (databaseInstance == null) {
                int appVersion = PreferenceManager.getDefaultSharedPreferences(context).getInt("appVersion", -1);

                if (appVersion != BuildConfig.VERSION_CODE) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                    editor.putInt("appVersion", BuildConfig.VERSION_CODE);
                    editor.apply();
                    editor.putBoolean("instantiated", false);
                    editor.commit();
                    context.deleteDatabase("destiny2_manifest.db");
                }
                databaseInstance = RoomAsset.databaseBuilder(context, ManifestDatabase.class, "destiny2_manifest.db").build();
            }
        }
    }

    /**
     * Gets DataAccessObjects.
     *
     * @return the Data Access Object.
     * @version 1.0.0
     * @since 1.0.0
     */
    public abstract DataAccessObjects getDao();
}
