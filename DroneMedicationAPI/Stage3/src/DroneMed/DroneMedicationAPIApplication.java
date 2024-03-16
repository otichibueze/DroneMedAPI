package DroneMed;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DroneMedicationAPIApplication  {

    public static void main(String[] args)  {
        SpringApplication.run(DroneMedicationAPIApplication.class, args);
    }

}