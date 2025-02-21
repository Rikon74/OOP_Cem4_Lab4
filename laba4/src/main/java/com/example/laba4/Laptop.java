package com.example.laba4;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Laptop implements Serializable {
    private String id;
    private String fabrick;
    private String model;
    private String cpu;
    private String ram;
    private String hdd;

    @Override
    public String toString() {
        return "Car{" +
                "make='" + fabrick + '\'' +
                ", model='" + model + '\'' +
                ", engine='" + cpu + '\'' +
                ", year=" + ram +
                ", transmission='" + hdd + '\'' +
                '}';
    }
}
