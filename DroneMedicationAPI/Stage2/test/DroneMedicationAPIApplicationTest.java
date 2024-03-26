

import DroneMed.DroneMedicationAPIApplication;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.mocks.web.response.HttpResponse;
import org.hyperskill.hstest.stage.SpringTest;
import org.hyperskill.hstest.testcase.CheckResult;

import static org.hyperskill.hstest.testing.expect.Expectation.expect;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.*;


public class DroneMedicationAPIApplicationTest extends SpringTest{

  public DroneMedicationAPIApplicationTest () {
    super(DroneMedicationAPIApplication.class, 28852, "../droneMed_db_mv.db");
  }

  //Drone info
  double latitude = 4.8156; //Random gps used as drone base coordinates
  double longitude = 7.0498; //Random gps used as drone base coordinates
  String coordinatesString = String.format("%.6fN %.6fE", latitude, longitude);

  //Drone create
  private  final Drone drone1 = new Drone("S001", 100, 0, 10000, Drone.State.IDLE, Drone.Model.LIGHTWEIGHT, coordinatesString);
  private  final String drone1Correct = drone1.toJson();
  String msgDrone1 = "The drone with serial number S001 was created successfully.";
  private  final Drone drone2 = new Drone("S002", 200, 0, 15000, Drone.State.IDLE, Drone.Model.MIDDLEWEIGHT, coordinatesString);
  private  final String drone2Correct = drone2.toJson();
  String msgDrone2 = "The drone with serial number S002 was created successfully.";
  private  final Drone drone3 = new Drone("S012", 200, 0, 15000, Drone.State.IDLE, Drone.Model.MIDDLEWEIGHT, null);
  private  final String drone3Invalid = drone3.toJson();
  private  final Drone drone4 = new Drone("S004", 340, 0, 18000, Drone.State.IDLE, Drone.Model.CRUISERWEIGHT, coordinatesString);
  private  final String drone4Correct = drone4.toJson();
  String msgDrone4 = "The drone with serial number S004 was created successfully.";

  private  final Drone drone5 = new Drone("S005", 300, 0, 15000, Drone.State.IDLE, Drone.Model.CRUISERWEIGHT, coordinatesString);
  private  final String drone5Correct = drone5.toJson();
  String msgDrone5 = "The drone with serial number S005 was created successfully.";

  private  final Drone drone6 = new Drone("S006", 400, 0, 20000, Drone.State.IDLE, Drone.Model.HEAVYWEIGHT, coordinatesString);
  private  final String drone6Correct = drone6.toJson();
  String msgDrone6 = "The drone with serial number S006 was created successfully.";

  String invalidDroneMsg = "The parameter you entered contains a null or invalid parameter, Please enter a valid drone parameter.";

  //Drone update
  private  final Drone droneUpdate1 = new Drone("S001", 100, 80, 10000, Drone.State.DELIVERING, Drone.Model.LIGHTWEIGHT, coordinatesString);
  private  final String droneUpdate1Correct = droneUpdate1.toJson();
  String msgDroneUpdate1 = "The drone with serial number S001 was updated successfully.";

  private  final Drone droneUpdate2 = new Drone("S099", 100, 80, 10000, Drone.State.DELIVERING, Drone.Model.LIGHTWEIGHT, coordinatesString);
  private  final String droneUpdate2Invalid = droneUpdate2.toJson();
  String msgDroneUpdate2 = "The drone with serial number S099 was not found.";

  private  final Drone droneUpdate4 = new Drone("S004", 340, 170, 14000, Drone.State.IDLE, Drone.Model.CRUISERWEIGHT, coordinatesString);
  private  final String droneUpdate4Correct = droneUpdate4.toJson();
  String msgDroneUpdate4 = "The drone with serial number S004 was updated successfully.";

