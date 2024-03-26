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
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hyperskill.hstest.testing.expect.Expectation.expect;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.*;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.isInteger;

public class DroneMedicationAPIApplicationTest extends SpringTest {
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

    //Drone update
    private  final Drone droneUpdate1 = new Drone("S001", 100, 80, 10000, Drone.State.DELIVERING, Drone.Model.LIGHTWEIGHT, coordinatesString);
    private  final String droneUpdate1Correct = droneUpdate1.toJson();
    String msgDroneUpdate1 = "The drone with serial number S001 was updated successfully.";

    //Drone Delete
    private final String droneDeleteID1 = "S001";
    private final String msgDeleteDrone1 = "The drone with serial number S001 was deleted Successfully.";

    //Drone Get
    private final String droneGetID1 = "S001";
    private final String msgGetDrone = "Drone fetched successfully.";
    private final Drone droneGet1 = droneUpdate1;

    private final String msgGetAllDrones = "All drones fetched successfully.";

    //Drone APi
    private final String createDrone = "/api/drones/create_drone";
    private final String updateDrone = "/api/drones/update_drone";
    private final String deleteDrone = "/api/drones/delete_drone/";
    private final String getDrone = "/api/drones/get_drone/";
    private final String getAllDrone = "api/drones/get_all_drones";
    private final String getDroneByState = "/api/drones/get_drones_by_state/";
    private final String getDroneByModel = "/api/drones/get_drones_by_model/";
    private final String getDroneByCharge = "/api/drones/get_drones_by_charge/";




    //Medication
    private  final Medication medication1 = new Medication("M001", "Paracetamol", 30, "https://th.bing.com/th/id/OIP.CjEDMQk7TEODMKh4MZFfGgD6D6?w=166&h=180&c=7&r=0&o=5&pid=1.7");
    private  final String medication1Correct = medication1.toJson();
    String msgMedication1 = "The medication with code M001 was created successfully.";

    private  final Medication medication2 = new Medication("M002", "Paracetamol", 38, "https://th.bing.com/th/id/OIP.CjEDMQk7TEODMKh4MZFfGgD6D6?w=166&h=180&c=7&r=0&o=5&pid=1.7");
    private  final String medication2Correct = medication2.toJson();
    String msgMedication2 = "The medication with code M002 was created successfully.";
    private  final Medication medication3 = new Medication("M003","Amoxicillin", 65, "https://th.bing.com/th/id/R.86be7f194443ce59c5e21c0e78302e50?rik=5XJ7UyNSYyE6Yw&pid=ImgRaw&r=0");
    private  final String medication3Correct = medication3.toJson();
    String msgMedication3 = "The medication with code M003 was created successfully.";
    private  final Medication medication4 = new Medication("M004", "Ibuprofen", 48, "https://th.bing.com/th/id/OIP.Nzkzq0Ic2me02XDdxi2DxQHaE8?rs=1&pid=ImgDetMain");
    private  final String medication4Correct = medication4.toJson();
    String msgMedication4 = "The medication with code M004 was created successfully.";
    private  final Medication medication5 = new Medication("M005", "Metformin", 80,"https://th.bing.com/th/id/OIP.t1kStl77O7UxOQq0KhCa8AHaD5?rs=1&pid=ImgDetMain");
    private  final String medication5Correct = medication5.toJson();
    String msgMedication5 = "The medication with code M005 was created successfully.";

    //Medication update
    private  final Medication medicationUpdate1 = new  Medication("M002", "Paracetamol", 78, "https://www.drugs.com/images/pills/fio/GMK03970.JPG");
    private  final String medicationUpdate1Correct = medicationUpdate1.toJson();
    String msgMedicationUpdate1 = "The medication with code M002 was updated successfully.";

    private  final Medication medicationUpdate2 = new Medication("M019", "Atorvastatin", 25, "https://www.drugs.com/images/pills/fio/GMK03970.JPG");
    private  final String medicationUpdate2Invalid = medicationUpdate2.toJson();
    String msgMedicationUpdate2 = "The medication with code M019 was not found.";

    //Medication Delete
    private final String medicationDeleteID1 = "M001";
    private final String msgDeleteMedication1 = "The medication with code M001 was deleted Successfully.";
    private final String medicationDeleteID2Invalid = "M021";
    private final String msgDeleteMedication2 = "The medication with code M021 was not found.";

