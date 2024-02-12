package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class Main {

    public static void main(String[] args) {

        // 1. Read and store the data of file measurements.txt
        System.out.println("\n--- 1 ---");
        ArrayList<Car> cars = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("measurements.txt"));

            String line = reader.readLine();
            while(line  != null) {
                String[] parts = line.split(" ");

                String licensePlate = parts[0];
                int entryHour = Integer.parseInt(parts[1]);
                int entryMinute = Integer.parseInt(parts[2]);
                int entrySecond = Integer.parseInt(parts[3]);
                int entryMillisecond = Integer.parseInt(parts[4]);
                int exitHour = Integer.parseInt(parts[5]);
                int exitMinute = Integer.parseInt(parts[6]);
                int exitSecond = Integer.parseInt(parts[7]);
                int exitMillisecond = Integer.parseInt(parts[8]);

                Car new_car = new Car(licensePlate, entryHour, entryMinute, entrySecond, entryMillisecond, exitHour, exitMinute, exitSecond, exitMillisecond);
                cars.add(new_car);

                line = reader.readLine();
            }

            reader.close();
        }
        catch(IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // 7. Writing the results to a file
        try {
            FileWriter writer = new FileWriter("results.txt");

            // 2. The number of recorded vehicles
            writer.write("\n--- 2 ---\n");
            writer.write(cars.size() + " cars recorded");

            // 3. The number of vehicles that passed the exit point before 09:00
            writer.write("\n--- 3 ---\n");
            int nCars9 = 0;
            for(Car car: cars) {
                if(car.exitHour < 9) {
                    nCars9++;
                }
            }
            writer.write(nCars9 + " vehicles passed the exit point before 09:00");

            // 4. Request a time given in hour minute form from the user.
            writer.write("\n--- 4 ---\n");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter an hour and minute value: ");
            int userHour = scanner.nextInt();
            int userMinute = scanner.nextInt();

            // 4.a. The number of vehicles that passed the entry point in the given minute.
            int nCarsMinute = 0;
            for(Car car: cars) {
                if(car.entryHour == userHour && car.entryMinute == userMinute) {
                    nCarsMinute++;
                }
            }
            writer.write(nCarsMinute + " vehicles passed the entry point in hour, minute : " + userHour + ", " + userMinute + "\n");

            // 4.b. The traffic intensity (in float format)
            int presentMinute = 0;
            for(Car car: cars) {
                // Current car has passed the entry after the start of the minute
                if((car.entryHour == userHour && car.entryMinute == userMinute) || (car.exitHour == userHour && car.exitMinute == userMinute)) {
                    presentMinute++;
                }
            }
            double trafficIntensityMinute = presentMinute/10.0;
            writer.write("The traffic intensity in hour, minute is " + trafficIntensityMinute);


            // 5.a. Fastest vehicle speed
            writer.write("\n--- 5 ---\n");
            Car fastestCar = cars.getFirst();
            double fastestSpeed = fastestCar.speed;
            for(Car car: cars) {
                if(car.speed > fastestSpeed) {
                    fastestCar = car;
                    fastestSpeed = car.speed;
                }
            }
            writer.write("The fastest vehicle is " + fastestCar.licensePlate + "\n");
            writer.write("It has an average speed of " + fastestSpeed + "\n");

            // 5.b. Fastest vehicle overtakes
            int overtakes = 0;
            for(Car car: cars) {
                // Current car has passed the entry point before the fastest car AND fastest car has passed the exit point before the current car
                if(car != fastestCar) {
                    if(car.entryAllSeconds <= fastestCar.entryAllSeconds && car.exitAllSeconds >= fastestCar.exitAllSeconds) {
                        overtakes++;
                    }
                }
            }
            writer.write("It has overtaken " + overtakes);

            // 6. The percentage of the vehicles that exceeded the maximum speed limit (90 km/h)
            writer.write("\n--- 6 ---\n");
            int nExceed = 0;
            for(Car car: cars) {
                if(car.speed > 90.0) {
                    nExceed++;
                }
            }
            double averageExceed = (float) nExceed / (float) cars.size() * 100.0;
            writer.write(averageExceed + "% of the vehicles were speeding.");

            writer.close();
        }
        catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
