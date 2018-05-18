package edu.fje.dam.mqtt_graph.Models;

import java.util.Date;

/**
 * Created by sava on 11/05/18.
 */

public class User {
    private static User utilUser = null;

    private String _id;
    private String token;
    private String email;
    private String name;
    private Room[] rooms;

    public User() {

    }

    public User(String _id, String name, String email, Room[] rooms) {
        this._id = _id;
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public Room[] getRooms() {
        return rooms;
    }

    public void setRooms(Room[] rooms) {
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
                "_id='" + _id + '\'' +
                "token='" + token + '\'' +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
