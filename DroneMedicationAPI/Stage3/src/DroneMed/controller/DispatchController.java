package DroneMed.controller;


import DroneMed.models.DroneDispatch;
import DroneMed.models.DroneDispatchDTO;
import DroneMed.response.ResponseHandler;
import DroneMed.service.DroneDispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dispatch")
public class DispatchController {

    // Service for drone related operations.
    private final DroneDispatchService droneDispatchService;

    /**
     * Constructor-based dependency injection for drone Dispatch
     *
     * @param droneDispatchService - Service to manage drone dispatch operations.
     */
    @Autowired
    public DispatchController(DroneDispatchService droneDispatchService) {
        this.droneDispatchService = droneDispatchService;
    }


    /**
     * Endpoint to create a new drone dispatch.
     *
     * @param droneDispatch - Drone dispatch entity to be created.
     * @return ResponseEntity with status and created droneDispatch.
     */
    @PostMapping("/dispatch_drone")
    public ResponseEntity<?> dispatchDrone(@RequestBody DroneDispatch droneDispatch) {
        String response = droneDispatchService.dispatchDrone(droneDispatch);
        if(response.contains("not found.")) return ResponseHandler.responseBuilder(response, HttpStatus.NOT_FOUND);

        return ResponseHandler.responseBuilder(response, HttpStatus.OK);
    }



    /**
     * Endpoint to retrieve a drone based on its serial number.
     *
     * @param DispatchId - Serial number of the drone to be fetched.
     * @return ResponseEntity with status and fetched drone.
     */
    @GetMapping("/get_dispatch/{DispatchId}")
    public ResponseEntity<?> getDispatch(@PathVariable Long DispatchId) {
        Optional<DroneDispatchDTO> droneDispatch = droneDispatchService.getDispatch(DispatchId);
        if(droneDispatch.isPresent()) {
            return ResponseHandler.responseBuilder("The drone Dispatched fetched successfully.", HttpStatus.OK, droneDispatch);
        }
        else {
             return ResponseHandler.responseBuilder("The drone Dispatched with id " + DispatchId + " was not found.", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to retrieve a drone based on its serial number.
     *
     * @return ResponseEntity with status list of all drone dispatched  fetched.
     */
    @GetMapping("/get_all_dispatch")
    public ResponseEntity<?> getAllDispatch() {
        List<DroneDispatchDTO> droneDispatch = droneDispatchService.getAllDispatch();
        if(droneDispatch.size() > 0) return ResponseHandler.responseBuilder("All Drone Dispatched fetched successfully.", HttpStatus.OK, droneDispatch);
        else return ResponseHandler.responseBuilder("The drone dispatch list is empty.", HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve drones dispatched based on their state.
     *
     * @param state - State of the drones dispatched to be fetched.
     * @return ResponseEntity with status and list of drone dispatched with  the specified state.
     */
    @GetMapping("/drone_dispatch_state/{state}")
    public ResponseEntity<?> getDronesByState(@PathVariable boolean state) {
        List<DroneDispatchDTO> droneDispatch = droneDispatchService.getDispatchByState(state);
        if(droneDispatch.size() > 0) return ResponseHandler.responseBuilder("Drones Dispatched by state fetched.", HttpStatus.OK, droneDispatch);
        else return ResponseHandler.responseBuilder("The drone dispatch list by state " +  state + " is empty.", HttpStatus.OK);
    }

}
