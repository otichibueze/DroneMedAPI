package DroneMed.config;

import DroneMed.service.DroneService;
import DroneMed.service.MedicationService;
import DroneMed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "DroneMed.repository")
@EntityScan(basePackages = "DroneMed")
@EnableAutoConfiguration
public class MyConfiguration {

    private final DroneService droneService;
    private final MedicationService medicationService;
    private final DroneMed.service.UserService userService;


    @Autowired
    public MyConfiguration(MedicationService medicationService, UserService userService,DroneService droneService) {
        this.droneService = droneService;
        this.medicationService = medicationService;
        this.userService = userService;
    }

    @Bean
    public DroneService droneService() {
        return droneService;
    }

    @Bean
    public MedicationService medicationService() {
        return medicationService;
    }

    @Bean
    public UserService userService() {
        return userService;
    }
}
