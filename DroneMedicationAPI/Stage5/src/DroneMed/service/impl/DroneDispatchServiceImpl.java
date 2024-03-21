package DroneMed.service.impl;


import DroneMed.Utility.MyMapper;
import DroneMed.models.*;
import DroneMed.repository.DroneDispatchRepository;
import DroneMed.repository.MedicationRepository;
import DroneMed.response.ResponseHandler;
import DroneMed.service.DroneDispatchService;
import DroneMed.service.DroneService;
import DroneMed.service.ScheduledTask;
import DroneMed.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DroneDispatchServiceImpl implements DroneDispatchService {


    // DroneDispatchRepository repository to interact with drone database.
    private final DroneDispatchRepository droneDispatchRepository;
    private final MedicationRepository medicationRepository;
    private final DroneService droneService;

    private final UserService userService;
    private final MyMapper myMapper;

    private final ScheduledTask scheduledTask;

    /**
     * Constructor-based dependency injection for droneDispatch repositories.
     *
     * @param droneDispatchRepository - Repository to manage dispatch operations.
     */
    public DroneDispatchServiceImpl(DroneDispatchRepository droneDispatchRepository, MedicationRepository medicationRepository, DroneService droneService,
                                    UserService userService, MyMapper myMapper, ScheduledTask scheduledTask)
    {
        this.droneDispatchRepository = droneDispatchRepository;
        this.medicationRepository = medicationRepository;
        this.droneService = droneService;
        this.userService = userService;
        this.myMapper = myMapper;
        this.scheduledTask = scheduledTask;
    }


    /**
     * Validates the provided dispatch data.
     *
     * @param droneDispatch - DroneDispatch entity
     */

    @Override
    public String dispatchDrone(DroneDispatch droneDispatch) {

        if(droneDispatch.getDrone() == null) return "Drone dispatch information is missing a drone! It should include details about the actual drone being dispatched.";
        if(droneDispatch.getMedications() == null) return "Drone dispatch information is missing medication details! It should include details about the actual medications being dispatched.";
        if(droneDispatch.getUser() == null) return "Drone dispatch information is missing user account details! It should include requesting user account information";

        int weight = checkWeight(droneDispatch.getDrone(), droneDispatch.getMedications());

        if(weight == -1) return "Drone selected cannot carry medications, You may reduce the number of medications or choose another drone with a larger capacity.";

        int batteryConsumption = droneDispatch.getEstimatedTime().toSecondOfDay()  * 10 * 2;
        if(droneDispatch.getDrone().getBatteryCapacity() < batteryConsumption) return "Drone selected does not have enough battery charge to delivery medication.";


       String response = medicationListAvailable(droneDispatch);

        if(response.contains("not found.")) return response;

        //confirm user exists in database
        Optional<UserAccount> user = userService.getUser(droneDispatch.getUser().getPhoneNumber());
        if(!user.isPresent()) return "User with phone number " + droneDispatch.getUser().getPhoneNumber() + " not found.";

        Optional<Drone> optDrone = droneService.getDrone(droneDispatch.getDrone().getSerialNumber());
        if(!optDrone.isPresent()) return "Drone with serial number " + droneDispatch.getDrone().getSerialNumber() +  " not found.";

        //Save drone dispatch
        droneDispatch.setTimestamp(Timestamp.from(Instant.now()));
        droneDispatchRepository.save(droneDispatch);

        //Update drone with loaded medication weight if it exists
        Drone drone = droneDispatch.getDrone();
        drone.setCurrentWeight(weight);
        response = droneService.updateDrone(drone);
        if(response.contains("not found.")) return response;



        //update foreign key in medication with drone dispatch id
        for(Medication m: droneDispatch.getMedications()) {

            Optional<Medication> existingMedication = medicationRepository.findById(m.getCode());

            medicationRepository.save(existingMedication.get());
        }

        //Start scheduled task for drone dispatched
        scheduledTask.droneDispatchedStateTask(droneDispatch);

        return "Drone dispatched successfully.";
    }

    /**
     * Retrieves drone dispatched with ID from the database.
     *
     * @return droneDispatchDTO - droneDispatch entity
     */
    @Override
    public Optional<DroneDispatchDTO> getDispatch(Long dispatchId) {
        Optional<DroneDispatchDTO> droneDispatchDTO = Optional.ofNullable(null);
        droneDispatchDTO =  droneDispatchRepository.findById(dispatchId).map(myMapper::convertDroneDispatchToDTO);
       return droneDispatchDTO;
    }

    /**
     * Retrieves all drone dispatched  from the database.
     *
     * @return List<DroneDispatch> - List of all DroneDispatch entities.
     */
    @Override
    public List<DroneDispatchDTO> getAllDispatch() {
        return droneDispatchRepository.findAll().stream().map(myMapper::convertDroneDispatchToDTO).collect(Collectors.toList());
    }

    @Override
    public List<DroneDispatchDTO> getDispatchByState(boolean isCancelled) {
        return droneDispatchRepository.findDroneDispatchByCancelled(isCancelled).stream().map(myMapper::convertDroneDispatchToDTO).collect(Collectors.toList());
       // return medicationRepository.findByName(name).stream().map(myMapper::convertMedicationToDTO).collect(Collectors.toList());
    }

    private int checkWeight(Drone drone, List<Medication> medications) {

        int maxWeight = drone.getMaxWeight();

        int currentWeight = 0;

        for(Medication m: medications) {
            currentWeight += m.getWeight();
        }

        if(currentWeight >= maxWeight) {
            return -1; //-1 means that the weight is too much
        }

        return currentWeight;

    }

    private String medicationListAvailable(DroneDispatch droneDispatch) {
        for(Medication m: droneDispatch.getMedications()) {

            Optional<Medication> existingMedication = medicationRepository.findById(m.getCode());

            if(existingMedication.isPresent()) {

                existingMedication.get().setDroneDispatch(droneDispatch);

            }
            else {
                return "medication with code " + m.getCode() + " not found.";
            }
        }

        return "Medication list available.";
    }
}