    //Medication Get
    private final String medicationGetID1 = "M002";
    private final String msgGetMedication = "Medication fetched successfully.";
    private final Medication medicationGet1 = medicationUpdate1;
    private final String medicationGetID2Invalid = "M041";
    private final String msgGetMedicationGetID2 = "The medication with code M041 was not found.";

    private final String msgGetAllMedication = "All medications fetched successfully.";

    //Medication APi
    private final String createMedication = "/api/medications/create_medication";
    private final String updateMedication = "/api/medications/update_medication";
    private final String deleteMedication = "/api/medications/delete_medication/";
    private final String getMedication = "/api/medications/get_medication/";
    private final String getAllMedication = "api/medications/get_all_medications";
    private final String getMedicationByName = "/api/medications/get_by_name/";



    //User create
    private  final UserAccount user1 = new UserAccount(UserAccount.Account.CUSTOMER, "Michael Martinez", "9163456789", "101 Walnut Avenue, San Diego, California(CA), 92101", "32.71574, -117.16109");
    private  final String user1Correct = user1.toJson();
    String msgUser1 = "The user with phone number 9163456789 was created successfully.";

    private  final UserAccount user2 = new UserAccount (UserAccount.Account.CUSTOMER, "Daniel Taylor", "3238765432", "303 Elm Avenue, Oakland, California(CA), 94601", "37.80493, -122.27080");
    private  final String user2Correct = user2.toJson();
    String msgUser2 = "The user with phone number 3238765432 was created successfully.";


    //User update
    private  final UserAccount userUpdate1 = new UserAccount(UserAccount.Account.GUEST, "Michael Martinez", "9163456789", "202 Birch Street, San Jose, California(CA), 95110", "37.33821, -121.88633");
    private  final String userUpdate1Correct = userUpdate1.toJson();
    String msgUserUpdate1 = "The user with phone number 9163456789 was updated successfully.";

    private  final UserAccount userUpdate2 = new UserAccount (UserAccount.Account.ADMIN, "Daniel Taylor", "3238765492", "303 Elm Avenue, Oakland, California(CA), 94601", "37.80493, -122.27080");
    private  final String userUpdate2Invalid = userUpdate2.toJson();
    String msgUserUpdate2 = "The user with phone number 3238765492 was not found.";

    //User Get
    private final String userGetID1 = "9163456789";
    private final String msgGetUser = "User fetched successfully.";

    private final UserAccount userGet1 = user1;

    private final UserAccount userGetUpdate1 = userUpdate1;
    private final String userGetID2Invalid = "9199456799";
    private final String msgGetUserGetID2 = "The user with phone number 9199456799 was not found.";

    private final String msgGetAllUser = "All users fetched successfully.";

    //User Delete
    private final String userDeleteID1 = "3238765432";
    private final String msgDeleteUser1 = "The user with phone number 3238765432 was not found.";
    private final String userDeleteID2Invalid = "7149876555";
    private final String msgDeleteUser2 = "The user with phone number 7149876555 was not found.";

    //User APi
    private final String createUser = "/api/users/create_user";
    private final String updateUser = "/api/users/update_user";
    private final String deleteUser = "/api/users/delete_user/";
    private final String getUser = "/api/users/get_user/";
    private final String getAllUsers = "api/users/get_all_users";


    //dispatch drone
    private ArrayList<Medication> medicationsA;
    private ArrayList<Medication> medicationsB;
    //dispatch drone
    private  DroneDispatch droneDispatch1;
    private  String droneDispatch1Correct;
    private  DroneDispatch droneDispatch2;
    private  String droneDispatch2Correct;

    private final String msgDispatchCorrect = "Drone dispatched successfully.";
    private final String msgGetDroneDispatch = "The drone Dispatched fetched successfully.";
    private final String msgGetAllDroneDispatch = "All Drone Dispatched fetched successfully.";

    //Registration
    private final AppUser appUserAdmin= new AppUser("administrator","password1","ROLE_ADMIN");
    private final String appUserAdminJson = appUserAdmin.toJson();
    private final AppUser appUserCustomer = new AppUser("customer","password2","ROLE_USER");
    private final String appUserCustomerJson = appUserCustomer.toJson();

    private final String msgAppUserSuccess = "The user registration was successful.";


