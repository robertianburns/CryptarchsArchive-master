package robertianburns.cryptarchsarchive;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

/**
 * The 'Main activity' for the Destiny 2 menu (the first menu). This is the second activity and is
 * what shows the user the option to choose to view Destiny 2 Grimoire.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class MainActivityD2 extends AppCompatActivity {
    MediaPlayer currentMusic;
    Boolean muted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d2selectmenu);

//      Main menu music when the activity starts. Can be overridden with the music button.
        final MediaPlayer vanillaMusic = MediaPlayer.create(this, R.raw.destiny2_vanillamenumusic);
        final MediaPlayer cooMusic = MediaPlayer.create(this, R.raw.destiny2_coomenumusic);
        final MediaPlayer warmindMusic = MediaPlayer.create(this, R.raw.destiny2_warmindmenumusic);
        final MediaPlayer forsakenMusic = MediaPlayer.create(this, R.raw.destiny2_forsakenmenumusic);
        final MediaPlayer shadowkeepMusic = MediaPlayer.create(this, R.raw.destiny2_shadowkeepmenumusic);
        final MediaPlayer blMusic = MediaPlayer.create(this, R.raw.destiny2_blmenumusic);

        currentMusic = vanillaMusic;
        currentMusic.start();

//      A mute button for the music.
        final Button muteButton = this.findViewById(R.id.muteButton);
        muteButton.setOnClickListener(new View.OnClickListener() {
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

//      Starts the Destiny 2 Lore activity.
        Button loreButton = findViewById(R.id.loreButton);
        loreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivityD2.this, LoreMainmenuActivity.class));
            }
        });

//      Starts the Destiny 1 Menu activity.
        Button openDestiny1Menu = findViewById(R.id.destiny2to1Button);
        openDestiny1Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMusic.stop();
                startActivity(new Intent(MainActivityD2.this, MainActivityD1.class));
            }
        });

        // Referencing and Initializing the button
        final Button d2ThemeButton = (Button) findViewById(R.id.d2ThemeButton);

        // Setting onClick behavior to the button
        d2ThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu d2PopupMenu = new PopupMenu(MainActivityD2.this, d2ThemeButton);

                // Inflating popup menu from popup_menu.xml file
                d2PopupMenu.getMenuInflater().inflate(R.menu.destiny2_popup_menu, d2PopupMenu.getMenu());
                d2PopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        ImageView background = findViewById(R.id.d2menubackground);
                        int id = menuItem.getItemId();
                        switch (id) {
                            case (R.id.original):
                                background.setImageResource(R.drawable.d2menuvanilla);

                                currentMusic.pause();
                                currentMusic.seekTo(0);
                                currentMusic = vanillaMusic;
                                currentMusic.start();
                                break;
                            case (R.id.curseOfOsiris):
                                background.setImageResource(R.drawable.d2menucurseofosiris);

                                currentMusic.pause();
                                currentMusic.seekTo(0);
                                currentMusic = cooMusic;
                                currentMusic.start();
                                break;
                            case (R.id.warmind):
                                background.setImageResource(R.drawable.d2menuwarmind);

                                currentMusic.pause();
                                currentMusic.seekTo(0);
                                currentMusic = warmindMusic;
                                currentMusic.start();
                                break;
                            case (R.id.forsaken):
                                background.setImageResource(R.drawable.d2menuforsaken);

                                currentMusic.pause();
                                currentMusic.seekTo(0);
                                currentMusic = forsakenMusic;
                                currentMusic.start();
                                break;
                            case (R.id.shadowkeep):
                                background.setImageResource(R.drawable.d2menushadowkeep);

                                currentMusic.pause();
                                currentMusic.seekTo(0);
                                currentMusic = shadowkeepMusic;
                                currentMusic.start();
                                break;
                            case (R.id.beyondLight):
                                background.setImageResource(R.drawable.d2menubeyondlight);

                                currentMusic.pause();
                                currentMusic.seekTo(0);
                                currentMusic = blMusic;
                                currentMusic.start();
                                break;
                            default:
                                return false;
                        }
                        return false;
                    }
                });
                d2PopupMenu.show();
            }
        });
    }

    // Stops the Destiny 2 music playing in the Destiny 1 menu if the user presses the back button.
    public void onBackPressed() {
        currentMusic.pause();
        currentMusic.seekTo(0);
        super.onBackPressed();
    }
}