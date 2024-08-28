package com.marcinchowaniec.mapper;

import com.marcinchowaniec.dto.UserDto;
import com.marcinchowaniec.entity.User;

public class UserMapper {

    public static User mapToUser(UserDto userDto) {
        var user = new User();
        user.name = userDto.name();
        user.login = userDto.login();
        user.url = "https://api.github.com/users/" + userDto.login();
        user.repos_url = "https://api.github.com/users/" + userDto.login() + "/repos";
        // user.repos = new HashSet<>();
        return user;
    }
}
