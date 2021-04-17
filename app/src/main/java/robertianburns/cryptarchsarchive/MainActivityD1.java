package robertianburns.cryptarchsarchive;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The 'Main activity' for the Destiny 1 menu (the first menu). This is the first activity and is
 * what shows the user the option to choose to view Destiny 1 Grimoire.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class MainActivityD1 extends AppCompatActivity {
    MediaPlayer currentMusic;
    Boolean muted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d1selectmenu);

        new Thread(new parseGrimoire()).start();

//      Main menu music when the activity starts. Can be overridden with the music button.
        final MediaPlayer vanillaMusic = MediaPlayer.create(this, R.raw.destiny1_vanillamenumusic);
        final MediaPlayer tdbMusic = MediaPlayer.create(this, R.raw.destiny1_tdbmenumusic);
        final MediaPlayer howMusic = MediaPlayer.create(this, R.raw.destiny1_howmenumusic);
        final MediaPlayer ttkMusic = MediaPlayer.create(this, R.raw.destiny1_ttkmenumusic);
        final MediaPlayer roiMusic = MediaPlayer.create(this, R.raw.destiny1_roimenumusic);

        currentMusic = vanillaMusic;
        currentMusic.start();

//      A mute button for the music.
        final Button muteButton = (Button) this.findViewById(R.id.muteButton);
        muteButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            public void onClick(View v) {
                if (!muted) {
                    muted = true;
                    currentMusic.setVolume(0, 0);
                    muteButton.setText("Unmute music");
                } else {
                    muted = false;
                    currentMusic.setVolume(1, 1);
                    muteButton.setText("Mute music");
                }
            }
        });

//      Starts the Destiny 1 Grimoire activity.
        Button grimorieButton = findViewById(R.id.grimorieButton);
        grimorieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivityD1.this, GrimoireMainmenuActivity.class));
            }
        });

//      Starts the Destiny 2 Menu activity.
        Button openDestiny2Menu = findViewById(R.id.destiny1to2Button);
        openDestiny2Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMusic.stop();
                startActivity(new Intent(MainActivityD1.this, MainActivityD2.class));
            }
        });

        // Referencing and Initializing the button
        final Button d1ThemeButton = (Button) findViewById(R.id.d1ThemeButton);

        // Setting onClick behavior to the button
        d1ThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu d1PopupMenu = new PopupMenu(MainActivityD1.this, d1ThemeButton);

                // Inflating popup menu from popup_menu.xml file
                d1PopupMenu.getMenuInflater().inflate(R.menu.destiny1_popup_menu, d1PopupMenu.getMenu());
                d1PopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        ImageView background = findViewById(R.id.d1menubackground);
                        int id = menuItem.getItemId();
                        switch (id) {
                            case (R.id.original):
                                background.setImageResource(R.drawable.d1menuvanilla);

                                currentMusic.pause();
                                currentMusic.seekTo(0);
                                currentMusic = vanillaMusic;
                                currentMusic.start();
                                break;
                            case (R.id.theDarkBelow):
                                background.setImageResource(R.drawable.d1menuthedarkbelow);

                                currentMusic.pause();
                                currentMusic.seekTo(0);
                                currentMusic = tdbMusic;
                                currentMusic.start();
                                break;
                            case (R.id.houseOfWolves):
                                background.setImageResource(R.drawable.d1menuhouseofwolves);

                                currentMusic.pause();
                                currentMusic.seekTo(0);
                                currentMusic = howMusic;
                                currentMusic.start();
                                break;
                            case (R.id.theTakenKing):
                                background.setImageResource(R.drawable.d1menuthetakenking);

                                currentMusic.pause();
                                currentMusic.seekTo(0);
                                currentMusic = ttkMusic;
                                currentMusic.start();
                                break;
                            case (R.id.riseOfIron):
                                background.setImageResource(R.drawable.d1menuriseofiron);

                                currentMusic.pause();
                                currentMusic.seekTo(0);
                                currentMusic = roiMusic;
                                currentMusic.start();
                                break;
                            default:
                                return false;
                        }
                        return false;
                    }
                });
                d1PopupMenu.show();
            }
        });
    }

    // Stops the Destiny 1 music playing in the Destiny 2 menu if the user presses the back button.
    public void onBackPressed() {
        currentMusic.pause();
        currentMusic.seekTo(0);
        super.onBackPressed();
    }


    /**
     * Parses Grimoire Json.
     * <p>
     * This is parsed in the initial activity to improve speed and performance. The application can
     * be very slow without this, and can cause the application to hang.
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
            GrimoireContainer.getObject().setSectionCollection(themeCollection);
        }
    }
}
