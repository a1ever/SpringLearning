package ru.verbitskiy.controllerMappers;

import org.springframework.stereotype.Service;
import ru.verbitskiy.data.Kitty;
import ru.verbitskiy.models.KittyModel;

@Service
public interface KittyControllerMapper {

    KittyModel DataToModel(Kitty data);

    Kitty ModelToData(KittyModel model);
}
