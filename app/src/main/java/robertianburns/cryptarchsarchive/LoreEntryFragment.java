package robertianburns.cryptarchsarchive;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * The type Lore fragment.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class LoreEntryFragment extends Fragment {
    private final ManifestDatabase database;
    private LoreEntrySelectionInformation info;

    /**
     * Instantiates a new Lore fragment.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    public LoreEntryFragment() {
        database = ManifestDatabase.getInstance(getActivity());
    }


    /**
     * Creates the <b>Lore Entry/lore_entry.</b>
     * <p>
     * This method grabs objects from the database and compiles them to create a Lore Entry. A
     * Lore Entry is compiled from:
     * <ul>
     *     <li><b>title</b>, the title associated with the Lore Entry.</li>
     *     <li><b>entryText</b>, the description associated with the Lore Entry.</li>
     * <ul/>
     *
     * @return The chosen Lore Entry.
     * @version 1.0.0
     * @since 1.0.0
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lore_entry, container, false);
        try {
            final TextView loreText = view.findViewById(R.id.entryText);
            final Thread getLore = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ArrayList<Long> arraylist = new ArrayList<>();
                        arraylist.add(info.getLoreEntryID());
                        DataAccessObject_LoreEntryDefinition dataAccessObjectLoreDefinition = database.getDao().getLoreById(arraylist).get(0);
                        String text = dataAccessObjectLoreDefinition.getJson().getAsJsonObject("displayProperties").get("description").getAsString();
                        loreText.setText(text);

                    } catch (Exception e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "Unable to access database", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            });

//          Start a new thread to get the lore.
            getLore.start();

//          Get the lore title and lore description.
            TextView title = view.findViewById(R.id.entryTitle);
            title.setText(info.getLoreEntryName());

//          Wait for the getLore thread to die, so all information is properly gathered.
            getLore.join();

            return view;
        } catch (Exception exception) {
//          Show a Toast if loading the Lore Entry failed.
            Toast.makeText(getActivity(), "Unable to load Lore Entry", Toast.LENGTH_LONG).show();
            return view;
        }
    }


    /**
     * Sets the LoreEntrySelectionInformation as the information of the Lore Entry.
     *
     * @return LoreEntrySelectionInformation.
     * @version 1.0.0
     * @since 1.0.0
     */
    public void setInfo(LoreEntrySelectionInformation info) {
        this.info = info;
    }
}
