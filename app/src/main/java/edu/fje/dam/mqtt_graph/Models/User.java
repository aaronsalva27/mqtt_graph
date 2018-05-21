package edu.fje.dam.mqtt_graph.Models;

import java.util.List;

/**
 * Created by sava on 11/05/18.
 */

public class User {
    private static User utilUser = null;


    private String uid;
    private String token;
    private String email;
    private String name;
    private List<Room> rooms;

    public User() {

    }

    public User(String uid, String name, String email, List<Room> rooms) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.rooms = rooms;
    }

    public static User getUtilUser() {
        if (utilUser == null) {
            utilUser = new User();
        }

        return  utilUser;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                "token='" + token + '\'' +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
