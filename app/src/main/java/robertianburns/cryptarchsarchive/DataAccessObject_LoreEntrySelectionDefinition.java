package robertianburns.cryptarchsarchive;

import android.arch.persistence.room.Entity;

/**
 * The Data access object type for record (what I call Lore Entry Selection) definition.
 * <p>
 * This is used to access DestinyRecordDefinition from Destiny 2's manifest (destiny2_manifest in
 * <i>assets > databases</i>). The manifest is a collection compressed SQLite files holding static
 * definitions of objects found within Destiny.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
@Entity(tableName = "DestinyRecordDefinition")
public class DataAccessObject_LoreEntrySelectionDefinition extends ManifestDefinition {
}
