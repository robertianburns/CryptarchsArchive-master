package robertianburns.cryptarchsarchive;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * The 'GrimoireCategorySelectionAdapter' class for the Grimoire Card selection Activity.
 * <p>
 * This class extends BaseAdapter, so an Adapter can be used and customised. Base Adapter is a
 * common base class of a general implementation of an Adapter and is used in ListView, GridView,
 * Spinner, and so forth. As an adapter is a bridge between the User Interface and data sources, it
 * can be used to fill data in User Interface components. It pulls data from database or an array
 * and sends  data to an adapter view to send it to a view. A view can show this data on different
 * views Like ListView, GridView, Spinner, RecyclerView, et cetera, many of which Cryptarch's
 * Archive uses.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class GrimoireCategorySelectionAdapter extends BaseAdapter {
    private final List<String> categorySelectionList;
    private final Activity activity;

    /**
     * Instantiates a new GrimoireCategorySelectionActivity adapter.
     *
     * @param activity              The activity.
     * @param categorySelectionList The category name list.
     * @version 1.0.0
     * @since 1.0.0
     */
    public GrimoireCategorySelectionAdapter(Activity activity, List<String> categorySelectionList) {
        this.categorySelectionList = categorySelectionList;
        this.activity = activity;
    }

    /**
     * Gets how many items are in the data set represented by this Adapter.
     *
     * @return Count of categorySelectionList items.
     * @version 1.0.0
     * @since 1.0.0
     */
    @Override
    public int getCount() {
        return categorySelectionList.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position The position of the item whose data we want within the adapter's data set.
     * @return The categorySelectionList data at the specified position.
     * @version 1.0.0
     * @since 1.0.0
     */
    @Override
    public String getItem(int position) {
        return categorySelectionList.get(position);
    }

    /**
     * Get the row ID associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row ID we want.
     * @return The ID of the item at the specified position.
     * @version 1.0.0
     * @since 1.0.0
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Initialises and sets the <b>Grimoire Category selection icons/grimoire_selection_icon.</b>
     * <p>
     * This method instantiates the 'grimoire_selection_icon' XML file into its corresponding
     * View object.
     *
     * @param position    Position of the Grimoire Card selection icon.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent.
     * @return The id of the item at the specified position.
     * @version 1.0.0
     * @since 1.0.0
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GrimoireCategorySelectionAdapter.ViewHolder view;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            view = new ViewHolder();
            convertView = inflater.inflate(R.layout.grimoire_selection_icon, parent, false);
            view.imageButtonText = convertView.findViewById(R.id.imageButtonText);
            view.imageButtonIcon = convertView.findViewById(R.id.imageButtonIcon);

            convertView.setTag(view);
        } else {
            view = (GrimoireCategorySelectionAdapter.ViewHolder) convertView.getTag();
        }

        String categoryName = categorySelectionList.get(position);
        view.imageButtonText.setText(categoryName);
        int categoryImageID = getImage(categoryName);

        view.imageButtonIcon.setImageBitmap(BitmapFactory.decodeResource(activity.getResources(), categoryImageID));
        view.imageButtonIcon.setTag(categoryName);
        return convertView;
    }

    /**
     * Gets the Grimoire Card selection icon image.
     *
     * @param name The Grimoire Card selection icon name.
     * @return The Grimoire Card selection icon image.
     * @version 1.0.0
     * @since 1.0.0
     */
    private int getImage(String name) {
        String imageFileName = name.replaceAll(" & ", "_").replaceAll("[^a-zA-Z0-9_ ]", "").replaceAll(" ", "_").toLowerCase();
        return activity.getResources().getIdentifier(imageFileName, "drawable", activity.getPackageName());
    }

    /**
     * The View holder for the Grimoire Category/Card Icons.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    public static class ViewHolder {

        /**
         * The image button icon for the Grimoire Category/Card Icons.
         *
         * @version 1.0.0
         * @since 1.0.0
         */
        public ImageButton imageButtonIcon;

        /**
         * The image button text for the Grimoire Category/Card Icons.
         *
         * @version 1.0.0
         * @since 1.0.0
         */
        public TextView imageButtonText;
    }
}
