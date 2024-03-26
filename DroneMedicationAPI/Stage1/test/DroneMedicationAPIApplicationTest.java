
import DroneMed.DroneMedicationAPIApplication;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;


public class DroneMedicationAPIApplicationTest  extends StageTest {


    public DroneMedicationAPIApplicationTest() {
        super(DroneMedicationAPIApplication.class);
    }

    private final String MAIN_MENU = "1. drones\n2. medications\n3. useraccounts\n4. exit";
    //Drone Menus
    private final String DRONE_MENU = "1. view drones\n2. create drone\n3. update drone\n4. delete drone\n5. get drone\n6. back";
    private final String DRONE_VIEW_MENU = "View drones\n1. Back";
    private final String DRONE_CREATE_MENU = "Create drones" + "\n" + "1. Back" + "\nEnter new drone information...." + "\n" + "Serial Number, Max Weight, CurrentWeight, Battery Capacity, Drone State, Drone Model, GPS Coordinates";
    private final String DRONE_UPDATE_MENU = "Update drones" + "\n" + "1. Back";
    private final String DRONE_DELETE_MENU = "Delete drones" + "\n" + "1. Back";
    private final String GET_DRONE_MENU = "Get drones" + "\n" + "1. Back";

    //Drone Create,Read, Update, Delete information
    private final String DRONE_INFO1 = "S001,100,0,1000,IDLE,LIGHTWEIGHT,4.815600°N 7.049800°E";
    private final String DRONE_INFO1_UPDATE = "S001,100,0,700,IDLE,LIGHTWEIGHT,4.815600°N 7.049800°E";
    private final String DRONE_INFO2_UPDATE_INVALID_ID = "S011,100,0,700,IDLE,LIGHTWEIGHT,4.815600°N 7.049800°E";
    private final String DRONE_RESPONSE2_UPDATE = "Drone with serial number S011 not found.";
    private final String DRONE_RESPONSE1 = "Drone with serial number S001 created successfully.";
    private final String DRONE_RESPONSE1_UPDATE = "Drone with serial number S001 updated successfully.";
    private final String DRONE_INFO2_INVALID =  "S002,200,0,15000,IDLE,MIDDLEWEIGHT";
    private final String DRONE_INFO3 = "S003,300,0,18000,IDLE,CRUISERWEIGHT,4.815600°N 7.049800°E,200,500";
    private final String DRONE_INFO4 = "S004,100,0,9000,IDLE,LIGHTWEIGHT, 4.815600°N 7.049800°E";
    private final String DRONE_RESPONSE4 = "Drone with serial number S004 created successfully.";
    private final String DRONE_INFO5 =  "S005,220,0,15000,IDLE,MIDDLEWEIGHT,4.815600°N 7.049800°E";
    private final String DRONE_RESPONSE5 = "Drone with serial number S005 created successfully.";
    private final String DRONE_INFO6 = "S006,330,0,18000,IDLE,CRUISERWEIGHT,4.815600°N 7.049800°E";
    private final String DRONE_RESPONSE6 = "Drone with serial number S006 created successfully.";
    private final String DELETE_DRONE_ID1 = "S006";
    private final String DELETE_DRONE_RESPONSE1 = "Drone with serial number S006 deleted Successfully.";
    private final String DELETE_DRONE_ID2 = "S005";
    private final String DELETE_DRONE_RESPONSE2 = "Drone with serial number S005 deleted Successfully.";
    private final String DELETE_DRONE_ID3_INVALID = "S016";
    private final String DELETE_DRONE_RESPONSE3 = "Drone with serial number s016 not found.";
    private final String GET_DRONE_ID1 = "S001";
    private final String GET_DRONE_RESPONSE1 = "Drone serialnumber: S001 maxWeight: 100 currentWeight: 0 batteryCapacity: 700 State: IDLE Model: LIGHTWEIGHT Coordinate: 4.815600°N 7.049800°E";
    private final String GET_DRONE_ID2 = "S004";
    private final String GET_DRONE_RESPONSE2 = "Drone serialnumber: S004 maxWeight: 100 currentWeight: 0 batteryCapacity: 9000 State: IDLE Model: LIGHTWEIGHT Coordinate: 4.815600°N 7.049800°E";
    private final String GET_DRONE_ID3_INVALID = "S020";
    private final String GET_DRONE_RESPONSE3 = "Drone with serial number s020 not found.";


    //Medication Menus
    private final String MEDICATION_MENU = "1. view medications\n2. create medication\n3. update medication\n4. delete medication\n5. get medication\n6. back";
    private final String MEDICATIONS_VIEW_MENU = "View medications\n1. Back";
    private final String MEDICATION_CREATE_MENU = "Create medications" + "\n" + "1. Back" + "\nEnter new medication information...." + "\n" + "medication code, name, weight, imageURL";
    private final String MEDICATION_UPDATE_MENU = "Update medications" + "\n" + "1. Back";
    private final String MEDICATION_DELETE_MENU = "Delete medications" + "\n" + "1. Back";
    private final String GET_MEDICATION_MENU = "Get medications" + "\n" + "1. Back";

