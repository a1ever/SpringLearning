package ru.verbitskiy.Mappers;

import org.springframework.stereotype.Service;
import ru.verbitskiy.Entities.OwnerEntity;
import ru.verbitskiy.models.OwnerModel;

@Service
public interface OwnerMapper {
    OwnerModel EntityToModel(OwnerEntity entity);

    OwnerEntity ModelToEntity(OwnerModel model);
}
