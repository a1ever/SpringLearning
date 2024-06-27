package ru.verbitskiy.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.verbitskiy.Entities.OwnerEntity;

import java.util.UUID;

@Repository
public interface OwnerDAO extends JpaRepository<OwnerEntity, UUID> {
}