    //Medication Create,Read, Update, Delete information
    private final String MEDICATION_INFO1 = "XYGIY_01,Paracetamol,30,https://th.bing.com/th/id/OIP.CjEDMQk7TEODMKh4MZFfGgD6D6?w=166&h=180&c=7&r=0&o=5&pid=1.7";
    private final String MEDICATION_INFO1_UPDATE = "XYGIY_01,Paracetamol,45,https://th.bing.com/th/id/OIP.CjEDMQk7TEODMKh4MZFfGgD6D6?w=166&h=180&c=7&r=0&o=5&pid=1.7";
    private final String MEDICATION_UPDATE_INVALID_ID = "XYGIY_11,Paracetamol,30,https://th.bing.com/th/id/OIP.CjEDMQk7TEODMKh4MZFfGgD6D6?w=166&h=180&c=7&r=0&o=5&pid=1.7";
    private final String MEDICATION_RESPONSE2_UPDATE = "Medication with code XYGIY_11 not found.";
    private final String MEDICATION_RESPONSE1 = "Medication with code XYGIY_01 created successfully.";
    private final String MEDICATION_RESPONSE1_UPDATE = "Medication with code XYGIY_01 updated successfully.";
    private final String MEDICATION_INFO2_INVALID =  "ZZGIY_01,Paracetamol,30";
    private final String MEDICATION_INFO3 = "0591_0405,60,https://www.lupin.com/US/wp-content/uploads/2021/06/lisinopril-tablets-usp-40mg.jpg";
    private final String MEDICATION_INFO4 = "EM2N5P8,Montelukast,30,https://th.bing.com/th/id/OIP.UKCusqDb2OqO0Yc7YkUZYwHaHa?pid=ImgDet&rs=1";
    private final String MEDICATION_RESPONSE4 = "Medication with code EM2N5P8 created successfully.";
    private final String MEDICATION_INFO5 =  "0378_1800,levothyroxine,45,https://th.bing.com/th/id/R.7fbd35f211e5d8d1d26a917886324ee2?rik=0V5LafFrz83tFQ&pid=ImgRaw&r=0";
    private final String MEDICATION_RESPONSE5 = "Medication with code 0378_1800 created successfully.";
    private final String MEDICATION_INFO6 = "0093_5056,atorvastatin,55,https://th.bing.com/th/id/OIP.UweW6J3LBoimlOFacJE1cAHaFj?rs=1&pid=ImgDetMain";
    private final String MEDICATION_RESPONSE6 = "Medication with code 0093_5056 created successfully.";
    private final String DELETE_MEDICATION_ID1 = "0093_5056";
    private final String DELETE_MEDICATION_RESPONSE1 = "Medication with code 0093_5056 deleted Successfully.";
    private final String DELETE_MEDICATION_ID2 = "0378_1800";
    private final String DELETE_MEDICATION_RESPONSE2 = "Medication with code 0378_1800 deleted Successfully.";
    private final String DELETE_MEDICATION_ID3_INVALID = "0378_1816";
    private final String DELETE_MEDICATION_RESPONSE3 = "Medication with code 0378_1816 not found.";
    private final String GET_MEDICATION_ID1 = "XYGIY_01";
    private final String GET_MEDICATION_RESPONSE1 = "Medication code: XYGIY_01 name: Paracetamol Weight: 45 imageURL: https://th.bing.com/th/id/OIP.CjEDMQk7TEODMKh4MZFfGgD6D6?w=166&h=180&c=7&r=0&o=5&pid=1.7";
    private final String GET_MEDICATION_ID2 = "EM2N5P8";
    private final String GET_MEDICATION_RESPONSE2 = "Medication code: EM2N5P8 name: Montelukast Weight: 30 imageURL: https://th.bing.com/th/id/OIP.UKCusqDb2OqO0Yc7YkUZYwHaHa?pid=ImgDet&rs=1";
    private final String GET_MEDICATION_ID3_INVALID = "0591_0000";
    private final String GET_MEDICATION_RESPONSE3 = "Medication with code 0591_0000 not found.";


    //USer Menu
    private final String USER_MENU = "1. View UserAccounts\n2. Create UserAccount\n3. Update UserAccount\n4. Delete UserAccount\n5. Get UserAccount\n6. Back";

    //Medication Menus
    private final String USER_VIEW_MENU = "View UserAccounts\n1. Back";
    private final String USER_CREATE_MENU = "Enter new UserAccount information...." + "\n" + "User accountType, name, phoneNumber, address, gps coordinates ";
    private final String USER_UPDATE_MENU = "Update UserAccount" + "\n" + "1. Back";
    private final String USER_DELETE_MENU = "Delete UserAccount" + "\n" + "1. Back";
    private final String GET_USER_MENU = "Get UserAccount" + "\n" + "1. Back";

