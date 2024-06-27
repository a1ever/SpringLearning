package ru.verbitskiy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.verbitskiy.Operations.OwnerDataService;
import ru.verbitskiy.controllerMappers.OwnerControllerMapper;
import ru.verbitskiy.data.Owner;

import java.util.UUID;

@Service("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final OwnerDataService owner_data;
    private final OwnerControllerMapper mapper;

    @Autowired
    public UserDetailsServiceImpl(OwnerDataService ownerDataService, OwnerControllerMapper ownerControllerMapper) {
        owner_data = ownerDataService;
        this.mapper = ownerControllerMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Owner owner = mapper.ModelToData(owner_data.getByID(UUID.fromString(username)));
        return new User(owner.getName(), owner.getPassword(), Authorities.getAuthorities(owner.getRole()));
    }
}