  //Drone Delete
  private final String droneDeleteID1 = "S002";
  private final String msgDeleteDrone1 = "The drone with serial number S002 was deleted Successfully.";
  private final String droneDeleteID2Invalid = "S090";
  private final String msgDeleteDrone2 = "The drone with serial number S090 was not found.";
  private final String droneDeleteID3 = "S005";
  private final String msgDeleteDrone3 = "The drone with serial number S005 was deleted Successfully.";


  //Drone Get
  private final String droneGetID1 = "S001";
  private final String msgGetDrone = "Drone fetched successfully.";
  private final Drone droneGet1 = droneUpdate1;
  private final String droneGetID2Invalid = "S091";
  private final String msgGetDroneGetID2 = "The drone with serial number S091 was not found.";
  private final String droneGetID3 = "S004";
  private final Drone droneGet3 = droneUpdate4;
  private final String msgGetAllDrones = "All drones fetched successfully.";


  //Drone APi
  private final String createDrone = "/api/drones/create_drone";
  private final String updateDrone = "/api/drones/update_drone";
  private final String deleteDrone = "/api/drones/delete_drone/";
  private final String getDrone = "/api/drones/get_drone/";
  private final String getAllDrone = "api/drones/get_all_drones";



  //Medication create
  private  final Medication medication1 = new Medication("M001", "Paracetamol", 30, "https://th.bing.com/th/id/OIP.CjEDMQk7TEODMKh4MZFfGgD6D6?w=166&h=180&c=7&r=0&o=5&pid=1.7");
  private  final String medication1Correct = medication1.toJson();
  String msgMedication1 = "The medication with code M001 was created successfully.";

  private  final Medication medication2 = new Medication("M002", "omeprazole", 38, "https://www.drugs.com/images/pills/fio/GMK03970.JPG");
  private  final String medication2Correct = medication2.toJson();
  String msgMedication2 = "The medication with code M002 was created successfully.";
  private  final Medication medication3 = new Medication("M003", "Lisinopril", 0,null);
  private  final String medication3Invalid = medication3.toJson();
  private  final Medication medication4 = new Medication("M004", "Ibuprofen", 48, "https://th.bing.com/th/id/OIP.Nzkzq0Ic2me02XDdxi2DxQHaE8?rs=1&pid=ImgDetMain");
  private  final String medication4Correct = medication4.toJson();
  String msgMedication4 = "The medication with code M004 was created successfully.";

  private  final Medication medication5 = new Medication("M005", "Metformin", 80,"https://th.bing.com/th/id/OIP.t1kStl77O7UxOQq0KhCa8AHaD5?rs=1&pid=ImgDetMain");
  private  final String medication5Correct = medication5.toJson();
  String msgMedication5 = "The medication with code M005 was created successfully.";

  private  final Medication medication6 = new Medication("M006","Amoxicillin", 65, "https://th.bing.com/th/id/R.86be7f194443ce59c5e21c0e78302e50?rik=5XJ7UyNSYyE6Yw&pid=ImgRaw&r=0");
  private  final String medication6Correct = medication6.toJson();
  String msgMedication6 = "The medication with code M006 was created successfully.";



  String invalidMedicationMsg = "The parameter you entered contains a null or invalid parameter, Please enter a valid medication parameter.";

  //Medication update
  private  final Medication medicationUpdate1 = new  Medication("M002", "omeprazole", 78, "https://www.drugs.com/images/pills/fio/GMK03970.JPG");
  private  final String medicationUpdate1Correct = medicationUpdate1.toJson();
  String msgMedicationUpdate1 = "The medication with code M002 was updated successfully.";

  private  final Medication medicationUpdate2 = new Medication("M019", "Atorvastatin", 25, "https://www.drugs.com/images/pills/fio/GMK03970.JPG");
  private  final String medicationUpdate2Invalid = medicationUpdate2.toJson();
  String msgMedicationUpdate2 = "The medication with code M019 was not found.";

