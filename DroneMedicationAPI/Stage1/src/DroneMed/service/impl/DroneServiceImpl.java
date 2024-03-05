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

        droneRepository.save(drone);
        return "Drone with serial number " + drone.getSerialNumber() + " created successfully";
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

            return "Drone updated successfully";
        }
        else {
            return ("Drone not found");
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
            return "Drone with serial number " + drone_serial_number + " deleted Successfully";
        } else {
            return ("Drone with serial " + drone_serial_number + " not found.");
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

        //-> new RuntimeException("Drone with serial " + drone_serial_number + " not found"));
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


    /**
     * Retrieves drones in the system based on current model.
     * @param model - model from enum in class
     * @return List of drones with model provided.
     */
    @Override
    public List<Drone> getDroneByModel(Drone.Model model) {

        return droneRepository.findDroneByModel(model);
    }


    /**
     * Retrieves drones in the system greater than percentage.
     * @param percentage - percentage value to be used
     * @return List of drones with percentage or greater.
     */
    @Override
    public List<Drone> getDroneByCharge(int percentage) {

        return droneRepository.findDroneCharged(percentage);
    }


    /**
     * Retrieves drones in the system based on current state.
     * @param state - state from enum in class
     * @return List of drones with state provided.
     */
    @Override
    public List<Drone> getDronesByState(Drone.State state) {
        return droneRepository.findDroneByState(state);
    }

    /*
    //Check drone values are correct and assigns the correct model type
    private void checkDroneValues(Drone drone) {

        if (drone.getCurrentWeight() < 0 || drone.getCurrentWeight() >= drone.getMaxWeight() ) {
            throw new RuntimeException("Weight Invalid range 1 " + drone.maxWeight);
        }

        if (drone.getBatteryCapacity() < 0 || drone.getBatteryCapacity() > 30000) {
            throw new RuntimeException("Battery value invalid range 1 - 30000");
        }

        if(drone.getMaxWeight() <= 100) drone.setModel(Drone.Model.LIGHTWEIGHT);
        else if (drone.getMaxWeight() <= 200) drone.setModel(Drone.Model.MIDDLEWEIGHT);
        else if (drone.getMaxWeight() <= 350) drone.setModel(Drone.Model.CRUISERWEIGHT);
        else if (drone.getMaxWeight() <= 500) drone.setModel(Drone.Model.HEAVYWEIGHT);


        // Ensuring the drone's state is one of the defined values
        if (drone.getState() == null) {
            drone.setState(Drone.State.IDLE);
        } else {
            // Check if the provided state is valid
            boolean isValidState = false;
            for (Drone.State state : Drone.State.values()) {
                if (state == drone.getState()) {
                    isValidState = true;
                    break;
                }
            }
            if (!isValidState) {
                throw new RuntimeException("Invalid drone state provided");
            }
        }
    }
     */
}
