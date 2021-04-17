package robertianburns.cryptarchsarchive;

import android.arch.persistence.room.Entity;

/**
 * The Data access object type for lore (what I call Lore Entry) definition.
 * <p>
 * This is used to access DestinyLoreDefinition from Destiny 2's manifest (destiny2_manifest in
 * <i>assets > databases</i>). The manifest is a collection compressed SQLite files holding static
 * definitions of objects found within Destiny.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
@Entity(tableName = "DestinyLoreDefinition")
public class DataAccessObject_LoreEntryDefinition extends ManifestDefinition {
}
