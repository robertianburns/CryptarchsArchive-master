package robertianburns.cryptarchsarchive;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The 'categorySelectionActivity' class for the Grimoire Card selection Activity.
 * <p>
 * Sets the category selection for cardSelectionActivity, and also the header for the category
 * title.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class GrimoireCategorySelectionActivity extends AppCompatActivity {
    private final GrimoireContainer grimoire = GrimoireContainer.getObject();
    private List<String> adapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.grimoire_categoryselection);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    adapterList = createCategorySelectionList();
                }
            });
            thread.start();

            HeaderGridView gridView = findViewById(R.id.selectionGridView);
            @SuppressLint("InflateParams") LinearLayout categoryHeader = (LinearLayout) getLayoutInflater().inflate(R.layout.grimoire_categoryselection_header, null);

//          Create the Grimoire category selection header with the Grimoire Section (theme) text.
            TextView categoryHeaderText = (TextView) categoryHeader.getChildAt(0);
            String headerText = getIntent().getStringExtra("themeTag");
            headerText = headerText.substring(0, 1).toUpperCase() + headerText.substring(1);

            categoryHeaderText.setText(headerText);
            gridView.addHeaderView(categoryHeader);
            try {
                thread.join();
            } catch (InterruptedException interruptedexception) {
                interruptedexception.printStackTrace();
            }
            GrimoireCategorySelectionAdapter categorySelectionAdapter = new GrimoireCategorySelectionAdapter(this, adapterList);
            gridView.setAdapter(categorySelectionAdapter);
        } catch (Exception exception) {
            Toast.makeText(this, "ERROR CODE KIWI: Unable to setup Grimoire Category selection", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Creates the Grimoire category selection list.
     * <p>
     * This method gets a JSON object (category), converts that into a
     * string, and adds that into an arraylist of categories. This is repeated until the size of
     * the categories collection has been reduced to 0.
     *
     * @return The pageCollection list.
     * @version 1.0.0
     * @since 1.0.0
     */
    private List<String> createCategorySelectionList() {
        ArrayList<String> categorySelectionList = new ArrayList<>();
        try {
            JsonArray categorySelectionCollection = grimoire.getCategoryCollection();

            for (int i = 0; i < categorySelectionCollection.size(); i++) {
                JsonObject category = (JsonObject) categorySelectionCollection.get(i);
                String categorySelectionName = category.get("pageName").getAsString();

                categorySelectionList.add(categorySelectionName);
            }
            return categorySelectionList;

        } catch (Exception exception) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(GrimoireCategorySelectionActivity.this, "ERROR CODE PAPAYA: Unable to create Grimoire Category selection list", Toast.LENGTH_LONG).show();
                }
            });
            return categorySelectionList;
        }
    }

    /**
     * Shows the Grimoire Category selection cards and gives them intents.
     * <p>
     * This method dynamically creates a new intent and activity. This supplies a tag for this view
     * containing a String (page), which is to be later retrieved with View.getTag(). The page is
     * set with this tag and the page is added as extended data to the new intent.
     *
     * @param view The view of the Grimoire Category selection card.
     * @version 1.0.0
     * @since 1.0.0
     */
    public void showCard(View view) {
        String card = view.getTag().toString();
        grimoire.setCategory(card);

        Intent intent = new Intent(GrimoireCategorySelectionActivity.this, GrimoireCardSelectionActivity.class);
        intent.putExtra("pageTag", card);
        startActivity(intent);
    }
}