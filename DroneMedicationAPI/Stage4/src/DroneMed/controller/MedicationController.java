package DroneMed.controller;

import DroneMed.models.Medication;
import DroneMed.models.MedicationDTO;
import DroneMed.response.ResponseHandler;
import DroneMed.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for handling medication-related operations.
 * It provides CRUD operations for medications .
 */
@RestController
@RequestMapping("/medications")
public class MedicationController {


    // Service to handle medication-related operations.
    private final MedicationService medicationService;

    /**
     * Constructor-based dependency injection for the medication service.
     *
     * @param medicationService - Service to manage medication operations.
     */
    @Autowired
    public MedicationController(MedicationService medicationService) {

        this.medicationService = medicationService;
    }

    @PostMapping("/create_medication")
    public ResponseEntity<?> createMedication(@RequestBody Medication medication) {

        String response = medicationService.createMedication(medication);
        return ResponseHandler.responseBuilder(response, HttpStatus.OK);
    }

    /**
     * Endpoint to create multiple medications in bulk.
     *
     * @param medications - List of Medication entities to be created.
     * @return ResponseEntity with status and list of success messages.
     */
    @PostMapping("/create_medications")
    public ResponseEntity<?> createMedications(@RequestBody List<Medication> medications) {
        List<String> responses = medicationService.createMedications(medications);
        return ResponseHandler.responseBuilder("Medications created successfully.", HttpStatus.OK, responses);
    }

    /**
     * Endpoint to update the details of an existing medication.
     *
     * @param medication - Medication entity with updated values.
     * @return ResponseEntity with status and updated medication.
     */
    @PutMapping("/update_medication")
    public ResponseEntity<?> updateMedication(@RequestBody Medication medication) {
        String response = medicationService.updateMedication(medication);
        if(response.contains("not found.")) return ResponseHandler.responseBuilder(response, HttpStatus.NOT_FOUND);

        return ResponseHandler.responseBuilder(response, HttpStatus.OK);
    }

    /**
     * Endpoint to delete a medication based on its code.
     *
     * @param code - Code of the medication to be deleted.
     * @return ResponseEntity with status and success message.
     */
    @DeleteMapping("/delete_medication/{code}")
    public ResponseEntity<?> deleteMedication(@PathVariable String code) {
        String response = medicationService.deleteMedication(code);
        if(response.contains("not found.")) return ResponseHandler.responseBuilder(response, HttpStatus.NOT_FOUND);

        return ResponseHandler.responseBuilder(response, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a medication based on its code.
     *
     * @param code - Code of the medication to be fetched.
     * @return ResponseEntity with status and fetched medication.
     */
    @GetMapping("/get_medication/{code}")
    public ResponseEntity<?> getMedication(@PathVariable String code) {
        Optional<MedicationDTO> medication = medicationService.getMedication(code);
        if(medication.isPresent()) {
            return ResponseHandler.responseBuilder("Medication fetched successfully.", HttpStatus.OK, medication);
        }
        else return ResponseHandler.responseBuilder("The medication with code " + code + " was not found.", HttpStatus.NOT_FOUND);
    }

    /**
     * Endpoint to retrieve all medications from the database.
     *
     * @return ResponseEntity with status and list of all medications.
     */
    @GetMapping("/get_all_medications")
    public ResponseEntity<?> getAllMedication() {
        List<MedicationDTO> medications = medicationService.getAllMedication();
        if(medications.size() > 0) {
            return ResponseHandler.responseBuilder("All medications fetched successfully.", HttpStatus.OK, medications);
        }
        else  return ResponseHandler.responseBuilder("The medication list is empty.", HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a medication based on its name.
     *
     * @param name - name of the medication to be fetched.
     * @return ResponseEntity with status and fetched medication.
     */
    @GetMapping("/get_by_name/{name}")
    public ResponseEntity<?> getMedicationByName(@PathVariable String name) {
        List<MedicationDTO> medication = medicationService.getMedicationByName(name);

        if(medication.size() == 0) return ResponseHandler.responseBuilder("The medication list by name " + name + " empty.", HttpStatus.OK);

        return ResponseHandler.responseBuilder("Medications fetched successfully.", HttpStatus.OK, medication);
    }


}
