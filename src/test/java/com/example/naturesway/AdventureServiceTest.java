package com.example.naturesway;

import com.example.naturesway.domain.entities.Adventure;
import com.example.naturesway.domain.enumerations.AdventureCategoryEnum;
import com.example.naturesway.domain.serviceModels.AdventureServiceModel;
import com.example.naturesway.error.AdventureAlreadyExistException;
import com.example.naturesway.error.RecordNotFoundException;
import com.example.naturesway.repository.AdventureRepository;
import com.example.naturesway.service.impl.AdventureServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;


import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdventureServiceTest {

    private AdventureServiceModel adventureServiceModel = new AdventureServiceModel();
    private Adventure adventure = new Adventure();

    private static final String RANDOM_ID = "TestRandomId";
    private static final String NAME = "Test Adventure";
    private static final String NEW_NAME = "NEW Test Adventure";
    private static final AdventureCategoryEnum CATEGORY = AdventureCategoryEnum.MOUNTAIN;
    private static final String COUNTRY = "Test Country";
    private static final Integer LEVEL = 1;
    private static final String TIPS = "Test Tips";
    private static final String REQUIRED_EQUIPMENTS = "Test Required Equipments";
    private static final String DURATION = "Test Duration";
    private static final String DESCRIPTION = "Test Description";
    private static final String IMAGE_URL = "test.image.url.com";


    @InjectMocks
    AdventureServiceImpl adventureService;

    @Mock
    AdventureRepository adventureRepository;

    @Mock
    ModelMapper mapper;

    @Before
    public void init(){
        ModelMapper actualMapper = new ModelMapper();

        /**
         * init mocked attitude of the entites and service models
         */
        when(mapper.map(any(AdventureServiceModel.class), eq(Adventure.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], Adventure.class));

        when(mapper.map(any(Adventure.class), eq(AdventureServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], AdventureServiceModel.class));


        /**
         * create adventure_models
         */
        adventureServiceModel.setId(RANDOM_ID);
        adventureServiceModel.setName(NAME);
        adventureServiceModel.setCategory(CATEGORY);
        adventureServiceModel.setCountry(COUNTRY);
        adventureServiceModel.setLevel(LEVEL);
        adventureServiceModel.setTips(TIPS);
        adventureServiceModel.setRequiredEquipment(REQUIRED_EQUIPMENTS);
        adventureServiceModel.setDuration(DURATION);
        adventureServiceModel.setDescription(DESCRIPTION);
        adventureServiceModel.setImageUrl(IMAGE_URL);

        /**
         * create adventures
         */
        adventure.setId(RANDOM_ID);
        adventure.setName(NAME);
        adventure.setCategory(CATEGORY);
        adventure.setCountry(COUNTRY);
        adventure.setLevel(LEVEL);
        adventure.setTips(TIPS);
        adventure.setRequiredEquipment(REQUIRED_EQUIPMENTS);
        adventure.setDuration(DURATION);
        adventure.setDescription(DESCRIPTION);
        adventure.setImageUrl(IMAGE_URL);
    }

    @Test
    public void addAdventure_shouldSaveCorrectly_whenAdventureDontPersistInDB(){
        when(adventureRepository.findByName(NAME))
                .thenReturn(Optional.empty());

        adventureService.addAdventure(adventureServiceModel);

        verify(adventureRepository).save(any());
    }

    @Test(expected = AdventureAlreadyExistException.class)
    public void addAdventure_shouldThrowException_whenAdventurePersistInDB(){
        when(adventureRepository.findByName(NAME))
                .thenReturn(Optional.of(new Adventure()));

        adventureService.addAdventure(adventureServiceModel);
    }

    @Test
    public void findAll_shouldReturnAllAdventures(){
        when(adventureRepository.findAll())
                .thenReturn(List.of(adventure));

        List<AdventureServiceModel> result = (List<AdventureServiceModel>) adventureService.findAll();

        assertEquals(1, result.size());
        assertEquals(NAME, result.get(0).getName());
    }

    @Test
    public void findAll_shouldReturnEmptyCollection() {
        when(adventureRepository.findAll())
                .thenReturn(new ArrayList<>());

        List<AdventureServiceModel> result = (List<AdventureServiceModel>) adventureService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    public void deleteById_shouldDeleteRecord(){
        adventureService.deleteAdventureById(adventure.getId());

        verify(adventureRepository, times(1)).deleteById(adventure.getId());
    }

    @Test
    public void findById_shouldReturnCorrectAdventure_whenIdIsValid(){
        when(adventureRepository.findById(any()))
                .thenReturn(Optional.of(adventure));

        Optional<Adventure> adventureResult = adventureRepository.findById(RANDOM_ID);

        assertEquals(RANDOM_ID, adventureResult.get().getId());
        assertEquals(NAME, adventureResult.get().getName());
    }

    @Test(expected = RecordNotFoundException.class)
    public void findById_shouldThrowException_whenIdIsNotValid(){
        when(adventureRepository.findById(any()))
                .thenReturn(Optional.empty());

        adventureService.findById(RANDOM_ID);
    }

    @Test
    public void editById_shouldEditAdventureCorrectly(){
        when(adventureRepository.findById(any()))
                .thenReturn(Optional.of(adventure));

        adventureService.editById(RANDOM_ID, adventureServiceModel);

        verify(adventureRepository).save(any());
    }

    @Test(expected = RecordNotFoundException.class)
    public void editById_shouldThrowException_WhenIdIsNotValid(){
        when(adventureRepository.findById(any()))
                .thenReturn(Optional.empty());

        adventureService.editById(RANDOM_ID, adventureServiceModel);
    }

}
