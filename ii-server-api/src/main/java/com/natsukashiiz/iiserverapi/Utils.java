package com.natsukashiiz.iiserverapi;

import com.natsukashiiz.iiboot.configuration.jwt.UserDetailsImpl;
import com.natsukashiiz.iiserverapi.entity.User;

public class Utils {
    public static User getUserFromAuth(UserDetailsImpl auth) {
        User user = new User();
        user.setId(auth.getId());
        return user;
    }
}
