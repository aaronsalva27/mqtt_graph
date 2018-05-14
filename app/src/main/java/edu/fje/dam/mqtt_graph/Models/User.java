package edu.fje.dam.mqtt_graph.Models;

import java.util.Date;

/**
 * Created by sava on 11/05/18.
 */

public class User {
    private String _id;
    private String name;
    private String password;
    private Date created;
    private Date update;
    private Room[] rooms;

    public User() {

    }

    public User(String _id, String name, String password, Date created, Date update, Room[] rooms) {
        this._id = _id;
        this.name = name;
        this.password = password;
        this.created = created;
        this.update = update;
        this.rooms = rooms;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public void setRooms(Room[] rooms) {
        this.rooms = rooms;
    }


    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", created=" + created +
                ", update=" + update +
                '}';
    }
}
