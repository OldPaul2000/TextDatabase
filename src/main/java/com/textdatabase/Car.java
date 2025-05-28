package com.textdatabase;

public class Car {

    private String name;
    private int horsePower;
    private int torque;

    public Car(String name, int horsePower, int torque) {
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

    public int getTorque() {
        return torque;
    }

    public void setTorque(int torque) {
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
