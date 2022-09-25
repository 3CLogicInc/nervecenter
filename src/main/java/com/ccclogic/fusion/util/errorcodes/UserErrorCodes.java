package com.ccclogic.fusion.util.errorcodes;

public interface UserErrorCodes {
    String userNotFound = "user_not_found";
    
    String invalidUserId = "invalid_user_id";
    String adminCannotBeAssigned = "admin_cannot_be_assigned";
    String emailCannotBeEmpty = "email_cannot_be_empty";
    String usernameAliasCannotBeEmpty = "usernameAlias_cannot_be_empty";
    String nameCannotBeEmpty = "name_cannot_be_empty";
    String invalidUserType = "invalid_user_type";
    String invalidUsername = "invalid_username";
    String invalidNameLength = "invalid_name_length";
    String invalidPasswordLength = "invalid_password_length";
    String userNotInProject = "user_not_assigned";
    String invalidUsernameConditions= "invalid_username_criteria";
    String invalidUserAtCustomer = "invalid_user_at_customer_name";
}
