package robertianburns.cryptarchsarchive;

import android.arch.persistence.room.Entity;

/**
 * The Data access object type for lore (what I call Lore Entry) definition .
 * <p>
 * This is used to access DestinyLoreDefinition from Bungie's database.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
@Entity(tableName = "DestinyLoreDefinition")
public class DataAccessObject_LoreEntryDefinition extends ManifestDefinition {
}
