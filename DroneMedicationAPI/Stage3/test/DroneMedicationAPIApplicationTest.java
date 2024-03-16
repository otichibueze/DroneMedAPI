import DroneMed.DroneMedicationAPIApplication;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.mocks.web.response.HttpResponse;
import org.hyperskill.hstest.stage.SpringTest;
import org.hyperskill.hstest.testcase.CheckResult;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


import static org.hyperskill.hstest.testing.expect.Expectation.expect;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.*;


public class DroneMedicationAPIApplicationTest extends SpringTest {

  public DroneMedicationAPIApplicationTest () {
    super(DroneMedicationAPIApplication.class, 28852, "../droneMed_db_mv.db");
  }

  private ArrayList<Medication> medicationsA;
  private ArrayList<Medication> medicationsB;
  private ArrayList<Medication> medicationsC;
  private ArrayList<Medication> medicationsD;

  private ArrayList<Medication> medicationsE;


  //Drone info
  double latitude = 4.8156; //Random gps used as drone base coordinates
  double longitude = 7.0498; //Random gps used as drone base coordinates
  String coordinatesString = String.format("%.6fN %.6fE", latitude, longitude);

  //Drones
  private  final Drone drone1 = new Drone("S001", 100, 0, 10000, Drone.State.IDLE, Drone.Model.LIGHTWEIGHT, coordinatesString);
  private  final String drone1Correct = drone1.toJson();

  String msgDrone1 = "Drone with serial number S001 created successfully.";
  private  final Drone drone2 = new Drone("S002", 200, 0, 15000, Drone.State.DELIVERING, Drone.Model.MIDDLEWEIGHT, coordinatesString);
  private  final String drone2Correct = drone2.toJson();
  String msgDrone2 = "Drone with serial number S002 created successfully.";
  private  final Drone drone3 = new Drone("S003", 200, 0, 18000, Drone.State.DELIVERING, Drone.Model.MIDDLEWEIGHT, coordinatesString);
  private  final String drone3Correct = drone3.toJson();

  String msgDrone3 = "Drone with serial number S003 created successfully.";
  private  final Drone drone4 = new Drone("S004", 300, 0, 15000, Drone.State.IDLE, Drone.Model.CRUISERWEIGHT, coordinatesString);
  private  final String drone4Correct = drone4.toJson();
  String msgDrone4 = "Drone with serial number S004 created successfully.";
  private  final Drone drone5 = new Drone("S005", 400, 0, 20000, Drone.State.IDLE, Drone.Model.CRUISERWEIGHT, coordinatesString);
  private  final String drone5Correct = drone5.toJson();
  String msgDrone5 = "Drone with serial number S005 created successfully.";

  private  final Drone drone6 = new Drone("S006", 400, 0, 20000, Drone.State.IDLE, Drone.Model.CRUISERWEIGHT, coordinatesString);
  //private  final String drone6Correct = drone6.toJson();
  //String msgDrone6 = "Drone with serial number S005 created successfully.";



  //Medication
  private  final Medication medication1 = new Medication("M001", "Paracetamol", 30, "https://th.bing.com/th/id/OIP.CjEDMQk7TEODMKh4MZFfGgD6D6?w=166&h=180&c=7&r=0&o=5&pid=1.7");
  private  final String medication1Correct = medication1.toJson();
  String msgMedication1 = "Medication with code M001 created successfully.";

