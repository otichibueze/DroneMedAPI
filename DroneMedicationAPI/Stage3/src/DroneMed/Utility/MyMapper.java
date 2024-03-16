package DroneMed.Utility;

import DroneMed.models.DroneDispatch;
import DroneMed.models.DroneDispatchDTO;
import DroneMed.models.Medication;
import DroneMed.models.MedicationDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MyMapper {

    public MedicationDTO convertMedicationToDTO(Medication medication) {
        long dispatchID = medication.getDroneDispatch() == null ? -1L : medication.getDroneDispatch().getDispatchId();
        return new MedicationDTO(medication.getCode(), medication.getName(), medication.getWeight(), medication.getImageURL(), dispatchID);
    }

    public MedicationDTO convertMedicationToDTO(Optional<Medication> medication) {
        long dispatchID = medication.get().getDroneDispatch() == null ? -1L : medication.get().getDroneDispatch().getDispatchId();
        return new MedicationDTO(medication.get().getCode(), medication.get().getName(), medication.get().getWeight(), medication.get().getImageURL(), dispatchID);
    }


    public DroneDispatchDTO convertDroneDispatchToDTO(DroneDispatch droneDispatch) {
        return new DroneDispatchDTO(droneDispatch.getDispatchId(),droneDispatch.isCancelled(), droneDispatch.getEstimatedTime(), droneDispatch.getDrone().getSerialNumber(), droneDispatch.getDrone().getState().toString(),
                droneDispatch.getUser().getName(),  droneDispatch.getUser().getPhoneNumber(), droneDispatch.getMedications().stream().map(Medication::getCode).collect(Collectors.toList()));
    }

    public DroneDispatchDTO convertDroneDispatchToDTO(Optional<DroneDispatch> droneDispatch) {
        return new DroneDispatchDTO(droneDispatch.get().getDispatchId(),droneDispatch.get().isCancelled(), droneDispatch.get().getEstimatedTime(), droneDispatch.get().getDrone().getSerialNumber(),  droneDispatch.get().getDrone().getState().toString(),
                droneDispatch.get().getUser().getName(),  droneDispatch.get().getUser().getPhoneNumber(), droneDispatch.get().getMedications().stream().map(Medication::getCode).collect(Collectors.toList()));
    }



}
