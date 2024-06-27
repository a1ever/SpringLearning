package ru.verbitskiy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.verbitskiy.DAO.KittyDAO;
import ru.verbitskiy.DAO.OwnerDAO;
import ru.verbitskiy.Entities.KittyEntity;
import ru.verbitskiy.Entities.OwnerEntity;
import ru.verbitskiy.data.Kitty;
import ru.verbitskiy.data.KittyColor;
import ru.verbitskiy.data.Owner;
import ru.verbitskiy.data.Role;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ControllerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerDAO ownerDAO;

    @MockBean
    private KittyDAO kittyDAO;

    ArgumentCaptor<KittyEntity> kent;
    ArgumentCaptor<OwnerEntity> notKent;

    ObjectMapper mapper;

    @BeforeEach
    void Startup() {
        mapper = new ObjectMapper();
        kent = ArgumentCaptor.forClass(KittyEntity.class);
        notKent = ArgumentCaptor.forClass(OwnerEntity.class);
    }

    @SneakyThrows
    @Test
    @WithMockUser(authorities = {"kitty/add"})
    void HelloKittyTest() {
        Kitty based_one = new Kitty("Based", new Date(2100, 1, 12), "Fast", KittyColor.Gray1, null, new ArrayList<>());
        mockMvc.perform(post("/kitty/add").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(based_one))).andExpect(status().isOk());

        Mockito.verify(kittyDAO, Mockito.times(1)).save(kent.capture());

        assertEquals(based_one.getUuid(), kent.getValue().getUuid());
        assertEquals(based_one.getName(), kent.getValue().getName());
        assertEquals(based_one.getColor().toString(), kent.getValue().getColor());
    }

    @SneakyThrows
    @Test
    @WithMockUser(authorities = {"kitty/add"})
    void AddFriendKittyTest() {
        Kitty based_one = new Kitty("Based", new Date(2100, 1, 12), "Fast", KittyColor.Gray1, null, new ArrayList<>());
        Kitty based_two = new Kitty("Based2", new Date(2000, 1, 12), "Fast", KittyColor.Gray50, null, new ArrayList<>(List.of(based_one.getUuid())));

        KittyEntity based_entity_one = new KittyEntity(based_one.getUuid(), based_one.getName(), based_one.getBirthday(), based_one.getRace(), based_one.getColor().toString(), null, new ArrayList<>());

        Mockito.when(kittyDAO.findById(based_one.getUuid())).thenReturn(Optional.of(based_entity_one));
        String json = mapper.writeValueAsString(based_two);

        mockMvc.perform(post("/kitty/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        Mockito.verify(kittyDAO, Mockito.atLeastOnce()).save(kent.capture());
        assertEquals(based_one.getUuid(), kent.getValue().getUuid());
        assertEquals(based_two.getUuid(), kent.getValue().getFriends().get(0).getUuid());
    }
    @SneakyThrows
    @Test
    @WithMockUser(authorities = {"owner/add"})
    void AddOwnerKittyTest() {
        Kitty based_one = new Kitty("Based", new Date(100, 1, 12), "Fast", KittyColor.Gray1, null, new ArrayList<>());
        KittyEntity based_entity_one = new KittyEntity(based_one.getUuid(), based_one.getName(), based_one.getBirthday(), based_one.getRace(), based_one.getColor().toString(), null, new ArrayList<>());
        Owner owner_ocherednyar = new Owner("Shaverman", "Pwd", Role.user, new Date(50, 1, 12), new ArrayList<>(List.of(based_one.getUuid())));

        Mockito.when(kittyDAO.findById(based_one.getUuid())).thenReturn(Optional.of(based_entity_one));

        String json = mapper.writeValueAsString(owner_ocherednyar);

        mockMvc.perform(post("/owner/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        Mockito.verify(kittyDAO, Mockito.times(1)).save(kent.capture());
        assertEquals(owner_ocherednyar.getUuid(), kent.getValue().getOwner().getUuid());
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "168560e5-ac59-4607-9ab3-6729b600444f", authorities = {"kitty/delete"})
    void DeleteKittyOwnerTest() {
        Kitty based_one = new Kitty("Based", new Date(100, 1, 12), "Fast", KittyColor.Gray1, null, new ArrayList<>());
        Owner owner_ocherednyar = new Owner(UUID.fromString("168560e5-ac59-4607-9ab3-6729b600444f"),"Shaverman", "Pwd", Role.user , new Date(50, 1, 12), new ArrayList<>(List.of(based_one.getUuid())));
        based_one.setOwner_id(owner_ocherednyar.getUuid());
        KittyEntity based_entity_one = new KittyEntity(based_one.getUuid(), based_one.getName(), based_one.getBirthday(), based_one.getRace(), based_one.getColor().toString(), null, new ArrayList<>());
        OwnerEntity ownerEntity = new OwnerEntity(owner_ocherednyar.getUuid(), owner_ocherednyar.getName(), owner_ocherednyar.getPassword(), owner_ocherednyar.getRole().toString(), owner_ocherednyar.getBirthday(), new ArrayList<>(List.of(based_entity_one)));
        based_entity_one.setOwner(ownerEntity);
        Mockito.when(ownerDAO.findById(ownerEntity.getUuid())).thenReturn(Optional.of(ownerEntity));
        Mockito.when(kittyDAO.findById(based_one.getUuid())).thenReturn(Optional.of(based_entity_one));

        mockMvc.perform(delete("/kitty/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(based_one)))
                .andExpect(status().isOk());

        Mockito.verify(ownerDAO, Mockito.times(1)).save(notKent.capture());
        var el = notKent.getValue();
        assertTrue(notKent.getValue()
                .getKitties().isEmpty());
    }

    @SneakyThrows
    @Test
    @WithMockUser(authorities = {"kitty/all", "owner/all"})
    void UpdateOwnerUpdateKittyTest() {
        Kitty based_one = new Kitty("Based", new Date(100, 1, 12), "Fast", KittyColor.Gray1, null, new ArrayList<>());
        Kitty based_two = new Kitty("Based2", new Date(100, 1, 12), "Fast", KittyColor.Gray50, null, new ArrayList<>(List.of(based_one.getUuid())));
        Owner owner_ocherednyar = new Owner("Shaverman", "Pwd", Role.user, new Date(50, 1, 12), new ArrayList<>(List.of(based_one.getUuid())));

        based_one.setOwner_id(owner_ocherednyar.getUuid());

        KittyEntity based_entity_one = new KittyEntity(based_one.getUuid(), based_one.getName(), based_one.getBirthday(), based_one.getRace(), based_one.getColor().toString(), null, new ArrayList<>());
        KittyEntity based_entity_two = new KittyEntity(based_two.getUuid(), based_two.getName(), based_two.getBirthday(), based_two.getRace(), based_two.getColor().toString(), null, new ArrayList<>());
        OwnerEntity ownerEntity = new OwnerEntity(owner_ocherednyar.getUuid(), owner_ocherednyar.getName(), owner_ocherednyar.getPassword(), owner_ocherednyar.getRole().toString(), owner_ocherednyar.getBirthday(), new ArrayList<>(List.of(based_entity_one)));
        based_entity_one.setOwner(ownerEntity);

        Mockito.when(ownerDAO.findById(ownerEntity.getUuid())).thenReturn(Optional.of(ownerEntity));
        Mockito.when(kittyDAO.findById(based_one.getUuid())).thenReturn(Optional.of(based_entity_one));
        Mockito.when(kittyDAO.findById(based_two.getUuid())).thenReturn(Optional.of(based_entity_two));

        owner_ocherednyar.setKitties_ids(new ArrayList<>(List.of(based_two.getUuid())));

        String json = mapper.writeValueAsString(owner_ocherednyar);

        mockMvc.perform(put("/owner/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        Mockito.verify(kittyDAO, Mockito.times(2)).save(kent.capture());
        List<KittyEntity> edited = kent.getAllValues();

        assertEquals(based_one.getUuid(), edited.get(0).getUuid());
        assertNull(edited.get(0).getOwner());
        assertEquals(based_two.getUuid(), edited.get(1).getUuid());
        assertEquals(owner_ocherednyar.getUuid(), edited.get(1).getOwner().getUuid());
    }
}
