package ru.verbitskiy.Entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "owner")
public class OwnerEntity {
    public OwnerEntity(UUID uuid, String name, String password, String role, Date birthday, List<KittyEntity> kitties) {
        this.uuid = uuid;
        this.name = name;
        this.password = password;
        this.role = role;
        this.birthday = birthday;
        this.kitties = kitties;
    }

    public OwnerEntity(String name, String password, String role, Date birthday, List<KittyEntity> kitties) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.password = password;
        this.role = role;
        this.birthday = birthday;
        this.kitties = kitties;
    }

    public OwnerEntity() {
    }

    @Id
    @Column(name = "uuid")
    UUID uuid;
    @Column(name = "name")
    String name;
    @Column(name = "password")
    String password;
    @Column(name = "role")
    String role;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column(name = "birthday")
    Date birthday;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner", cascade = CascadeType.MERGE)
    List<KittyEntity> kitties;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<KittyEntity> getKitties() {
        return kitties;
    }

    public void setKitties(List<KittyEntity> kitties) {
        this.kitties = kitties;
    }

    public OwnerEntity addKittie(KittyEntity kittie) {
        if (this.kitties.stream().anyMatch(x -> x.getUuid().equals(kittie.getUuid()))) {
            delKittie(kittie);
        }
        this.kitties.add(kittie);
        return this;
    }

    public OwnerEntity delKittie(KittyEntity kittie) {
        if (this.kitties.stream().noneMatch(x -> x.getUuid().equals(kittie.getUuid()))) return this;
        this.kitties.remove(this.kitties.stream().filter(x -> x.getUuid().equals(kittie.getUuid())).toList().get(0));
        return this;
    }
}