  private  final Medication medication2 = new Medication("M002", "Paracetamol", 38, "https://th.bing.com/th/id/OIP.CjEDMQk7TEODMKh4MZFfGgD6D6?w=166&h=180&c=7&r=0&o=5&pid=1.7");
  private  final String medication2Correct = medication2.toJson();
  String msgMedication2 = "Medication with code M002 created successfully.";
  private  final Medication medication3 = new Medication("M006","Amoxicillin", 65, "https://th.bing.com/th/id/R.86be7f194443ce59c5e21c0e78302e50?rik=5XJ7UyNSYyE6Yw&pid=ImgRaw&r=0");
  private  final String medication3Correct = medication3.toJson();
  String msgMedication3 = "Medication with code M006 created successfully.";
  private  final Medication medication4 = new Medication("M004", "Ibuprofen", 48, "https://th.bing.com/th/id/OIP.Nzkzq0Ic2me02XDdxi2DxQHaE8?rs=1&pid=ImgDetMain");
  private  final String medication4Correct = medication4.toJson();
  String msgMedication4 = "Medication with code M004 created successfully.";
  private  final Medication medication5 = new Medication("M005", "Metformin", 80,"https://th.bing.com/th/id/OIP.t1kStl77O7UxOQq0KhCa8AHaD5?rs=1&pid=ImgDetMain");
  private  final String medication5Correct = medication5.toJson();
  String msgMedication5 = "Medication with code M005 created successfully.";

  private  final Medication medication6 = new Medication("M006","Amoxicillin", 65, "https://th.bing.com/th/id/R.86be7f194443ce59c5e21c0e78302e50?rik=5XJ7UyNSYyE6Yw&pid=ImgRaw&r=0");
  private  final String medication6Correct = medication6.toJson();
  String msgMedication6 = "Medication with code M006 created successfully.";

  //User create
  private  final UserAccount user1 = new UserAccount(UserAccount.Account.GUEST, "Michael Martinez", "9163456789", "101 Walnut Avenue, San Diego, California(CA), 92101", "32.71574, -117.16109");
  private  final String user1Correct = user1.toJson();
  String msgUser1 = "User with phone number 9163456789 created successfully.";

  private  final UserAccount user2 = new UserAccount (UserAccount.Account.ADMIN, "Daniel Taylor", "3238765432", "303 Elm Avenue, Oakland, California(CA), 94601", "37.80493, -122.27080");
  private  final String user2Correct = user2.toJson();
  String msgUser2 = "User with phone number 3238765432 created successfully.";

  private  final UserAccount user3 = new UserAccount (UserAccount.Account.ADMIN, "Daniel Hunter", "3238765400", "303 Elm Avenue, Oakland, California(CA), 94601", "37.80493, -122.27080");

  //dispatch drone
  private  DroneDispatch droneDispatch1;
  private  String droneDispatch1Correct;
  private  DroneDispatch droneDispatch2;
  private  String droneDispatch2Correct;
  private  DroneDispatch droneDispatch3;
  private  String droneDispatch3Invalid;

  private  DroneDispatch droneDispatch4;
  private  String droneDispatch4Invalid;

  private  DroneDispatch droneDispatch5;
  private  String droneDispatch5Invalid;

  private  DroneDispatch droneDispatch6;
  private  String droneDispatch6Invalid;

  private  DroneDispatch droneDispatch7;
  private  String droneDispatch7Invalid;

  private final String msgDispatchCorrect = "Drone dispatched successfully.";
  private final String msgDispatchOverload = "Drone selected cannot carry medications, You may reduce the number of medications or choose another drone with a larger capacity.";
  private final String msgDispatchBatteryInsufficient = "Drone selected does not have enough battery charge to delivery medication.";
  private final String msgMedDispatchNotFound =  "medication with code M007 not found.";
  private final String msgUserDispatchNotFound =  "User with phone number 3238765400 not found.";
  private final String msgDroneDispatchNotFound =  "Drone with serial number S006 not found.";
  private final String msgGetDroneDispatch = "Drone Dispatched fetched successfully.";
  private final String msgGetDroneDispatchNotFound = "Drone Dispatched with id 12 not found.";
  private final String msgGetAllDroneDispatch = "All Drone Dispatched fetched successfully.";



