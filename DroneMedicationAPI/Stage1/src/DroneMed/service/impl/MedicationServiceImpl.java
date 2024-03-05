package DroneMed.service.impl;



import DroneMed.models.Medication;
import DroneMed.repository.MedicationRepository;
import DroneMed.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class MedicationServiceImpl implements MedicationService  {

    // Regular expression for validating the name
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");

    // Regular expression for validating the code
    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Z0-9_]+$");


    // Medication repository to interact with medication data in the database.
    private final MedicationRepository medicationRepository;

    /**
     * Constructor-based dependency injection for medication repository.
     *
     * @param medicationRepository - Repository to manage medication operations.
     */
    @Autowired
    public MedicationServiceImpl(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }


    /**
     * Creates a new medication entry in the database.
     *
     * @param medication - Medication entity to be created.
     * @return String - Success message.
     */
    @Override
    public String createMedication(Medication medication) {
        //checkMedicationValues(medication);

        medicationRepository.save(medication);
        return "Medication " + medication.getCode() + " created successfully";
    }

    /**
     * Creates multiple new medication entries in the database.
     *
     * @param medications - List of Medication entities to be created.
     * @return List<String> - List of success messages.
     */
    @Transactional
    @Override
    public List<String> createMedications(List<Medication> medications) {
        List<String> responses = new ArrayList<>();
        for(Medication m: medications)
        {
            responses.add(createMedication(m));
        }

        return responses;
    }

    /**
     * Updates the details of an existing medication.
     *
     * @param medication - Medication entity with updated values.
     * @return String - Success message.
     */
    @Override
    public String updateMedication(Medication medication) {

        //checkMedicationValues(medication);

        if(medicationRepository.existsById(medication.getCode())) {
            // Find the medication by code
            Medication existingMedication = medicationRepository.findById(medication.getCode()).get();

            medicationRepository.save(medication);

            return "Medication with code " + medication.getCode() + " updated successfully";
        }
        else {
            return ("Medication not found");
        }
    }

    /**
     * Deletes a medication entry from the database based on its code.
     *
     * @param code - Code of the medication to be deleted.
     * @return String - Success message.
     */
    @Override
    public String deleteMedication(String code) {
        if(medicationRepository.existsById(code))
        {
            medicationRepository.deleteById(code);
            return "Medication with code " + code  + " deleted Successfully";
        }
        else {
            return "Medication with code " + code + " not found";
        }
    }

    /**
     * Retrieves a medication based on its code.
     *
     * @param code - Code of the medication to be fetched.
     * @return Medication - Medication entity.
     */
    @Override
    public Optional<Medication> getMedication(String code) {
        return medicationRepository.findById(code);
               // .orElseThrow(() -> new RuntimeException("Medication with code " + code + " not found"));
    }

    /**
     * Retrieves all medications from the database.
     *
     * @return List<Medication> - List of all Medication entities.
     */
    @Override
    public List<Medication> getAllMedication() {
        return medicationRepository.findAll();
        //we can use pageable here
    }

    @Override
    public  List<Optional<Medication>> getMedicationByName(String name) {
        return medicationRepository.findByName(name);
    }

    /*
    private void checkMedicationValues(Medication medication)
    {
        if (!NAME_PATTERN.matcher(medication.getName()).matches()) {
            throw new RuntimeException ("Invalid name");
        }

        if (!CODE_PATTERN.matcher(medication.getCode()).matches()) {
            throw new RuntimeException("Invalid code provided");
        }
    }
     */
}
