package DroneMed.service;


import java.util.List;
import java.util.Optional;

import DroneMed.models.DroneDispatch;
import DroneMed.models.DroneDispatchDTO;

public interface DroneDispatchService {

    String dispatchDrone(DroneDispatch droneDispatch);
    Optional<DroneDispatchDTO> getDispatch(Long dispatchId);
    List<DroneDispatchDTO> getAllDispatch();
    List<DroneDispatchDTO> getDispatchByState(boolean isCancelled);
}