  //Create API
  private final String createDrone = "/api/drones/create_drone";
  private final String getDroneByState = "/api/drones/get_drones_by_state/";
  private final String getDroneByModel = "/api/drones/get_drones_by_model/";
  private final String getDroneByCharge = "/api/drones/get_drones_by_charge/";
  private final String createMedication = "/api/medications/create_medication";
  private final String getMedicationByName = "/api/medications/get_by_name/";
  private final String createUser = "/api/users/create_user";
  private  final String dispatchDrone = "/api/dispatch/dispatch_drone";
  private  final String getDispatchDrone = "/api/dispatch/get_dispatch/";
  private  final String getAllDispatchDrone = "/api/dispatch/get_all_dispatch";

  String okSuccessCode = "OK";
  String notFoundCode = "NOT_FOUND";

  private final String StateSearch = "State";
  private final String SearchEmpty = "SearchEmpty";
  private final String ModelSearch = "Model";
  private final String ChargeSearch = "Charge";
  private final String NameSearch = "Name";


  CheckResult testCreateApi(String api, String body, String message, String status) {
    HttpResponse response = post(api, body).send();

    //Check json in response
    if(!response.getJson().isJsonObject()) {
      return CheckResult.wrong("Wrong object in response, expected JSON but was \n" +
              response.getContent().getClass());
    }

    expect(response.getContent()).asJson().check(isObject()
            .value("message", message)
            .value("httpStatus", status)
    );


    return CheckResult.correct();
  }

