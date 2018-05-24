package edu.fje.dam.mqtt_graph.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.SubMenu;

import com.google.gson.annotations.Expose;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sava on 11/05/18.
 */

public class Room implements Parcelable {
    public static SubMenu subMenu;

    private String _id;
    private String broker;
    private String name;
    @Expose
    private List<Chart> charts = new ArrayList<Chart>();

    public Room() {
    }

    public Room(String _id,String broker, String name, List<Chart> charts) {
        this._id = _id;
        this.name = name;
        this.broker = broker;
        this.charts = charts;
    }

    protected Room(Parcel in) {
        _id = in.readString();
        broker = in.readString();
        name = in.readString();
        in.readTypedList(charts, Chart.CREATOR);
    }



    public static final Parcelable.Creator<Room> CREATOR
            = new Parcelable.Creator<Room>() {
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Chart> getCharts() {
        return charts;
    }

    public void setCharts(List<Chart> charts) {
        this.charts = charts;
    }

    @Override
    public String toString() {
        return "Room{" +
                "broker='" + broker + '\'' +
                ", name='" + name + '\'' +
                ", charts='" + getCharts().size() + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(broker);
        parcel.writeString(name);
        parcel.writeTypedList(charts);
    }
}
