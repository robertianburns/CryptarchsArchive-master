package robertianburns.cryptarchsarchive;

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
 * Creates the <b>Grimoire Card icons/grimoire_cardselection_icon.</b>
 * <p>
 * This class creates the Grimoire Card icons through getting the amount of Grimoire Cards, getting
 * the Grimoire Card item position, getting Grimoire Card item ID, getting the image from drawable,
 * and setting them all in getView.
 *
 * @version 1.0.0
 * @return Grimoire Card icon.
 * @since 1.0.0
 */
public class GrimoireCardSelectionIconAdapter extends BaseAdapter {
    private final List<String> cardIdList;
    private final List<String> cardNameList;
    private final Activity activity;

    /**
     * Instantiates a new Grid view adapter.
     *
     * @param activity     The activity.
     * @param cardIdList   The card ID list.
     * @param cardNameList The card name list.
     */
    public GrimoireCardSelectionIconAdapter(Activity activity, List<String> cardIdList, List<String> cardNameList) {
        super();
        this.cardIdList = cardIdList;
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
        return cardIdList.size();
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
        return cardIdList.get(position);
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
     * @param fileName The filename of the image.
     * @return The image from drawable.
     * @version 1.0.0
     * @since 1.0.0
     */
    private int getImage(String fileName) {
        Resources resources = activity.getResources();
        if (Character.isDigit(fileName.charAt(0))) {
            fileName = 'c' + fileName;
        }
        return resources.getIdentifier(fileName, "drawable", activity.getPackageName());
    }

    /**
     * Creates the Grimoire Card icons.
     * <p>
     * Sets the Grimoire Card icon position and image from the drawable folder.
     *
     * @param position    The position of the Grimoire Card icon.
     * @param convertView The Grimoire Card icon to inflate (the XML layout to be parsed).
     * @param parent      The inherited view group (from the superclass GrimoireCardSelectionIconAdapter).
     * @return The Grimoire Card icon position and image.
     * @version 1.0.0
     * @since 1.0.0
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GrimoireCardSelectionIconAdapter.ViewHolder view;
        LayoutInflater inflator = activity.getLayoutInflater();

        if (convertView == null) {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.grimoire_cardselection_icon, null);

            view.pageText = (TextView) convertView.findViewById(R.id.imageButtonText);
            view.imageButtonPage = (ImageButton) convertView.findViewById(R.id.imageButtonIcon);

            convertView.setTag(view);
        } else {
            view = (GrimoireCardSelectionIconAdapter.ViewHolder) convertView.getTag();
        }

        view.pageText.setText(cardNameList.get(position));
        int imageID = getImage(cardIdList.get(position));

        if (cardIdList.get(position).equals("grimoire_cover")) {
            view.imageButtonPage.setTag("");
            view.imageButtonPage.setImageResource(imageID);
        } else {
            view.imageButtonPage.setImageResource(imageID);
            view.imageButtonPage.setTag(cardIdList.get(position));
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
        public ImageButton imageButtonPage;
        /**
         * The Page text.
         *
         * @version 1.0.0
         * @since 1.0.0
         */
        public TextView pageText;
    }
}
