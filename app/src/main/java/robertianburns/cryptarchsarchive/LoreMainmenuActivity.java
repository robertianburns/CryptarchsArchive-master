package robertianburns.cryptarchsarchive;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Lore Records activity.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class LoreMainmenuActivity extends AppCompatActivity {
    private final String[] sectionNames = {"The Light", "Dusk and Dawn", "The Darkness"};
    private final ConcurrentHashMap<String, long[]> bookMap = new ConcurrentHashMap<>();
    private RecyclerView recyclerView;
    private ManifestDatabase database;
    private Thread getNodeChildrenThread;
    private ImageButton sectionImageButton;

    /**
     * Creates the <b>Lore Page/lore_entry.</b>
     * <p>
     * This method grabs objects from the database and compiles them to create a Lore Page. A Lore
     * Page is compiled from:
     * <ul>
     *     <li><b>title</b>, the title associated with the Lore Page.</li>
     *     <li><b>entryText</b>, the description associated with the Lore Page.</li>
     * <ul/>
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database = ManifestDatabase.getInstance(this);

        getNodeChildrenThread = new LoreBooksThread(sectionNames);
        getNodeChildrenThread.start();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lore_mainmenu);
        recyclerView = findViewById(R.id.bookArea);

//      Pre-select 'The Light' Lore Books when lore_mainmenu's loads.
        sectionImageButton = findViewById(R.id.sectionButton);
        showBooks(findViewById(R.id.sectionButton));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Shows the Lore Books view.
     *
     * @param view The view of the Lore Books.
     * @version 1.0.0
     * @since 1.0.0
     */
    public void showBooks(View view) {
        try {
            sectionImageButton.setBackgroundTintList(null);
            sectionImageButton = (ImageButton) view;

//          Returns the ColorStateList from loreSectionSelectedColour, and sets that to tint the
//          currently selected Lore Book Section.
            view.setBackgroundTintList(getResources().getColorStateList(R.color.loreSectionSelectedColour));

            String name = view.getTag().toString();

            try {
                getNodeChildrenThread.join();
            } catch (InterruptedException interruptedexception) {
                interruptedexception.printStackTrace();
            }

            long[] nodes = bookMap.get(name);
            final ArrayList<LoreBookSelectionInformation> bookSelectionInformationList = new ArrayList<>();
            final ArrayList<Long> nodeID = new ArrayList<>(1);
            nodeID.add(0L); // 0L means the number zero of type long.

            for (long node : Objects.requireNonNull(nodes)) {
                nodeID.set(0, node);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<DataAccessObject_LoreBookSelectionDefinition> list = database.getDao().getPresentationNodeById(nodeID);
                            JsonObject json = list.get(0).getJson();
                            String bookName = json.getAsJsonObject("displayProperties").get("name").getAsString();
                            // Sometimes Bungie adds a lore book but makes it inaccessible until they release it.
                            if (bookName.equals("Classified") || bookName.isEmpty()) {
                                return;
                            }
                            int bookImage = getImageResource(bookName);
                            bookSelectionInformationList.add(new LoreBookSelectionInformation(bookImage, bookName, list.get(0).getID()));
                        } catch (Exception exception) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoreMainmenuActivity.this, "Unable to access database", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException interruptedexception) {
                    interruptedexception.printStackTrace();
                }
            }
            LoreBookSelectionAdapter bookSelectionAdapter = new LoreBookSelectionAdapter(this, bookSelectionInformationList, getFragmentManager());
            recyclerView.setAdapter(bookSelectionAdapter);
        } catch (Exception exception) {
            Toast.makeText(this, "Unable to load Lore Books", Toast.LENGTH_LONG).show();
        }

    }


    /**
     * Converts the hash for 'presentationNode'.
     * <p>
     * This converts the hash identifier of the Presentation Node for whom we should return details
     * for using a convert hash method. Details will only be returned for Lore Books that are direct
     * descendants of this node.
     * <p>
     * Hashes are unique identifier for Destiny entities, and are guaranteed to be unique for the
     * type of entity, but not globally. When entities refer to each other in Destiny content, it is
     * this hash that they are referring to.
     *
     * @param hash The hash identifier of the Presentation Node.
     * @return The converted Presentation Node hash identifier.
     * @version 1.0.0
     * @since 1.0.0
     */
    private long convertHash(long hash) {
        final long offset = 4294967296L;
        if ((hash & (offset / 2)) != 0) {
            return hash - offset;
        } else {
            return hash;
        }
    }

    /**
     * Grabs the image for the book in 'lore_mainmenu'.
     * <p>
     * This gets the image resource identifier for the Lore Books. Images are stored in the drawable
     * folder to be used when needed.
     *
     * @param name The name of the object which needs the get the image.
     * @return The image for the Lore Book in 'lore_mainmenu'.
     * @version 1.0.0
     * @since 1.0.0
     */
    private int getImageResource(String name) {
        Resources resources = this.getResources();
        String fileName = name.replaceAll("[^a-zA-Z0-9 ]", "").replaceAll(" ", "_").toLowerCase();
        return resources.getIdentifier(fileName, "drawable", this.getPackageName());
    }


    /**
     * Loads the Lore Books in 'lore_mainmenu'.
     * <p>
     * This method uses multiple loops to get the books and IDs of the Lore Books in the three Lore
     * Sections. The Lore Books IDs are grabbed and their presentationNodeHashes are converted to be
     * paired with the corresponding Lore Book's name.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private class LoreBooksThread extends Thread {
        /**
         * The books of the NodeChildren.
         *
         * @version 1.0.0
         * @since 1.0.0
         */
        String[] books;

        /**
         * Instantiates a new Node children thread.
         *
         * @param books The books of the node children (Lore Books) of the Lore Sections (the three
         *              clickable icons at the top).
         * @version 1.0.0
         * @since 1.0.0
         */
        LoreBooksThread(String[] books) {
            this.books = books;
        }

        // This loads the lore books.
        @Override
        public void run() {
            try {
                for (String book : books) {
                    DataAccessObject_LoreBookSelectionDefinition section = database.getDao().getPresentationNodeByText("%" + book + "%").get(0);
                    JsonObject sectionJson = section.getJson();
                    JsonArray bookNodes = sectionJson.getAsJsonObject("children").getAsJsonArray("presentationNodes");

                    long[] id = new long[bookNodes.size()];
                    for (int i = 0; i < bookNodes.size(); i++) {
                        JsonObject loreBook = (JsonObject) bookNodes.get(i);
                        long hash = Long.parseLong(loreBook.get("presentationNodeHash").getAsString());
                        id[i] = convertHash(hash);
                    }
                    bookMap.put(book, id);
                }
            } catch (Exception exception) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoreMainmenuActivity.this, "Unable to access database", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}