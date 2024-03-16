package DroneMed.repository;



import DroneMed.models.DroneDispatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DroneDispatchRepository extends JpaRepository<DroneDispatch, Long> {

    //find by state
    List<Optional<DroneDispatch>> findDroneDispatchByCancelled(boolean cancelled);
}
