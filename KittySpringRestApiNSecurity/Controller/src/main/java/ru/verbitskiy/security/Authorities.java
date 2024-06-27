package ru.verbitskiy.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.verbitskiy.data.Role;

import java.util.ArrayList;
import java.util.List;

public enum Authorities {
    KittyAdd("kitty/add"),
    KittyUpdate("kitty/update"),
    KittyGet("kitty/get"),
    KittyDelete("kitty/delete"),
    KittyAll("kitty/all"),
    KittyAllRaces("kitty/races"),
    KittyAllColors("kitty/colors"),
    OwnerAdd("owner/add"),
    OwnerAll("owner/all"),
    OwnerGet("owner/get"),
    OwnerUpdate("owner/update"),
    OwnerDelete("owner/delete");


    private final String auth;

    Authorities(String authority) {
        this.auth = authority;
    }

    public String toString() {
        return auth;
    }

    public static ArrayList<SimpleGrantedAuthority> getAuthorities(Role role) {
        if (role == Role.admin) {
            return new ArrayList<>(List.of(
                    new SimpleGrantedAuthority(KittyAll.auth),
                    new SimpleGrantedAuthority(OwnerAll.auth)));
        } else if (role == Role.user) {
            return new ArrayList<>(List.of(new SimpleGrantedAuthority(KittyAdd.auth),
                    new SimpleGrantedAuthority(KittyUpdate.auth),
                    new SimpleGrantedAuthority(KittyGet.auth),
                    new SimpleGrantedAuthority(KittyDelete.auth),
                    new SimpleGrantedAuthority(KittyAllRaces.auth),
                    new SimpleGrantedAuthority(KittyAllColors.auth)));
        }
        return new ArrayList<>();
    }
}
