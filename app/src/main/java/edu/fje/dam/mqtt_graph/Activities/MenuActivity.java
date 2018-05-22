package edu.fje.dam.mqtt_graph.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.fje.dam.mqtt_graph.Api.ApiService;
import edu.fje.dam.mqtt_graph.Api.ApiTest;
import edu.fje.dam.mqtt_graph.Models.Chart;
import edu.fje.dam.mqtt_graph.Models.Room;
import edu.fje.dam.mqtt_graph.Models.Test;
import edu.fje.dam.mqtt_graph.Models.User;
import edu.fje.dam.mqtt_graph.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private ImageView userAvatar;
    private TextView userName;
    private TextView userEmail;

    private  NavigationView navigationView;

    private final String TITLE= "DASHBOARD";

    private GoogleApiClient googleApiClient;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private CompositeDisposable disposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(TITLE);

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        /*this.overridePendingTransition(R.anim.left_to_right,
                R.anim.right_to_left);*/

        /*this.overridePendingTransition(R.anim.slide_out,
                R.anim.slide_in);*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        userName = (TextView) headerView.findViewById(R.id.userName);
        userEmail = (TextView) headerView.findViewById(R.id.userEmail);
        userAvatar = (ImageView) headerView.findViewById(R.id.userAvatar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    setUserData(user);
                } else {
                    goLogInScreen();
                }
            }
        };


    }

    private void createTest() {
        ApiTest apiTest = ApiService.getClient(getApplicationContext()).create(ApiTest.class);
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+ String.valueOf(User.getUtilUser().getToken()));

        User u = new User();
        u.setUid(User.getUtilUser().getUid());

        Room r = new Room();
        r.setBroker("broker");
        r.setName("cocina");

        Room r2 = new Room();
        r2.setBroker("broker");
        r2.setName("lavabo");

        Chart c = new Chart();
        c.setName("temperatura");
        c.setType("gaugage");
        c.setQos(1);
        c.setTopic("statusTemp");

        Chart c2 = new Chart();
        c2.setName("humedad");
        c2.setType("gaugage");
        c2.setQos(1);
        c2.setTopic("statusHumedad");

        List<Chart> charts = new ArrayList<>();
        charts.add(c);
        charts.add(c2);

        r.setCharts(charts);
        r2.setCharts(charts);

        List<Room> rooms = new ArrayList<>();
        rooms.add(r);
        rooms.add(r2);

        u.setRooms(rooms);

        Gson g = new Gson();

        Log.d("USER",g.toJson(u));


        disposable.add(
          apiTest.createTest(User.getUtilUser().getUid(),map, u)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribeWith(new DisposableSingleObserver<User>(){

                      @Override
                      public void onSuccess(User user) {
                          Log.d("API",user.toString());
                      }

                      @Override
                      public void onError(Throwable e) {
                          Log.e("API_ERROR", String.valueOf(e.getMessage()));
                          showError(e);
                      }
                  })
        );

    }

    private void obtenerDatos() {
        ApiTest apiService = ApiService.getClient(getApplicationContext()).create(ApiTest.class);
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+ String.valueOf(User.getUtilUser().getToken()));

        Log.d("UID",User.getUtilUser().getUid());

        disposable.add(
                apiService.obtenerUser(User.getUtilUser().getUid(),map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<User>() {
                            @Override
                            public void onSuccess(User user) {
                                // Log.d("CGET",user.getRooms().get(0).getCharts().get(0).getTopic());
                                User.getUtilUser().setRooms(user.getRooms());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("API_ERROR", "onError: " + e.getMessage());
                                if(e.getMessage().contains("404")) {
                                    Log.e("API_ERROR","No existe");
                                    User.getUtilUser().setRooms(new ArrayList<Room>());
                                }else {
                                    showError(e);
                                }
                            }
                        })
        );

    }

    private void setUserData(FirebaseUser user) {
        userName.setText(user.getDisplayName());
        userEmail.setText(user.getEmail());
        Glide.with(this).load(user.getPhotoUrl()).into(userAvatar);

        User.getUtilUser().setUid(user.getUid());
        User.getUtilUser().setName(user.getDisplayName());
        User.getUtilUser().setEmail(user.getEmail());

        user.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if (task.isSuccessful()) {
                    String idToken = task.getResult().getToken();
                    User.getUtilUser().setToken(idToken);

                    obtenerDatos();
                } else {
                    Log.e("TOKEN_ERROR", String.valueOf(task.getException()));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(firebaseAuthListener);
        Log.d("START","START");

    }

    @Override
    protected void onResume() {
        super.onResume();
        // obtenerDatos();
    }

    public void logOut(View view) {
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else  {
                    Snackbar.make(findViewById(R.id.drawer_layout), "Error while login out...", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }


    public void revoke(View view) {
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else  {
                    Snackbar.make(findViewById(R.id.drawer_layout), "Error revoking session...", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            createTest();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Log.d("menu", String.valueOf(item.getTitle()));

        if (id == R.id.nav_camera) {
            startActivity(new Intent(this, ListRoomActivity.class));
        }else if (id == R.id.nav_manage) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if(id == R.id.nav_exit) {
            revoke(findViewById(R.id.drawer_layout));
        } else if(id == R.id.nav_revoke) {
            logOut(findViewById(R.id.drawer_layout));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
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
                .make(findViewById(R.id.drawer_layout), message, Snackbar.LENGTH_LONG);

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
