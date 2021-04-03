package robertianburns.cryptarchsarchive;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The LoreBookSelection adapter.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class LoreBookSelectionAdapter extends RecyclerView.Adapter<LoreBookSelectionAdapter.LoreBookSelectionViewHolder> {

    /**
     * The inflater.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private final LayoutInflater inflater;

    /**
     * The database.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private final ManifestDatabase database;

    /**
     * The fragment manager.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private final FragmentManager fragmentManager;

    /**
     * The LoreBookSelection data.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    List<LoreBookSelectionInformation> loreBookSelectionData;

    /**
     * The entry ID map.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private ConcurrentHashMap<String, long[]> entryIDMap;

    /**
     * The entry ID threads.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private ThreadGroup entryIDThreads;

    /**
     * Instantiates a new LoreBookSelection adapter.
     *
     * @param context               The context.
     * @param loreBookSelectionData The LoreBookSelection data.
     * @param fragmentManager       The fragment manager.
     * @version 1.0.0
     * @since 1.0.0
     */
    public LoreBookSelectionAdapter(Context context, List<LoreBookSelectionInformation> loreBookSelectionData, FragmentManager fragmentManager) {
        super();
        this.inflater = LayoutInflater.from(context);
        this.loreBookSelectionData = loreBookSelectionData;
        this.database = ManifestDatabase.getInstance(context);
        this.fragmentManager = fragmentManager;
        loadLoreEntries();
    }

    /**
     * This method is called when RecyclerView (bookArea in 'lore_mainmenu.xml') needs a new
     * RecyclerView.ViewHolder to represent a new Lore Book. This new ViewHolder is constructed
     * with a new View that can represent the new Lore Book. The view is inflated from the XML
     * layout file.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an
     *                 adapter position.
     * @param viewType The view type of the new View.
     * @return The presentation node view holder.
     * @version 1.0.0
     * @since 1.0.0
     */
    @NonNull
    @Override
    public LoreBookSelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lore_bookselection, parent, false);
        return new LoreBookSelectionViewHolder(view);
    }

    /**
     * This method is called by RecyclerView to display the data at the specified position. This
     * updates the contents of the RecyclerView.ViewHolder.itemView to reflect the Lore Book at the
     * given position. This method is overridden if Adapter can handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item
     *                 at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(LoreBookSelectionViewHolder holder, int position) {
        LoreBookSelectionInformation current = loreBookSelectionData.get(position);

        holder.bookIcon.setImageResource(current.getIconID());
        holder.bookName.setText(current.getNodeName());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items held.
     */
    @Override
    public int getItemCount() {
        return loreBookSelectionData.size();
    }

    /**
     * Loads the child entities (entries of Lore Books) contained by the selected Lore Book in the
     * selection menu.
     * <p>
     * This method creates the entries of Lore Books of a Lore Book in the selection menu by
     * creating a new Runnable that is put into a thread, and pairs the entry's name with its ID.
     * If a presentation node (Lore Book) has an associated 'Record' (entry), then
     * <b>RecordHash</b> is the identifier of that Record.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private void loadLoreEntries() {
        entryIDMap = new ConcurrentHashMap<>();
        entryIDThreads = new ThreadGroup("entryIDThreads");

        for (int i = 0; i < loreBookSelectionData.size(); i++) {

            final String entryName = loreBookSelectionData.get(i).getNodeName();
            final long entryID = loreBookSelectionData.get(i).getNodeID();

            new Thread(entryIDThreads, new Runnable() {
                @Override
                public void run() {
                    try {
                        ArrayList<Long> arraylist = new ArrayList<>();
                        arraylist.add(entryID);
                        DataAccessObject_LoreBookSelectionDefinition node = database.getDao().getPresentationNodeById(arraylist).get(0);
                        JsonObject json = node.getJson();
                        JsonArray entryNodes = json.getAsJsonObject("children").getAsJsonArray("records");

                        long[] ID = new long[entryNodes.size()];
                        for (int j = 0; j < entryNodes.size(); j++) {
                            JsonObject entry = (JsonObject) entryNodes.get(j);
                            long hash = Long.parseLong(entry.get("recordHash").getAsString());
                            ID[j] = convertHash(hash);
                        }
                        entryIDMap.put(entryName, ID);
                    } catch (Exception ignored) {
                    }
                }
            }).start();
        }
    }

    /**
     * Converts the hash for 'presentationNode'.
     * <p>
     * This converts the hash identifier of the Presentation Node for whom we should return
     * details for using a convert hash method.
     * <p>
     * When entities refer to each other in Destiny content, it is this hash that they are
     * referring to.
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
     * The LoreBookSelection view holder.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    class LoreBookSelectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * The Lore Book icon.
         *
         * @version 1.0.0
         * @since 1.0.0
         */
        ImageView bookIcon;

        /**
         * The Lore Book name text.
         *
         * @version 1.0.0
         * @since 1.0.0
         */
        TextView bookName;

        /**
         * Instantiates a new LoreBookSelection view holder in 'lore_bookselection.xml'.
         *
         * @param itemView The Lore Book.
         * @version 1.0.0
         * @since 1.0.0
         */
        public LoreBookSelectionViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            bookIcon = itemView.findViewById(R.id.bookIcon);
            bookName = itemView.findViewById(R.id.bookName);
        }

        /**
         * The on click method for the Lore Books.
         * <p>
         * This method is what causes the list of clickable lore entries to appear after clicking
         * on a Lore Book. It adds entries into LoreEntrySelectionFragment to be added to the back
         * stack and committed to the FragmentTransaction transaction. This uses FragmentManager
         * as an interface for interacting with LoreEntrySelectionFragment inside of an Activity,
         * and uses beginTransaction() to start a series of edit operations on the Fragments
         * associated with this FragmentManager.
         *
         * @param view The Lore Book causing the onClick to trigger.
         * @version 1.0.0
         * @since 1.0.0
         */
        @Override
        public void onClick(View view) {
            String name = bookName.getText().toString();
            while (entryIDThreads.activeCount() > 0) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException interruptedexception) {
                    interruptedexception.printStackTrace();
                }
            }
            LoreEntrySelectionFragment entryFragment = new LoreEntrySelectionFragment();
            entryFragment.setName(name);
            entryFragment.setloreEntrySelectionIDs(entryIDMap.get(name));
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.loreBooksActivity, entryFragment, "LoreEntrySelectionFragment").addToBackStack(null).commit();
        }
    }
}
