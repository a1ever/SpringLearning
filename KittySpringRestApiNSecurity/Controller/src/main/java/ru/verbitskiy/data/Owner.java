package ru.verbitskiy.data;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Owner {
    UUID uuid;
    String name;
    String password;
    Role role;
    Date birthday;
    List<UUID> kitties_ids;

    public Owner(String name, String password, Role role, Date birthday, List<UUID> kitties_ids) {
        this.uuid = UUID.randomUUID();
        this.password = password;
        this.role = role;
        this.name = name;
        this.birthday = birthday;
        this.kitties_ids = kitties_ids;
    }
}
