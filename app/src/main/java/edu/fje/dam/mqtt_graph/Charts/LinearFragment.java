package edu.fje.dam.mqtt_graph.Charts;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akaita.android.circularseekbar.CircularSeekBar;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import edu.fje.dam.mqtt_graph.Activities.RoomActivity;
import edu.fje.dam.mqtt_graph.Api.MqttHelper;
import edu.fje.dam.mqtt_graph.R;

public class LinearFragment extends Fragment {
    private LineChart lineChart;

    public MqttHelper mqttHelper;
    private String server;
    private String subTopic;
    private String pubTopic;
    private String clientId;

    public LinearFragment() {
    }

    public static LinearFragment newInstance(String server, String subTopic, String pubTopic , String clientId) {
        Bundle bundle = new Bundle();
        bundle.putString("server", server);
        bundle.putString("subTopic", subTopic);
        bundle.putString("pubTopic", pubTopic);
        bundle.putString("clientId", clientId);


        LinearFragment fragment = new LinearFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_linear, container, false);
        readBundle(getArguments());

        this.lineChart = (LineChart) v.findViewById(R.id.linearChartFragment);

        // no description text
        Description desc = new Description();
        desc.setText("");
        lineChart.setDescription(desc);
        lineChart.setNoDataText("No data");



        // enable touch gestures
        lineChart.setTouchEnabled(true);

        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(true);

        // set an alternative background color

        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setBorderColor(Color.rgb(67,164,34));


        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        lineChart.setData(data);

        XAxis xl = lineChart.getXAxis();
        xl.setEnabled(false);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTypeface(Typeface.MONOSPACE);
        leftAxis.setTextColor(R.color.colorPrimaryDark);

        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

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
                addEntry(Float.parseFloat(mqttMessage.toString()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    public void addEntry(float value) {

        LineData data = lineChart.getData();

        if (data != null){

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            data.addEntry(new Entry(set.getEntryCount(),value),0);
            // Log.w("anjing", set.getEntryForIndex(set.getEntryCount()-1).toString());

            data.notifyDataChanged();

            // let the chart know it's data has changed
            lineChart.notifyDataSetChanged();

            // limit the number of visible entries
            lineChart.setVisibleXRangeMaximum(10);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            lineChart.moveViewTo(set.getEntryCount()-1, data.getYMax(), YAxis.AxisDependency.LEFT);

            // this automatically refreshes the chart (calls invalidate())
            // mChart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "");
        set.setCircleColor(R.color.color_accent);
        // set.setFillColor(R.color.color_accent);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(R.color.colorPrimaryDark);
        //set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        //set.setCircleRadius(4f);
        // set.setFillAlpha(65);

        /*set.setFillColor(Color.rgb(67, 164, 34));
        set.setHighLightColor(Color.rgb(67, 164, 34));
        set.setValueTextColor(Color.rgb(67, 164, 34));*/
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }
}
