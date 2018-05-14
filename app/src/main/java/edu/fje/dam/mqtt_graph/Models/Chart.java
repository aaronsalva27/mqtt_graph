package edu.fje.dam.mqtt_graph.Models;

import java.sql.Date;

/**
 * Created by sava on 11/05/18.
 */

public class Chart {
    private String _id;
    private String name;
    private Date created;
    private Date updated;
    private String type;
    private String topic;
    private int qos;

    public Chart() { }

    public Chart(String _id, String name, Date created, Date updated, String type, String topic, int qos) {
        this._id = _id;
        this.name = name;
        this.created = created;
        this.updated = updated;
        this.type = type;
        this.topic = topic;
        this.qos = qos;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    @Override
    public String toString() {
        return "Chart{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", type='" + type + '\'' +
                ", topic='" + topic + '\'' +
                ", qos=" + qos +
                '}';
    }
}
