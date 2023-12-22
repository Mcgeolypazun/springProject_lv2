package com.sparta.firsttask.dto;

import com.sparta.firsttask.entity.User;
import com.sparta.firsttask.entity.UserRole;
import com.sparta.firsttask.entity.UserRoleEnum;

public record JwtUser(
        Long id,
        String username,
        UserRoleEnum role
) {

    public static JwtUser of(User user) {
        return new JwtUser(user.getId(), user.getUsername(), user.getRole());
    }

}

