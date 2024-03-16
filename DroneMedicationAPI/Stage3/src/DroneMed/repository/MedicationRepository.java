package DroneMed.repository;


import DroneMed.models.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, String> {

    //Custom query to find medication by name
    List<Optional<Medication>> findByName(String name);

    //Custom query to find by medication weight
    List<Optional<Medication>> findByWeight(int weight);
}
