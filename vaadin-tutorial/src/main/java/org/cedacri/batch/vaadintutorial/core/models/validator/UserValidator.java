package org.cedacri.batch.vaadintutorial.core.models.validator;

import com.vaadin.copilot.shaded.commons.lang3.StringUtils;
import jakarta.validation.ValidationException;
import org.cedacri.batch.vaadintutorial.core.models.entity.User;

public class UserValidator {

    public static void validate(User user){
        if(!validateLogin(user.getLogin())){
            throw new ValidationException("Invalid login, it must contain at least 5 chars");
        }
        if(!validateEmail(user.getEmail())){
            throw new ValidationException("Invalid email, it must contain at least 5 chars");
        }
        if(!validateFullName(user.getFullName())){
            throw new ValidationException("Invalid full name, it must contain name and surname");
        }
    }

    private static boolean validateLogin(String login){
        return StringUtils.isNotBlank(login) && login.length() >= 5;
    }

    private static boolean validateFullName(String fullName){
        return StringUtils.isNotBlank(fullName) && fullName.contains(" ");
    }

    private static boolean validateEmail(String email){
        return StringUtils.isNotBlank(email) && email.contains("@") && email.length() > 8;
    }

}