    //Medication Create,Read, Update, Delete information
    private final String USER_INFO1 = "ADMIN,Emily Davis,4157890123,789 Cedar Street Sacramento California(CA) 95814,38.58157 -121.49440";
    private final String USER_INFO1_UPDATE = "ADMIN,Emily Davis,4157890123,789 Cedar Street Oakland California(CA) 77714,38.58157 -121.49440";
    private final String USER_UPDATE_INVALID_ID = "CUSTOMER,John Smith,5109876543,456 Pine Avenue Los Angeles California(CA) 90001,34.05223 -118.24368";
    private final String USER_RESPONSE2_UPDATE = "User with phone number 5109876543 not found.";
    private final String USER_RESPONSE1 = "User with phone number 4157890123 created successfully.";
    private final String USER_RESPONSE1_UPDATE = "User with phone number 4157890123 updated successfully.";
    private final String USER_INFO2_INVALID =  "CUSTOMER,John Smith,5109876543,456 Pine Avenue, Los Angeles, California(CA), 90001,34.05223 -118.24368";
    private final String USER_INFO3 = "CUSTOMER,Olivia White,6504321876,202 Birch Street San Jose California(CA) 95110, 37.33821 -121.88633";
    private final String USER_RESPONSE3 = "User with phone number 6504321876 created successfully.";
    private final String USER_INFO4 = "GUEST,Michael Martinez,9163456789,101 Walnut Avenue San Diego California(CA) 92101,32.71574 -117.16109";
    private final String USER_RESPONSE4 = "User with phone number 9163456789 created successfully.";
    private final String USER_INFO5 =  "ADMIN,Daniel Taylor,3238765432, 303 Elm Avenue Oakland California(CA) 94601,37.80493 -122.27080";
    private final String USER_RESPONSE5 = "User with phone number 3238765432 created successfully.";
    private final String USER_INFO6 = "GUEST,Sophia Rodriguez,7149876543, 404 Maple Avenue Fresno California(CA) 93701,36.73929 -119.78429";
    private final String USER_RESPONSE6 = "User with phone number 7149876543 created successfully.";
    private final String DELETE_USER_ID1 = "7149876543";
    private final String DELETE_USER_RESPONSE1 = "User with phone number 7149876543 deleted Successfully.";
    private final String DELETE_USER_ID2 = "3238765432";
    private final String DELETE_USER_RESPONSE2 = "User with phone number 3238765432 deleted Successfully.";
    private final String DELETE_USER_ID3_INVALID = "3238765400";
    private final String DELETE_USER_RESPONSE3 = "User with phone number 3238765400 not found.";
    private final String GET_USER_ID1 = "4157890123";
    private final String GET_USER_RESPONSE1 = "UserAccount name: Emily Davis phoneNumber: 4157890123 address: 789 Cedar Street Oakland California(CA) 77714 account: ADMIN";
    private final String GET_USER_ID2 = "6504321876";
    private final String GET_USER_RESPONSE2 = "UserAccount name: Olivia White phoneNumber: 6504321876 address: 202 Birch Street San Jose California(CA) 95110 account: CUSTOMER";
    private final String GET_USER_ID3_INVALID = "6504321800";
    private final String GET_USER_RESPONSE3 = "UserAccount with phone number 6504321800 not found.";

    private final String INVALID_RESPONSE = "Invalid response, Please enter a valid response.";
    private final String INVALID_DRONE_PARAM = "The parameters you entered are not valid drone parameters, please enter a valid drone parameter.";
    private final String INVALID_MED_PARAM = "The parameters you entered are not valid medication parameters, please enter a valid medication parameter.";
    private final String INVALID_USER_PARAM = "The parameters you entered are not valid userAccounts parameters, please enter a valid userAccounts parameter.";



    @DynamicTest
    CheckResult testDroneMenu() {
        TestedProgram pr = new TestedProgram();
        String output = pr.start();

        //Select drones menu
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().equals(DRONE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user input \"1\" into your main menu, your program should display \"Drones Menu\" with the following output: \n" + DRONE_MENU + "\n\nYour output is \n\n" + output);
        }

