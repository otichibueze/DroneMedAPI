package DroneMed.controller;


import DroneMed.models.Drone;
import DroneMed.response.ResponseHandler;
import DroneMed.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for handling drone-related operations.
 * It provides CRUD operations for drones and other specific drone query functionalities.
 */
@RestController
@RequestMapping("/drones")
public class DroneController {

    // Service to handle drone-related operations.
    private final DroneService droneService;

    /**
     * Constructor-based dependency injection for the drone service.
     *
     * @param droneService - Service to manage drone operations.
     */
    @Autowired
    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    /**
     * Endpoint to create a new drone.
     *
     * @param drone - Drone entity to be created.
     * @return ResponseEntity with status and created drone.
     */
    @PostMapping("/create_drone")
    public ResponseEntity<?> createDrone(@RequestBody Drone drone) {
        String response = droneService.createDrone(drone);
        return ResponseHandler.responseBuilder(response, HttpStatus.OK);
    }

    /**
     * Endpoint to create multiple drones in bulk.
     *
     * @param drones - List of Drone entities to be created.
     * @return ResponseEntity with status and list of success messages.
     */
    @PostMapping("/create_drones")
    public ResponseEntity<?> createDrones(@RequestBody List<Drone> drones) {
        List<String> response = droneService.createDrones(drones);
        return ResponseHandler.responseBuilder("Drones created successfully.", HttpStatus.OK, response);
    }


    /**
     * Endpoint to update the details of an existing drone.
     *
     * @param drone - Drone entity with updated values.
     * @return ResponseEntity with status and updated drone.
     */
    @PutMapping("/update_drone")
    public ResponseEntity<?> updateDrone(@RequestBody Drone drone) {
        String response = droneService.updateDrone(drone);
        if(response.contains("not found.")) return ResponseHandler.responseBuilder(response, HttpStatus.NOT_FOUND);

        return ResponseHandler.responseBuilder(response, HttpStatus.OK);
    }


    /**
     * Endpoint to delete a drone based on its serial number.
     *
     * @param serialNumber - Serial number of the drone to be deleted.
     * @return ResponseEntity with status and success message.
     */
    @DeleteMapping("/delete_drone/{serialNumber}")
    public ResponseEntity<?> deleteDrone(@PathVariable String serialNumber) {
        String response = droneService.deleteDrone(serialNumber);
        if(response.contains("not found.")) return ResponseHandler.responseBuilder(response, HttpStatus.NOT_FOUND);

        return ResponseHandler.responseBuilder(response, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a drone based on its serial number.
     *
     * @param serialNumber - Serial number of the drone to be fetched.
     * @return ResponseEntity with status and fetched drone.
     */
    @GetMapping("/get_drone/{serialNumber}")
    public ResponseEntity<?> getDrone(@PathVariable String serialNumber) {
        Optional<Drone> drone = droneService.getDrone(serialNumber);
        if(drone.isPresent()) {
            return ResponseHandler.responseBuilder("Drone fetched successfully.", HttpStatus.OK, drone);
        }
        else return ResponseHandler.responseBuilder("Drone with serial number " + serialNumber + " not found.", HttpStatus.NOT_FOUND);
    }

    /**
     * Endpoint to retrieve all drones from the database.
     *
     * @return ResponseEntity with status and list of all drones.
     */
    @GetMapping("/get_all_drones")
    public ResponseEntity<?> getAllDrones() {
        List<Drone> drones = droneService.getAllDrones();
       if(drones.size() > 0) return ResponseHandler.responseBuilder("All drones fetched successfully.", HttpStatus.OK, drones);
       else return ResponseHandler.responseBuilder("Drone list empty.", HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve drones based on their state.
     *
     * @param state - State of the drones to be fetched.
     * @return ResponseEntity with status and list of drones with the specified state.
     */
    @GetMapping("/get_drones_by_state/{state}")
    public ResponseEntity<?> getDronesByState(@PathVariable Drone.State state) {
        List<Optional<Drone>> drones = droneService.findDroneByState(state);
        if(drones.size() == 0) return ResponseHandler.responseBuilder("Drones list by state " + state + " empty.", HttpStatus.OK);

        return ResponseHandler.responseBuilder("Drones fetched by state.", HttpStatus.OK, drones);
    }

    /**
     * Endpoint to retrieve drones based on their model.
     *
     * @param model - Model of the drones to be fetched.
     * @return ResponseEntity with status and list of drones with the specified model.
     */
    @GetMapping("/get_drones_by_model/{model}")
    public ResponseEntity<?> getDronesByModel(@PathVariable Drone.Model model) {
        List<Optional<Drone>> drones = droneService.findDronesByModel(model);
        if(drones.size() == 0) return ResponseHandler.responseBuilder("Drones list by model " + model + " empty.", HttpStatus.OK);

        return ResponseHandler.responseBuilder("Drones fetched by model.", HttpStatus.OK, drones);
    }

    /**
     * Endpoint to retrieve drones based on their charge percentage.
     *
     * @param percentage - Charge percentage to filter drones.
     * @return ResponseEntity with status and list of drones with the specified charge percentage.
     */
    @GetMapping("/get_drones_by_charge/{percentage}")
    public ResponseEntity<?> getDronesByCharge(@PathVariable int percentage) {
        List<Optional<Drone>> drones = droneService.findDronesByBatteryCapacityAfter(percentage);
        if(drones.size() == 0) return ResponseHandler.responseBuilder("Drones list by percentage " + percentage + " empty.", HttpStatus.OK);

        return ResponseHandler.responseBuilder("Drones fetched by charge.", HttpStatus.OK, drones);
    }

    @PatchMapping("/validEndpoint")
    public void validEndpoint() {
        System.out.println("Invalid endpoint requested.");
    }

}
