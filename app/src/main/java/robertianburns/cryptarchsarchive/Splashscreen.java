package robertianburns.cryptarchsarchive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * The splashscreen for Cryptarch's Archive. This displays an image and then slowly fades over two
 * seconds into the Destiny 1 menu.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class Splashscreen extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        int secondsDelayed = 2;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Splashscreen.this, MainActivityD1.class));

//              Fade into the mainmenu. This is much better than a sudden, snappy change (fits
//              better with the music).
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, secondsDelayed * 1000);
    }
}