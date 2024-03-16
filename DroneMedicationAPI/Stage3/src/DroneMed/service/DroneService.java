package DroneMed.service;

import DroneMed.models.Drone;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface DroneService {

    String createDrone(Drone drone);
    List<String> createDrones(List<Drone> Drones);
    String updateDrone(Drone drone);
    String deleteDrone(String drone_serial_number);
    Optional<Drone> getDrone(String drone_serial_number);
    List<Drone> getAllDrones();
    List<Optional<Drone>> findDroneByState(Drone.State state);
    List<Optional<Drone>> findDronesByModel(Drone.Model model);
    List<Optional<Drone>> findDronesByBatteryCapacityAfter(int percentage);

}
