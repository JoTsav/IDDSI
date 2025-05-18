package com.jo.IDDSI.registration;

import com.jo.IDDSI.appuser.AppUser;
import com.jo.IDDSI.appuser.AppUserRole;
import com.jo.IDDSI.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    // TODO : Check that email is valid or already stored, if not invoke a new mail
    public String register(RegistrationRequest request) {
        boolean isValidEmail =
                emailValidator.test(request.getEmail());
        if ( !isValidEmail) {
            throw new IllegalStateException(String.format("Email: '%s' is not valid", request.getEmail()));
        }
        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER // then registered as a user
                )
        );
    }
}
