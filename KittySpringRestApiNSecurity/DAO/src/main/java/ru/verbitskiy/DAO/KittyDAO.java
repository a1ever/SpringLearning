package ru.verbitskiy.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.verbitskiy.Entities.KittyEntity;

import java.util.UUID;

@Repository
public interface KittyDAO extends JpaRepository<KittyEntity, UUID> {
}
