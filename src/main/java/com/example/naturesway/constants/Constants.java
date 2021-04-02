package com.example.naturesway.constants;

public final class Constants {

    //roles
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_MODERATOR = "ROLE_MODERATOR";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_ROOT = "ROLE_ROOT";

    //error massages
    public static final String USERNAME_NOT_FOUND = "Username not found!";
    public static final String PASSWORDS_DONT_MATCH = "Old password dont match current password!";
    public static final String INCORRECT_ID = "Incorrect id!";
    public static final String INCORRECT_AUTHORITY = "Incorrect authority name!";

    public static final String DUPLICATE_USERNAME = "Username already exist!";
    public static final String DUPLICATE_EMAIL = "Email already exist!";
    public static final String DUPLICATE_ADVENTURE= "Adventure already exist!";
    public static final String DUPLICATE_EVENT = "Event already exist!";
    public static final String DUPLICATE_LIVING_TIP = "Living Tip already exist!";

    //LocalDateTime format pattern
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    //validation
    public static final String EMAIL_PATTERN_STRING ="^((\"[\\w-\\s]+\")|([\\w-]+(?:\\.[\\w-]+)*)|(\"[\\w-\\s]+\")([\\w-]+(?:\\.[\\w-]+)*))(@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$)|(@\\[?((25[0-5]\\.|2[0-4][0-9]\\.|1[0-9]{2}\\.|[0-9]{1,2}\\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\]?$)";
    public static final String INVALID_EMAIL_MESSAGE = "Invalid email.";
    public static final String NULL_EMAIL_MESSAGE = "Email cannot be null.";
    public static final String EMPTY_EMAIL_MESSAGE = "Email cannot be empty.";

    public static final String INVALID_USERNAME_LENGTH_MESSAGE = "Username must be between 3 and 15 characters long.";
    public static final String NULL_USERNAME_MESSAGE = "Username cannot be null.";
    public static final String EMPTY_USERNAME_MESSAGE = "Username cannot be empty.";

    public static final String INVALID_PASSWORD_LENGTH_MESSAGE = "Password must be between 4 and 20 symbols long.";
    public static final String NULL_PASSWORD_MESSAGE = "Password cannot be null.";
    public static final String EMPTY_PASSWORD_MESSAGE = "Password cannot be empty.";

    public static final String INVALID_FIRST_NAME_LENGTH_MESSAGE = "First name must be at least 2 symbols long.";
    public static final String INVALID_FIRST_NAME_CAPITAL_CASE_MESSAGE = "First name must start with capital letter.";
    public static final String NULL_FIRST_NAME_MESSAGE = "First name cannot be null.";
    public static final String EMPTY_FIRST_NAME_MESSAGE = "First name cannot be empty.";

    public static final String INVALID_LAST_NAME_LENGTH_MESSAGE = "Last name must be at least 2 symbols long.";
    public static final String INVALID_LAST_NAME_CAPITAL_CASE_MESSAGE = "Last name must start with capital letter.";
    public static final String NULL_LAST_NAME_MESSAGE = "Last name cannot be null.";
    public static final String EMPTY_LAST_NAME_MESSAGE = "Last name cannot be empty.";

    public static final String NULL_ADVENTURE_NAME_MESSAGE = "Adventure name cannot be null.";
    public static final String EMPTY_ADVENTURE_NAME_MESSAGE = "Adventure name cannot be empty.";
    public static final String INVALID_ADVENTURE_NAME_MESSAGE = "Adventure name must be between 3 and 30 characters long.";

    public static final String NULL_ADVENTURE_CATEGORY_MESSAGE = "Adventure category cannot be null.";
    public static final String EMPTY_ADVENTURE_CATEGORY_MESSAGE = "Adventure category cannot be empty.";
    public static final String INVALID_ADVENTURE_CATEGORY_MESSAGE = "Adventure category must be between 3 and 30 characters long.";

