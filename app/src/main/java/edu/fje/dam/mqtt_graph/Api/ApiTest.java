package edu.fje.dam.mqtt_graph.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.fje.dam.mqtt_graph.Models.Test;
import edu.fje.dam.mqtt_graph.Models.TestRepuesta;
import edu.fje.dam.mqtt_graph.Models.User;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by sava on 17/05/18.
 */

public interface ApiTest {

    @GET("user")
    Single<List<User>> obtenerLista(
            @HeaderMap Map<String, String> headers
    );

    @GET("user/{id}")
    Single<User> obtenerUser(
            @Path("id") String userId,
            @HeaderMap Map<String, String> headers
    );

    @POST("user/{id}")
    Single<User> createTest(
            @Path("id") String userId,
            @HeaderMap Map<String, String> headers,
            @Body User user
    );
}
