package DroneMed.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
public class MedicationDTO {
    private String code;

    private String name;

    private int weight;

    private String imageURL;

    private  long droneDispatchID;
}
