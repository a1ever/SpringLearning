package ru.verbitskiy.Mappers;

import org.springframework.stereotype.Service;
import ru.verbitskiy.Entities.KittyEntity;
import ru.verbitskiy.models.KittyModel;

@Service
public interface KittyMapper {
    KittyModel EntityToModel(KittyEntity kittyEntity) ;

    KittyEntity ModelToEntity(KittyModel kittyModel) ;
}
