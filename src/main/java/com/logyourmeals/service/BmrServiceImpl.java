package com.logyourmeals.service;

import com.logyourmeals.entity.User;
import org.springframework.stereotype.Service;

@Service
public class BmrServiceImpl implements BmrService {

    @Override
    public int calculateBmr(User user) {

        if (user.getWeight() == null || user.getHeight() == null || user.getAge() == null) {
            return 2000; // fallback default
        }

        if ("female".equalsIgnoreCase(user.getGender())) {
            return (int) (10 * user.getWeight()
                    + 6.25 * user.getHeight()
                    - 5 * user.getAge()
                    - 161);
        }

        // default male
        return (int) (10 * user.getWeight()
                + 6.25 * user.getHeight()
                - 5 * user.getAge()
                + 5);
    }
}
