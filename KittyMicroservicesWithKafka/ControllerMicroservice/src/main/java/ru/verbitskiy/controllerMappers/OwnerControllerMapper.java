package ru.verbitskiy.controllerMappers;

import org.springframework.stereotype.Service;
import ru.verbitskiy.data.Owner;
import ru.verbitskiy.models.OwnerModel;

@Service
public interface OwnerControllerMapper {
    OwnerModel DataToModel(Owner data);

    Owner ModelToData(OwnerModel model);
}
