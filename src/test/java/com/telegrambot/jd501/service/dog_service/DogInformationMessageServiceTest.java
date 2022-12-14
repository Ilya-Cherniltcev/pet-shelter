package com.telegrambot.jd501.service.dog_service;

import com.telegrambot.jd501.exceptions.InformationMessageNotFoundException;
import com.telegrambot.jd501.model.dog.DogInformationMessage;
import com.telegrambot.jd501.repository.dog.DogInformationMessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.telegrambot.jd501.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DogInformationMessageServiceTest {
    
    @Mock
    private DogInformationMessageRepository dogInformationMessageRepository;

    @InjectMocks
    private DogInformationMessageService out;

    @Test
    void getAllDogInformationMessage() {
        when(dogInformationMessageRepository.findAll()).thenReturn(DOG_INFORMATION_MESSAGE_LIST);

        assertEquals(out.getAllInformationMessages(), DOG_INFORMATION_MESSAGE_LIST);
    }

    @Test
    void createDogInformationMessage() {
        when(dogInformationMessageRepository.save(any(DogInformationMessage.class))).thenReturn(DOG_INFORMATION_MESSAGE_1);

        assertEquals(out.createInformationMessage(DOG_INFORMATION_MESSAGE_1), DOG_INFORMATION_MESSAGE_1);
    }

    @Test
    void updateDogInformationMessage() {
        when(dogInformationMessageRepository.findById(anyLong())).thenReturn(Optional.of(DOG_INFORMATION_MESSAGE_1));
        when(dogInformationMessageRepository.save(any(DogInformationMessage.class))).thenReturn(DOG_INFORMATION_MESSAGE_11);

        assertEquals(out.updateInformationMessage(DOG_INFORMATION_MESSAGE_11), DOG_INFORMATION_MESSAGE_11);
    }

    @Test
    void deleteDogInformationMessage() {
        when(dogInformationMessageRepository.findById(anyLong())).thenReturn(Optional.of(DOG_INFORMATION_MESSAGE_1));

        assertEquals(out.deleteInformationMessage(ID1), DOG_INFORMATION_MESSAGE_1);
    }

    @Test
    void findDogInformationMessageById()  {
        when(dogInformationMessageRepository.findById(anyLong())).thenReturn(Optional.of(DOG_INFORMATION_MESSAGE_2));

        assertEquals(out.findInformationMessageById(ID2), DOG_INFORMATION_MESSAGE_2);
    }
    @Test
    void informationMessageNotFoundExceptionTest1() {
        when(dogInformationMessageRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(InformationMessageNotFoundException.class,() ->out.deleteInformationMessage(ID2));
    }
    @Test
    void informationMessageNotFoundExceptionTest2() {
        when(dogInformationMessageRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(InformationMessageNotFoundException.class,() ->out.updateInformationMessage(DOG_INFORMATION_MESSAGE_2));
    }
}