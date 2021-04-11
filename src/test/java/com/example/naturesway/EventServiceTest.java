package com.example.naturesway;

import com.example.naturesway.domain.entities.Event;
import com.example.naturesway.domain.serviceModels.EventServiceModel;
import com.example.naturesway.error.EventAlreadyExistException;
import com.example.naturesway.error.RecordNotFoundException;
import com.example.naturesway.repository.EventRepository;
import com.example.naturesway.service.impl.EventServiceImpl;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {
    private EventServiceModel eventServiceModel = new EventServiceModel();
    private Event event = new Event();

    private static final String RANDOM_ID = "TestRandomId";
    private static final String NAME = "Test Event";
    private static final Date TODAY_DATE_FOR_TEST_EVENT = LocalDate.now().toDate();
    private static final String LOCATION = "Test Location";
    private static final String PROGRAMME = "Test Programme";

    @InjectMocks
    EventServiceImpl eventService;

    @Mock
    EventRepository eventRepository;

    @Mock
    ModelMapper mapper;

    @Before
    public void init(){
        ModelMapper actualMapper = new ModelMapper();

        /**
         * init mocked attitude of the entities and service models
         */
        when(mapper.map(any(EventServiceModel.class), eq(Event.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], Event.class));

        when(mapper.map(any(Event.class), eq(EventServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], EventServiceModel.class));


        /**
         * create event_models
         */
        eventServiceModel.setId(RANDOM_ID);
        eventServiceModel.setName(NAME);
        eventServiceModel.setEventDate(TODAY_DATE_FOR_TEST_EVENT);
        eventServiceModel.setLocation(LOCATION);
        eventServiceModel.setProgramme(PROGRAMME);

        /**
         * create event
         */
        event.setId(RANDOM_ID);
        event.setName(NAME);
        event.setEventDate(TODAY_DATE_FOR_TEST_EVENT);
        event.setLocation(LOCATION);
        event.setProgramme(PROGRAMME);
    }

    @Test
    public void addEvent_shouldSaveCorrectly_whenEventDontPersistInDB(){
        when(eventRepository.findByName(NAME))
                .thenReturn(Optional.empty());

        eventService.addEvent(eventServiceModel);

        verify(eventRepository).save(any());
    }

    @Test(expected = EventAlreadyExistException.class)
    public void addEvent_shouldThrowException_whenEventPersistInDB(){
        when(eventRepository.findByName(NAME))
                .thenReturn(Optional.of(new Event()));

        eventService.addEvent(eventServiceModel);
    }

    @Test
    public void findAll_shouldReturnAllEvents(){
        when(eventRepository.findAll())
                .thenReturn(List.of(event));

        List<EventServiceModel> result = eventService.findAll();

        assertEquals(1, result.size());
        assertEquals(NAME, result.get(0).getName());
    }

    @Test
    public void findAll_shouldReturnEmptyCollection() {
        when(eventRepository.findAll())
                .thenReturn(new ArrayList<>());

        List<EventServiceModel> result = eventService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    public void deleteById_shouldDeleteRecord(){
        eventService.deleteEventById(event.getId());

        verify(eventRepository, times(1)).deleteById(event.getId());
    }

    @Test
    public void findById_shouldReturnCorrectEvent_whenIdIsValid(){
        when(eventRepository.findById(any()))
                .thenReturn(Optional.of(event));

        Optional<Event> eventResult = eventRepository.findById(RANDOM_ID);

        assertEquals(RANDOM_ID, eventResult.get().getId());
        assertEquals(NAME, eventResult.get().getName());
    }

    @Test(expected = RecordNotFoundException.class)
    public void findById_shouldThrowException_whenIdIsNotValid(){
        when(eventRepository.findById(any()))
                .thenReturn(Optional.empty());

        eventService.findById(RANDOM_ID);
    }

    @Test
    public void editById_shouldEditEventCorrectly(){
        when(eventRepository.findById(any()))
                .thenReturn(Optional.of(event));

        eventService.editById(RANDOM_ID, eventServiceModel);

        verify(eventRepository).save(any());
    }

    @Test(expected = RecordNotFoundException.class)
    public void editById_shouldThrowException_WhenIdIsNotValid(){
        when(eventRepository.findById(any()))
                .thenReturn(Optional.empty());

        eventService.editById(RANDOM_ID, eventServiceModel);
    }
}
