package DroneMed.service;

import DroneMed.models.UserAccount;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public String createUser(UserAccount user);

    public List<String> createUsers(List<UserAccount> users);

    public String updateUser (UserAccount user);

    public String deleteUser(String phoneNumber);

    public Optional<UserAccount> getUser(String phoneNumber);

    public List<UserAccount> getAllUsers();

}