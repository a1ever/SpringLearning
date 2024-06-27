package ru.verbitskiy.Operations;

import org.springframework.stereotype.Service;
import ru.verbitskiy.models.OwnerModel;

import java.util.List;
import java.util.UUID;

@Service
public interface OwnerDataService{
    void add(OwnerModel model) ;

    void update(OwnerModel model);

    OwnerModel getByID(UUID id) ;

    void delete(OwnerModel model) ;

    List<OwnerModel> getAll() ;
}