    public static final String NULL_ADVENTURE_LEVEL_MESSAGE = "Adventure level cannot be null.";
    public static final String INVALID_ADVENTURE_LEVEL_MESSAGE = "Adventure level must be not less than 1 and not higher from 3.";

    public static final String NULL_ADVENTURE_TIPS_MESSAGE = "Adventure tips cannot be null.";
    public static final String EMPTY_ADVENTURE_TIPS_MESSAGE = "Adventure tips cannot be empty.";

    public static final String NULL_ADVENTURE_REQUIRED_EQUIPMENT_MESSAGE = "Adventure required equipment cannot be null.";
    public static final String EMPTY_ADVENTURE_REQUIRED_EQUIPMENT_MESSAGE = "Adventure required equipment cannot be empty.";

    public static final String NULL_ADVENTURE_DURATION_MESSAGE = "Adventure duration cannot be null.";
    public static final String EMPTY_ADVENTURE_DURATION_MESSAGE = "Adventure duration cannot be empty.";
    public static final String INVALID_ADVENTURE_DURATION_MESSAGE = "Adventure duration must be at less 3 characters long.";

    public static final String NULL_ADVENTURE_DESCRIPTION_MESSAGE = "Adventure description cannot be null.";
    public static final String EMPTY_ADVENTURE_DESCRIPTION_MESSAGE = "Adventure description cannot be empty.";
    public static final String INVALID_ADVENTURE_DESCRIPTION_MESSAGE = "Adventure description must be at less 3 characters long.";

    public static final String NULL_EVENT_NAME_MESSAGE = "Event name cannot be null.";
    public static final String EMPTY_EVENT_NAME_MESSAGE = "Event name cannot be empty.";
    public static final String INVALID_EVENT_NAME_MESSAGE = "Event name must be between 3 and 30 characters long.";

    public static final String NULL_EVENT_DATE_MESSAGE = "Event date cannot be null.";
    public static final String FUTURE_OR_PRESENT_EVENT_DATE_MESSAGE = "The event date cannot be in the past!";

    public static final String NULL_EVENT_LOCATION_MESSAGE = "Event location cannot be null.";
    public static final String EMPTY_EVENT_LOCATION_MESSAGE = "Event location cannot be empty.";
    public static final String INVALID_EVENT_LOCATION_MESSAGE = "Event location must be between 3 and 30 characters long.";

    public static final String NULL_EVENT_PROGRAMME_MESSAGE = "Event location cannot be null.";
    public static final String EMPTY_EVENT_PROGRAMME_MESSAGE = "Event location cannot be empty.";
    public static final String INVALID_EVENT_PROGRAMME_MESSAGE = "Event location must be at less 3 characters long.";

    public static final String NULL_LIVING_TIP_NAME_MESSAGE = "Living Tip name cannot be null.";
    public static final String EMPTY_LIVING_TIP_NAME_MESSAGE = "Living Tip name cannot be empty.";
    public static final String INVALID_LIVING_TIP_NAME_MESSAGE = "Living Tip name must be between 3 and 30 characters long.";

    public static final String NULL_LIVING_CATEGORY_MESSAGE = "Living Tip category cannot be null.";

    public static final String NULL_LIVING_TIP_DESCRIPTION_MESSAGE = "Living Tip description cannot be null.";
    public static final String EMPTY_LIVING_TIP_DESCRIPTION_MESSAGE = "Living Tip description cannot be empty.";
    public static final String INVALID_LIVING_TIP_DESCRIPTION_MESSAGE = "Living Tip description must be at less 3 characters long.";

    public static final String NULL_LIVING_TIP_USABILITY_MESSAGE = "Living Tip usability cannot be null.";
    public static final String EMPTY_LIVING_TIP_USABILITY_MESSAGE = "Living Tip usability cannot be empty.";
    public static final String INVALID_LIVING_TIP_USABILITY_MESSAGE = "Living Tip usability must be at less 3 characters long.";

}
