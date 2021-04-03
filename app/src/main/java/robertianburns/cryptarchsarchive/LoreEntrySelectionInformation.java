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
    private final String loreEntryName;

    /**
     * The Lore entry id.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private final long loreEntryID;

    /**
     * Instantiates a new Record info.
     *
     * @param iconId        The icon ID.
     * @param nodeName      The node name.
     * @param loreEntryName The lore entry name.
     * @param loreEntryID   The lore entry ID.
     * @version 1.0.0
     * @since 1.0.0
     */
    public LoreEntrySelectionInformation(int iconId, String nodeName, String loreEntryName, long loreEntryID) {
        super(iconId, nodeName);
        this.loreEntryName = loreEntryName;
        this.loreEntryID = loreEntryID;
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
    public String getLoreEntryName() {
        return loreEntryName;
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
    public long getLoreEntryID() {
        return loreEntryID;
    }
}
