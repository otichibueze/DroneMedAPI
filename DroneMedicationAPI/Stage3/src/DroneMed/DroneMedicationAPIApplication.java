package DroneMed;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class DroneMedicationAPIApplication  {

    public static void main(String[] args)  {
        SpringApplication.run(DroneMedicationAPIApplication.class, args);
    }

}