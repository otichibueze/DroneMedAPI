package DroneMed.service.impl;



import DroneMed.Utility.MyMapper;
import DroneMed.models.Medication;
import DroneMed.models.MedicationDTO;
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
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class MedicationServiceImpl implements MedicationService  {

    // Medication repository to interact with medication data in the database.
    private final MedicationRepository medicationRepository;

    private final MyMapper myMapper;

    /**
     * Constructor-based dependency injection for medication repository.
     *
     * @param medicationRepository - Repository to manage medication operations.
     */
    @Autowired
    public MedicationServiceImpl(MedicationRepository medicationRepository, MyMapper myMapper) {
        this.medicationRepository = medicationRepository;
        this.myMapper = myMapper;
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
        if(medication == null || medication.getCode() == null || medication.getName() == null || medication.getWeight() == 0
                || medication.getImageURL() == null ) {
            return "The parameter you entered contains a null or invalid parameter, Please enter a valid medication parameter.";

        }

        medicationRepository.save(medication);
        return "The medication with code " + medication.getCode() + " was created successfully.";
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

            return "The medication with code " + medication.getCode() + " was updated successfully.";
        }
        else {
            return ("The medication with code " + medication.getCode() + " was not found.");
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
            return "The medication with code " + code  + " was deleted Successfully.";
        }
        else {
            return "The medication with code " + code + " was not found.";
        }
    }

    /**
     * Retrieves a medication based on its code.
     *
     * @param code - Code of the medication to be fetched.
     * @return Medication - Medication entity.
     */
    @Override
    public Optional<MedicationDTO> getMedication(String code) {
        Optional<MedicationDTO> medicationDTO =  Optional.ofNullable(null);
        medicationDTO = medicationRepository.findById(code).map(myMapper::convertMedicationToDTO);
        return medicationDTO;
    }

    /**
     * Retrieves all medications from the database.
     *
     * @return List<Medication> - List of all Medication entities.
     */
    @Override
    public List<MedicationDTO> getAllMedication() {
        return medicationRepository.findAll().stream().map(myMapper::convertMedicationToDTO).collect(Collectors.toList());
    }

    @Override
    public  List<MedicationDTO> getMedicationByName(String name) {
        return medicationRepository.findByName(name).stream().map(myMapper::convertMedicationToDTO).collect(Collectors.toList());

    }

}
