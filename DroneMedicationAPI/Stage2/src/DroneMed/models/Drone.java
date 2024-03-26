package DroneMed.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Drone {

    //This has max length 100 characters
    @Id
    private String serialNumber;

    //Max weight is 500
    @Column(nullable = false)
    private int currentWeight;

    //Maximum weight limit for the drone
    @Column(nullable = false)
    public int maxWeight;

    @Column(nullable = false)
    private int batteryCapacity;

    @Column(nullable = false)
    private Model model;

    @Column(nullable = false)
    private State state;

    @Column(nullable = false)
    private String currentCoordinate;

    //This represents the different models of drones
    public enum Model {
        LIGHTWEIGHT,
        MIDDLEWEIGHT,
        CRUISERWEIGHT,
        HEAVYWEIGHT
    }

    //Different states the drone can be in
    public enum State {
        IDLE,
        DELIVERING,
        DELIVERED,
        RETURNING
    }


    @Override
    public String toString() {
        return "Drone serialnumber: " + this.getSerialNumber() + " maxWeight: " + this.getMaxWeight() + " currentWeight: " + this.getCurrentWeight() + " batteryCapacity: " + this.getBatteryCapacity() +
                " State: " + this.getState() + " Model: " + this.model + " Coordinate: " + this.getCurrentCoordinate();
    }


}
