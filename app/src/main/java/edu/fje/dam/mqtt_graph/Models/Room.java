package edu.fje.dam.mqtt_graph.Models;

import java.sql.Date;
import java.util.Arrays;

/**
 * Created by sava on 11/05/18.
 */

public class Room {
    private String broker;
    private String name;
    private Date created;
    private Date updated;
    private Chart[] charts;

    public Room() {
    }

    public Room(String broker, String name, Date created, Date updated, Chart[] charts) {
        this.broker = broker;
        this.name = name;
        this.created = created;
        this.updated = updated;
        this.charts = charts;
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

    public Chart[] getCharts() {
        return charts;
    }

    public void setCharts(Chart[] charts) {
        this.charts = charts;
    }

    @Override
    public String toString() {
        return "Room{" +
                "broker='" + broker + '\'' +
                ", name='" + name + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", charts=" + Arrays.toString(charts) +
                '}';
    }
}
