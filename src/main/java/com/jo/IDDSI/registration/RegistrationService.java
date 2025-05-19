package com.jo.IDDSI.registration;

import com.jo.IDDSI.appuser.AppUser;
import com.jo.IDDSI.appuser.AppUserRole;
import com.jo.IDDSI.appuser.AppUserService;
import com.jo.IDDSI.registration.token.ConfirmationToken;
import com.jo.IDDSI.registration.token.ConfirmationTokenService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

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

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken =
                confirmationTokenService
                        .getToken(token)
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "token not found"
                                ));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }
}