        //Invalid response
        output = pr.execute("7");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(INVALID_RESPONSE.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"7\" into your program, this is an invalid input your response should contain the following output: \n" + INVALID_RESPONSE + "\n\nYour output is \n\n" + output);
        }

        //Select View Drones
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_VIEW_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 followed by another 1\" into your program, should display \"View Drones\" menu which includes the following output: \n" + DRONE_VIEW_MENU + "\n\nYour output is \n\n" + output);
        }

        //Invalid response
        output = pr.execute("abcd");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(INVALID_RESPONSE.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"abcd\" into your program, this is an invalid input your program should respond should contain the following output: \n" + INVALID_RESPONSE + "\n\nYour output is \n\n" + output);
        }

        //Go back to drones menu
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 followed by another 1 and then 1\" into your program, it should display \"Drones Menu\" with the following output: \n" + DRONE_MENU + "\n\nYour output is \n\n" + output);
        }

        //Invalid response
        output = pr.execute("andfnf");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(INVALID_RESPONSE.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"andfnf\" into your program, this is an invalid input the response should contain the following output:\n" + INVALID_RESPONSE + "\n\nYour output is \n\n" + output);
        }

        //Create Drone menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_CREATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 followed and then 2\" into your program, the response should contain the following output: \n" + DRONE_CREATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //create drone with valid data
        output = pr.execute(DRONE_INFO1);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_RESPONSE1.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in drone create menu and enters a valid information example: \n" + DRONE_INFO1 + "\ninto your program, the response should contain the following output: \n" + DRONE_RESPONSE1 + "\n\nYour output is \n\n" + output);
        }

        //Create more Drone menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_CREATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" after creating a drone to create more into your program, the response should contain the following output: \n" + DRONE_CREATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //create drone with valid data
        output = pr.execute(DRONE_INFO4);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_RESPONSE4.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a drone create menu and enters a valid information example: \n" + DRONE_INFO4 + "\ninto your program, the response should contain the following output: \n" + DRONE_RESPONSE4 + "\n\nYour output is \n\n" + output);
        }

        //Create more Drone menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_CREATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" after creating a drone to create more into your program, the response should contain the following output: \n" + DRONE_CREATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //create drone with valid data
        output = pr.execute(DRONE_INFO5);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_RESPONSE5.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a drone create menu and enters a valid information example: \n" + DRONE_INFO5 + "\ninto your program, the response should contain the following output: \n" + DRONE_RESPONSE5 + "\n\nYour output is \n\n" + output);
        }

        //Create more Drone menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_CREATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" after creating drone to create more into your program, the response should contain the following output: \n" + DRONE_CREATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //create drone with valid data
        output = pr.execute(DRONE_INFO6);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_RESPONSE6.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a drone create menu and enters a valid information example: \n" + DRONE_INFO6 + "\ninto your program, the response should contain the following output: \n" + DRONE_RESPONSE5 + "\n\nYour output is \n\n" + output);
        }

        //Create more Drone menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_CREATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" after creating a drone to create more into your program, the response should contain the following output: \n" + DRONE_CREATE_MENU + "\n\nYour output is \n\n" + output);
        }


        //create drone with invalid data incomplete data
        output = pr.execute(DRONE_INFO2_INVALID);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(INVALID_DRONE_PARAM.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a drone create menu enters a invalid drone information example: " + DRONE_INFO2_INVALID + " into your program, the response should contain the following output: \n" + INVALID_DRONE_PARAM + "\n\nYour output is \n\n" + output);
        }

        //create drone with invalid data incomplete data
        output = pr.execute(DRONE_INFO3);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(INVALID_DRONE_PARAM.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in drone create menu enters a invalid drone information example: " + DRONE_INFO3 + " into your program, the response should contain the following output: \n" + INVALID_DRONE_PARAM + "\n\nYour output is \n\n" + output);
        }

        //Go back to drones menu
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 \" into your program, it should display \"Drones Menu\" with the following output: \n" + DRONE_MENU + "\n\nYour output is \n\n" + output);
        }

        //Update Drone menu
        output = pr.execute("3");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_UPDATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 followed by 3\" into your program, response should contain \"Drone update menu\" with the following output starting with: \n" + DRONE_UPDATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //update drone with valid data
        output = pr.execute(DRONE_INFO1_UPDATE);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_RESPONSE1_UPDATE.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a drone update menu and enters a valid information example: \n" + DRONE_INFO1_UPDATE + "\ninto your program, the response should contain the following output: \n" + DRONE_RESPONSE1_UPDATE + "\n\nYour output is \n\n" + output);
        }

        //Update more drones menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_UPDATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" into your program to update more drones, the response should contain the following output: \n" + DRONE_UPDATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //update drone with invalid id
        output = pr.execute(DRONE_INFO2_UPDATE_INVALID_ID);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_RESPONSE2_UPDATE.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a drone update menu and enters a invalid drone id information example: \n" + DRONE_INFO2_UPDATE_INVALID_ID + "\ninto your program, the response should contain the following output: \n" + DRONE_RESPONSE2_UPDATE + "\n\nYour output is \n\n" + output);
        }

        //Update more drones menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_UPDATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" into your program to update more drones, the response should contain the following output: \n" + DRONE_UPDATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //update drone with invalid data incomplete data
        output = pr.execute(DRONE_INFO2_INVALID);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(INVALID_DRONE_PARAM.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a drone update menu enters a invalid drone information example: " + DRONE_INFO2_INVALID + " into your program, the response should contain the following output: \n" + INVALID_DRONE_PARAM + "\n\nYour output is \n\n" + output);
        }

        //Go back to drones menu
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 \" into your program, it should display \"Drones Menu\" with the following output: \n" + DRONE_MENU + "\n\nYour output is \n\n" + output);
        }

        //Delete Drone menu
        output = pr.execute("4");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_DELETE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 followed by 4\" into your program, response should contain \"Delete drone menu\" with the following output starting with: \n" + DRONE_DELETE_MENU + "\n\nYour output is \n\n" + output);
        }

        //Delete drone with valid id
        output = pr.execute(DELETE_DRONE_ID1);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DELETE_DRONE_RESPONSE1.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in drone delete menu and enters a valid drone id information example: \n" + DELETE_DRONE_ID1 + "\ninto your program, the response should contain the following output: \n" + DELETE_DRONE_RESPONSE1 + "\n\nYour output is \n\n" + output);
        }

        //Delete more drones menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_DELETE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" into your program to delete more drones, the response should contain the following output: \n" + DRONE_DELETE_MENU + "\n\nYour output is \n\n" + output);
        }

        //Delete drone with valid id
        output = pr.execute(DELETE_DRONE_ID2);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DELETE_DRONE_RESPONSE2.toLowerCase().trim())) {
            return CheckResult.wrong("When the user in delete drone menu and enters a valid drone id information example: \n" + DELETE_DRONE_ID2 + "\ninto your program, the response should contain the following output: \n" + DELETE_DRONE_RESPONSE2 + "\n\nYour output is \n\n" + output);
        }

        //Delete more drones menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_DELETE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" into your program to delete more drones, the response should contain the following output: \n" + DRONE_DELETE_MENU + "\n\nYour output is \n\n" + output);
        }

        //Delete drone with valid id
        output = pr.execute(DELETE_DRONE_ID3_INVALID);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DELETE_DRONE_RESPONSE3.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in drone delete menu and enters a invalid drone ID information example: \n" + DELETE_DRONE_ID3_INVALID + "\ninto your program, the response should contain the following output: \n" + DELETE_DRONE_RESPONSE3 + "\n\nYour output is \n\n" + output);
        }

        //Go back to drones menu
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 \" into your program, it should display \"Drones Menu\" with the following output: \n" + DRONE_MENU + "\n\nYour output is \n\n" + output);
        }

        //Get Drone menu
        output = pr.execute("5");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(GET_DRONE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 followed by 5\" into your program, response should contain \"Get drone menu\" with the following output starting with: \n" + GET_DRONE_MENU + "\n\nYour output is \n\n" + output);
        }

        //Get drone with valid id
        output = pr.execute(GET_DRONE_ID1);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(GET_DRONE_RESPONSE1.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a get drone menu and enters a valid drone id information example: \n" + GET_DRONE_ID1 + "\ninto your program, the response should contain the following output: \n" + GET_DRONE_RESPONSE1 + "\n\nYour output is \n\n" + output);
        }

        //Get more drones menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(GET_DRONE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" into your program to delete more drones, the response should contain the following output: \n" + GET_DRONE_MENU + "\n\nYour output is \n\n" + output);
        }

        //Get drone with valid id
        output = pr.execute(GET_DRONE_ID2);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(GET_DRONE_RESPONSE2.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in get drone menu and enters a valid drone id information example: \n" + GET_DRONE_ID2 + "\ninto your program, the response should contain the following output: \n" + GET_DRONE_RESPONSE2 + "\n\nYour output is \n\n" + output);
        }

        //Get more drones menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(GET_DRONE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" into your program to delete more drones, the response should contain the following output: \n" + GET_DRONE_MENU + "\n\nYour output is \n\n" + output);
        }

        //Get drone with valid id
        output = pr.execute(GET_DRONE_ID3_INVALID);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(GET_DRONE_RESPONSE3.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in get a drone menu and enters a valid drone id information example: \n" + GET_DRONE_ID3_INVALID + "\ninto your program, the response should contain the following output: \n" + GET_DRONE_RESPONSE3 + "\n\nYour output is \n\n" + output);
        }

        //Go back to drones menu
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DRONE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 \" into your program, it should display \"Drones Menu\" with the following output: \n" + DRONE_MENU + "\n\nYour output is \n\n" + output);
        }

        //Go back to main menu
        output = pr.execute("6");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MAIN_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"6 \" into your program, it should display \"Main Menu\" with the following output: \n" + MAIN_MENU + "\n\nYour output is \n\n" + output);
        }


        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult testMedicationMenu() {
        TestedProgram pr = new TestedProgram();
        String output = pr.start();

        //Select medication menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().equals(MEDICATION_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user input \"2\" into your main menu, your program should it display \"Medication Menu\" with the following output: \n" + MEDICATION_MENU + "\n\nYour output is \n\n" + output);
        }

        //Invalid response
        output = pr.execute("7");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(INVALID_RESPONSE.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"7\" into your program, this is an invalid input your response should contain the following output: \n" + INVALID_RESPONSE + "\n\nYour output is \n\n" + output);
        }

        //Select View Medication
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATIONS_VIEW_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2 followed by another 1\" into your program, it should display \"View Medications\" menu which includes the following output: \n" + MEDICATIONS_VIEW_MENU + "\n\nYour output is \n\n" + output);
        }

        //Invalid response
        output = pr.execute("abcd");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(INVALID_RESPONSE.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"abcd\" into your program, this is an invalid input your program should response should contain the following output: \n" + INVALID_RESPONSE + "\n\nYour output is \n\n" + output);
        }

        //Go back to medication menu
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2 followed by another 1 and then 1\" into your program, it should display \"Medication Menu\" with the following output: \n" + MEDICATION_MENU + "\n\nYour output is \n\n" + output);
        }

        //Invalid response
        output = pr.execute("andfnf");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(INVALID_RESPONSE.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"andfnf\" into your program, this is an invalid input your response should contain the following output:\n" + INVALID_RESPONSE + "\n\nYour output is \n\n" + output);
        }

        //Create Medication menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_CREATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2 followed by another 2\" into your program, the response should contain the following output: \n" + MEDICATION_CREATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //create medication with valid data
        output = pr.execute(MEDICATION_INFO1);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_RESPONSE1.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a medication create menu and enters a valid information example: \n" + MEDICATION_INFO1 + "\ninto your program, the response should contain the following output: \n" + MEDICATION_RESPONSE1 + "\n\nYour output is \n\n" + output);
        }

        //Create more Medication menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_CREATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" after creating medication to create more into your program, the response should contain the following output: \n" + MEDICATION_CREATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //create medication with valid data
        output = pr.execute(MEDICATION_INFO4);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_RESPONSE4.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a medication create menu and enters a valid information example: \n" + MEDICATION_INFO4 + "\ninto your program, the response should contain the following output: \n" + MEDICATION_RESPONSE4 + "\n\nYour output is \n\n" + output);
        }

        //Create more Medication menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_CREATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" after creating medication to create more into your program, the response should contain the following output: \n" + MEDICATION_CREATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //create medication with valid data
        output = pr.execute(MEDICATION_INFO5);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_RESPONSE5.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a medication create menu and enters a valid information example: \n" + MEDICATION_INFO5 + "\ninto your program, the response should contain the following output: \n" + MEDICATION_RESPONSE5 + "\n\nYour output is \n\n" + output);
        }

        //Create more medication menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_CREATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" after creating medication to create more into your program, the response should contain the following output: \n" + MEDICATION_CREATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //create medication with valid data
        output = pr.execute(MEDICATION_INFO6);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_RESPONSE6.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a medication create menu and enters a valid information example: \n" + MEDICATION_INFO6 + "\ninto your program, the response should contain the following output: \n" + MEDICATION_RESPONSE6 + "\n\nYour output is \n\n" + output);
        }

        //Create more medication menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_CREATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" after creating medication to create more into your program,the response should contain the following output: \n" + MEDICATION_CREATE_MENU + "\n\nYour output is \n\n" + output);
        }


        //create medication with invalid data incomplete data
        output = pr.execute(MEDICATION_INFO2_INVALID);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(INVALID_MED_PARAM.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a medication create menu enters a invalid medication information example: " + MEDICATION_INFO2_INVALID + " into your program, the response should contain the following output: \n" + INVALID_MED_PARAM + "\n\nYour output is \n\n" + output);
        }

        //create medication with invalid data incomplete data
        output = pr.execute(MEDICATION_INFO3);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(INVALID_MED_PARAM.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a medication create menu enters a invalid medication information example: " + MEDICATION_INFO3 + " into your program, response should contain the following output: \n" + INVALID_MED_PARAM + "\n\nYour output is \n\n" + output);
        }

        //Go back to medication menu
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 \" into your program, it should display \"Medication Menu\" with the following output: \n" + MEDICATION_MENU + "\n\nYour output is \n\n" + output);
        }

        //Update medication menu
        output = pr.execute("3");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_UPDATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 followed by 3\" into your program, the response should contain \"Medication update menu\" with the following output starting with: \n" + MEDICATION_UPDATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //update medication with valid data
        output = pr.execute(MEDICATION_INFO1_UPDATE);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_RESPONSE1_UPDATE.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a medication update menu and enters a valid information example: \n" + MEDICATION_INFO1_UPDATE + "\ninto your program, the response should contain the following output: \n" + MEDICATION_RESPONSE1_UPDATE + "\n\nYour output is \n\n" + output);
        }

        //Update more medication menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_UPDATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" into your program to update more medication, the response should contain the following output: \n" + MEDICATION_UPDATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //update medication with invalid id
        output = pr.execute(MEDICATION_UPDATE_INVALID_ID);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_RESPONSE2_UPDATE.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a medication update menu and enters a invalid medication id information example: \n" + MEDICATION_UPDATE_INVALID_ID + "\ninto your program, the response should contain the following output: \n" + MEDICATION_RESPONSE2_UPDATE + "\n\nYour output is \n\n" + output);
        }

        //Update more medication menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_UPDATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" into your program to update more medication, the response should contain the following output: \n" + MEDICATION_UPDATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //update medication with invalid data incomplete data
        output = pr.execute(MEDICATION_INFO2_INVALID);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(INVALID_MED_PARAM.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in medication update menu enters a invalid medication information example: " + MEDICATION_INFO2_INVALID + " into your program, the response should contain the following output: \n" + INVALID_MED_PARAM + "\n\nYour output is \n\n" + output);
        }

        //Go back to medication menu
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 \" into your program, it should display \"Medication Menu\" with the following output: \n" + MEDICATION_MENU + "\n\nYour output is \n\n" + output);
        }

        //Delete medication menu
        output = pr.execute("4");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_DELETE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 followed by 4\" into your program, the response should contain \"Delete medication menu\" with the following output starting with: \n" + MEDICATION_DELETE_MENU + "\n\nYour output is \n\n" + output);
        }

        //Delete medication with valid id
        output = pr.execute(DELETE_MEDICATION_ID1);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DELETE_MEDICATION_RESPONSE1.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a medication delete menu and enters a valid medication id information example: \n" + DELETE_MEDICATION_ID1 + "\ninto your program, the response should contain the following output: \n" + DELETE_MEDICATION_RESPONSE1 + "\n\nYour output is \n\n" + output);
        }

        //Delete more medication menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_DELETE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" into your program to delete more medication, the response should contain the following output: \n" + MEDICATION_DELETE_MENU + "\n\nYour output is \n\n" + output);
        }

        //Delete medication with valid id
        output = pr.execute(DELETE_MEDICATION_ID2);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DELETE_MEDICATION_RESPONSE2.toLowerCase().trim())) {
            return CheckResult.wrong("When the user in a medication delete menu and enters a valid medication id information example: \n" + DELETE_MEDICATION_ID2 + "\ninto your program, the response should contain the following output: \n" + DELETE_DRONE_RESPONSE2 + "\n\nYour output is \n\n" + output);
        }

        //Delete more medication menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_DELETE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" into your program to delete more medications, response should contain the following output: \n" + DRONE_DELETE_MENU + "\n\nYour output is \n\n" + output);
        }

        //Delete medication with valid id
        output = pr.execute(DELETE_MEDICATION_ID3_INVALID);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DELETE_MEDICATION_RESPONSE3.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a medication delete menu and enters a invalid medication id information example: \n" + DELETE_MEDICATION_ID3_INVALID + "\ninto your program, the response should contain the following output: \n" + DELETE_MEDICATION_RESPONSE3 + "\n\nYour output is \n\n" + output);
        }

        //Go back to medications menu
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 \" into your program, it should display \"Medication Menu\" with the following output: \n" + MEDICATION_MENU + "\n\nYour output is \n\n" + output);
        }

        //Get medication menu
        output = pr.execute("5");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(GET_MEDICATION_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 followed by 5\" into your program, response should contain \"Get medication menu\" with the following output starting with: \n" + GET_MEDICATION_MENU + "\n\nYour output is \n\n" + output);
        }

        //Get medication with valid id
        output = pr.execute(GET_MEDICATION_ID1);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(GET_MEDICATION_RESPONSE1.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a get medication menu and enters a valid medication id information example: \n" + GET_MEDICATION_ID1 + "\ninto your program, the response should contain the following output: \n" + GET_MEDICATION_RESPONSE1 + "\n\nYour output is \n\n" + output);
        }

        //Get more medications menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(GET_MEDICATION_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" into your program to delete more medication, response should contain the following output: \n" + GET_MEDICATION_MENU + "\n\nYour output is \n\n" + output);
        }

        //Get medication with valid id
        output = pr.execute(GET_MEDICATION_ID2);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(GET_MEDICATION_RESPONSE2.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in get medication menu and enters a valid medication id information example: \n" + GET_MEDICATION_ID2 + "\ninto your program, the response should contain the following output: \n" + GET_MEDICATION_RESPONSE2 + "\n\nYour output is \n\n" + output);
        }

        //Get more medication menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(GET_MEDICATION_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" into your program to delete more medications, the response should contain the following output: \n" + GET_MEDICATION_MENU + "\n\nYour output is \n\n" + output);
        }

        //Get medication with valid id
        output = pr.execute(GET_MEDICATION_ID3_INVALID);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(GET_MEDICATION_RESPONSE3.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in get medication menu and enters a invalid medication id information example: \n" + GET_MEDICATION_ID3_INVALID + "\ninto your program,the response should contain the following output: \n" + GET_MEDICATION_RESPONSE3 + "\n\nYour output is \n\n" + output);
        }

        //Go back to medication menu
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MEDICATION_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 \" into your program, it should display \"Medication Menu\" with the following output: \n" + MEDICATION_MENU + "\n\nYour output is \n\n" + output);
        }

        //Go back to main menu
        output = pr.execute("6");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MAIN_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"6 \" into your program, it should display \"Main Menu\" with the following output: \n" + MAIN_MENU + "\n\nYour output is \n\n" + output);
        }


        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult testUserMenu() {
        TestedProgram pr = new TestedProgram();
        String output = pr.start();

        //Select User menu
        output = pr.execute("3");
        if (!output.isEmpty() && !output.toLowerCase().trim().equals(USER_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user input \"3\" into your main menu, your program should it display \"User Menu\" with the following output: \n" + USER_MENU + "\n\nYour output is \n\n" + output);
        }

        //Invalid response
        output = pr.execute("15");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(INVALID_RESPONSE.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"15\" into your program, this is an invalid input your response should contain the following output: \n" + INVALID_RESPONSE + "\n\nYour output is \n\n" + output);
        }

        //Select View User
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_VIEW_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"3 followed by another 1\" into your program, it should display \"View UserAccounts\" menu which includes the following output: \n" + USER_VIEW_MENU + "\n\nYour output is \n\n" + output);
        }

        //Invalid response
        output = pr.execute("abcdkjasdffasfasjkn");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(INVALID_RESPONSE.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"abcdkjasdffasfasjkn\" into your program, this is an invalid input your program should response should contain the following output: \n" + INVALID_RESPONSE + "\n\nYour output is \n\n" + output);
        }

        //Go back to user menu
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"3 followed by another 1 and then 1\" into your program, it should display \"UserAccounts Menu\" with the following output: \n" + USER_MENU + "\n\nYour output is \n\n" + output);
        }

        //Invalid response
        output = pr.execute("analsgaslkdfnf");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(INVALID_RESPONSE.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"analsgaslkdfnf\" into your program, this is an invalid input your response should contain the following output:\n" + INVALID_RESPONSE + "\n\nYour output is \n\n" + output);
        }

        //Create User menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_CREATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"3 followed by another 2\" into your program, the response should contain the following output: \n" + USER_CREATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //create user with valid data
        output = pr.execute(USER_INFO1);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_RESPONSE1.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a UserAccounts create menu and enters a valid information example: \n" + USER_INFO1 + "\ninto your program, the response should contain the following output: \n" + USER_RESPONSE1 + "\n\nYour output is \n\n" + output);
        }

        //Create more User menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_CREATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" after creating UserAccounts to create more into your program, response should contain the following output: \n" + USER_CREATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //create user with valid data
        output = pr.execute(USER_INFO4);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_RESPONSE4.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a UserAccounts create menu and enters a valid information example: \n" + USER_INFO4 + "\ninto your program, the response should contain the following output: \n" + USER_RESPONSE4 + "\n\nYour output is \n\n" + output);
        }

        //Create more user menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_CREATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" after creating UserAccounts to create more into your program, response should contain the following output: \n" + USER_CREATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //create user with valid data
        output = pr.execute(USER_INFO5);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_RESPONSE5.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a UserAccounts create menu and enters a valid information example: \n" + USER_INFO5 + "\ninto your program, the response should contain the following output: \n" + USER_RESPONSE5 + "\n\nYour output is \n\n" + output);
        }

        //Create more user menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_CREATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" after creating a UserAccounts to create more into your program, the response should contain the following output: \n" + USER_CREATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //create user with valid data
        output = pr.execute(USER_INFO6);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_RESPONSE6.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a UserAccounts create menu and enters a valid information example: \n" + USER_INFO6 + "\ninto your program, the response should contain the following output: \n" + USER_RESPONSE6 + "\n\nYour output is \n\n" + output);
        }

        //Create more user menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_CREATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" after creating UserAccounts to create more into your program,the response should contain the following output: \n" + USER_CREATE_MENU + "\n\nYour output is \n\n" + output);
        }


        //create user with invalid data incomplete data
        output = pr.execute(USER_INFO2_INVALID);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(INVALID_USER_PARAM.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a UserAccounts create menu enters a invalid UserAccounts information example: " + USER_INFO2_INVALID + " into your program, the response should contain the following output: \n" + INVALID_USER_PARAM + "\n\nYour output is \n\n" + output);
        }

        //create user with valid data complete data
        output = pr.execute(USER_INFO3);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_RESPONSE3.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a UserAccounts create menu enters a valid UserAccounts information example: " + USER_INFO3 + " into your program, the response should contain the following output: \n" + USER_RESPONSE3 + "\n\nYour output is \n\n" + output);
        }

        //Go back to user menu
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 \" into your program, it should display \"UserAccounts Menu\" with the following output: \n" + USER_MENU + "\n\nYour output is \n\n" + output);
        }

        //Update user menu
        output = pr.execute("3");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_UPDATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 followed by 3\" into your program, the response should contain \"UserAccounts update menu\" with the following output starting with: \n" + MEDICATION_UPDATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //update user with valid data
        output = pr.execute(USER_INFO1_UPDATE);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_RESPONSE1_UPDATE.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in UserAccounts update menu and enters a valid information example: \n" + USER_INFO1_UPDATE + "\ninto your program, the response should contain the following output: \n" + USER_RESPONSE1_UPDATE + "\n\nYour output is \n\n" + output);
        }

        //Update more user menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_UPDATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" into your program to update more UserAccounts, the response should contain the following output: \n" + USER_UPDATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //update user with invalid id
        output = pr.execute(USER_UPDATE_INVALID_ID);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_RESPONSE2_UPDATE.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a UserAccounts update menu and enters a invalid drone id information example: \n" + USER_UPDATE_INVALID_ID + "\ninto your program, the response should contain the following output: \n" + USER_RESPONSE2_UPDATE + "\n\nYour output is \n\n" + output);
        }

        //Update more user menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_UPDATE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" into your program to update more UserAccounts, response should contain the following output: \n" + USER_UPDATE_MENU + "\n\nYour output is \n\n" + output);
        }

        //update user with invalid data incomplete data
        output = pr.execute(USER_INFO2_INVALID);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(INVALID_USER_PARAM.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a UserAccounts update menu and enters an invalid UserAccounts information example: " + USER_INFO2_INVALID + " into your program, the response should contain the following output: \n" + INVALID_USER_PARAM + "\n\nYour output is \n\n" + output);
        }

        //Go back to user menu
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 \" into your program, it should display \"UserAccounts Menu\" with the following output: \n" + USER_MENU + "\n\nYour output is \n\n" + output);
        }

        //Delete user menu
        output = pr.execute("4");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_DELETE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 followed by 4\" into your program, response should contain \"Delete UserAccounts menu\" with the following output starting with: \n" + USER_DELETE_MENU + "\n\nYour output is \n\n" + output);
        }

        //Delete user with valid id
        output = pr.execute(DELETE_USER_ID1);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DELETE_USER_RESPONSE1.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in UserAccounts delete menu and enters a valid UserAccounts id information example: \n" + DELETE_USER_ID1 + "\ninto your program, the response should contain the following output: \n" + DELETE_USER_RESPONSE1 + "\n\nYour output is \n\n" + output);
        }

        //Delete more user menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_DELETE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" into your program to delete more UserAccounts, the response should contain the following output: \n" + USER_DELETE_MENU + "\n\nYour output is \n\n" + output);
        }

        //Delete user with valid id
        output = pr.execute(DELETE_USER_ID2);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DELETE_USER_RESPONSE2.toLowerCase().trim())) {
            return CheckResult.wrong("When the user in a delete UserAccounts menu and enters a valid UserAccounts id information example: \n" + DELETE_USER_ID2 + "\ninto your program, the response should contain the following output: \n" + DELETE_USER_RESPONSE2 + "\n\nYour output is \n\n" + output);
        }

        //Delete more user menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_DELETE_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" into your program to delete more UserAccounts, the response should contain the following output: \n" + USER_DELETE_MENU + "\n\nYour output is \n\n" + output);
        }

        //Delete user with valid id
        output = pr.execute(DELETE_USER_ID3_INVALID);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(DELETE_USER_RESPONSE3.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a UserAccounts delete menu and enters a invalid UserAccounts id information example: \n" + DELETE_USER_ID3_INVALID + "\ninto your program, the response should contain the following output: \n" + DELETE_USER_RESPONSE3 + "\n\nYour output is \n\n" + output);
        }

        //Go back to user menu
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 \" into your program, it should display \"UserAccounts Menu\" with the following output: \n" + USER_MENU + "\n\nYour output is \n\n" + output);
        }

        //Get user menu
        output = pr.execute("5");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(GET_USER_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 followed by 5\" into your program, the response should contain \"Get UserAccounts menu\" with the following output starting with: \n" + GET_USER_MENU + "\n\nYour output is \n\n" + output);
        }

        //Get user with valid id
        output = pr.execute(GET_USER_ID1);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(GET_USER_RESPONSE1.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in a get UserAccounts menu and enters a valid UserAccounts id information example: \n" + GET_USER_ID1 + "\ninto your program, the response should contain the following output: \n" + GET_USER_RESPONSE1 + "\n\nYour output is \n\n" + output);
        }

        //Get more user menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(GET_USER_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" into your program to delete more UserAccounts, the response should contain the following output: \n" + GET_USER_MENU + "\n\nYour output is \n\n" + output);
        }

        //Get user with valid id
        output = pr.execute(GET_USER_ID2);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(GET_USER_RESPONSE2.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in get UserAccounts menu and enters a valid UserAccounts id information example: \n" + GET_USER_ID2 + "\ninto your program, the response should contain the following output: \n" + GET_USER_RESPONSE2 + "\n\nYour output is \n\n" + output);
        }

        //Get more user menu
        output = pr.execute("2");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(GET_USER_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"2\" into your program to delete more UserAccounts, the response should contain the following output: \n" + GET_USER_MENU + "\n\nYour output is \n\n" + output);
        }

        //Get user with valid id
        output = pr.execute(GET_USER_ID3_INVALID);
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(GET_USER_RESPONSE3.toLowerCase().trim())) {
            return CheckResult.wrong("When the user is in get UserAccounts menu and enters a invalid UserAccounts id information example: \n" + GET_USER_ID3_INVALID + "\ninto your program, the response should contain the following output: \n" + GET_USER_RESPONSE3 + "\n\nYour output is \n\n" + output);
        }

        //Go back to user menu
        output = pr.execute("1");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(USER_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"1 \" into your program, it should display \"UserAccounts Menu\" with the following output: \n" + USER_MENU + "\n\nYour output is \n\n" + output);
        }

        //Go back to main menu
        output = pr.execute("6");
        if (!output.isEmpty() && !output.toLowerCase().trim().contains(MAIN_MENU.toLowerCase().trim())) {
            return CheckResult.wrong("When the user inputs \"6 \" into your program, it should display \"Main Menu\" with the following output: \n" + MAIN_MENU + "\n\nYour output is \n\n" + output);
        }


        return CheckResult.correct();
    }


}