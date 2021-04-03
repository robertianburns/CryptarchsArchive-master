package robertianburns.cryptarchsarchive;

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
 * The 'GrimoireCategorySelectionActivity' class for the Destiny 1 Grimoire GrimoireCardSelectionActivity.
 * <p>
 * Sets the category selection for the Grimoire GrimoireCardSelectionActivity, and also the header for the category title.
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
                    adapterList = createList();
                }
            });
            thread.start();

            HeaderGridView gridView = findViewById(R.id.selectionGridView);

            LinearLayout header = (LinearLayout) getLayoutInflater().inflate(R.layout.grimoire_categoryselection_header, null);

            TextView label = (TextView) header.getChildAt(0);
            String theme = getIntent().getStringExtra("themeTag");
            theme = theme.substring(0, 1).toUpperCase() + theme.substring(1);
            label.setText(theme);

            gridView.addHeaderView(header);
            try {
                thread.join();
            } catch (InterruptedException interruptedexception) {
                interruptedexception.printStackTrace();
            }
            GrimoireCategorySelectionAdapter grimoireCategorySelectionAdapter = new GrimoireCategorySelectionAdapter(this, adapterList);
            gridView.setAdapter(grimoireCategorySelectionAdapter);
        } catch (Exception exception) {
            Toast.makeText(this, "Unable to load Grimoire GrimoireCategorySelectionActivity", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Creates the pageCollection list.
     * <p>
     * This method gets a JSON object (page) from pageCollection, converts that into a string, and
     * adds that into an arraylist of page books. This is repeated until the size of the page
     * collection has been reduced to 0.
     *
     * @return The pageCollection list.
     * @version 1.0.0
     * @since 1.0.0
     */
    private List<String> createList() {
        ArrayList<String> pageNameList = new ArrayList<>();
        try {
            JsonArray pageCollection = grimoire.getPageCollection();

            for (int i = 0; i < pageCollection.size(); i++) {
                JsonObject page = (JsonObject) pageCollection.get(i);

                String name = page.get("pageName").getAsString();
                pageNameList.add(name);
            }
            return pageNameList;

        } catch (Exception exception) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(GrimoireCategorySelectionActivity.this, "Error Loading Grimoire GrimoireCategorySelectionActivity", Toast.LENGTH_LONG).show();
                }
            });
            return pageNameList;
        }


    }

    /**
     * Shows the Destiny 1 Grimoire Card.
     * <p>
     * This method supplies a tag for this view containing a String (page), which is to be later
     * retrieved with View.getTag(). The page is set with this tag and the page is added as
     * extended data to the new intent.
     *
     * @param view The view of the card.
     * @version 1.0.0
     * @since 1.0.0
     */
    public void showCard(View view) {
        String page = view.getTag().toString();

        grimoire.setPage(page);

        Intent intent = new Intent(GrimoireCategorySelectionActivity.this, GrimoireCardSelectionActivity.class);
        intent.putExtra("pageTag", page);
        startActivity(intent);
    }
}
