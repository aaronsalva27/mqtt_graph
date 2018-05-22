package edu.fje.dam.mqtt_graph.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.fje.dam.mqtt_graph.Models.Room;
import edu.fje.dam.mqtt_graph.R;

/**
 * Created by sava on 22/05/18.
 */

public class RoomListAdapter extends ArrayAdapter<Room> {
    public RoomListAdapter(Context context, ArrayList<Room> rooms) {
        super(context, 0, rooms);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Room room = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.room_list_adapter, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.roomName);
        TextView tvHome = (TextView) convertView.findViewById(R.id.roomBroker);
        // Populate the data into the template view using the data object
        tvName.setText(room.getName());
        tvHome.setText(room.getBroker());
        // Return the completed view to render on screen
        return convertView;
    }
}
