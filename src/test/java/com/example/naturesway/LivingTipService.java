package com.example.naturesway;

import com.example.naturesway.domain.entities.Event;
import com.example.naturesway.domain.entities.LivingTip;
import com.example.naturesway.domain.enumerations.LivingTipEnum;
import com.example.naturesway.domain.serviceModels.EventServiceModel;
import com.example.naturesway.domain.serviceModels.LivingTipServiceModel;
import com.example.naturesway.error.LivingTipAlreadyExistException;
import com.example.naturesway.error.RecordNotFoundException;
import com.example.naturesway.repository.LivingTipRepository;
import com.example.naturesway.service.impl.LivingTipServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LivingTipService {
    private LivingTipServiceModel livingTipServiceModel = new LivingTipServiceModel();
    private LivingTip livingTip = new LivingTip();

    private static final String RANDOM_ID = "TestRandomId";
    private static final String NAME = "Test LivingTip";
    private static final LivingTipEnum CATEGORY = LivingTipEnum.EVERYDAY_HACKS;
    private static final String DESCRIPTION = "Test Description";
    private static final String USABILITY = "Test Usability";

    @InjectMocks
    LivingTipServiceImpl livingTipService;

    @Mock
    LivingTipRepository livingTipRepository;

    @Mock
    ModelMapper mapper;

    @Before
    public void init(){
        ModelMapper actualMapper = new ModelMapper();

        /**
         * init mocked attitude of the entities and service models
         */
        when(mapper.map(any(LivingTipServiceModel.class), eq(LivingTip.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], LivingTip.class));

        when(mapper.map(any(LivingTip.class), eq(LivingTipServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], LivingTipServiceModel.class));


        /**
         * create event_models
         */
        livingTipServiceModel.setId(RANDOM_ID);
        livingTipServiceModel.setName(NAME);
        livingTipServiceModel.setCategory(CATEGORY);
        livingTipServiceModel.setDescription(DESCRIPTION);
        livingTipServiceModel.setUsability(USABILITY);

        /**
         * create event
         */
        livingTip.setId(RANDOM_ID);
        livingTip.setName(NAME);
        livingTip.setCategory(CATEGORY);
        livingTip.setDescription(DESCRIPTION);
        livingTip.setUsability(USABILITY);
    }

    @Test
    public void addLivingTip_shouldSaveCorrectly_whenLivingTipDontPersistInDB(){
        when(livingTipRepository.findByName(NAME))
                .thenReturn(Optional.empty());

        livingTipService.addLivingTip(livingTipServiceModel);

        verify(livingTipRepository).save(any());
    }

    @Test(expected = LivingTipAlreadyExistException.class)
    public void addLivingTip_shouldThrowException_whenLivingTipPersistInDB(){
        when(livingTipRepository.findByName(NAME))
                .thenReturn(Optional.of(new LivingTip()));

        livingTipService.addLivingTip(livingTipServiceModel);
    }

    @Test
    public void findAll_shouldReturnAllLivingTips(){
        when(livingTipRepository.findAll())
                .thenReturn(List.of(livingTip));

        List<LivingTipServiceModel> result = livingTipService.findAll();

        assertEquals(1, result.size());
        assertEquals(NAME, result.get(0).getName());
    }

    @Test
    public void findAll_shouldReturnEmptyCollection() {
        when(livingTipRepository.findAll())
                .thenReturn(new ArrayList<>());

        List<LivingTipServiceModel> result = livingTipService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    public void deleteById_shouldDeleteRecord(){
        livingTipService.deleteLivingTipById(livingTip.getId());

        verify(livingTipRepository, times(1)).deleteById(livingTip.getId());
    }

    @Test
    public void findById_shouldReturnCorrectLivingTip_whenIdIsValid(){
        when(livingTipRepository.findById(any()))
                .thenReturn(Optional.of(livingTip));

        Optional<LivingTip> eventResult = livingTipRepository.findById(RANDOM_ID);

        assertEquals(RANDOM_ID, eventResult.get().getId());
        assertEquals(NAME, eventResult.get().getName());
    }

    @Test(expected = RecordNotFoundException.class)
    public void findById_shouldThrowException_whenIdIsNotValid(){
        when(livingTipRepository.findById(any()))
                .thenReturn(Optional.empty());

        livingTipService.findById(RANDOM_ID);
    }

    @Test
    public void editById_shouldEditLivingTipCorrectly(){
        when(livingTipRepository.findById(any()))
                .thenReturn(Optional.of(livingTip));

        livingTipService.editById(RANDOM_ID, livingTipServiceModel);

        verify(livingTipRepository).save(any());
    }

    @Test(expected = RecordNotFoundException.class)
    public void editById_shouldThrowException_WhenIdIsNotValid(){
        when(livingTipRepository.findById(any()))
                .thenReturn(Optional.empty());

        livingTipService.editById(RANDOM_ID, livingTipServiceModel);
    }
}
