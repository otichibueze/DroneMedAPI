package DroneMed.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class DroneDispatchDTO {

    private Long dispatchId;
    private Boolean cancelled;
    private LocalTime estimatedTime;
    private String droneID;
    private String droneStatus;
    private String userName;
    private String userPhoneNumber;
    private List<String> medicationCodes;
}
