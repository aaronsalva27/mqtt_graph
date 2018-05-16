package edu.fje.dam.mqtt_graph.Charts;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import edu.fje.dam.mqtt_graph.Api.MqttHelper;
import edu.fje.dam.mqtt_graph.R;
import me.rishabhkhanna.customtogglebutton.CustomToggleButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToggleFragment extends Fragment {
    public MqttHelper mqttHelper;

    private CustomToggleButton toggleButton;

    public ToggleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_toggle, container, false);

        startMqtt(v);

        toggleButton = (CustomToggleButton) v.findViewById(R.id.toggleButton);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    Log.d("Toggle","Check");
                    mqttHelper.publishMessage("13H");

                }
                else
                {
                    Log.d("Toggle","UNCheck");
                    mqttHelper.publishMessage("13L");
                }
            }
        });

        return  v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        this.mqttHelper.close();
    }

    private void startMqtt(View v){
        mqttHelper = new MqttHelper(v.getContext());
        mqttHelper.mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("Debug","Connected");
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug",mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

}
