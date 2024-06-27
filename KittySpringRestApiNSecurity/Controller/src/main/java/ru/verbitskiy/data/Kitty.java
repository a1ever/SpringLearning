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
public class Kitty {
    UUID uuid;
    String name;
    Date birthday;
    String race;
    KittyColor color;
    UUID owner_id;
    List<UUID> friends_ids;

    public Kitty(String name, Date birthday, String race, KittyColor color, UUID owner_id, List<UUID> friends_ids) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.birthday = birthday;
        this.race = race;
        this.color = color;
        this.owner_id = owner_id;
        this.friends_ids = friends_ids;
    }
}
