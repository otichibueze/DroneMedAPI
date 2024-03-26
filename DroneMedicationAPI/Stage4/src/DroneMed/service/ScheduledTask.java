package DroneMed.service;


import DroneMed.models.Drone;
import DroneMed.models.DroneDispatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTask {

    //to Interact with drones
    private final DroneService droneService;


   // private String filePath = "log/dispatchlog";

    private final int BatteryConsumptionPerSec = 10;

    @Autowired
    public ScheduledTask(DroneService droneService) {

        this.droneService = droneService;
    }


    public void droneDispatchedStateTask(DroneDispatch droneDispatch) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

        LocalTime localTime = droneDispatch.getEstimatedTime();
        long deliverTime = convertLocalTimeToMilliseconds(localTime);
        int batConsumption = (int)((deliverTime / 1000) * BatteryConsumptionPerSec);
        Drone drone = droneDispatch.getDrone();

        updateDrone(Mode.DELIVERING, batConsumption, drone);



        scheduler.schedule(() -> {
            // First execution
            updateDrone(Mode.DELIVERING,batConsumption, drone);
            //System.out.println("Timestamp " + Timestamp.from(Instant.now()) + " Drone " + drone.getSerialNumber() + " with a battery level of " + drone.getBatteryCapacity() + " amps is on a medication delivery.");
            String logInfo1 = "Timestamp " + Timestamp.from(Instant.now()) + " Drone " + drone.getSerialNumber() + " with a battery level of " + drone.getBatteryCapacity() + " amps is on medication delivery.";

            try {
                writeToFile(logInfo1);
            }
            catch (Exception e){
                System.out.println("Exception writing to file " + e.getMessage().toString());
            }
            scheduler.schedule(() -> {
                // Second execution
                updateDrone(Mode.DELIVERED,batConsumption, drone);
                updateDrone(Mode.RETURNING,batConsumption, drone);
                //System.out.println("Timestamp " + Timestamp.from(Instant.now()) + " Drone " + drone.getSerialNumber() + " delivery complete! battery level " + drone.getBatteryCapacity() + "amps, drone returning to base.");

                String logInfo2 = "Timestamp " + Timestamp.from(Instant.now()) + " Drone " + drone.getSerialNumber() + " delivery is complete! battery level " + drone.getBatteryCapacity() + "amps, drone returning to base.";

                try {
                    writeToFile(logInfo2);
                }
                catch (Exception e){
                    System.out.println("Exception writing to file " + e.getMessage().toString());
                }

                scheduler.schedule(() -> {
                    // Third execution
                    updateDrone(Mode.IDLE,batConsumption, drone);
                    //System.out.println("Timestamp " + Timestamp.from(Instant.now()) + " Drone " + drone.getSerialNumber() + " has arrived base, battery level " + drone.getBatteryCapacity());

                    String logInfo3 = "Timestamp " + Timestamp.from(Instant.now()) + " Drone " + drone.getSerialNumber() + " has arrived at the base, battery level " + drone.getBatteryCapacity();

                    try {
                        writeToFile(logInfo3);
                    }
                    catch (Exception e){
                        System.out.println("Exception writing to file " + e.getMessage().toString());
                    }

                    scheduler.shutdown();

                }, deliverTime, TimeUnit.MILLISECONDS);

            }, deliverTime, TimeUnit.MILLISECONDS);

        }, 0, TimeUnit.MILLISECONDS);
    }



    private void updateDrone(Mode mode, int tripConsumption, Drone drone) {
        if(mode == Mode.DELIVERING) {
            drone.setState(Drone.State.DELIVERING);
            droneService.updateDrone(drone);
        }
        else if (mode == Mode.DELIVERED ) {
            drone.setState(Drone.State.DELIVERED);
            int currentBattery = drone.getBatteryCapacity();
            drone.setBatteryCapacity(currentBattery - tripConsumption);
            droneService.updateDrone(drone);
        }
        else if (mode == Mode.RETURNING ) {
            drone.setState(Drone.State.RETURNING);
            droneService.updateDrone(drone);
        }
        else if (mode == Mode.IDLE ) {
            drone.setState(Drone.State.IDLE);
            int currentBattery = drone.getBatteryCapacity();
            drone.setBatteryCapacity(currentBattery - tripConsumption);
            droneService.updateDrone(drone);
        }
    }


    private void writeToFile(String data) throws IOException {



        String filePath = "log/dispatchlog.txt";

        File file = new File(filePath);

        // Check if file path is configured
        if (filePath == null || filePath.isEmpty()) {
            file = new File(filePath);
            System.out.println("File already exists: " + filePath);
        }


        // Create a FileWriter object
        FileWriter writer = new FileWriter(file, true);  // Set 'true' for appending content

        try {
            writer.write(data + System.lineSeparator());  // Write data with a newline character
        } finally {
            writer.close();  // Ensure closing the writer even in case of exceptions
        }
    }


    public enum Mode {
        IDLE,
        DELIVERING,
        DELIVERED,
        RETURNING
    }

    private static long convertLocalTimeToMilliseconds(LocalTime localTime) {
        // Extract hours, minutes, and seconds
        int hours = localTime.getHour();
        int minutes = localTime.getMinute();
        int seconds = localTime.getSecond();

        // Calculate total milliseconds
        long totalMilliseconds = (hours * 60 * 60 + minutes * 60 + seconds) * 1000;

        return totalMilliseconds;
    }

}
