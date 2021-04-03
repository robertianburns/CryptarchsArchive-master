package robertianburns.cryptarchsarchive;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The LoreEntrySelection fragment.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class LoreEntrySelectionFragment extends Fragment {

    /**
     * The database for LoreEntrySelection.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private final ManifestDatabase database;

    /**
     * The Lore Book name.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private String loreBookName;

    /**
     * The Lore Book icon ID.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private int loreBookIconID;

    /**
     * The lore entry selection IDs.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private long[] loreEntrySelectionIDs;

    /**
     * Instantiates a new LoreEntrySelection fragment.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    public LoreEntrySelectionFragment() {
        database = ManifestDatabase.getInstance(getActivity());
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in
     *                           the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should
     *                           be attached to. The fragment should not add the view itself, but
     *                           this can be used to generate the LayoutParams of the view. This
     *                           value may be null.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *                           saved state as given here.
     * @return The View for the fragment's UI, or null.
     * @version 1.0.0
     * @since 1.0.0
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lore_entryselection_header, container, false);

        try {

            ImageView loreBookIcon = view.findViewById(R.id.bookIconHEADER);
            loreBookIconID = getImageResource(loreBookName);
            loreBookIcon.setImageResource(loreBookIconID);

            TextView header = view.findViewById(R.id.bookNameHEADER);
            header.setText(loreBookName);

            RecyclerView recyclerView = view.findViewById(R.id.entryList);
            LoreEntrySelectionAdapter loreEntrySelectionAdapter = new LoreEntrySelectionAdapter(getActivity(), createLoreEntrySelectionInformationList(), getFragmentManager());
            recyclerView.setAdapter(loreEntrySelectionAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            return view;
        } catch (Exception exception) {
            Toast.makeText(getActivity(), "Unable to load Lore Entry list", Toast.LENGTH_LONG).show();
            return view;
        }
    }

    /**
     * Sets entryFragment.setName in LoreBookSelectionAdapter.
     *
     * @param name the name
     * @version 1.0.0
     * @since 1.0.0
     */
    public void setName(String name) {
        this.loreBookName = name;
    }

    /**
     * Sets entryFragment.setloreEntrySelectionIDs in LoreBookSelectionAdapter.
     *
     * @param loreEntrySelectionIDs The lore entry selection IDs
     * @version 1.0.0
     * @since 1.0.0
     */
    public void setloreEntrySelectionIDs(long[] loreEntrySelectionIDs) {
        this.loreEntrySelectionIDs = loreEntrySelectionIDs;
    }

    /**
     * Create LoreEntrySelectionInformation list.
     *
     * @return the list
     * @version 1.0.0
     * @since 1.0.0
     */
    private List<LoreEntrySelectionInformation> createLoreEntrySelectionInformationList() {
        final ArrayList<LoreEntrySelectionInformation> loreEntrySelectionInformationList = new ArrayList<>(Arrays.asList(new LoreEntrySelectionInformation[loreEntrySelectionIDs.length - 1]));

        ThreadGroup getLoreEntrySelectionInformation = new ThreadGroup("getLoreEntrySelectionInformation");

        for (int i = 1; i < loreEntrySelectionIDs.length; i++) {
            final int index = i;
            final long id = loreEntrySelectionIDs[i];

            new Thread(getLoreEntrySelectionInformation, new Runnable() {
                @Override
                public void run() {
                    try {
                        ArrayList<Long> arraylist = new ArrayList<>();
                        arraylist.add(id);
                        DataAccessObject_LoreEntrySelectionDefinition loreEntrySelection = database.getDao().getRecordById(arraylist).get(0);
                        JsonObject loreEntrySelectionJson = loreEntrySelection.getJson();

                        String loreEntryName = loreEntrySelectionJson.getAsJsonObject("displayProperties").get("name").getAsString();
                        long loreEntryID = convertHash(loreEntrySelectionJson.get("loreHash").getAsLong());

                        loreEntrySelectionInformationList.set(index - 1, new LoreEntrySelectionInformation(loreBookIconID, loreBookName, loreEntryName, loreEntryID));

                    } catch (Exception e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "Error accessing database", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }).start();
        }

        // Hardcoded lore entries to fix missing lore entries.
        if (loreBookName.equals("Marasenna")) {
            loreEntrySelectionInformationList.add(new LoreEntrySelectionInformation(loreBookIconID, loreBookName, "Palingenesis III", 445714340));
        } else if (loreBookName.equals("The Awoken of the Reef")) {
            loreEntrySelectionInformationList.add(0, new LoreEntrySelectionInformation(loreBookIconID, loreBookName, "Revanche I", 445714341));
        }
        return loreEntrySelectionInformationList;
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
     * Gets the image of the Lore Book.
     *
     * @param name the name
     * @return The image of the Lore Book.
     * @version 1.0.0
     * @since 1.0.0
     */
    private int getImageResource(String name) {
        String fileName = name.replaceAll("[^a-zA-Z0-9 ]", "").replaceAll(" ", "_").toLowerCase();
        return getResources().getIdentifier(fileName, "drawable", getActivity().getPackageName());
    }
}
