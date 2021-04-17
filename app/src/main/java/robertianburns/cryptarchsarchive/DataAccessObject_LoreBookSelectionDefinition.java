package robertianburns.cryptarchsarchive;

import android.arch.persistence.room.Entity;

/**
 * The Data access object type for presentation node (what I call Lore Book Selection) definition.
 * <p>
 * This is used to access DestinyPresentationNodeDefinition from Destiny 2's manifest
 * (destiny2_manifest in <i>assets > databases</i>). The manifest is a collection compressed SQLite
 * files holding static definitions of objects found within Destiny.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
@Entity(tableName = "DestinyPresentationNodeDefinition")
public class DataAccessObject_LoreBookSelectionDefinition extends ManifestDefinition {
}
