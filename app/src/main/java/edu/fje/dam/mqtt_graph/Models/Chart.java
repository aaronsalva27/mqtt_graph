package edu.fje.dam.mqtt_graph.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;

/**
 * Created by sava on 11/05/18.
 */

public class Chart implements Parcelable {
    private String _id;
    private String name;
    private String type;
    private String topic;
    private int qos;

    public Chart() { }

    public Chart(String _id, String name, String type, String topic, int qos) {
        this._id = _id;
        this.name = name;
        this.type = type;
        this.topic = topic;
        this.qos = qos;
    }

    protected Chart(Parcel in) {
        _id = in.readString();
        name = in.readString();
        type = in.readString();
        topic = in.readString();
        qos = in.readInt();
    }



    public static final Parcelable.Creator<Chart> CREATOR
            = new Parcelable.Creator<Chart>() {
        public Chart createFromParcel(Parcel in) {
            return new Chart(in);
        }

        public Chart[] newArray(int size) {
            return new Chart[size];
        }
    };

    public Chart(String name, String type, String topic, int qos) {
        this.name = name;
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
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", topic='" + topic + '\'' +
                ", qos=" + qos +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeString(topic);
        parcel.writeInt(qos);
    }
}
