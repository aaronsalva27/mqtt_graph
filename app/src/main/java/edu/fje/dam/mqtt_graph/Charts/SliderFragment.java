package edu.fje.dam.mqtt_graph.Charts;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.akaita.android.circularseekbar.CircularSeekBar;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import edu.fje.dam.mqtt_graph.Api.MqttHelper;
import edu.fje.dam.mqtt_graph.R;


public class SliderFragment extends Fragment {
    public MqttHelper mqttHelper;
    private String server;
    private String subTopic;
    private String pubTopic;
    private String clientId;

    private CircularSeekBar circleDisplay;

    public SliderFragment() {
        // Required empty public constructor
    }


    public static SliderFragment newInstance(String server, String subTopic, String pubTopic , String clientId) {
        Bundle bundle = new Bundle();
        bundle.putString("server", server);
        bundle.putString("subTopic", subTopic);
        bundle.putString("pubTopic", pubTopic);
        bundle.putString("clientId", clientId);


        SliderFragment fragment = new SliderFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_slider, container, false);
        readBundle(getArguments());

        this.circleDisplay = (CircularSeekBar) v.findViewById(R.id.slider);


        circleDisplay.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {
                Log.d("SLIDER", String.valueOf(progress));
                mqttHelper.publishMessage(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {

            }
        });

        startMqtt(v, server, subTopic, pubTopic,clientId);

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
