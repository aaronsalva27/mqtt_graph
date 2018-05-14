package edu.fje.dam.mqtt_graph.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.fje.dam.mqtt_graph.R;

public class SettingsActivity extends AppCompatActivity {
    private final String TITLE= "Settings";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle(TITLE);

        this.overridePendingTransition(R.anim.slide_in,
                R.anim.slide_out);
    }
}
