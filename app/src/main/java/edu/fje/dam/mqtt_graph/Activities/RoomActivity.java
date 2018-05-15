package edu.fje.dam.mqtt_graph.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.franmontiel.fullscreendialog.FullScreenDialogFragment;

import edu.fje.dam.mqtt_graph.Charts.GaugageFragment;
import edu.fje.dam.mqtt_graph.R;

public class RoomActivity extends AppCompatActivity {
    GaugageFragment gaug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        this.overridePendingTransition(R.anim.slide_in,
                R.anim.slide_out);

        gaug = new GaugageFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.your_placeholder,gaug);
        fragmentTransaction.commit();

    }


    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_room, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // gaug.mqttHelper.close();

    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            // do something here
            new FullScreenDialogFragment.Builder(RoomActivity.this)
                    .setTitle("Chart")
                    .setConfirmButton("Create")
                    .setOnConfirmListener(new FullScreenDialogFragment.OnConfirmListener() {

                        @Override
                        public void onConfirm(@Nullable Bundle result) {
                            Log.d("MODAL","New chart");
                        }
                    })
                    .setOnDiscardListener(new FullScreenDialogFragment.OnDiscardListener() {
                        @Override
                        public void onDiscard() {

                        }
                    })
                    .setContent(ModalFragment.class, null)
                    .build()
                    .show(getSupportFragmentManager(),"modal");


        }
        return super.onOptionsItemSelected(item);
    }
}
