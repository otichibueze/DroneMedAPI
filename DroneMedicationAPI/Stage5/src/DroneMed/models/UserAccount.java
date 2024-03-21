package DroneMed.models;

//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
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
public class UserAccount {

    @Column(nullable = false)
    private String name;

    @Id
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String gpsCoordinate;

    @Column(nullable = false)
    private Account account;

    //Security properties for springboot user authentication and authorization
    /*
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String authority;

     */

    public enum Account {
        ADMIN,
        CUSTOMER,
        GUEST,
    }

    @Override
    public String toString() {
        return "UserAccount name: " + this.getName()+ " phoneNumber: " + this.getPhoneNumber() + " address: " + this.getAddress() + " account: " + this.getAccount();
    }
}
