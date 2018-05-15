package edu.fje.dam.mqtt_graph.Charts;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import edu.fje.dam.mqtt_graph.R;
import me.rishabhkhanna.customtogglebutton.CustomToggleButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToggleFragment extends Fragment {

    private CustomToggleButton toggleButton;

    public ToggleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_toggle, container, false);

        toggleButton = (CustomToggleButton) v.findViewById(R.id.toggleButton);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    Log.d("Toggle","Check");
                }
                else
                {
                    Log.d("Toggle","UNCheck");
                }
            }
        });

        return  v;
    }

}
