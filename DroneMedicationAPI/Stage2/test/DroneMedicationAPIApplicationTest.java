import DroneMed.DroneMedicationAPIApplication;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.mocks.web.response.HttpResponse;
import org.hyperskill.hstest.stage.SpringTest;
import org.hyperskill.hstest.testcase.CheckResult;
import javax.persistence.Column;
import javax.persistence.Id;



public class DroneMedicationAPIApplicationTest extends SpringTest {

  public DroneMedicationAPIApplicationTest () {
    super(DroneMedicationAPIApplication.class, 28852, "../droneMed_db_mv.db");
  }

  double latitude = 4.8156; //Random gps used as drone base coordinates
  double longitude = 7.0498; //Random gps used as drone base coordinates
  String coordinatesString = String.format("%.6fN %.6fE", latitude, longitude);

  private  final Drone drone1 = new Drone("0001", 100, 0, 10000, Drone.State.IDLE, Drone.Model.LIGHTWEIGHT, coordinatesString);
  private  final String drone1Correct = drone1.toJson();

  //Drone APi
  private final String createDrone = "/api/drones/create_drone";
  String msg = "Drone with serial number 0001 created successfully.";
  int createSuccessCode = 201;

  CheckResult testCreateApi(String api, String body,String message, int status) {
    HttpResponse response = post(api, body).send();


    if (response.getStatusCode() != status) {
      return CheckResult.wrong("POST " + api + " should respond with " +
              "status code " + status + ", responded: " + response.getStatusCode() + "\n\n" +
              "Response body:\n" + body);
    }
    

    return CheckResult.correct();
  }

  @DynamicTest
  DynamicTesting[] dt = new DynamicTesting[]{
          () -> testCreateApi(createDrone,drone1Correct, msg,createSuccessCode )
  };

}

class Drone {

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

  public Drone(String serialNumber, int maxWeight, int currentWeight, int batteryCapacity, State state, Model model, String currentCoordinate) {
    this.serialNumber = serialNumber;
    this.maxWeight = maxWeight;
    this.currentWeight = currentWeight;
    this.batteryCapacity = batteryCapacity;
    this.state = state;
    this.model = model;
    this.currentCoordinate = currentCoordinate;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public int getCurrentWeight() {
    return currentWeight;
  }

  public void setCurrentWeight(int currentWeight) {
    this.currentWeight = currentWeight;
  }

  public int getBatteryCapacity() {
    return batteryCapacity;
  }

  public void setBatteryCapacity(int batteryCapacity) {
    this.batteryCapacity = batteryCapacity;
  }

  public Model getModel() {
    return model;
  }

  public void setModel(Model model) {
    this.model = model;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public int getMaxWeight() {
    return maxWeight;
  }

  public void setMaxWeight(int max_weight) {
    this.maxWeight = max_weight;
  }

  public String getCurrentCoordinate() {
    return currentCoordinate;
  }

  public void setCurrentCoordinate(String currentCoordinate) {
    this.currentCoordinate = currentCoordinate;
  }

  public String toJson() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    try {
      return mapper.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }




}