    //Drone Dispatch API
    private  final String dispatchDrone = "/api/dispatch/dispatch_drone";
    private  final String getDispatchDrone = "/api/dispatch/get_dispatch/";
    private  final String getAllDispatchDrone = "/api/dispatch/get_all_dispatch";
    private final String registerNewUser = "/api/register_user";

    String okSuccessCode = "OK";
    String notFoundCode = "NOT_FOUND";
    String forbidden = "403";

    private final String StateSearch = "State";
    private final String SearchEmpty = "SearchEmpty";
    private final String ModelSearch = "Model";
    private final String ChargeSearch = "Charge";
    private final String NameSearch = "Name";

    CheckResult testRegistrationApi(String api, String body, String message, String status) {
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

    CheckResult testCreateApi(String api, String body, String message, String status, boolean authenticated, String login, String pass, boolean authorized) {
        HttpResponse response;
        if(authenticated && login.length() > 0 && pass.length() > 0) {
             response = post(api, body).basicAuth(login,pass).send();
        }
        else {
             response = post(api, body).send();
        }

        //Check json in response
        if(!response.getJson().isJsonObject()) {
            return CheckResult.wrong("Wrong object in response, expected JSON but was \n" +
                    response.getContent().getClass());
        }

        if(!authorized) {
            if(response.getStatusCode() == 403) return CheckResult.correct();
            else {
                return CheckResult.wrong("The user should not be able to access api " + api + " un-authorized\n"
                        + "POST " + api + " should respond with "
                        + "status code " + "403" + ", responded: " + response.getStatusCode() + "\n"
                        + message + "\n"
                        + "Response body:\n" + response.getContent() + "\n"
                        + "Request body:\n" + body
                );
            }

        }


        if(authenticated) {
            expect(response.getContent()).asJson().check(isObject()
                    .value("message", message)
                    .value("httpStatus", status)
            );
        }
        else {
            if( response.getStatusCode() != 401) {
                return CheckResult.wrong("The user should not be able to access api " + api + " un-authenticated\n"
                                + "POST " + api + " should respond with "
                                + "status code " + "40`" + ", responded: " + response.getStatusCode() + "\n"
                                + message + "\n"
                                + "Response body:\n" + response.getContent() + "\n"
                                + "Request body:\n" + body
                        );
            }
        }

        return CheckResult.correct();
    }
    CheckResult testUpdateApi(String api, String body,String message, String status,boolean authenticated, String login, String pass,boolean authorized) {
        HttpResponse response;
        if(authenticated && login.length() > 0 && pass.length() > 0) {
            response = put(api, body).basicAuth(login,pass).send();
        }
        else {
            response = put(api, body).send();
        }

        if(!authorized) {
            if(response.getStatusCode() == 403) return CheckResult.correct();
            else {
                return CheckResult.wrong("The user should not be able to access api " + api + " un-authorized\n"
                        + "PUT " + api + " should respond with "
                        + "status code " + "403" + ", responded: " + response.getStatusCode() + "\n"
                        + message + "\n"
                        + "Response body:\n" + response.getContent() + "\n"
                        + "Request body:\n" + body
                );
            }

        }

        //Check json in response
        if(!response.getJson().isJsonObject()) {
            return CheckResult.wrong("Wrong object in response, expected JSON but was \n" +
                    response.getContent().getClass());
        }

        if(authenticated) {
            expect(response.getContent()).asJson().check(isObject()
                    .value("message", message)
                    .value("httpStatus", status).anyOtherValues()
            );
        }
        else {
            if(response.getStatusCode() != 401) {
                return CheckResult.wrong("The user should not be able to access api " + api + " un-authenticated\n"
                        + "PUT " + api + " should respond with "
                        + "status code " + "401" + ", responded: " + response.getStatusCode() + "\n"
                        + message + "\n"
                        + "Response body:\n" + response.getContent() + "\n"
                        + "Request body:\n" + body
                );
            }
        }


        return CheckResult.correct();
    }
    CheckResult testDeleteApi(String api, String ID,String message, String status, boolean authenticated, String login, String pass, boolean authorized) {
        HttpResponse response;
        if(authenticated && login.length() > 0 && pass.length() > 0) {
            response = delete(api + ID).basicAuth(login,pass).send();
        }
        else {
            response = delete(api + ID).send();
        }

        //Check json in response
        if(!response.getJson().isJsonObject()) {
            return CheckResult.wrong("Wrong object in response, expected JSON but was \n" +
                    response.getContent().getClass());
        }

        if(!authorized) {
            if(response.getStatusCode() == 403) return CheckResult.correct();
            else {
                return CheckResult.wrong("The user should not be able to access api " + api + " un-authorized\n"
                        + "DELETE " + api + " should respond with "
                        + "status code " + "403" + ", responded: " + response.getStatusCode() + "\n"
                        + message + "\n"
                        + "Response body:\n" + response.getContent() + "\n"
                );
            }

        }


        if(authenticated) {
            expect(response.getContent()).asJson().check(isObject()
                    .value("message", message)
                    .value("httpStatus", status).anyOtherValues()
            );
        }
        else {
            if(response.getStatusCode() != 401) {
                return CheckResult.wrong("The user should not be able to access api " + api + " un-authenticated\n"
                        + "DELETE " + api + " should respond with "
                        + "status code " + "401" + ", responded: " + response.getStatusCode() + "\n"
                        + message + "\n"
                        + "Response body:\n" + response.getContent() + "\n"
                );
            }
        }



        return CheckResult.correct();
    }
    CheckResult testGetApi(String api, String ID, String message, String status, Object object, boolean found, boolean authenticated, String login, String pass, boolean authorized) {
        HttpResponse response;
        if(authenticated && login.length() > 0 && pass.length() > 0) {
            response = get(api + ID).basicAuth(login,pass).send();
        }
        else {
            response = get(api + ID).send();
        }

        //Check json in response
        if(!response.getJson().isJsonObject()) {
            return CheckResult.wrong("Wrong object in response, expected JSON but was \n" +
                    response.getContent().getClass());
        }

        if(!authorized) {
            if(response.getStatusCode() == 403) return CheckResult.correct();
            else {
                return CheckResult.wrong("The user should not be able to access api " + api + " un-authorized\n"
                        + "GET " + api + " should respond with "
                        + "status code " + "403" + ", responded: " + response.getStatusCode() + "\n"
                        + message + "\n"
                        + "Response body:\n" + response.getContent() + "\n"
                );
            }

        }


        if(authenticated) {
            if (found) {
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
                } else if (object instanceof UserAccount) {
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
            } else {
                expect(response.getContent()).asJson().check(isObject()
                        .value("message", message)
                        .value("httpStatus", status).anyOtherValues()
                );

            }
        }
        else {
            if(response.getStatusCode() != 401) {
                return CheckResult.wrong("The user should not be able to access api " + api + " un-authenticated\n"
                        + "GET " + api + " should respond with "
                        + "status code " + "401" + ", responded: " + response.getStatusCode() + "\n"
                        + message + "\n"
                        + "Response body:\n" + response.getContent() + "\n"
                );
            }
        }


        return CheckResult.correct();
    }
    CheckResult testGetAllApi(String api, String message, String status, boolean authenticated, String login, String pass, boolean authorized) {
        HttpResponse response;
        if(authenticated && login.length() > 0 && pass.length() > 0) {
            response = get(api).basicAuth(login,pass).send();
        }
        else {
            response = get(api).send();
        }

        //Check json in response
        if(!response.getJson().isJsonObject()) {
            return CheckResult.wrong("Wrong object in response, expected JSON but was \n" +
                    response.getContent().getClass());
        }

        if(!authorized) {
            if(response.getStatusCode() == 403) return CheckResult.correct();
            else {
                return CheckResult.wrong("The user should not be able to access api " + api + " un-authorized\n"
                        + "GET " + api + " should respond with "
                        + "status code " + "403" + ", responded: " + response.getStatusCode() + "\n"
                        + message + "\n"
                        + "Response body:\n" + response.getContent() + "\n"
                );
            }

        }

        if(authenticated) {
            expect(response.getContent()).asJson().check(isObject()
                    .value("message", message)
                    .value("httpStatus", status)
                    .value("data", isArray(isObject().anyOtherValues())).anyOtherValues()
            );
        }
        else {
            if(response.getStatusCode() != 401) {
                return CheckResult.wrong("The user should not be able to access api " + api + " un-authenticated\n"
                        + "GET " + api + " should respond with "
                        + "status code " + "401" + ", responded: " + response.getStatusCode() + "\n"
                        + message + "\n"
                        + "Response body:\n" + response.getContent() + "\n"
                );
            }

        }


        return CheckResult.correct();
    }
    CheckResult testDroneSearchApi(String api,String search,String type, String message, String status, boolean authenticated, String login, String pass,boolean authorized)  {
        HttpResponse response;
        if(authenticated && login.length() > 0 && pass.length() > 0) {
            response = get(api + search).basicAuth(login,pass).send();
        }
        else {
            response = get(api + search).send();
        }

        //Check json in response
        if (!response.getJson().isJsonObject()) {
            return CheckResult.wrong("Wrong object in response, expected JSON but was \n" +
                    response.getContent().getClass());
        }

        if(!authorized) {
            if(response.getStatusCode() == 403) return CheckResult.correct();
            else {
                return CheckResult.wrong("The user should not be able to access api " + api + " un-authorized\n"
                        + "GET " + api + " should respond with "
                        + "status code " + "403" + ", responded: " + response.getStatusCode() + "\n"
                        + message + "\n"
                        + "Response body:\n" + response.getContent() + "\n"
                );
            }

        }


        if(authenticated) {
            if (type == StateSearch) {
                expect(response.getContent()).asJson().check(isObject()
                        .value("message", message)
                        .value("httpStatus", status).anyOtherValues());
            } else if (type == SearchEmpty) {
                expect(response.getContent()).asJson().check(isObject()
                        .value("message", message).anyOtherValues());
            } else if (type == ModelSearch) {
                expect(response.getContent()).asJson().check(isObject()
                        .value("message", message)
                        .value("httpStatus", status).anyOtherValues());
            } else if (type == ChargeSearch) {
                int charge = Integer.parseInt(search);
                expect(response.getContent()).asJson().check(isObject()
                        .value("message", message)
                        .value("httpStatus", status).anyOtherValues());
            } else if (type == NameSearch) {
                expect(response.getContent()).asJson().check(isObject()
                        .value("message", message)
                        .value("httpStatus", status)
                        .value("data", isArray()
                                .item(isObject()
                                        .value("name", "Paracetamol").anyOtherValues()
                                )
                        ));
            }
        }
        else {
            if(response.getStatusCode() != 401) {
                return CheckResult.wrong("The user should not be able to access api " + api + " un-authenticated\n"
                        + "GET " + api + " should respond with "
                        + "status code " + "401" + ", responded: " + response.getStatusCode() + "\n"
                        + message + "\n"
                        + "Response body:\n" + response.getContent() + "\n"
                );
            }

        }

        return CheckResult.correct();

    }
    CheckResult testGetDispatchApi(String api, String ID, String message, String status,  boolean found, boolean authenticated, String login, String pass, boolean authorized) {
        HttpResponse response;
        if(authenticated && login.length() > 0 && pass.length() > 0) {
            response = get(api + ID).basicAuth(login,pass).send();
        }
        else {
            response = get(api + ID).send();
        }

        //Check json in response
        if(!response.getJson().isJsonObject()) {
            return CheckResult.wrong("Wrong object in response, expected JSON but was \n" +
                    response.getContent().getClass());
        }

        if(!authorized) {
            if(response.getStatusCode() == 403) return CheckResult.correct();
            else {
                return CheckResult.wrong("The user should not be able to access api " + api + " un-authorized\n"
                        + "GET " + api + " should respond with "
                        + "status code " + "403" + ", responded: " + response.getStatusCode() + "\n"
                        + message + "\n"
                        + "Response body:\n" + response.getContent()
                );
            }

        }


        if(authenticated) {
            if (found) {
                expect(response.getContent()).asJson().check(isObject()
                        .value("message", message)
                        .value("httpStatus", status).anyOtherValues()
                        );
            } else {
                expect(response.getContent()).asJson().check(isObject()
                        .value("message", message)
                        .value("httpStatus", status).anyOtherValues()
                );

            }
        }
        else {
            if(response.getStatusCode() != 401) {
                return CheckResult.wrong("The user should not be able to access api " + api + " un-authenticated\n"
                        + "GET " + api + " should respond with "
                        + "status code " + "401" + ", responded: " + response.getStatusCode() + "\n"
                        + message + "\n"
                        + "Response body:\n" + response.getContent() + "\n"
                );
            }

        }

        return CheckResult.correct();
    }
    CheckResult testSetUps() {
        medicationsA = new ArrayList<>();
        //Set up medication list for dispatch
        medicationsA.add(new Medication("M001", "Paracetamol", 30, "https://th.bing.com/th/id/OIP.CjEDMQk7TEODMKh4MZFfGgD6D6?w=166&h=180&c=7&r=0&o=5&pid=1.7"));
        medicationsA.add(new Medication("M002", "omeprazole", 18, "https://www.drugs.com/images/pills/fio/GMK03970.JPG"));

        medicationsB = new ArrayList<>();
        medicationsB.add(new Medication("M004", "Ibuprofen", 28, "https://th.bing.com/th/id/OIP.Nzkzq0Ic2me02XDdxi2DxQHaE8?rs=1&pid=ImgDetMain"));
        medicationsB.add(new Medication("M005", "Metformin", 80,"https://th.bing.com/th/id/OIP.t1kStl77O7UxOQq0KhCa8AHaD5?rs=1&pid=ImgDetMain"));


        droneDispatch1 = new DroneDispatch(drone1, user1,medicationsA, false,LocalTime.of(0,0,30));
        droneDispatch1Correct = droneDispatch1.toJson();

        droneDispatch2 = new DroneDispatch(drone2, user2,medicationsB, false,LocalTime.of(0,1,00));
        droneDispatch2Correct = droneDispatch2.toJson();

        return CheckResult.correct();
    }
    CheckResult testGetAllDispatchApi(String api, String message, String status, boolean authenticated, String login, String pass, boolean authorized) {
        HttpResponse response;
        if(authenticated && login.length() > 0 && pass.length() > 0) {
            response = get(api).basicAuth(login,pass).send();
        }
        else {
            response = get(api).send();
        }

        //Check json in response
        if(!response.getJson().isJsonObject()) {
            return CheckResult.wrong("Wrong object in response, expected JSON but was \n" +
                    response.getContent().getClass());
        }

        if(!authorized) {
            if(response.getStatusCode() == 403) return CheckResult.correct();
            else {
                return CheckResult.wrong("The user should not be able to access api " + api + " un-authorized\n"
                        + "GET " + api + " should respond with "
                        + "status code " + "403" + ", responded: " + response.getStatusCode() + "\n"
                        + message + "\n"
                        + "Response body:\n" + response.getContent()
                );
            }

        }

        if(authenticated) {
            expect(response.getContent()).asJson().check(isObject()
                    .value("message", message)
                    .value("httpStatus", status).anyOtherValues());
        }
        else {
            if(response.getStatusCode() != 401) {
                return CheckResult.wrong("The user should not be able to access api " + api + " un-authenticated\n"
                        + "GET " + api + " should respond with "
                        + "status code " + "401" + ", responded: " + response.getStatusCode() + "\n"
                        + message + "\n"
                        + "Response body:\n" + response.getContent() + "\n"
                );
            }

        }

        return CheckResult.correct();
    }

    @DynamicTest
    DynamicTesting[] dt = new DynamicTesting[]{
            //Register users
            () -> testRegistrationApi(registerNewUser,appUserAdminJson,msgAppUserSuccess,okSuccessCode),
            () -> testRegistrationApi(registerNewUser,appUserCustomerJson,msgAppUserSuccess,okSuccessCode),

           //Authorized Admin check drone
            () -> testCreateApi(createDrone,drone1Correct, msgDrone1,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testUpdateApi(updateDrone,droneUpdate1Correct, msgDroneUpdate1,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testGetApi(getDrone,droneGetID1, msgGetDrone,okSuccessCode, droneGet1, true , true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testDeleteApi(deleteDrone,droneDeleteID1, msgDeleteDrone1,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testDroneSearchApi(getDroneByState,"DELIVERING",StateSearch,"The drones list by state DELIVERING is empty.",okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testDroneSearchApi(getDroneByModel,"MIDDLEWEIGHT",ModelSearch,"The drones list by model MIDDLEWEIGHT is empty.",okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testDroneSearchApi(getDroneByCharge,"15000",ChargeSearch,"The drones list by percentage 15000 is empty.",okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),

            //Authorized Admin check user
            () -> testCreateApi(createUser,user1Correct, msgUser1,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testUpdateApi(updateUser,userUpdate1Correct, msgUserUpdate1,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testGetApi(getUser,userGetID1, msgGetUser,okSuccessCode, userGetUpdate1, true, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testGetAllApi(getAllUsers, msgGetAllUser, okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testDeleteApi(deleteUser,userDeleteID1, msgDeleteUser1,notFoundCode, true,appUserAdmin.username(),appUserAdmin.password(), true),

            //Authorized Admin check medication
            () -> testCreateApi(createMedication,medication1Correct, msgMedication1,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testCreateApi(createMedication,medication2Correct, msgMedication2,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testUpdateApi(updateMedication,medicationUpdate1Correct, msgMedicationUpdate1,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testDeleteApi(deleteMedication,medicationDeleteID1, msgDeleteMedication1,okSuccessCode ,true,appUserAdmin.username(),appUserAdmin.password(), true),

            //Open to all
            () -> testGetApi(getMedication,medicationGetID1, msgGetMedication,okSuccessCode, medicationUpdate1Correct, true, true,"","", true), //read
            () -> testGetAllApi(getAllMedication, msgGetAllMedication, okSuccessCode, true,"","", true),
            () -> testDroneSearchApi(getMedicationByName,"Paracetamol",NameSearch,"Medications fetched successfully.",okSuccessCode, true,"","", true),

            //Authorized Admin check dispatch
            () -> testSetUps(),
            () -> testCreateApi(createMedication,medication1Correct, msgMedication1,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testCreateApi(createMedication,medication2Correct, msgMedication2,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testCreateApi(createMedication,medication4Correct, msgMedication4,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testCreateApi(createMedication,medication5Correct, msgMedication5,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testCreateApi(createUser,user1Correct, msgUser1,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testCreateApi(createUser,user2Correct, msgUser2,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testCreateApi(createDrone,drone1Correct, msgDrone1,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testCreateApi(createDrone,drone2Correct, msgDrone2,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            //this should not work for authorized users
            () -> testCreateApi(dispatchDrone,droneDispatch1Correct, msgDispatchCorrect,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testCreateApi(dispatchDrone,droneDispatch2Correct, msgDispatchCorrect,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true ),
            () -> testGetAllDispatchApi(getAllDispatchDrone,msgGetAllDroneDispatch,okSuccessCode, true,appUserAdmin.username(),appUserAdmin.password(), true),
            () -> testGetDispatchApi(getDispatchDrone,"3",msgGetDroneDispatch,okSuccessCode,true, true,appUserAdmin.username(),appUserAdmin.password(), true),





            //non authorized Customer check drone
            () -> testCreateApi(createDrone,drone1Correct, msgDrone1,okSuccessCode, true,appUserCustomer.username(),appUserCustomer.password(), false),
            () -> testUpdateApi(updateDrone,droneUpdate1Correct, msgDroneUpdate1,okSuccessCode, true,appUserCustomer.username(),appUserCustomer.password(), false),
            () -> testGetApi(getDrone,droneGetID1, msgGetDrone,okSuccessCode, droneGet1, true , true,appUserCustomer.username(),appUserCustomer.password(), false),
            () -> testDeleteApi(deleteDrone,droneDeleteID1, msgDeleteDrone1,okSuccessCode, true,appUserCustomer.username(),appUserCustomer.password(), false),
            () -> testDroneSearchApi(getDroneByState,"DELIVERING",StateSearch,"The drones list by state DELIVERING is empty.",okSuccessCode, true,appUserCustomer.username(),appUserCustomer.password(), false),
            () -> testDroneSearchApi(getDroneByModel,"MIDDLEWEIGHT",ModelSearch,"The drones list by model MIDDLEWEIGHT is empty.",okSuccessCode, true,appUserCustomer.username(),appUserCustomer.password(), false),
            () -> testDroneSearchApi(getDroneByCharge,"15000",ChargeSearch,"The drones list by percentage 15000 is empty.",okSuccessCode, true,appUserCustomer.username(),appUserCustomer.password(), false),

            //non authorized customer check user
            () -> testCreateApi(createUser,user1Correct, msgUser1,okSuccessCode, true,appUserCustomer.username(),appUserCustomer.password(), false),
            () -> testUpdateApi(updateUser,userUpdate1Correct, msgUserUpdate1,okSuccessCode, true,appUserCustomer.username(),appUserCustomer.password(), false),
            //authorized below
            () -> testGetApi(getUser,userGetID1, msgGetUser,okSuccessCode, userGet1, true, true,appUserCustomer.username(),appUserCustomer.password(), true),
            //non authorized customer check user
            () -> testGetAllApi(getAllUsers, msgGetAllUser, okSuccessCode, true,appUserCustomer.username(),appUserCustomer.password(), false),
            () -> testDeleteApi(deleteUser,userDeleteID1, msgDeleteUser1,notFoundCode, true,appUserCustomer.username(),appUserCustomer.password(), false),

            //non authorized customer check medication
            () -> testCreateApi(createMedication,medication1Correct, msgMedication1,okSuccessCode, true,appUserCustomer.username(),appUserCustomer.password(), false),
            () -> testCreateApi(createMedication,medication2Correct, msgMedication2,okSuccessCode, true,appUserCustomer.username(),appUserCustomer.password(), false),
            () -> testUpdateApi(updateMedication,medicationUpdate1Correct, msgMedicationUpdate1,okSuccessCode, true,appUserCustomer.username(),appUserCustomer.password(), false),
            () -> testDeleteApi(deleteMedication,medicationDeleteID1, msgDeleteMedication1,okSuccessCode ,true,appUserCustomer.username(),appUserCustomer.password(), false),


            //non authorized customer check dispatch
            () -> testSetUps(),
            //this should not work for authorized users
            () -> testCreateApi(dispatchDrone,droneDispatch1Correct, msgDispatchCorrect,okSuccessCode, true,appUserCustomer.username(),appUserCustomer.password(), false),
            () -> testGetAllDispatchApi(getAllDispatchDrone,msgGetAllDroneDispatch,okSuccessCode, true,appUserCustomer.username(),appUserCustomer.password(), false),
            //authorized below
            () -> testGetDispatchApi(getDispatchDrone,"3",msgGetDroneDispatch,okSuccessCode,true, true,appUserCustomer.username(),appUserCustomer.password(), true),







            //Non-authenticated
            () -> testCreateApi(createDrone,drone1Correct, msgDrone1,okSuccessCode, false,"","", true),
            () -> testUpdateApi(updateDrone,droneUpdate1Correct, msgDroneUpdate1,okSuccessCode, false,"","", true),
            () -> testGetApi(getDrone,droneGetID1, msgGetDrone,okSuccessCode, droneGet1, true , false,"","", true),
            () -> testDeleteApi(deleteDrone,droneDeleteID1, msgDeleteDrone1,okSuccessCode, false,"","", true),
            () -> testDroneSearchApi(getDroneByState,"DELIVERING",StateSearch,"The drones list by state DELIVERING is empty.",okSuccessCode, false,"","", true),
            () -> testDroneSearchApi(getDroneByModel,"MIDDLEWEIGHT",ModelSearch,"The drones list by model MIDDLEWEIGHT is empty.",okSuccessCode, false,"","", true),
            () -> testDroneSearchApi(getDroneByCharge,"15000",ChargeSearch,"The drones list by percentage 15000 is empty.",okSuccessCode, false,"","", true),

            //Non-authenticated
            () -> testCreateApi(createUser,user1Correct, msgUser1,okSuccessCode, false,"","", true),
            () -> testUpdateApi(updateUser,userUpdate1Correct, msgUserUpdate1,okSuccessCode, false,"","", true),
            () -> testGetApi(getUser,userGetID1, msgGetUser,okSuccessCode, userGet1, true, false,"","", true),
            () -> testGetAllApi(getAllUsers, msgGetAllUser, okSuccessCode, false,"","", true),
            () -> testDeleteApi(deleteUser,userDeleteID1, msgDeleteUser1,notFoundCode, false,"","", true),

            //Non-authenticated
            () -> testCreateApi(createMedication,medication1Correct, msgMedication1,okSuccessCode, false,"","", true),
            () -> testCreateApi(createMedication,medication2Correct, msgMedication2,okSuccessCode, false,"","", true),
            () -> testUpdateApi(updateMedication,medicationUpdate1Correct, msgMedicationUpdate1,okSuccessCode, false,"","", true),
            () -> testDeleteApi(deleteMedication,medicationDeleteID1, msgDeleteMedication1,okSuccessCode ,false,"","", true),


            //Non-authenticated
            () -> testSetUps(),
            () -> testCreateApi(dispatchDrone,droneDispatch1Correct, msgDispatchCorrect,okSuccessCode, false,"","", true),
            () -> testGetAllDispatchApi(getAllDispatchDrone,msgGetAllDroneDispatch,okSuccessCode, false,"","", true),
            () -> testGetDispatchApi(getDispatchDrone,"3",msgGetDroneDispatch,okSuccessCode,true, false,"","", true),


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

record AppUser(String username, String password, String authority ) {
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