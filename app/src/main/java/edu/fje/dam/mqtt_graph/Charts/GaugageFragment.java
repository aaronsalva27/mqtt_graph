package edu.fje.dam.mqtt_graph.Charts;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import az.plainpie.PieView;
import edu.fje.dam.mqtt_graph.Api.MqttHelper;
import edu.fje.dam.mqtt_graph.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GaugageFragment extends Fragment {
    public MqttAndroidClient mqttAndroidClient;
    // public MqttHelper mqttHelper;
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

        mqttAndroidClient = new MqttAndroidClient(v.getContext(), server, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("mqtt", s);
            }

            @Override
            public void connectionLost(Throwable throwable) {
                Log.w("Paho","Stop connexion");
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Mqtt", mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
        connect();

        startMqtt(v, server, subTopic, pubTopic,clientId);

        return v;
    }

    public void connect(){
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        //mqttConnectOptions.setUserName(username);
        //mqttConnectOptions.setPassword(password.toCharArray());

        try {

            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Failed to connect to: " + server + exception.toString());
                }
            });


        } catch (MqttException ex){
            System.out.println(ex.getMessage());
        }
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

    public void publishMessage(String data){
        String publishMessage = data;
        // SavaPahoPub

        try {
            MqttMessage message = new MqttMessage();
            message.setPayload(publishMessage.getBytes());
            mqttAndroidClient.publish(pubTopic, message);

        } catch (MqttException e) {
            System.err.println("Error Publishing: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(subTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("Mqtt","Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Subscribed fail!");
                }
            });

        } catch (MqttException ex) {
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }


    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        this.close();
    }

    public void close() {
        if (mqttAndroidClient != null) {
            mqttAndroidClient.unregisterResources();
            mqttAndroidClient.close();
            /*try {
                if (mqttAndroidClient.isConnected()) {
                    mqttAndroidClient.unsubscribe(subscriptionTopic);
                    mqttAndroidClient.disconnect();

                }

            } catch (MqttException e) {
                e.printStackTrace();
            }*/
        }

    }

    private void startMqtt(View v,String server, String sub, String pub,String client){

        this.mqttAndroidClient.setCallback(new MqttCallbackExtended() {
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
