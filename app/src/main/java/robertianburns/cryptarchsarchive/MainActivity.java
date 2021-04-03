package robertianburns.cryptarchsarchive;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The 'Main activity. This is the first activity and is what shows the user the option to choose
 * between Destiny 1 Grimoire or Destiny 2 Lore.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        new Thread(new parseGrimoire()).start();
    }

    /**
     * Starts the Destiny 1 Grimoire activity.
     *
     * @param view The Destiny 1 Grimoire Lore view.
     * @version 1.0.0
     * @since 1.0.0
     */
    public void openGrimoire(View view) {
        startActivity(new Intent(MainActivity.this, GrimoireMainmenuActivity.class));
    }

    /**
     * Starts the Destiny 2 Lore activity.
     *
     * @param view The Destiny 2 Lore Sections and Books view.
     * @version 1.0.0
     * @since 1.0.0
     */
    public void openLore(View view) {
        startActivity(new Intent(MainActivity.this, LoreMainmenuActivity.class));
    }

    /**
     * Parses Grimoire Json.
     * <p>
     * This is parsed in the initial activity to improve speed and performance.
     *
     * @version 1.0.0
     * @since 1.0.0
     */
    private class parseGrimoire implements Runnable {

        @Override
        public void run() {
            StringBuilder stringbuilder = new StringBuilder();
            BufferedReader bufferedreader;
            try {
                Resources resource = getResources();
                InputStreamReader input = new InputStreamReader(resource.openRawResource(R.raw.grimoire));
                bufferedreader = new BufferedReader(input);
                String temp;
                while ((temp = bufferedreader.readLine()) != null) {
                    stringbuilder.append(temp);
                }
                bufferedreader.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            JsonObject json = new JsonParser().parse(stringbuilder.toString()).getAsJsonObject();
            JsonArray themeCollection = json.getAsJsonObject("Response").getAsJsonArray("themeCollection");
            GrimoireContainer.getObject().setThemeCollection(themeCollection);
        }
    }
}
