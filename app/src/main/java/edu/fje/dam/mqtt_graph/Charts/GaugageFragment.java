package edu.fje.dam.mqtt_graph.Charts;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import az.plainpie.PieView;
import edu.fje.dam.mqtt_graph.Api.MqttHelper;
import edu.fje.dam.mqtt_graph.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GaugageFragment extends Fragment {
    public MqttHelper mqttHelper;
    private String server;
    private String subTopic;
    private String pubTopic;
    private String clientId;

    PieView pieView;

    public GaugageFragment() {
        // Required empty public constructor

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gaugage, container, false);

        pieView = (PieView) v.findViewById(R.id.pieView);
        readBundle(getArguments());

        startMqtt(v, server, subTopic, pubTopic,clientId);

        return v;
    }

    public static GaugageFragment newInstance(String server, String subTopic, String pubTopic , String clientId) {

        Bundle bundle = new Bundle();
        bundle.putString("server", server);
        bundle.putString("subTopic", subTopic);
        bundle.putString("pubTopic", pubTopic);
        bundle.putString("clientId", clientId);

        GaugageFragment fragment = new GaugageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        this.mqttHelper.close();
    }

    private void startMqtt(View v,String server, String sub, String pub,String client){
        mqttHelper = new MqttHelper(v.getContext(), server, sub, pub,client);
        mqttHelper.mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("Debug","Connected: "+s);
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                // Log.w("Debug",topic + " " + mqttMessage.toString());
                pieView.setPercentage(Float.valueOf(mqttMessage.toString()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }


}
