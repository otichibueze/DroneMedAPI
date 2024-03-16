package DroneMed.repository;


import DroneMed.models.Drone;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {

    /**
     * Custom query to fetch drones based on their state.
     *
     * @param state - State of the drone.
     * @return List<Drone> - List of drones matching the provided state.
     */
    List<Optional<Drone>> findDronesByState(Drone.State state);

    /**
     * Custom query to fetch drones based on their model.
     *
     * @param model - Model of the drone.
     * @return List<Drone> - List of drones matching the provided model.
     */
    List<Optional<Drone>> findDronesByModel(Drone.Model model);

    /**
     * Custom query to fetch drones that have a battery capacity greater than the provided percentage.
     *
     * @param percentage - Minimum battery capacity.
     * @return List<Drone> - List of drones with battery capacity greater than the provided percentage.
     */
    List<Optional<Drone>> findDronesByBatteryCapacityAfter(int percentage);

}
