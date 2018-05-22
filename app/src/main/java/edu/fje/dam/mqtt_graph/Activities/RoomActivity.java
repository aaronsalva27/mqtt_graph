package edu.fje.dam.mqtt_graph.Activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.franmontiel.fullscreendialog.FullScreenDialogFragment;

import edu.fje.dam.mqtt_graph.Charts.GaugageFragment;
import edu.fje.dam.mqtt_graph.Charts.ToggleFragment;
import edu.fje.dam.mqtt_graph.Models.Room;
import edu.fje.dam.mqtt_graph.R;

public class RoomActivity extends AppCompatActivity {
    GaugageFragment gaug;

    private RecyclerView recyclerView;
    private Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        this.overridePendingTransition(R.anim.slide_in,
                R.anim.slide_out);

        Bundle b = this.getIntent().getExtras();
        if (b != null)
            room = b.getParcelable("ROOM_OBJECT");
            setTitle(room.getName());
            Log.d("ROOM",room.toString().toUpperCase());
       /* recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
*/



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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("SETTINGS_ACTIVITY");
                Log.w("CHART",result);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.your_placeholder,new ToggleFragment());
                fragmentTransaction.commit();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            Intent intent = new Intent(this, ChartSettingsActivity.class);
            startActivityForResult(intent,1);


        }
        return super.onOptionsItemSelected(item);
    }
}
