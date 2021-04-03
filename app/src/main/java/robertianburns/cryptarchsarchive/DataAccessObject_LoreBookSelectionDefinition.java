package robertianburns.cryptarchsarchive;

import android.arch.persistence.room.Entity;

/**
 * The Data access object type for presentation node (what I call Lore Book Selection) definition .
 * <p>
 * This is used to access DestinyPresentationNodeDefinition from Bungie's database.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
@Entity(tableName = "DestinyPresentationNodeDefinition")
public class DataAccessObject_LoreBookSelectionDefinition extends ManifestDefinition {
}
