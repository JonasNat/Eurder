package com.switchfully.eurder.repositories;

import com.switchfully.eurder.domain.Address;
import com.switchfully.eurder.domain.User;
import com.switchfully.eurder.domain.Role;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private final Map<String, User> users;

    public UserRepository() {
        users = new HashMap<>();
        users.put("0", new User("Jonas",
                "Nata",
                "jonas@eurder.com",
                "password",
                new Address("Teststraat", "50", "2000", "Antwerp"),
                "0498416686",
                Role.ADMIN)
        );
    }

    public List<User> getAll() {
        return users.values().stream().toList();
    }

    public Optional<User> findById(String id) {
        return users.values().stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public Optional<User> findByEmailAddress(String emailAddress) {
       return users.values().stream().filter(user -> user.getEmailAddress().equals(emailAddress)).findFirst();
    }

    public User create(User user) {
        users.put(user.getId(), user);
        return user;
    }
}
