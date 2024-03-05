package DroneMed.service.impl;


import DroneMed.models.UserAccount;
import DroneMed.repository.UserRepository;
import DroneMed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    // User repository to interact with drone data in the database.
    private final UserRepository userRepository;


    /**
     * Constructor-based dependency injection for user repositories.
     *
     * @param userRepository - Repository to manage user operations.
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    /**
     * @param user - User entity.
     */
    @Override
    public String createUser(UserAccount user) {

        userRepository.save(user);

        return "User " + user.getName() + " created successfully" ;
    }

    /**
     * Creates a List of user entry in the database.
     *
     * @param users - List of users entities to be created.
     * @return String - Success message.
     */
    @Transactional
    @Override
    public List<String> createUsers(List<UserAccount> users) {

        List<String> response = new ArrayList<>();
        for (UserAccount u: users) {
            response.add(createUser(u));
        }
        return response;
    }

    /**
     * Updates the details of an existing user.
     *
     * @param user - user entity with updated values.
     * @return String - Success message.
     */
    @Override
    public String updateUser(UserAccount user) {

        UserAccount existingUser = userRepository.findById(user.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.save(user);

        return "User updated successfully";
    }

    /**
     * Deletes a user entry from the database based on its code.
     *
     * @param phoneNumber - phoneNumber of the user to be deleted.
     * @return String - Success message.
     */
    @Override
    public String deleteUser(String phoneNumber) {

        if(userRepository.existsById(phoneNumber)) {
            userRepository.deleteById(phoneNumber);
            return  "Delete user Successfully";
        } else {
            throw new RuntimeException("User with phone number " + phoneNumber + " not found.");
        }
    }

    /**
     * Retrieves a user based on phone number.
     *
     * @param phoneNumber - phoneNumber of the user to be fetched.
     * @return User - User entity.
     */
    @Override
    public Optional<UserAccount> getUser(String phoneNumber) {
        return userRepository.findById(phoneNumber);
        //orElseThrow( () -> new RuntimeException("User with phone number " + phoneNumber + " not found"));
    }

    /**
     * Retrieves all user from the database.
     *
     * @return List<User> - List of all User entities.
     */
    @Override
    public List<UserAccount> getAllUsers() {
        return userRepository.findAll();
        //we can use pageable here
    }
}