  CheckResult testDroneSearchApi(String api,String search,String type, String message, String status)  {
    HttpResponse response = get(api + search).send();

    //Check json in response
    if (!response.getJson().isJsonObject()) {
      return CheckResult.wrong("Wrong object in response, expected JSON but was \n" +
              response.getContent().getClass());
    }


    if (type == StateSearch) {
      expect(response.getContent()).asJson().check(isObject()
              .value("message", message)
              .value("httpStatus", status)
              .value("data", isArray()
                      .item(isObject()
                              .value("state", search).anyOtherValues()
                      )
                      .item(isObject()
                              .value("state", search).anyOtherValues()
                      )
      ));
    }
    else if (type == SearchEmpty) {
      expect(response.getContent()).asJson().check(isObject()
              .value("message", message).anyOtherValues());
    }else if (type == ModelSearch) {
      expect(response.getContent()).asJson().check(isObject()
              .value("message", message)
              .value("httpStatus", status)
              .value("data", isArray()
                      .item(isObject()
                              .value("model", search).anyOtherValues()
                      )
                      .item(isObject()
                              .value("model", search).anyOtherValues()
                      )
      ));
    } else if (type == ChargeSearch) {
      int charge = Integer.parseInt(search);
      expect(response.getContent()).asJson().check(isObject()
              .value("message", message)
              .value("httpStatus", status)
              .value("data", isArray()
                      .item(isObject()
                              .value("batteryCapacity", isInteger(i -> i >= charge)).anyOtherValues()
                      )
                      .item(isObject()
                              .value("batteryCapacity", isInteger(i -> i >= charge)).anyOtherValues()
                      )
              ));
    } else if (type == NameSearch) {
      expect(response.getContent()).asJson().check(isObject()
              .value("message", message)
              .value("httpStatus", status)
              .value("data", isArray()
                      .item(isObject()
                              .value("name", "Paracetamol").anyOtherValues()
                      )
                      .item(isObject()
                              .value("name", "Paracetamol").anyOtherValues()
                      )
      ));
    }

      return CheckResult.correct();

  }
  CheckResult testSetUps() {
    medicationsA = new ArrayList<>();
    //Set up medication list for dispatch
    medicationsA.add(new Medication("M001", "Paracetamol", 30, "https://th.bing.com/th/id/OIP.CjEDMQk7TEODMKh4MZFfGgD6D6?w=166&h=180&c=7&r=0&o=5&pid=1.7"));

    medicationsB = new ArrayList<>();
    medicationsB.add(new Medication("M006","Amoxicillin", 65, "https://th.bing.com/th/id/R.86be7f194443ce59c5e21c0e78302e50?rik=5XJ7UyNSYyE6Yw&pid=ImgRaw&r=0"));
    medicationsB.add(new Medication("M004", "Ibuprofen", 48, "https://th.bing.com/th/id/OIP.Nzkzq0Ic2me02XDdxi2DxQHaE8?rs=1&pid=ImgDetMain"));
    medicationsB.add(new Medication("M005", "Metformin", 80,"https://th.bing.com/th/id/OIP.t1kStl77O7UxOQq0KhCa8AHaD5?rs=1&pid=ImgDetMain"));

    medicationsC = new ArrayList<>();
    medicationsC.add(new Medication("M002", "omeprazole", 118, "https://www.drugs.com/images/pills/fio/GMK03970.JPG"));
    medicationsC.add(new Medication("M005", "Metformin", 208,"https://th.bing.com/th/id/OIP.t1kStl77O7UxOQq0KhCa8AHaD5?rs=1&pid=ImgDetMain"));

    medicationsD = new ArrayList<>();
    medicationsD.add(new Medication("M002", "omeprazole", 58, "https://www.drugs.com/images/pills/fio/GMK03970.JPG"));
    medicationsD.add(new Medication("M005", "Metformin", 28,"https://th.bing.com/th/id/OIP.t1kStl77O7UxOQq0KhCa8AHaD5?rs=1&pid=ImgDetMain"));

    medicationsE = new ArrayList<>();
    medicationsD.add(new Medication("M002", "omeprazole", 58, "https://www.drugs.com/images/pills/fio/GMK03970.JPG"));

    //Medication not in db
    medicationsE.add(new Medication("M007", "omeprazole", 58, "https://www.drugs.com/images/pills/fio/GMK03970.JPG"));

    droneDispatch1 = new DroneDispatch(drone1, user1,medicationsA, false,LocalTime.of(0,1,30));
    droneDispatch1Correct = droneDispatch1.toJson();

    droneDispatch2 = new DroneDispatch(drone2, user2,medicationsB, false,LocalTime.of(0,2,00));
    droneDispatch2Correct = droneDispatch2.toJson();

    //too much weight from medication
    droneDispatch3 = new DroneDispatch(drone3, user1,medicationsC, false,LocalTime.of(0,2,00));
    droneDispatch3Invalid = droneDispatch3.toJson();

    //too much time to make delivery
    droneDispatch4 = new DroneDispatch(drone4, user1,medicationsD, false,LocalTime.of(0,19,00));
    droneDispatch4Invalid = droneDispatch4.toJson();

    //Medication don't exist in db
    droneDispatch5 = new DroneDispatch(drone5, user1,medicationsE, false,LocalTime.of(0,2,00));
    droneDispatch5Invalid = droneDispatch5.toJson();

    //drone does not exist in db
    droneDispatch6 = new DroneDispatch(drone6, user1,medicationsD, false,LocalTime.of(0,2,00));
    droneDispatch6Invalid = droneDispatch6.toJson();

    //User does not exist in db
    droneDispatch7 = new DroneDispatch(drone4, user3,medicationsD, false,LocalTime.of(0,1,10));
    droneDispatch7Invalid = droneDispatch7.toJson();




    return CheckResult.correct();
  }

