package DroneMed.service;




import DroneMed.models.Medication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface MedicationService {

    public String createMedication(Medication medication);
    List<String> createMedications(List<Medication> medications);
    public String updateMedication(Medication medication);
    public String deleteMedication(String code);
    public Optional<Medication> getMedication(String code);
    public List<Optional<Medication>> getMedicationByName(String name);
    public List<Medication> getAllMedication();
}