  private  final Medication medicationUpdate4 = new Medication("M004", "Ibuprofen", 100, "https://th.bing.com/th/id/OIP.Nzkzq0Ic2me02XDdxi2DxQHaE8?rs=1&pid=ImgDetMain");
  private  final String medicationUpdate4Correct = medicationUpdate4.toJson();
  String msgMedicationUpdate4 = "The medication with code M004 was updated successfully.";

  //Medication Delete
  private final String medicationDeleteID1 = "M001";
  private final String msgDeleteMedication1 = "The medication with code M001 was deleted Successfully.";
  private final String medicationDeleteID2Invalid = "M021";
  private final String msgDeleteMedication2 = "The medication with code M021 was not found.";
  private final String medicationDeleteID3 = "M006";
  private final String msgDeleteMedication3 = "The medication with code M006 was deleted Successfully.";


  //Medication Get
  private final String medicationGetID1 = "M002";
  private final String msgGetMedication = "Medication fetched successfully.";
  private final Medication medicationGet1 = medicationUpdate1;
  private final String medicationGetID2Invalid = "M041";
  private final String msgGetMedicationGetID2 = "The medication with code M041 was not found.";
  private final String medicationGetID3 = "M004";
  private final Medication medicationGet3 = medicationUpdate4;
  private final String msgGetAllMedication = "All medications fetched successfully.";


  //Medication APi
  private final String createMedication = "/api/medications/create_medication";
  private final String updateMedication = "/api/medications/update_medication";
  private final String deleteMedication = "/api/medications/delete_medication/";
  private final String getMedication = "/api/medications/get_medication/";
  private final String getAllMedication = "api/medications/get_all_medications";



  //User create
  private  final UserAccount user1 = new UserAccount(UserAccount.Account.GUEST, "Michael Martinez", "9163456789", "101 Walnut Avenue, San Diego, California(CA), 92101", "32.71574, -117.16109");
  private  final String user1Correct = user1.toJson();
  String msgUser1 = "The user with phone number 9163456789 was created successfully.";

  private  final UserAccount user2 = new UserAccount (UserAccount.Account.ADMIN, "Daniel Taylor", "3238765432", "303 Elm Avenue, Oakland, California(CA), 94601", "37.80493, -122.27080");
  private  final String user2Correct = user2.toJson();
  String msgUser2 = "The user with phone number 3238765432 was created successfully.";
  private  final UserAccount user3 = new UserAccount(UserAccount.Account.GUEST, "Sophia Rodriguez", "7149876543", null, null);
  private  final String user3Invalid = user3.toJson();
  private  final UserAccount user4 =new UserAccount(UserAccount.Account.CUSTOMER, "David Garcia", "2098765432", "505 Oak Street, Long Beach, California(CA), 90802", "33.76902, -118.19147");
  private  final String user4Correct = user4.toJson();
  String msgUser4 = "The user with phone number 2098765432 was created successfully.";

  private  final UserAccount user5 = new UserAccount(UserAccount.Account.CUSTOMER, "Ava Brown", "9492345678", "606 Pine Avenue, Santa Ana, California(CA), 92701", "33.74557, -117.86783");
  private  final String user5Correct = user5.toJson();
  String msgUser5 = "The user with phone number 9492345678 was created successfully.";

  private  final UserAccount user6 = new UserAccount(UserAccount.Account.CUSTOMER, "Ethan Wilson", "8188765432", "707 Cedar Street, Riverside, California(CA), 92501", coordinatesString);
  private  final String user6Correct = user6.toJson();
  String msgUser6 = "The user with phone number 8188765432 was created successfully.";
  String invalidUserMsg = "The parameter you entered contains a null or invalid parameter, Please enter a valid user account parameter.";

  //User update
  private  final UserAccount userUpdate1 = new UserAccount(UserAccount.Account.GUEST, "Michael Martinez", "9163456789", "202 Birch Street, San Jose, California(CA), 95110", "37.33821, -121.88633");
  private  final String userUpdate1Correct = userUpdate1.toJson();
  String msgUserUpdate1 = "The user with phone number 9163456789 was updated successfully.";

