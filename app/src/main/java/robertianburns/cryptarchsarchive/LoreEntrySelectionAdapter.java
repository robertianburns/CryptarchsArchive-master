package robertianburns.cryptarchsarchive;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * The Record adapter.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class LoreEntrySelectionAdapter extends RecyclerView.Adapter<LoreEntrySelectionAdapter.EntrySelectionViewHolder> {

    /**
     * The layout inflater.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private final LayoutInflater inflater;

    /**
     * The fragment manager.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private final FragmentManager fragmentManager;

    /**
     * The Lore Entry selection data.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    List<LoreEntrySelectionInformation> entrySelectionInformation;

    /**
     * Instantiates a new Record adapter.
     *
     * @param context                   The context.
     * @param entrySelectionInformation The record data.
     * @param fragmentManager           The fragmentManager.
     * @version 1.0.0
     * @since 1.0.0
     */
    public LoreEntrySelectionAdapter(Context context, List<LoreEntrySelectionInformation> entrySelectionInformation, FragmentManager fragmentManager) {
        super();
        this.inflater = LayoutInflater.from(context);
        this.entrySelectionInformation = entrySelectionInformation;
        this.fragmentManager = fragmentManager;
    }


    /**
     * On create view holder record view holder.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an
     *                 adapter position.
     * @param viewType The view type of the new View.
     * @return The record view holder.
     * @version 1.0.0
     * @since 1.0.0
     */
    @NonNull
    @Override
    public EntrySelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View entrySelectionView = inflater.inflate(R.layout.lore_entryselection, parent, false);
        return new EntrySelectionViewHolder(entrySelectionView);
    }

    /**
     * On bind view holder.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item
     *                 at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     * @version 1.0.0
     * @since 1.0.0
     */
    @Override
    public void onBindViewHolder(EntrySelectionViewHolder holder, int position) {
        LoreEntrySelectionInformation current = entrySelectionInformation.get(position);
        holder.entrySelectionText.setText(current.getEntryName());
    }

    /**
     * Gets the amount of the Lore Entry selections.
     *
     * @return The amount of the Lore Entry selections.
     * @version 1.0.0
     * @since 1.0.0
     */
    @Override
    public int getItemCount() {
        return entrySelectionInformation.size();
    }

    /**
     * The type Record view holder.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    class EntrySelectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * The Lore Entry Selection text.
         *
         * @version 1.0.0
         * @since 1.0.0
         */
        TextView entrySelectionText;

        /**
         * Instantiates a new Record view holder.
         *
         * @param itemView the item view
         * @version 1.0.0
         * @since 1.0.0
         */
        public EntrySelectionViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            entrySelectionText = itemView.findViewById(R.id.selectionName);
        }

        /**
         * The on click method for the Lore Entries in 'lore_entryselection.xml'.
         *
         * @param view The Lore Entry causing the onClick to trigger.
         * @version 1.0.0
         * @since 1.0.0
         */
        @Override
        public void onClick(View view) {
            LoreEntrySelectionInformation information = entrySelectionInformation.get(getAdapterPosition());

            LoreEntryFragment entryFragment = new LoreEntryFragment();
            entryFragment.setEntrySelectionInformation(information);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.loreBooksActivity, entryFragment, "LoreEntryFragment").addToBackStack(null).commit();
        }
    }
}
