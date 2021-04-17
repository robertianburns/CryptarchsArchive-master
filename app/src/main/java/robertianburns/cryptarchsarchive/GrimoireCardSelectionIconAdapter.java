package robertianburns.cryptarchsarchive;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Creates the <b>Grimoire Card icons/grimoire_selection_icon.xml.</b>
 * <p>
 * This class creates the Grimoire Card icons through getting the amount of Grimoire Cards, getting
 * the Grimoire Card item position, getting Grimoire Card item ID, getting the image from drawable,
 * and setting them all in getView.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class GrimoireCardSelectionIconAdapter extends BaseAdapter {
    private final List<String> cardIDList, cardNameList;
    private final Activity activity;

    /**
     * Instantiates a new Grid view adapter.
     *
     * @param activity     The activity.
     * @param cardIDList   The card ID list.
     * @param cardNameList The card name list.
     */
    public GrimoireCardSelectionIconAdapter(Activity activity, List<String> cardIDList, List<String> cardNameList) {
        super();
        this.cardIDList = cardIDList;
        this.cardNameList = cardNameList;
        this.activity = activity;
    }

    /**
     * Gets the amount of Grimoire Cards.
     *
     * @return The amount of Grimoire Cards.
     * @version 1.0.0
     * @since 1.0.0
     */
    @Override
    public int getCount() {
        return cardIDList.size();
    }

    /**
     * Gets the Grimoire Card item position.
     *
     * @return The Grimoire Card item position.
     * @version 1.0.0
     * @since 1.0.0
     */
    @Override
    public String getItem(int position) {
        return cardIDList.get(position);
    }

    /**
     * Gets the Grimoire Card item ID.
     *
     * @return The Grimoire Card item ID.
     * @version 1.0.0
     * @since 1.0.0
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Gets the image from drawable (the folder containing the images).
     *
     * @param imageFileName The filename of the image.
     * @return The image from drawable.
     * @version 1.0.0
     * @since 1.0.0
     */
    private int getImage(String imageFileName) {
        Resources resources = activity.getResources();
        if (Character.isDigit(imageFileName.charAt(0))) {
            imageFileName = 'c' + imageFileName;
        }
        return resources.getIdentifier(imageFileName, "drawable", activity.getPackageName());
    }

    /**
     * Creates the Grimoire Card icons.
     * <p>
     * Sets the Grimoire Card icon position, image text (the white text), and image from the
     * drawable folder.
     *
     * @param position    The position of the Grimoire Card icon.
     * @param convertView The Grimoire Card icon to inflate (the XML layout to be parsed).
     * @param parent      The inherited view group (from the superclass cardSelectionIconAdapter).
     * @return The Grimoire Card icon position and image.
     * @version 1.0.0
     * @since 1.0.0
     */
    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GrimoireCardSelectionIconAdapter.ViewHolder view;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            view = new ViewHolder();
            convertView = inflater.inflate(R.layout.grimoire_selection_icon, null);

            view.imageButtonTextView = convertView.findViewById(R.id.imageButtonText);
            view.imageButtonIcon = convertView.findViewById(R.id.imageButtonIcon);

            convertView.setTag(view);
        } else {
            view = (GrimoireCardSelectionIconAdapter.ViewHolder) convertView.getTag();
        }
        view.imageButtonTextView.setText(cardNameList.get(position));

        int imageID = getImage(cardIDList.get(position));
        if (cardIDList.get(position).equals("grimoire_cover")) {
            view.imageButtonIcon.setTag("");
            view.imageButtonIcon.setImageResource(imageID);
        } else {
            view.imageButtonIcon.setImageResource(imageID);
            view.imageButtonIcon.setTag(cardIDList.get(position));
        }
        return convertView;
    }

    /**
     * The type View holder.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    public static class ViewHolder {

        /**
         * The Image button page.
         *
         * @version 1.0.0
         * @since 1.0.0
         */
        public ImageButton imageButtonIcon;

        /**
         * The Page text.
         *
         * @version 1.0.0
         * @since 1.0.0
         */
        public TextView imageButtonTextView;
    }
}
