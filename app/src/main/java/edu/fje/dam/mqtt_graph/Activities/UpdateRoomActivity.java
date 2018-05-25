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
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.fje.dam.mqtt_graph.Api.ApiService;
import edu.fje.dam.mqtt_graph.Api.ApiTest;
import edu.fje.dam.mqtt_graph.Models.Room;
import edu.fje.dam.mqtt_graph.Models.User;
import edu.fje.dam.mqtt_graph.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class UpdateRoomActivity extends AppCompatActivity {
    private EditText etRoomNameUpdate;
    private EditText etRoomBrookerUpdate;
    private EditText etRoomClientUpdate;
    private EditText etRoomPassUpdate;
    private Button btnRoomUpdate;
    private Button btnRoomDelete;
    private ProgressBar loaderRoomUpdate;
    private Room room;
    private int position;

    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_room);

        etRoomNameUpdate = (EditText) findViewById(R.id.etRoomNameUpdate);
        etRoomBrookerUpdate = (EditText) findViewById(R.id.etRoomBrookerUpdate);
        etRoomClientUpdate = (EditText) findViewById(R.id.etRoomClientUpdate);
        etRoomPassUpdate = (EditText) findViewById(R.id.etRoomPassUpdate);
        btnRoomUpdate = (Button) findViewById(R.id.btnRoomUpdate);
        btnRoomDelete = (Button) findViewById(R.id.btnRoomDelete);
        loaderRoomUpdate = (ProgressBar) findViewById(R.id.loaderRoomUpdate);

        loaderRoomUpdate.setVisibility(View.GONE);

        Intent intent = new Intent();
        position = intent.getIntExtra("POS",-1);

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            room = b.getParcelable("ROOM_OBJECT");
            setTitle(room.getName());
            Log.d("ROOM", room.toString().toUpperCase());

            etRoomNameUpdate.setText(room.getName());
            etRoomBrookerUpdate.setText(room.getBroker());

        }

        btnRoomUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                room.setName(String.valueOf(etRoomNameUpdate.getText()));
                room.setName(String.valueOf(etRoomBrookerUpdate.getText()));

                if (position != -1) {
                    User.getUtilUser().getRooms().set(position,room);

                }
                saveUser();
            }
        });

        btnRoomDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != -1) {
                    User.getUtilUser().getRooms().remove(position);
                }

                saveUser();
            }
        });


    }

    private void saveUser() {
        btnRoomUpdate.setVisibility(View.GONE);
        btnRoomDelete.setVisibility(View.GONE);
        loaderRoomUpdate.setVisibility(View.VISIBLE);

        ApiTest apiTest = ApiService.getClient(getApplicationContext()).create(ApiTest.class);
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+ String.valueOf(User.getUtilUser().getToken()));

        disposable.add(
                apiTest.createTest(User.getUtilUser().getUid(),map, User.getUtilUser())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<User>(){

                            @Override
                            public void onSuccess(User user) {
                                Log.d("API",user.toString());
                                btnRoomUpdate.setVisibility(View.VISIBLE);
                                btnRoomDelete.setVisibility(View.VISIBLE);
                                loaderRoomUpdate.setVisibility(View.GONE);
                                Intent intent = new Intent();
                                intent.putExtra("NEW_ROOM_ACTIVITY", "your string");
                                setResult(RESULT_OK, intent);
                                finish();
                            }

                            @Override
                            public void onError(Throwable e) {
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
                .make(findViewById(R.id.room_settings_layout), message, Snackbar.LENGTH_LONG);

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
