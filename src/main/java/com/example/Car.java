package com.example;

public class Car {

    String licensePlate;
    int entryHour;
    int entryMinute;
    int entrySecond;
    int entryMillisecond;
    int exitHour;
    int exitMinute;
    int exitSecond;
    int exitMillisecond;
    double entryAllSeconds;
    double exitAllSeconds;
    double speed; // Speed in [km/h]

    public Car(
            String licensePlate,
            int entryHour,
            int entryMinute,
            int entrySecond,
            int entryMillisecond,
            int exitHour,
            int exitMinute,
            int exitSecond,
            int exitMillisecond) {
        this.licensePlate = licensePlate;
        this.entryHour = entryHour;
        this.entryMinute = entryMinute;
        this.entrySecond = entrySecond;
        this.entryMillisecond = entryMillisecond;
        this.exitHour = exitHour;
        this.exitMinute = exitMinute;
        this.exitSecond = exitSecond;
        this.exitMillisecond = exitMillisecond;

        this.entryAllSeconds = entryHour*3600 + entryMinute*60 + entrySecond + entryMillisecond/1000.0;
        this.exitAllSeconds = exitHour*3600 + exitMinute*60 + exitSecond + exitMillisecond/1000.0;
        
        double duration = this.exitAllSeconds - this.entryAllSeconds;
        this.speed =  3600/(duration/10.0);
    }

}
