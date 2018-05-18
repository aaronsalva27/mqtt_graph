package edu.fje.dam.mqtt_graph.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.fje.dam.mqtt_graph.Models.Test;
import edu.fje.dam.mqtt_graph.Models.TestRepuesta;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * Created by sava on 17/05/18.
 */

public interface ApiTest {

    @GET("test")
    Call<List<Test>> obtenerLista(
            @HeaderMap Map<String, String> headers
    );

    @POST("test")
    @FormUrlEncoded
    Call<Test> createTest(
            @HeaderMap Map<String, String> headers,
            @Field("name") String name,
            @Field("price") int price
    );
}
