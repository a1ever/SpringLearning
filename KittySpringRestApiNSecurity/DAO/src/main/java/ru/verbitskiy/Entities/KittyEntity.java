package ru.verbitskiy.Entities;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "kitty")
public class KittyEntity {
    public KittyEntity(String name, Date birthday, String race, String color, OwnerEntity owner, List<KittyEntity> friends) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.birthday = birthday;
        this.race = race;
        this.color = color;
        this.owner = owner;
        this.friends = friends;
    }

    public KittyEntity(UUID id, String name, Date birthday, String race, String color, OwnerEntity owner, List<KittyEntity> friends) {
        this.uuid = id;
        this.name = name;
        this.birthday = birthday;
        this.race = race;
        this.color = color;
        this.owner = owner;
        this.friends = friends;
    }

    @Id
    @Column(name = "uuid")
    UUID uuid;
    @Column(name = "name")
    String name;
    @Column(name = "birthday")
    Date birthday;
    @Column(name = "race")
    String race;
    @Column(name = "color")
    String color;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "uuid")
    OwnerEntity owner;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "kitty_kitty",
            joinColumns = @JoinColumn(name = "first_one"),
            inverseJoinColumns = @JoinColumn(name = "second_one")
    )
    List<KittyEntity> friends;

    public KittyEntity() {
    }

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

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public OwnerEntity getOwner() {
        return owner;
    }

    public KittyEntity setOwner(OwnerEntity owner) {
        this.owner = owner;
        return this;
    }

    public List<KittyEntity> getFriends() {
        return friends;
    }

    public void setFriends(List<KittyEntity> friends) {
        this.friends = friends;
    }

    public KittyEntity addFriend(KittyEntity friend) {
        if (this.friends.stream().anyMatch(x -> x.getUuid().equals(friend.getUuid()))){
            delFriend(friend);
        }
        this.friends.add(friend);
        return this;
    }

    public KittyEntity delFriend(KittyEntity friend) {
        if (this.friends.stream().noneMatch(x -> x.getUuid().equals(friend.getUuid()))) return this;
        this.friends.remove(this.friends.stream().filter(x -> x.getUuid().equals(friend.getUuid())).toList().get(0));
        return this;
    }

}
