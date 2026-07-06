package com.jpmc.midascore.controller; // Update this if your package is different
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
// Ensure this matches your actual User class import
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    @Autowired
    private UserRepository userRepository;



    @GetMapping("/balance")
    public Balance getBalance(@RequestParam Long userId) {
        // Query the database for the user
        UserRecord user = userRepository.findById(userId).orElse(null);

        // If the user exists, return their balance. Otherwise, return 0.
        if (user != null) {
            return new Balance(user.getBalance());
        } else {
            return new Balance(0);
        }
    }
}