package DroneMed.models;

//import jakarta.persistence.*;
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
public class Medication {

    @Id
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int weight;

    private String imageURL;

    @Override
    public String toString() {
        return "Medication code: " + this.getCode() + " name: " + this.getName() + " Weight: " + this.getWeight() + " imageURL: " + this.getImageURL();
    }

}
