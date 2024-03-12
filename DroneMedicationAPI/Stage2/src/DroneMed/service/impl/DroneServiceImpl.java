package DroneMed.service.impl;



import DroneMed.models.Drone;
import DroneMed.repository.DroneRepository;
import DroneMed.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DroneServiceImpl implements DroneService {

    // Drone repository to interact with drone database.
    private final DroneRepository droneRepository;

    /**
     * Constructor-based dependency injection for drone repositories.
     *
     * @param droneRepository - Repository to manage drone operations.
     */

    @Autowired
    public DroneServiceImpl(DroneRepository droneRepository)
    {
        this.droneRepository = droneRepository;
    }

    @Override
    public String createDrone(Drone drone) {

        //checkDroneValues(drone);
        if(drone == null || drone.getSerialNumber() == null ||  drone.getMaxWeight() == 0 || drone.getBatteryCapacity() == 0
        || drone.getModel() == null || drone.getState() == null || drone.getCurrentCoordinate() == null) {
            return "The parameter you entered contains null or invalid parameter, Please enter a valid drone parameter.";

        }

        droneRepository.save(drone);
        return "Drone with serial number " + drone.getSerialNumber() + " created successfully.";
    }

    /**
     * Creates a new medication entry in the database.
     *
     * @param Drones - List of Drone entities to be created.
     * @return String - Success message.
     */
    @Transactional
    @Override
    public List<String> createDrones(List<Drone> Drones) {
        List<String> responses = new ArrayList<>();
        for (Drone d : Drones) {
            responses.add(createDrone(d));
        }
        return responses;
    }

    /**
     * Updates the details of an existing drone.
     *
     * @param drone - Drone entity with updated values.
     * @return String - Success message.
     */
    @Override
    public String updateDrone(Drone drone) {

        // Find the drone by serialNumber
        if(droneRepository.existsById(drone.getSerialNumber())) {
            Drone existingDrone = droneRepository.findById(drone.getSerialNumber()).get();
            droneRepository.save(drone);

            return "Drone with serial number " + drone.getSerialNumber() + " updated successfully.";
        }
        else {
            return ("Drone with serial number " + drone.getSerialNumber() +  " not found.");
        }
    }

    /**
     * Deletes a drone entry from the database based on its code.
     *
     * @param drone_serial_number - Serial number of the drone to be deleted.
     * @return String - Success message.
     */
    @Override
    public String deleteDrone(String drone_serial_number) {
        if(droneRepository.existsById(drone_serial_number))
        {
            droneRepository.deleteById(drone_serial_number);
            return "Drone with serial number " + drone_serial_number + " deleted Successfully.";
        } else {
            return ("Drone with serial number " + drone_serial_number + " not found.");
        }
    }

    /**
     * Retrieves a drone based on its code.
     *
     * @param drone_serial_number - Serial number of the drone to be fetched.
     * @return Drone - Drone entity.
     */
    @Override
    public Optional<Drone> getDrone(String drone_serial_number) {

        return droneRepository.findById(drone_serial_number);

    }

    /**
     * Retrieves all drones from the database.
     *
     * @return List<Drone> - List of all Drone entities.
     */
    @Override
    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }

}
