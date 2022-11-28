package com.switchfully.eurder.repositories;

import com.switchfully.eurder.domain.Address;
import com.switchfully.eurder.domain.User;
import com.switchfully.eurder.domain.Role;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private final Map<String, User> users;

    public UserRepository() {
        users = new HashMap<>();
        users.put("1", new User("Jonas",
                "Nata",
                "jonas@eurder.com",
                new Address("Teststraat", "50", "2000", "Antwerp"),
                "0498416686",
                Role.ADMIN)
        );
    }

    public List<User> getAll() {
        return users.values().stream().toList();
    }

    public User create(User user) {
        return users.put(user.getId(), user);
    }
}
