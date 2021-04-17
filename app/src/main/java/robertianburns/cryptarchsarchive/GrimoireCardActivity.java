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
     * This method grabs specific objects and compiles them all to create a Destiny 1 Grimoire
     * Card. A Grimoire Card is compiled from:
     * <ul>
     *     <li>cardImageFile, the image associated with the Grimoire Card.</li>
     *     <li>cardName, the name associated with the Grimoire Card.</li>getCardJson
     *     <li>cardQuote, the quote associated with the Grimoire Card.</li>
     *     <li>cardQuoteReference, the quote reference associated with the Grimoire Card.</li>
     *     <li>cardDescription, the description associated with the Grimoire Card.</li>
     * <ul/>
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, then this Bundle contains the data it most recently
     *                           supplied in onSaveInstanceState(Bundle).
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
            int cardImage = getImage(cardJson.get("cardId").toString());
            ImageView cardImageView = findViewById(R.id.cardImage);
            cardImageView.setImageResource(cardImage);

//          Set the card's name text.
            String cardName = cardJson.get("cardName").toString();
            cardName = cardName.substring(1, cardName.length() - 1);
            TextView cardNameText = findViewById(R.id.cardName);
            cardNameText.setText(cardName);

//          Set the quote and quote reference, if applicable.
            if (cardJson.get("cardIntro") != null) {
                String cardQuote = parseHTML(cardJson.get("cardIntro").toString());
                cardQuote = cardQuote.substring(1, cardQuote.length() - 1);
                TextView cardQuoteTextView = findViewById(R.id.cardQuote);
                cardQuoteTextView.setText(cardQuote);
                cardQuoteTextView.setVisibility(View.VISIBLE);
            }
            if (cardJson.get("cardIntroAttribution") != null) {
                String cardQuoteReference = parseHTML(cardJson.get("cardIntroAttribution").toString());
                cardQuoteReference = cardQuoteReference.substring(1, cardQuoteReference.length() - 1);
                TextView cardQuoteReferenceTextView = findViewById(R.id.cardQuoteReference);
                cardQuoteReferenceTextView.setText(cardQuoteReference);
                cardQuoteReferenceTextView.setVisibility(View.VISIBLE);
            }

//          Set the card's description text.
            String cardDescription = parseHTML(cardJson.get("cardDescription").toString());
            cardDescription = cardDescription.substring(1, cardDescription.length() - 1);
            TextView cardDescriptionTextView = findViewById(R.id.cardDescription);
            cardDescriptionTextView.setText(cardDescription);
        } catch (Exception exception) {
            Toast.makeText(this, "ERROR CODE APPLE: Unable to create Grimoire Card", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Parses the Grimoire Card description.
     * <p>
     * As there are words and phrases which are bolded (<b>like this<b/>) or italicised
     * (<i>like this<i/>) with HTML tags in Destiny 1's Grimoire Card descriptions, the HTML must
     * be parsed to prevent these appearing as plain text.
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
    private int getImage(String imageFileName) {
        Resources resources = getResources();
        if (Character.isDigit(imageFileName.charAt(0))) {
            imageFileName = 'c' + imageFileName;
        }
        return resources.getIdentifier(imageFileName, "drawable", getPackageName());
    }
}