  private  final UserAccount userUpdate2 = new UserAccount (UserAccount.Account.ADMIN, "Daniel Taylor", "3238765492", "303 Elm Avenue, Oakland, California(CA), 94601", "37.80493, -122.27080");
  private  final String userUpdate2Invalid = userUpdate2.toJson();
  String msgUserUpdate2 = "The user with phone number 3238765492 was not found.";

  private  final UserAccount userUpdate4 = new UserAccount(UserAccount.Account.CUSTOMER, "David Garcia", "2098765432", "456 Pine Avenue, Los Angeles, California(CA), 90001", "34.05223, -118.24368");
  private  final String userUpdate4Correct = userUpdate4.toJson();
  String msgUserUpdate4 = "The user with phone number 2098765432 was updated successfully.";

  //User Delete
  private final String userDeleteID1 = "3238765432";
  private final String msgDeleteUser1 = "The user with phone number 3238765432 was deleted Successfully.";
  private final String userDeleteID2Invalid = "7149876555";
  private final String msgDeleteUser2 = "The user with phone number 7149876555 was not found.";
  private final String userDeleteID3 = "9492345678";
  private final String msgDeleteUser3 = "The user with phone number 9492345678 was deleted Successfully.";


  //User Get
  private final String userGetID1 = "9163456789";
  private final String msgGetUser = "User fetched successfully.";
  private final UserAccount userGet1 = userUpdate1;
  private final String userGetID2Invalid = "9199456799";
  private final String msgGetUserGetID2 = "The user with phone number 9199456799 was not found.";
  private final String userGetID3 = "2098765432";
  private final UserAccount userGet3 = userUpdate4;
  private final String msgGetAllUser = "All users fetched successfully.";


  //User APi
  private final String createUser = "/api/users/create_user";
  private final String updateUser = "/api/users/update_user";
  private final String deleteUser = "/api/users/delete_user/";
  private final String getUser = "/api/users/get_user/";
  private final String getAllUsers = "api/users/get_all_users";


  String okSuccessCode = "OK";
  String notFoundCode = "NOT_FOUND";


