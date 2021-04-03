package robertianburns.cryptarchsarchive;

/**
 * The Presentation node info.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class LoreBookSelectionInformation {

    /**
     * The icon ID.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private final int iconID;

    /**
     * The Node name.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private final String nodeName;

    /**
     * The node ID.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private long nodeID;


    /**
     * Instantiates a new Presentation node info.
     *
     * @param iconID   The Lore Book icon ID.
     * @param nodeName The Lore Book name.
     * @version 1.0.0
     * @since 1.0.0
     */
    public LoreBookSelectionInformation(int iconID, String nodeName) {
        this.iconID = iconID;
        this.nodeName = nodeName;
    }

    /**
     * Instantiates a new Presentation node info.
     *
     * @param iconID   The Lore Book icon ID.
     * @param nodeName The Lore Book name.
     * @param nodeID   The Lore Book ID.
     * @version 1.0.0
     * @since 1.0.0
     */
    public LoreBookSelectionInformation(int iconID, String nodeName, long nodeID) {
        this.iconID = iconID;
        this.nodeName = nodeName;
        this.nodeID = nodeID;
    }

    /**
     * Gets the Lore Book icon ID.
     *
     * @return The Lore Book icon ID.
     * @version 1.0.0
     * @since 1.0.0
     */
    public int getIconID() {
        return iconID;
    }

    /**
     * Gets Lore Book name.
     *
     * @return The Lore Book name.
     * @version 1.0.0
     * @since 1.0.0
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * Gets the Lore Book ID.
     *
     * @return The Lore Book ID.
     * @version 1.0.0
     * @since 1.0.0
     */
    public long getNodeID() {
        return nodeID;
    }
}
