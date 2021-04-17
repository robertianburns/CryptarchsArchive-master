package robertianburns.cryptarchsarchive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * The GrimoireMainmenuActivity Class.
 */
public class GrimoireMainmenuActivity extends AppCompatActivity {

    /**
     * On create for 'grimoire_mainmenu'.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, then this Bundle contains the data it most recently
     *                           supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grimoire_mainmenu);

        // OnClickListeners for the Grimoire Sections (Themes)
        ImageButton guardianImageButton = (ImageButton) findViewById(R.id.guardianImageButton);
        guardianImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGrimoireCategorySelection(v);
            }
        });

        ImageButton inventoryImageButton = (ImageButton) findViewById(R.id.inventoryImageButton);
        inventoryImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGrimoireCategorySelection(v);
            }
        });

        ImageButton alliesImageButton = (ImageButton) findViewById(R.id.alliesImageButton);
        alliesImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGrimoireCategorySelection(v);
            }
        });

        ImageButton enemiesImageButton = (ImageButton) findViewById(R.id.enemiesImageButton);
        enemiesImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGrimoireCategorySelection(v);
            }
        });

        ImageButton placesImageButton = (ImageButton) findViewById(R.id.placesImageButton);
        placesImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGrimoireCategorySelection(v);
            }
        });

        ImageButton activitiesImageButton = (ImageButton) findViewById(R.id.activitiesImageButton);
        activitiesImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGrimoireCategorySelection(v);
            }
        });
    }

    /**
     * Opens the Grimoire category selection.
     * <p>
     * This method opens the Grimoire category selection from the
     * Grimoire Section menu/grimoire_mainemnu.
     *
     * @param view The Grimoire Category Section.
     */
    public void openGrimoireCategorySelection(View view) {
        try {
            String section = capitalise(view.getTag().toString());

            GrimoireContainer object = GrimoireContainer.getObject();
            while (object.getSectionCollection() == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedexception) {
                    interruptedexception.printStackTrace();
                }
                object = GrimoireContainer.getObject();
            }
            object.setSection(section);

            Intent intent = new Intent(GrimoireMainmenuActivity.this, GrimoireCategorySelectionActivity.class);
            String sectionTag = view.getTag().toString();
            intent.putExtra("themeTag", sectionTag);
            startActivity(intent);
        } catch (Exception ignored) {
        }
    }

    /**
     * Capitalises a string. Used for the Section tag.
     *
     * @param string The string being capitalised.
     * @return The capitalised string.
     */
    private String capitalise(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

}