  CheckResult testCreateApi(String api, String body,String message, String status) {
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

  CheckResult testUpdateApi(String api, String body,String message, String status) {
    HttpResponse response = put(api, body).send();

    //Check json in response
    if(!response.getJson().isJsonObject()) {
      return CheckResult.wrong("Wrong object in response, expected JSON but was \n" +
              response.getContent().getClass());
    }

    expect(response.getContent()).asJson().check(isObject()
            .value("message", message)
            .value("httpStatus", status).anyOtherValues()
    );


    return CheckResult.correct();
  }
  CheckResult testDeleteApi(String api, String ID,String message, String status) {
    HttpResponse response = delete(api + ID).send();

    //Check json in response
    if(!response.getJson().isJsonObject()) {
      return CheckResult.wrong("Wrong object in response, expected JSON but was \n" +
              response.getContent().getClass());
    }

    expect(response.getContent()).asJson().check(isObject()
            .value("message", message)
            .value("httpStatus", status).anyOtherValues()
    );

    return CheckResult.correct();
  }
  CheckResult testGetApi(String api, String ID, String message, String status, Object object, boolean found) {
    HttpResponse response = get(api + ID).send();

    //Check json in response
    if(!response.getJson().isJsonObject()) {
      return CheckResult.wrong("Wrong object in response, expected JSON but was \n" +
              response.getContent().getClass());
    }

    if(found) {
      if (object instanceof Drone) {
        expect(response.getContent()).asJson().check(isObject()
                .value("message", message)
                .value("httpStatus", status)
                .value("data", isObject()
                        .value("serialNumber", ((Drone) object).getSerialNumber())
                        .value("currentWeight", ((Drone) object).getCurrentWeight())
                        .value("maxWeight", ((Drone) object).maxWeight)
                        .value("batteryCapacity", ((Drone) object).getBatteryCapacity())
                        .value("model", ((Drone) object).getModel().toString())
                        .value("state", ((Drone) object).getState().toString())
                        .value("currentCoordinate", ((Drone) object).getCurrentCoordinate())).anyOtherValues()
        );
      } else if (object instanceof Medication) {
        expect(response.getContent()).asJson().check(isObject()
                .value("message", message)
                .value("httpStatus", status)
                .value("data", isObject()
                        .value("code", ((Medication) object).getCode())
                        .value("name", ((Medication) object).getName())
                        .value("weight", ((Medication) object).getWeight())
                        .value("imageURL", ((Medication) object).getImageURL())).anyOtherValues()
        );
      }
      else if (object instanceof UserAccount) {
        expect(response.getContent()).asJson().check(isObject()
                .value("message", message)
                .value("httpStatus", status)
                .value("data", isObject()
                        .value("name", ((UserAccount) object).getName())
                        .value("phoneNumber", ((UserAccount) object).getPhoneNumber())
                        .value("address", ((UserAccount) object).getAddress())
                        .value("gpsCoordinate", ((UserAccount) object).getGpsCoordinate())
                        .value("account", ((UserAccount) object).getAccount().toString())).anyOtherValues()
        );
      }
    }
    else {
      expect(response.getContent()).asJson().check(isObject()
              .value("message", message)
              .value("httpStatus", status).anyOtherValues()
      );

    }


    return CheckResult.correct();
  }
  CheckResult testGetAllApi(String api, String message, String status) {
    HttpResponse response = get(api).send();

    //Check json in response
    if(!response.getJson().isJsonObject()) {
      return CheckResult.wrong("Wrong object in response, expected JSON but was \n" +
              response.getContent().getClass());
    }

      expect(response.getContent()).asJson().check(isObject()
              .value("message", message)
              .value("httpStatus", status)
              .value("data", isArray(isObject().anyOtherValues())).anyOtherValues()
      );


    return CheckResult.correct();
  }


  @DynamicTest
  DynamicTesting[] dt = new DynamicTesting[]{
          () -> testCreateApi(createDrone,drone1Correct, msgDrone1,okSuccessCode ),
          () -> testCreateApi(createDrone,drone2Correct, msgDrone2,okSuccessCode ),
          () -> testCreateApi(createDrone,drone3Invalid, invalidDroneMsg,okSuccessCode ),
          () -> testCreateApi(createDrone,drone4Correct, msgDrone4,okSuccessCode ),
          () -> testCreateApi(createDrone,drone5Correct, msgDrone5,okSuccessCode ),
          () -> testCreateApi(createDrone,drone6Correct, msgDrone6,okSuccessCode ),

          () -> testUpdateApi(updateDrone,droneUpdate1Correct, msgDroneUpdate1,okSuccessCode ),
          () -> testUpdateApi(updateDrone,droneUpdate2Invalid, msgDroneUpdate2, notFoundCode ),
          () -> testUpdateApi(updateDrone,droneUpdate4Correct, msgDroneUpdate4,okSuccessCode ),

          () -> testDeleteApi(deleteDrone,droneDeleteID1, msgDeleteDrone1,okSuccessCode ),
          () -> testDeleteApi(deleteDrone,droneDeleteID2Invalid, msgDeleteDrone2,notFoundCode ),
          () -> testDeleteApi(deleteDrone,droneDeleteID3, msgDeleteDrone3,okSuccessCode),

          () -> testGetApi(getDrone,droneGetID1, msgGetDrone,okSuccessCode, droneGet1, true ),
          () -> testGetApi(getDrone,droneGetID2Invalid, msgGetDroneGetID2,notFoundCode, null, false ),
          () -> testGetApi(getDrone,droneGetID3, msgGetDrone,okSuccessCode, droneGet3, true),
          () -> testGetAllApi(getAllDrone, msgGetAllDrones, okSuccessCode),


          () -> testCreateApi(createMedication,medication1Correct, msgMedication1,okSuccessCode ),
          () -> testCreateApi(createMedication,medication2Correct, msgMedication2,okSuccessCode ),
          () -> testCreateApi(createMedication,medication3Invalid, invalidMedicationMsg,okSuccessCode ),
          () -> testCreateApi(createMedication,medication4Correct, msgMedication4,okSuccessCode ),
          () -> testCreateApi(createMedication,medication5Correct, msgMedication5,okSuccessCode ),
          () -> testCreateApi(createMedication,medication6Correct, msgMedication6,okSuccessCode ),

          () -> testUpdateApi(updateMedication,medicationUpdate1Correct, msgMedicationUpdate1,okSuccessCode ),
          () -> testUpdateApi(updateMedication,medicationUpdate2Invalid, msgMedicationUpdate2, notFoundCode ),
          () -> testUpdateApi(updateMedication,medicationUpdate4Correct, msgMedicationUpdate4,okSuccessCode ),

          () -> testDeleteApi(deleteMedication,medicationDeleteID1, msgDeleteMedication1,okSuccessCode ),
          () -> testDeleteApi(deleteMedication,medicationDeleteID2Invalid, msgDeleteMedication2,notFoundCode ),
          () -> testDeleteApi(deleteMedication,medicationDeleteID3, msgDeleteMedication3,okSuccessCode),

          () -> testGetApi(getMedication,medicationGetID1, msgGetMedication,okSuccessCode, medicationGet1, true ),
          () -> testGetApi(getMedication,medicationGetID2Invalid, msgGetMedicationGetID2,notFoundCode, null, false ),
          () -> testGetApi(getMedication,medicationGetID3, msgGetMedication,okSuccessCode, medicationGet3, true),
          () -> testGetAllApi(getAllMedication, msgGetAllMedication, okSuccessCode),


          () -> testCreateApi(createUser,user1Correct, msgUser1,okSuccessCode ),
          () -> testCreateApi(createUser,user2Correct, msgUser2,okSuccessCode ),
          () -> testCreateApi(createUser,medication3Invalid, invalidUserMsg,okSuccessCode ),
          () -> testCreateApi(createUser,user4Correct, msgUser4,okSuccessCode ),
          () -> testCreateApi(createUser,user5Correct, msgUser5,okSuccessCode ),
          () -> testCreateApi(createUser,user6Correct, msgUser6,okSuccessCode ),

          () -> testUpdateApi(updateUser,userUpdate1Correct, msgUserUpdate1,okSuccessCode ),
          () -> testUpdateApi(updateUser,userUpdate2Invalid, msgUserUpdate2, notFoundCode ),
          () -> testUpdateApi(updateUser,userUpdate4Correct, msgUserUpdate4,okSuccessCode ),

          () -> testDeleteApi(deleteUser,userDeleteID1, msgDeleteUser1,okSuccessCode ),
          () -> testDeleteApi(deleteUser,userDeleteID2Invalid, msgDeleteUser2,notFoundCode ),
          () -> testDeleteApi(deleteUser,userDeleteID3, msgDeleteUser3,okSuccessCode),

          () -> testGetApi(getUser,userGetID1, msgGetUser,okSuccessCode, userGet1, true ),
          () -> testGetApi(getUser,userGetID2Invalid, msgGetUserGetID2,notFoundCode, null, false ),
          () -> testGetApi(getUser,userGetID3, msgGetUser,okSuccessCode, userGet3, true),

          () -> testGetAllApi(getAllUsers, msgGetAllUser, okSuccessCode)


  };

  private CheckResult reloadServer() {
    try {
      reloadSpring();
    } catch (Exception ex) {
      throw new UnexpectedError(ex.getMessage());
    }
    return CheckResult.correct();
  }

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

