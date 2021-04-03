package robertianburns.cryptarchsarchive;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

public class GrimoireCardActivity extends AppCompatActivity {

    /**
     * Creates the <b>Grimoire Card/grimoire_card.</b>
     * <p>
     * This method grabs specific objects and compiles them all to create a Grimoire Card for
     * Destiny 1. A Grimoire Card is compiled from:
     * <ul>
     *     <li>cardImageFile, the image associated with the Grimoire Card.</li>
     *     <li>cardName, the name associated with the Grimoire Card.</li>
     *     <li>cardQuote, the quote associated with the Grimoire Card.</li>
     *     <li>cardQuoteReference, the quote reference associated with the Grimoire Card.</li>
     *     <li>cardDescription, the description associated with the Grimoire Card.</li>
     * <ul/>
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, then this Bundle contains the data it most recently
     *                           supplied in onSaveInstanceState(Bundle).
     * @return The chosen Grimoire Card.
     * @version 1.0.0
     * @since 1.0.0
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.grimoire_card);

            JsonObject cardJson = GrimoireContainer.getObject().getCardJson();

//          Set the card's image source.
            int cardImageFile = getImage(cardJson.get("cardId").toString());
            ImageView cardImage = findViewById(R.id.cardImage);
            cardImage.setImageResource(cardImageFile);

//          Set the card's name text.
            String cardName = cardJson.get("cardName").toString();
            cardName = cardName.substring(1, cardName.length() - 1);
            TextView cardNameText = findViewById(R.id.cardName);
            cardNameText.setText(cardName);

//          Set the quote and quote reference, if applicable.
            if (cardJson.get("cardIntro") != null) {
                String cardQuote = parseHTML(cardJson.get("cardIntro").toString());
                cardQuote = cardQuote.substring(1, cardQuote.length() - 1);
                TextView cardIntroText = findViewById(R.id.cardQuote);
                cardIntroText.setText(cardQuote);
                cardIntroText.setVisibility(View.VISIBLE);
            }
            if (cardJson.get("cardIntroAttribution") != null) {
                String cardQuoteReference = parseHTML(cardJson.get("cardIntroAttribution").toString());
                cardQuoteReference = cardQuoteReference.substring(1, cardQuoteReference.length() - 1);
                TextView cardIntroAttributionText = findViewById(R.id.cardQuoteReference);
                cardIntroAttributionText.setText(cardQuoteReference);
                cardIntroAttributionText.setVisibility(View.VISIBLE);
            }

//          Set the card's description text.
            if (cardJson.get("cardDescription") != null) {
                String cardDescription = parseHTML(cardJson.get("cardDescription").toString());
                cardDescription = cardDescription.substring(1, cardDescription.length() - 1);
                TextView cardDescriptionText = findViewById(R.id.cardDescription);
                cardDescriptionText.setText(cardDescription);
            } else {
                findViewById(R.id.cardDescriptionArea).setVisibility(View.GONE);
            }
        } catch (Exception exception) {
            Toast.makeText(this, "Unable to load Grimoire Card", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Parses the Grimoire Card description.
     * <p>
     * As there are words and phrases which are bolded (<b>like this<b/>) or italicised
     * (<i>like this<i/>) with HTML tags in Bungie's Grimoire Card descriptions, the HTML must be
     * parsed so these don't appear as plain text.
     *
     * @return The parsed HTML for the retrieved description.
     * @version 1.0.0
     * @since 1.0.0
     */
    @SuppressWarnings("deprecation")
    private String parseHTML(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return String.valueOf(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY));
        } else {
            return String.valueOf(Html.fromHtml(html));
        }
    }

    /**
     * Grabs the image for the specified Grimoire Card.
     * <p>
     * This method grabs the image for the specified Grimoire Card from the drawable folder.
     * Destiny 1 images have a 'c' at the beginning to differentiate them from Destiny 2 Lore Book
     * images.
     *
     * @return The Grimoire Card image.
     * @version 1.0.0
     * @since 1.0.0
     */
    private int getImage(String fileName) {
        Resources resources = getResources();
        if (Character.isDigit(fileName.charAt(0))) {
            fileName = 'c' + fileName;
        }
        return resources.getIdentifier(fileName, "drawable", getPackageName());
    }
}
