package com.aircondition.refrigerationsys.Service;

import com.aircondition.refrigerationsys.Model.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    static void updatePassword(User user, String newPassword) {
    }




    List<Object> isUserPresent(User user);

    User findUserByEmail(String email);

    void createPasswordResetTokenForUser(User user, String token);
}
