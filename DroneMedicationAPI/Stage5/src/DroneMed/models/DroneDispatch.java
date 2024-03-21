package DroneMed.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DroneDispatch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long dispatchId;

    @Column(nullable = false)
    private boolean cancelled;

    @Column(nullable = false)
    private LocalTime estimatedTime;
    //LocalTime.of(2, 30))

    private Timestamp timestamp;

    @OneToOne
    @JoinColumn(name = "serialNumber")
    private Drone drone;

    @OneToOne
    @JoinColumn(name = "phoneNumber")
    private UserAccount user;

    //Table is owned by medication and will cascade data when created in droneDispatch
    @OneToMany(mappedBy = "droneDispatch")
    private List<Medication> medications;


}
