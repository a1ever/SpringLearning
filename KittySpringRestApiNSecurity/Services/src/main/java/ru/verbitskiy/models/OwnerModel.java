package ru.verbitskiy.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OwnerModel {
    private UUID uuid;
    private String name;
    private String password;
    private String role;
    private Date birthday;
    private List<UUID> kitties_ids;
}
