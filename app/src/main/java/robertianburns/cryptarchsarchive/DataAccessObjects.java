package robertianburns.cryptarchsarchive;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Used to access data objects from Bungie's database.
 * <p>
 * These queries are run when the methods are called. Queries are verified at compile time to
 * ensure that they compile properly against the database.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
@Dao
public interface DataAccessObjects {

    /**
     * Gets LoreBookSelection (presentation node) by IDs.
     *
     * @param nodeIds The node IDs.
     * @return LoreBookSelection (presentation node) by ID.
     */
    @Query("select * from DestinyPresentationNodeDefinition where id = (:nodeIds)")
    List<DataAccessObject_LoreBookSelectionDefinition> getPresentationNodeById(List<Long> nodeIds);

    /**
     * Gets LoreBookSelection (presentation node) by text.
     *
     * @param s The LoreBookSelection (PresentationNode) string.
     * @return LoreBookSelection (PresentationNode) by text.
     */
    @Query("select * from DestinyPresentationNodeDefinition where json like :s")
    List<DataAccessObject_LoreBookSelectionDefinition> getPresentationNodeByText(String s);

    /**
     * Gets LoreEntrySelection (record) by ID.
     *
     * @param recordIds The LoreEntrySelection (record) IDs.
     * @return LoreEntrySelection (record) by ID.
     */
    @Query("select * from DestinyRecordDefinition where id = (:recordIds)")
    List<DataAccessObject_LoreEntrySelectionDefinition> getRecordById(List<Long> recordIds);

    /**
     * Gets LoreEntry (lore) by ID.
     *
     * @param loreIds The LoreEntry (lore) IDs.
     * @return LoreEntry (lore) by ID.
     */
    @Query("select * from DestinyLoreDefinition where id = (:loreIds)")
    List<DataAccessObject_LoreEntryDefinition> getLoreById(List<Long> loreIds);
}
