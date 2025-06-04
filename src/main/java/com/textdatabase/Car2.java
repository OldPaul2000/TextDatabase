package com.textdatabase;

public class Car2 {

    private String name;
    private int horsePower;
    private double torque;

    public Car2(String name, int horsePower, double torque) {
        this.name = name;
        this.horsePower = horsePower;
        this.torque = torque;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public double getTorque() {
        return torque;
    }

    public void setTorque(double torque) {
        this.torque = torque;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", horsePower=" + horsePower +
                ", torque=" + torque +
                '}';
    }

}