  CheckResult testGetDispatchApi(String api, String ID, String message, String status,  boolean found) {
    HttpResponse response = get(api + ID).send();

    //Check json in response
    if(!response.getJson().isJsonObject()) {
      return CheckResult.wrong("Wrong object in response, expected JSON but was \n" +
              response.getContent().getClass());
    }

    if(found) {
      expect(response.getContent()).asJson().check(isObject()
              .value("message", message)
              .value("httpStatus", status)
              .value("data", isObject()
                      .value("cancelled", false)
                      .value("estimatedTime", "00:01:30")
                      .value("droneID","S001")
                      .value("droneStatus", "IDLE")
                      .value("userName","Michael Martinez")
                      .value("userPhoneNumber","9163456789")
                      .value("medicationCodes", isArray()
                              .item(isString("M001"))
                      ).anyOtherValues()
      ));
    }
    else {
      expect(response.getContent()).asJson().check(isObject()
              .value("message", message)
              .value("httpStatus", status).anyOtherValues()
      );

    }

    return CheckResult.correct();
  }
  CheckResult testGetAllDispatchApi(String api, String message, String status) {
    HttpResponse response = get(api).send();

    //Check json in response
    if(!response.getJson().isJsonObject()) {
      return CheckResult.wrong("Wrong object in response, expected JSON but was \n" +
              response.getContent().getClass());
    }

    expect(response.getContent()).asJson().check(isObject()
            .value("message", message)
            .value("httpStatus", status)
            .value("data", isArray()
                    .item(isObject()
                            .value("cancelled", false)
                            .value("estimatedTime", "00:01:30")
                            .value("droneID","S001")
                            .value("droneStatus", "IDLE")
                            .value("userName","Michael Martinez")
                            .value("userPhoneNumber","9163456789")
                            .value("medicationCodes", isArray()
                                    .item(isString("M001"))).anyOtherValues())
                    .item(isObject()
                            .value("cancelled", false)
                            .value("estimatedTime", "00:02:00")
                            .value("droneID","S002")
                            .value("droneStatus", "DELIVERING")
                            .value("userName","Daniel Taylor")
                            .value("userPhoneNumber","3238765432")
                            .value("medicationCodes", isArray()
                                    .item(isString("M006"))
                                    .item(isString("M004"))
                                    .item(isString("M005"))).anyOtherValues()
                    )));

    return CheckResult.correct();
  }



  @DynamicTest
  DynamicTesting[] dt = new DynamicTesting[]{
          () -> testCreateApi(createDrone,drone1Correct, msgDrone1,okSuccessCode ),
          () -> testCreateApi(createDrone,drone2Correct, msgDrone2,okSuccessCode ),
          () -> testCreateApi(createDrone,drone3Correct, msgDrone3,okSuccessCode ),
          () -> testCreateApi(createDrone,drone4Correct, msgDrone4,okSuccessCode ),
          () -> testCreateApi(createDrone,drone5Correct, msgDrone5,okSuccessCode ),

          () -> testCreateApi(createMedication,medication1Correct, msgMedication1,okSuccessCode ),
          () -> testCreateApi(createMedication,medication2Correct, msgMedication2,okSuccessCode ),
          () -> testCreateApi(createMedication,medication3Correct, msgMedication3,okSuccessCode ),
          () -> testCreateApi(createMedication,medication4Correct, msgMedication4,okSuccessCode ),
          () -> testCreateApi(createMedication,medication5Correct, msgMedication5,okSuccessCode ),

          () -> testCreateApi(createUser,user1Correct, msgUser1,okSuccessCode ),
          () -> testCreateApi(createUser,user2Correct, msgUser2,okSuccessCode ),

          () -> testDroneSearchApi(getDroneByState,"DELIVERING",StateSearch,"Drones fetched by state.",okSuccessCode),
          () -> testDroneSearchApi(getDroneByState,"RETURNING",SearchEmpty,"Drones list by state RETURNING empty.",okSuccessCode),
          () -> testDroneSearchApi(getDroneByModel,"MIDDLEWEIGHT",ModelSearch,"Drones fetched by model.",okSuccessCode),
          () -> testDroneSearchApi(getDroneByModel,"HEAVYWEIGHT",SearchEmpty,"Drones list by model HEAVYWEIGHT empty.",okSuccessCode),
          () -> testDroneSearchApi(getDroneByCharge,"15000",ChargeSearch,"Drones fetched by charge.",okSuccessCode),
          () -> testDroneSearchApi(getDroneByCharge,"30000",SearchEmpty,"Drones list by percentage 30000 empty.",okSuccessCode),
          () -> testDroneSearchApi(getMedicationByName,"Paracetamol",NameSearch,"Medications fetched successfully.",okSuccessCode),
          () -> testDroneSearchApi(getMedicationByName,"levothyroxine",SearchEmpty,"Medication list by name levothyroxine empty.",okSuccessCode),

          () -> testSetUps(),
          () -> testCreateApi(dispatchDrone,droneDispatch3Invalid, msgDispatchOverload,okSuccessCode ),
          () -> testCreateApi(dispatchDrone,droneDispatch4Invalid, msgDispatchBatteryInsufficient,okSuccessCode ),
          () -> testCreateApi(dispatchDrone,droneDispatch5Invalid, msgMedDispatchNotFound,notFoundCode),
          () -> testCreateApi(dispatchDrone,droneDispatch6Invalid, msgDroneDispatchNotFound,notFoundCode),
          () -> testCreateApi(dispatchDrone,droneDispatch7Invalid, msgUserDispatchNotFound,notFoundCode),
          () -> testCreateApi(dispatchDrone,droneDispatch1Correct, msgDispatchCorrect,okSuccessCode ),
          () -> testCreateApi(dispatchDrone,droneDispatch2Correct, msgDispatchCorrect,okSuccessCode ),
          () -> testGetDispatchApi(getDispatchDrone,"1",msgGetDroneDispatch,okSuccessCode,true),
          () -> testGetDispatchApi(getDispatchDrone,"12", msgGetDroneDispatchNotFound,notFoundCode,false),
          () -> testGetAllDispatchApi(getAllDispatchDrone,msgGetAllDroneDispatch,okSuccessCode)

  };

}

