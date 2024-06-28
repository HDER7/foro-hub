package com.example.forohub.domains.users;

public record UserResponseData(Long id, String name) {
    public UserResponseData(User user){
        this(user.getId(), user.getName());
    }
}
