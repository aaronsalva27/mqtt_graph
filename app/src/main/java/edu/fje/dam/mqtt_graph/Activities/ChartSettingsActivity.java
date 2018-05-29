package edu.fje.dam.mqtt_graph.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.fje.dam.mqtt_graph.Api.ApiService;
import edu.fje.dam.mqtt_graph.Api.ApiUser;
import edu.fje.dam.mqtt_graph.Models.Chart;
import edu.fje.dam.mqtt_graph.Models.User;
import edu.fje.dam.mqtt_graph.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class ChartSettingsActivity extends AppCompatActivity {
    private Button btnCreate;
    private Button btnCancel;
    private EditText chart_name;
    private EditText chart_topic;
    private Spinner types;
    private ProgressBar loaderChart;
    private String currentRoomId;
    private Chart c;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_settings);

        final Intent intent = getIntent();
        currentRoomId = intent.getStringExtra("CURRENT_ROOM");

        Log.d("NEW_CHART",currentRoomId);


        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        types = (Spinner) findViewById(R.id.chart_type);
        chart_name = (EditText) findViewById(R.id.chart_name);
        chart_topic = (EditText) findViewById(R.id.chart_topic);
        loaderChart = (ProgressBar) findViewById(R.id.loaderChart);

        btnCreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnCreate.setVisibility(View.INVISIBLE);
                loaderChart.setVisibility(View.VISIBLE);

                c = new Chart(chart_name.getText().toString(),
                                        types.getSelectedItem().toString(),
                                        chart_topic.getText().toString(), 1);
                Log.d("CHART",c.toString().toUpperCase());

                for (int i = 0; i< User.getUtilUser().getRooms().size(); i ++) {
                    if (User.getUtilUser().getRooms().get(i).get_id().equals(currentRoomId)) {
                        User.getUtilUser().getRooms().get(i).getCharts().add(c);
                        Log.d("CHART",User.getUtilUser().getRooms().get(i).getCharts().get(
                                User.getUtilUser().getRooms().get(i).getCharts().size()-1
                        ).toString());
                    }
                }

                saveChart();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("SETTINGS_ACTIVITY", "your string");
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    private void saveChart() {
        ApiUser apiUser = ApiService.getClient(getApplicationContext()).create(ApiUser.class);
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+ String.valueOf(User.getUtilUser().getToken()));

        disposable.add(
                apiUser.createTest(User.getUtilUser().getUid(),map, User.getUtilUser())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<User>(){

                            @Override
                            public void onSuccess(User user) {
                                btnCreate.setVisibility(View.VISIBLE);
                                loaderChart.setVisibility(View.GONE);

                                Intent intent = new Intent();
                                Bundle b = new Bundle();
                                b.putParcelable("CHART_OBJECT", c);
                                intent.putExtras(b);
                                setResult(RESULT_OK, intent);
                                finish();

                            }

                            @Override
                            public void onError(Throwable e) {
                                btnCreate.setVisibility(View.VISIBLE);
                                loaderChart.setVisibility(View.GONE);
                                Log.e("API_ERROR", String.valueOf(e.getMessage()));
                                showError(e);
                            }
                        })
        );
    }

    /**
     * Showing a Snackbar with error message
     * The error body will be in json format
     * {"error": "Error message!"}
     */
    private void showError(Throwable e) {
        String message = "";
        try {
            if (e instanceof IOException) {
                message = "No internet connection!";
            } else if (e instanceof HttpException) {
                HttpException error = (HttpException) e;
                String errorBody = error.response().errorBody().string();
                JSONObject jObj = new JSONObject(errorBody);

                message = jObj.getString("error");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (JSONException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (TextUtils.isEmpty(message)) {
            message = "Unknown error occurred! Check LogCat.";
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.chart_settings_layout), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }


}
