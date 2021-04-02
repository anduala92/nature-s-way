package com.example.naturesway;

import com.example.naturesway.domain.entities.Role;
import com.example.naturesway.domain.entities.User;
import com.example.naturesway.domain.serviceModels.RoleServiceModel;
import com.example.naturesway.domain.serviceModels.UserServiceModel;
import com.example.naturesway.error.EmailAlreadyExistException;
import com.example.naturesway.error.PasswordDontMatchException;
import com.example.naturesway.error.UserNotFoundException;
import com.example.naturesway.error.UsernameAlreadyExistException;
import com.example.naturesway.repository.UserRepository;
import com.example.naturesway.service.RoleService;
import com.example.naturesway.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserServiceModel USER_SERVICE_MODEL = new UserServiceModel();
    private User USER_ENTITY_MODEL;
    private User INVALID_MODEL = new User();


    private static final String VALID_USERNAME = "anduala92";
    private static final String VALID_FIRST_NAME = "Alexander";
    private static final String VALID_LAST_NAME = "Alexandrov";
    private static final String VALID_EMAIL = "alex@test.com";
    private static final String DTO_EMAIL = "dto@test.com";


    private static final String VALID_PASSWORD = "1234";
    private static final String VALID_EDITED_PASSWORD = "4321";
    private static final String VALID_EDITED_FIRST_NAME = "Test";
    private static final String VALID_EDITED_LAST_NAME = "Testov";
    private static final String VALID_EDITED_EMAIL = "test@test.com";

    private static final String ROLE_USER = "user";
    private static final String ROLE_MODERATOR = "moderator";
    private static final String ROLE_ADMIN = "admin";

    private static final String INVALID_OLD_PASSWORD = "Invalid_Password_1234321";
    private static final String RANDOM_ID = "00309000009pW6oAAE";


    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleService roleService;

    @Mock
    ModelMapper modelMapper;

    @Mock
    BCryptPasswordEncoder encoder;


    @Before
    public void init() {
        ModelMapper actualMapper = new ModelMapper();
        BCryptPasswordEncoder actualEncoder = new BCryptPasswordEncoder();

        when(modelMapper.map(any(UserServiceModel.class), eq(User.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], User.class));

        when(modelMapper.map(any(User.class), eq(UserServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], UserServiceModel.class));

        when(roleService.findByAuthority(anyString()))
                .thenAnswer(invocationOnMock -> actualMapper.map(new Role((String) invocationOnMock.getArguments()[0]),
                        RoleServiceModel.class));

        when(encoder.encode(any())).thenAnswer(invocationOnMock -> actualEncoder.encode((CharSequence) invocationOnMock.getArguments()[0]));
        when(encoder.matches(any(), any())).thenAnswer(invocationOnMock -> actualEncoder.matches((String) invocationOnMock.getArguments()[0], (String) invocationOnMock.getArguments()[1]));
        when(userRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        USER_SERVICE_MODEL.setUsername(VALID_USERNAME);
        USER_SERVICE_MODEL.setFirstName(VALID_FIRST_NAME);
        USER_SERVICE_MODEL.setLastName(VALID_LAST_NAME);
        USER_SERVICE_MODEL.setEmail(VALID_EMAIL);
        USER_SERVICE_MODEL.setPassword(VALID_PASSWORD);

        USER_ENTITY_MODEL = new User();
        USER_ENTITY_MODEL.setUsername(VALID_USERNAME);
        USER_ENTITY_MODEL.setFirstName(VALID_FIRST_NAME);
        USER_ENTITY_MODEL.setLastName(VALID_LAST_NAME);
        USER_ENTITY_MODEL.setEmail(VALID_EMAIL);
        USER_ENTITY_MODEL.setPassword(VALID_PASSWORD);
    }

    @Test
    public void registerUser_shouldSuccessfullyRegisterUser_whenValidUser() {
        //Act
        userService.registerUser(USER_SERVICE_MODEL);

        //Assert
        verify(userRepository).save(any());
    }

    @Test
    public void registerUser_shouldSuccessfullyRegisterSecondUserWithRoleUser_whenValidUser() {
        //Arrange
        when(userRepository.count())
                .thenReturn(1L);

        when(userRepository.save(any()))
                .then(invocationOnMock -> {
                    USER_ENTITY_MODEL = (User) invocationOnMock.getArguments()[0];
                    return USER_ENTITY_MODEL;
                });

        //Act
        userService.registerUser(USER_SERVICE_MODEL);

        //Assert
        verify(userRepository).save(any());
        assertEquals(1, USER_ENTITY_MODEL.getAuthorities().size());
    }

    @Test(expected = UsernameAlreadyExistException.class)
    public void registerUser_shouldThrowException_whenUserIsDuplicateByUsername() {
        //Arrange
        INVALID_MODEL.setUsername(VALID_USERNAME);
        when(userRepository.findByUsername(USER_SERVICE_MODEL.getUsername())).thenReturn(Optional.of(INVALID_MODEL));

        //Act
        userService.registerUser(USER_SERVICE_MODEL);
    }

    @Test(expected = EmailAlreadyExistException.class)
    public void registerUser_shouldThrowException_whenUserIsDuplicateByEmail() {
        //Arrange
        INVALID_MODEL.setEmail(VALID_EMAIL);
        when(userRepository.findByEmail(USER_SERVICE_MODEL.getEmail())).thenReturn(Optional.of(INVALID_MODEL));

        //Act
        userService.registerUser(USER_SERVICE_MODEL);
    }

    @Test
    public void findUserByUsername_shouldReturnCorrectUser_whenUsernameIsValid() {
        //Arrange
        when(userRepository.findByUsername(VALID_USERNAME))
                .thenReturn(Optional.of(USER_ENTITY_MODEL));

        //Act
        UserServiceModel user = userService.findUserByUsername(VALID_USERNAME);

        //Assert
        assertEquals(VALID_USERNAME, user.getUsername());
        assertEquals(VALID_EMAIL, user.getEmail());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void findUserByUsername_shouldThrowException_whenUsernameDontExistInDB() {
        //Act
        userService.findUserByUsername(VALID_USERNAME);
    }

    @Test
    public void editUserProfile_shouldEditAllFieldsOfTheUser_whenDataIsValid() {
        //Arrange
        when(userRepository.findByUsername(VALID_USERNAME))
                .thenReturn(Optional.of(USER_ENTITY_MODEL));

        USER_ENTITY_MODEL.setPassword(encoder.encode(USER_ENTITY_MODEL.getPassword()));

        UserServiceModel editedServiceModel = new UserServiceModel();
        editedServiceModel.setUsername(VALID_USERNAME);
        editedServiceModel.setPassword(VALID_EDITED_PASSWORD);
        editedServiceModel.setFirstName(VALID_EDITED_FIRST_NAME);
        editedServiceModel.setLastName(VALID_EDITED_LAST_NAME);
        editedServiceModel.setEmail(VALID_EDITED_EMAIL);

        //Act
        UserServiceModel newUserModel = userService.editUserProfile(editedServiceModel, VALID_PASSWORD, DTO_EMAIL);

        //Assert
        assertEquals(VALID_EDITED_EMAIL, newUserModel.getEmail());
        assertEquals(VALID_EDITED_FIRST_NAME, newUserModel.getFirstName());
        assertEquals(VALID_EDITED_LAST_NAME, newUserModel.getLastName());
        assertTrue(encoder.matches(VALID_EDITED_PASSWORD, newUserModel.getPassword()));
    }

    @Test
    public void editUserProfile_shouldEditAllFieldsOfTheUser_whenDataIsValidButOldPasswordIsTheSame() {
        //Arrange
        when(userRepository.findByUsername(VALID_USERNAME))
                .thenReturn(Optional.of(USER_ENTITY_MODEL));

        USER_ENTITY_MODEL.setPassword(encoder.encode(USER_ENTITY_MODEL.getPassword()));

        UserServiceModel editedServiceModel = new UserServiceModel();
        editedServiceModel.setUsername(VALID_USERNAME);
        editedServiceModel.setPassword("");
        editedServiceModel.setFirstName(VALID_EDITED_FIRST_NAME);
        editedServiceModel.setLastName(VALID_EDITED_LAST_NAME);
        editedServiceModel.setEmail(VALID_EDITED_EMAIL);

        //Act
        UserServiceModel newUserModel = userService.editUserProfile(editedServiceModel, VALID_PASSWORD, DTO_EMAIL);

        //Assert
        assertEquals(VALID_EDITED_EMAIL, newUserModel.getEmail());
        assertEquals(VALID_EDITED_FIRST_NAME, newUserModel.getFirstName());
        assertEquals(VALID_EDITED_LAST_NAME, newUserModel.getLastName());
        assertTrue(encoder.matches(VALID_PASSWORD, newUserModel.getPassword()));
    }

    @Test(expected = PasswordDontMatchException.class)
    public void editUserProfile_shouldThrowException_whenOldPasswordDontMatch() {
        //Arrange
        when(userRepository.findByUsername(VALID_USERNAME))
                .thenReturn(Optional.of(USER_ENTITY_MODEL));

        //Act
        userService.editUserProfile(USER_SERVICE_MODEL, INVALID_OLD_PASSWORD, DTO_EMAIL);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void editUserProfile_shouldThrowException_whenUsernameDontMatch() {
        //Act
        userService.editUserProfile(USER_SERVICE_MODEL, VALID_PASSWORD, DTO_EMAIL);
    }

    @Test(expected = EmailAlreadyExistException.class)
    public void editUserProfile_shouldThrowException_whenEmailExistInDB() {
        //Arrange
        when(userRepository.findByUsername(VALID_USERNAME))
                .thenReturn(Optional.of(USER_ENTITY_MODEL));

        USER_ENTITY_MODEL.setPassword(encoder.encode(USER_ENTITY_MODEL.getPassword()));

        when(userRepository.findByEmail(any()))
                .thenReturn(Optional.of(USER_ENTITY_MODEL));

        UserServiceModel editedServiceModel = new UserServiceModel();
        editedServiceModel.setUsername(VALID_USERNAME);
        editedServiceModel.setPassword(VALID_EDITED_PASSWORD);
        editedServiceModel.setFirstName(VALID_EDITED_FIRST_NAME);
        editedServiceModel.setLastName(VALID_EDITED_LAST_NAME);
        editedServiceModel.setEmail(VALID_EDITED_EMAIL);

        //Act
        userService.editUserProfile(editedServiceModel, VALID_PASSWORD, DTO_EMAIL);
    }

    @Test
    public void findAll_shouldReturnAllUsersInDB() {

        //Arrange
        when(userRepository.findAll())
                .thenReturn(List.of(USER_ENTITY_MODEL));

        //Act
        List<UserServiceModel> users = userService.findAll();

        //Assert
        assertEquals(1, users.size());
        assertEquals(VALID_USERNAME, users.get(0).getUsername());
    }

    @Test
    public void findAll_shouldReturnEmptyCollection_whenNoUsersInDb() {

        //Arrange
        when(userRepository.findAll())
                .thenReturn(new ArrayList<>());

        //Act
        List<UserServiceModel> users = userService.findAll();

        //Assert
        assertEquals(0, users.size());
    }

    @Test
    public void setUserRole_shouldChangeRoleToModerator_whenDataIsValid() {
        //Arrange
        USER_ENTITY_MODEL.setAuthorities(new HashSet<>());

        when(userRepository.findById(RANDOM_ID))
                .thenReturn(Optional.of(USER_ENTITY_MODEL));

        when(userRepository.save(any()))
                .then(invocationOnMock -> {
                    USER_ENTITY_MODEL = (User) invocationOnMock.getArguments()[0];
                    return USER_ENTITY_MODEL;
                });

        //Act
        userService.setUserRole(RANDOM_ID, ROLE_MODERATOR);

        //Assert
        verify(userRepository).save(any());
        assertEquals(2, USER_ENTITY_MODEL.getAuthorities().size());
    }

    @Test
    public void setUserRole_shouldChangeRoleToAdmin_whenDataIsValid() {
        //Arrange
        USER_ENTITY_MODEL.setAuthorities(new HashSet<>());

        when(userRepository.findById(RANDOM_ID))
                .thenReturn(Optional.of(USER_ENTITY_MODEL));

        when(userRepository.save(any()))
                .then(invocationOnMock -> {
                    USER_ENTITY_MODEL = (User) invocationOnMock.getArguments()[0];
                    return USER_ENTITY_MODEL;
                });

        //Act
        userService.setUserRole(RANDOM_ID, ROLE_ADMIN);

        //Assert
        verify(userRepository).save(any());
        assertEquals(3, USER_ENTITY_MODEL.getAuthorities().size());
    }

    @Test
    public void setUserRole_shouldChangeRoleToUser_whenDataIsValid() {
        //Arrange
        USER_ENTITY_MODEL.setAuthorities(new HashSet<>());

        when(userRepository.findById(RANDOM_ID))
                .thenReturn(Optional.of(USER_ENTITY_MODEL));

        when(userRepository.save(any()))
                .then(invocationOnMock -> {
                    USER_ENTITY_MODEL = (User) invocationOnMock.getArguments()[0];
                    return USER_ENTITY_MODEL;
                });

        //Act
        userService.setUserRole(RANDOM_ID, ROLE_USER);

        //Assert
        verify(userRepository).save(any());
        assertEquals(1, USER_ENTITY_MODEL.getAuthorities().size());
    }

    @Test(expected = UserNotFoundException.class)
    public void changeRoles_shouldThrowException_whenCalledWithInvalidUserIdParam() {

        //Arrange
        when(userRepository.findById(RANDOM_ID))
                .thenReturn(Optional.empty());

        //Act
        userService.setUserRole(RANDOM_ID, ROLE_USER);
    }
}