package gustavus.mcs270.gustavusinteractivemap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

/**
 * Created by Matt on 4/23/18.
 */

public class StartupActivity extends AppCompatActivity {

    FrameLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        background = (FrameLayout) findViewById(R.id.startup_frame);
    }
}
