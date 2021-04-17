package robertianburns.cryptarchsarchive;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * The Activity class for the Destiny 1 Grimoire Card selection.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class GrimoireCardSelectionActivity extends AppCompatActivity {
    private ArrayList<String> cardIDList, cardNameList;
    private GrimoireContainer grimoire;
    private JsonArray cardCollection;

    /**
     * Initialises and sets the layout for the <b>Grimoire card menu/grimoire_cardselection.xml.</b>
     * <p>
     * This method gets grimoire objects and puts them into a GridView. It creates:
     * <ul>
     *     <li>The selectionGridView, for a view that shows items in two-dimensional scrolling grid.</li>
     *     <li>The header, so the user can see the category name at the top of the screen.</li>
     *     <li>The category name, which links to the header.</li>
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
            setContentView(R.layout.grimoire_cardselection);

//          Get Grimoire objects and the Grimoire Card selection Activity.
            grimoire = GrimoireContainer.getObject();
            cardCollection = grimoire.getCardCollection();

//          Set custom adapter to selectionGridView.
            HeaderGridView gridView = findViewById(R.id.selectionGridView);
            LayoutInflater inflater = this.getLayoutInflater();
            @SuppressLint("InflateParams") LinearLayout cardSelectionHeader = (LinearLayout) inflater.inflate(R.layout.grimoire_cardselection_header, null);

//          The name of category the user clicked on is displayed at the top of the screen.
            String categoryName = getIntent().getStringExtra("pageTag");
            categoryName = categoryName.charAt(0) + categoryName.substring(1);

            TextView headerTextView = (TextView) cardSelectionHeader.getChildAt(0);
            headerTextView.setText(categoryName);

//          Create the card categories with a new selectionGridView.
            createCardLists();
            GrimoireCardSelectionIconAdapter adapter = new GrimoireCardSelectionIconAdapter(this, cardIDList, cardNameList);

//          Add HeaderView and set the adapter.
            gridView.addHeaderView(cardSelectionHeader);
            gridView.setAdapter(adapter);

        } catch (Exception exception) {
            Toast.makeText(this, "ERROR CODE ORANGE: Unable to create Grimoire Card selection", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Creates the Grimoire Cards selection list.
     * <p>
     * This method populates two empty array lists (cardIDList and cardNameList) with JSON objects.
     * These JSON objects are got from JSON elements to be given to these two empty array lists as
     * strings.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private void createCardLists() {
        cardIDList = new ArrayList<>();
        cardNameList = new ArrayList<>();

        for (JsonElement jElement : cardCollection) {
            JsonObject jObject = jElement.getAsJsonObject();
            cardIDList.add(jObject.get("cardId").getAsString());
            cardNameList.add(jObject.get("cardName").getAsString());
        }
    }

    /**
     * Shows the Grimoire Card selection cards and gives them intents.
     * <p></p>
     * This method dynamically creates a new intent and activity. cardID is a string that is set
     * onto a bit of Grimoire, creating a clickable card.
     *
     * @param view The view of the Grimoire Card selection card.
     * @version 1.0.0
     * @since 1.0.0
     */
    public void showCard(View view) {
        String cardID = view.getTag().toString();
        grimoire.setCard(cardID);

        Intent intent = new Intent(GrimoireCardSelectionActivity.this, GrimoireCardActivity.class);
        startActivity(intent);
    }
}