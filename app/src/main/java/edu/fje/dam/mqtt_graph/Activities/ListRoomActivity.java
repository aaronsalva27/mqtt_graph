package edu.fje.dam.mqtt_graph.Activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import edu.fje.dam.mqtt_graph.Adapters.RoomListAdapter;
import edu.fje.dam.mqtt_graph.Charts.ToggleFragment;
import edu.fje.dam.mqtt_graph.Models.Room;
import edu.fje.dam.mqtt_graph.Models.User;
import edu.fje.dam.mqtt_graph.R;

public class ListRoomActivity extends AppCompatActivity {

    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room);

        fillRooms();

    }

    public void fillRooms() {
        Log.d("FILL","FILL");
        list = (ListView) findViewById(R.id.listRoom);
        list.invalidate();

        setTitle("ROOMS");

        RoomListAdapter adapter = new RoomListAdapter(this, (ArrayList<Room>) User.getUtilUser().getRooms());

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("ROOM", String.valueOf(i));
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putParcelable("ROOM_OBJECT", User.getUtilUser().getRooms().get(i));
                intent.putExtras(b);
                intent.setClass(getApplicationContext(), RoomActivity.class);
                startActivity(intent);
            }

        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                Log.v("long clicked","pos: " + pos);

                goToUpdateActivity(User.getUtilUser().getRooms().get(pos), pos);

                return true;
            }
        });

    }

    public void goToUpdateActivity(Room r,int pos) {
        Intent intent = new Intent(this, UpdateRoomActivity.class);
        Bundle b = new Bundle();
        intent.putExtra("POS",pos);
        b.putParcelable("ROOM_OBJECT", r);
        intent.putExtras(b);
        startActivityForResult(intent,2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_room, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            Intent intent = new Intent(this, NewRoomActivity.class);
            startActivityForResult(intent,1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("NEW_ROOM_ACTIVITY");
                Log.w("CHART", result);
                fillRooms();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                Log.w("ROOM_UPDATE", "Updated" );
                fillRooms();
            }
        } else if(requestCode == 2) {
            fillRooms();
        }
    }
}
