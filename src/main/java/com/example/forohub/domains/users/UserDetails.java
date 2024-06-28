package com.example.forohub.domains.users;

public record UserDetails(Long id, String name, String email) {
    public UserDetails(User user) {
        this(user.getId(), user.getName(),user.getMail());
    }
}
