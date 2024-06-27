package ru.verbitskiy.Operations;

import org.springframework.stereotype.Service;
import ru.verbitskiy.models.KittyModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface KittyDataService {

    void add(KittyModel kittyModel);

    void update(KittyModel kittyModel) ;

    Optional<KittyModel> getByID(UUID id);

    void delete(KittyModel kittyModel) ;

    List<KittyModel> getAll() ;
}
