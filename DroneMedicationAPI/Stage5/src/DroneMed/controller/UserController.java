package DroneMed.controller;


import DroneMed.models.UserAccount;
import DroneMed.response.ResponseHandler;
import DroneMed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for handling user-related operations.
 * It provides CRUD operations for user and other specific drone query functionalities.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    // Service to handle user-related operations.
    private final UserService userService;

    /**
     * Constructor-based dependency injection for the drone service.
     *
     * @param userService - Service to manage drone operations.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to create a new user.
     *
     * @param user - User entity to be created.
     * @return ResponseEntity with status and created user.
     */
    @PostMapping("/create_user")
    public ResponseEntity<?> createUser(@RequestBody UserAccount user) {
        String response = userService.createUser(user);
        if(response.contains("not found.")) return ResponseHandler.responseBuilder(response, HttpStatus.NOT_FOUND);

        return ResponseHandler.responseBuilder(response, HttpStatus.OK);
    }

    /**
     * Endpoint to create multiple user in bulk.
     *
     * @param users - List of Drone entities to be created.
     * @return ResponseEntity with status and list of success messages.
     */
    @PostMapping("/create_users")
    public ResponseEntity<?> createUsers(@RequestBody List<UserAccount> users) {
        List<String> response = userService.createUsers(users);
        return ResponseHandler.responseBuilder("Users created successfully.", HttpStatus.OK, response);
    }

    /**
     * Endpoint to update the details of an existing user.
     *
     * @param user - User entity with updated values.
     * @return ResponseEntity with status and updated user.
     */
    @PutMapping("/update_user")
    public ResponseEntity<?> updateUser(@RequestBody UserAccount user) {
        String response = userService.updateUser(user);
        if(response.contains("not found.")) return ResponseHandler.responseBuilder(response, HttpStatus.NOT_FOUND);

        return ResponseHandler.responseBuilder(response, HttpStatus.OK);
    }

    /**
     * Endpoint to delete a user based on its phone number.
     *
     * @param phoneNumber - phone number of the user to be deleted.
     * @return ResponseEntity with status and success message.
     */
    @DeleteMapping("/delete_user/{phoneNumber}")
    public ResponseEntity<?> deleteUser(@PathVariable String phoneNumber) {
        String response = userService.deleteUser(phoneNumber);
        if(response.contains("not found.")) return ResponseHandler.responseBuilder(response, HttpStatus.NOT_FOUND);

        return ResponseHandler.responseBuilder(response, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a user based on its phone number.
     *
     * @param phoneNumber - Serial number of the user to be fetched.
     * @return ResponseEntity with status and fetched user.
     */
    @GetMapping("/get_user/{phoneNumber}")
    public ResponseEntity<?> getUser(@PathVariable String phoneNumber) {
        Optional<UserAccount> user = userService.getUser(phoneNumber);
        if(user.isPresent()) return ResponseHandler.responseBuilder("User fetched successfully.", HttpStatus.OK, user);
        else return ResponseHandler.responseBuilder("User with phone number " + phoneNumber + " not found.", HttpStatus.NOT_FOUND);

    }

    /**
     * Endpoint to retrieve all user from the database.
     *
     * @return ResponseEntity with status and list of all user.
     */
    @GetMapping("/get_all_users")
    public ResponseEntity<?> getAllUsers() {
        List<UserAccount> users = userService.getAllUsers();
        if(users.size() > 0) return ResponseHandler.responseBuilder("All users fetched successfully.", HttpStatus.OK, users);
        else return ResponseHandler.responseBuilder("User list empty.", HttpStatus.OK);
    }


}
