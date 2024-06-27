package ru.verbitskiy.Operations;

import org.springframework.stereotype.Service;
import ru.verbitskiy.models.OwnerModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface OwnerDataService{

    void add(OwnerModel model) ;


    void update(OwnerModel model);


    Optional<OwnerModel> getByID(UUID id) ;

    void delete(OwnerModel model) ;


    List<OwnerModel> getAll() ;
}
