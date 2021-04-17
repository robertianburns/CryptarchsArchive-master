package robertianburns.cryptarchsarchive;

/**
 * The Presentation node info.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class LoreBookSelectionInformation {

    /**
     * The book's icon ID.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private final int bookIconID;

    /**
     * The book name.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private final String bookName;

    /**
     * The book ID.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private long bookID;


    /**
     * Instantiates a new Presentation node info.
     *
     * @param bookIconID The Lore Book icon ID.
     * @param bookName   The Lore Book name.
     * @version 1.0.0
     * @since 1.0.0
     */
    public LoreBookSelectionInformation(int bookIconID, String bookName) {
        this.bookIconID = bookIconID;
        this.bookName = bookName;
    }

    /**
     * Instantiates a new Presentation node info.
     *
     * @param bookIconID The Lore Book icon ID.
     * @param bookName   The Lore Book name.
     * @param bookID     The Lore Book ID.
     * @version 1.0.0
     * @since 1.0.0
     */
    public LoreBookSelectionInformation(int bookIconID, String bookName, long bookID) {
        this.bookIconID = bookIconID;
        this.bookName = bookName;
        this.bookID = bookID;
    }

    /**
     * Gets the Lore Book icon ID.
     *
     * @return The Lore Book icon ID.
     * @version 1.0.0
     * @since 1.0.0
     */
    public int getBookIconID() {
        return bookIconID;
    }

    /**
     * Gets Lore Book name.
     *
     * @return The Lore Book name.
     * @version 1.0.0
     * @since 1.0.0
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * Gets the Lore Book ID.
     *
     * @return The Lore Book ID.
     * @version 1.0.0
     * @since 1.0.0
     */
    public long getBookID() {
        return bookID;
    }
}
