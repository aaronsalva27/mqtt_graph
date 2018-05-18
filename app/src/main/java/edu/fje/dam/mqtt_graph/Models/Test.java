package edu.fje.dam.mqtt_graph.Models;

/**
 * Created by sava on 17/05/18.
 */

public class Test {
    private String name;
    private int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUrl() {
        return price;
    }

    public void setUrl(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Test{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
