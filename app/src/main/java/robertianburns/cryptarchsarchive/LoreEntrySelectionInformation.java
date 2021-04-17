package robertianburns.cryptarchsarchive;

/**
 * The type Record info.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class LoreEntrySelectionInformation extends LoreBookSelectionInformation {

    /**
     * The Lore entry name.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private final String entryName;

    /**
     * The Lore entry id.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private final long entryID;

    /**
     * Instantiates a new Record info.
     *
     * @param iconId    The icon ID.
     * @param nodeName  The node name.
     * @param entryName The lore entry name.
     * @param entryID   The lore entry ID.
     * @version 1.0.0
     * @since 1.0.0
     */
    public LoreEntrySelectionInformation(int iconId, String nodeName, String entryName, long entryID) {
        super(iconId, nodeName);
        this.entryName = entryName;
        this.entryID = entryID;
    }

    /**
     * Gets lore entry name.
     * <p>
     * This method affects the Lore Entry name in the selection menu.
     *
     * @return The Lore Entry name in the selection menu.
     * @version 1.0.0
     * @since 1.0.0
     */
//  Lore Entry name in the selection menu.
    public String getEntryName() {
        return entryName;
    }

    /**
     * Gets lore entry ID.
     * <p>
     * This method affects Lore Entry text.
     *
     * @return the lore entry ID.
     * @version 1.0.0
     * @since 1.0.0
     */
    public long getEntryID() {
        return entryID;
    }
}
