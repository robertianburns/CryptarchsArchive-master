package robertianburns.cryptarchsarchive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
    }

    /**
     * Opens the Grimoire category selection.
     * <p>
     * This method opens the Grimoire category selection from the
     * Grimoire Section menu/grimoire_mainenu.
     *
     * @param view The Grimoire Category Section.
     */
    public void openGrimoireCategorySelection(View view) {
        try {
            String grimoireSection = capitalise(view.getTag().toString());

            GrimoireContainer grimoireObject = GrimoireContainer.getObject();
            while (grimoireObject.getThemeCollection() == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedexception) {
                    interruptedexception.printStackTrace();
                }
                grimoireObject = GrimoireContainer.getObject();
            }
            grimoireObject.setTheme(grimoireSection);

            Intent intent = new Intent(GrimoireMainmenuActivity.this, GrimoireCategorySelectionActivity.class);
            String themeTag = view.getTag().toString();
            intent.putExtra("themeTag", themeTag);
            startActivity(intent);
        } catch (Exception ignored) {
        }
    }

    /**
     * Capitalises a string.
     *
     * @param string The string being capitalised.
     * @return The capitalised string.
     */
    private String capitalise(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

}
