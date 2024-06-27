package ru.verbitskiy.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class KittyModel {
    private UUID uuid;
    private String name;
    private Date birthday;
    private String race;
    private String color;
    private UUID owner_id;
    private List<UUID> friends_ids;
}