class Drone {


  private String serialNumber;

  private int currentWeight;


  public int maxWeight;

  private int batteryCapacity;

  private Model model;

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

class Medication {

  private String code;

  private String name;

  private int weight;

  private String imageURL;

  public Medication(String code, String name, int weight,  String imageURL) {
    this.name = name;
    this.weight = weight;
    this.code = code;
    this.imageURL = imageURL;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
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

class UserAccount {

  private String name;

  private String phoneNumber;

  private String address;

  private String gpsCoordinate;

  private Account account;

  public enum Account {
    ADMIN,
    CUSTOMER,
    GUEST,
  }


  public UserAccount(Account _account, String _name, String _phoneNumber, String _address, String _gpsCoordinate) {
    this.account = _account;
    this.name = _name;
    this.phoneNumber = _phoneNumber;
    this.address = _address;
    this.gpsCoordinate = _gpsCoordinate;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getGpsCoordinate() {
    return gpsCoordinate;
  }

  public void setGpsCoordinate(String gpsCoordinate) {
    this.gpsCoordinate = gpsCoordinate;
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

class DroneDispatch {


  private Long dispatchId;

  private boolean cancelled = false;

  private LocalTime estimatedTime;

  private Timestamp timestamp;

  private Drone drone;

  private UserAccount user;

  private List<Medication> medications;

  public DroneDispatch() {}

  public DroneDispatch(Drone drone, UserAccount user, List<Medication> medications, boolean cancelled, LocalTime estimatedTime) {
    this.cancelled = cancelled;
    this.estimatedTime = estimatedTime;
    this.drone = drone;
    this.user = user;
    this.medications = medications;
    this.timestamp = Timestamp.from(Instant.now());
  }

  public Long getDispatchId() {
    return dispatchId;
  }
  public boolean isCancelled() {
    return cancelled;
  }

  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  public LocalTime getEstimatedTime() {
    return estimatedTime;
  }

  public void setEstimatedTime(LocalTime estimatedTime) {
    this.estimatedTime = estimatedTime;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public UserAccount getUser() {
    return user;
  }

  public Drone getDrone() {
    return drone;
  }

  public List<Medication> getMedications() {
    return medications;
  }

  public void setMedications(List<Medication> medications) {
    this.medications = medications;
  }

  public String toJson() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); //this helps accept local time

    try {
      return mapper.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

}