package edu.fje.dam.mqtt_graph.Charts;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

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
    public static MqttHelper mqttHelper;
    private static String server;
    private static String subTopic;
    private static String pubTopic;
    private static String clientId;

    private CustomToggleButton toggleButton;

    public ToggleFragment() {

    }

    public static ToggleFragment newInstance(String server, String subTopic, String pubTopic , String clientId) {
        Bundle bundle = new Bundle();
        bundle.putString("server", server);
        bundle.putString("subTopic", subTopic);
        bundle.putString("pubTopic", pubTopic);
        bundle.putString("clientId", clientId);


        ToggleFragment fragment = new ToggleFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_toggle, container, false);

        readBundle(getArguments());
        startMqtt(v, server, subTopic, pubTopic,clientId);

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
                    mqttHelper.publishMessage("13H");
                }
            }
        });

        return  v;
    }

    private void readBundle(Bundle arguments) {
        if (arguments != null) {
            server = arguments.getString("server");
            subTopic = arguments.getString("subTopic");
            pubTopic = arguments.getString("pubTopic");
            clientId = arguments.getString("clientId");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        this.mqttHelper.close();
    }

    private void startMqtt(View v, String server, String sub, String pub,String client){
        mqttHelper = new MqttHelper(v.getContext(), server, sub, pub,client);
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
                Log.w("Debug","TOGGLE: " + mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

}
