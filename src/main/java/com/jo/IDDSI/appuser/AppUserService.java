package com.jo.IDDSI.appuser;

import com.jo.IDDSI.registration.token.ConfirmationToken;
import com.jo.IDDSI.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service class responsible for handling user-related operations and interactions
 * with the {@link AppUserRepository}.
 *
 * This class implements the {@code UserDetailsService} interface, which is required
 * for integrating with Spring Security's user authentication system. It defines
 * the custom logic for loading user details based on the provided email.
 */
@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {// query against database

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ConfirmationTokenService confirmationTokenService;

    private final AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(
                                USER_NOT_FOUND_MSG, email)));
    }

    // Link to confirm email
    public String signUpUser(AppUser appUser) {

        boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
        if(userExists) {
            throw new IllegalStateException(
                    String.format("Email: %s already taken", appUser.getEmail()));
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);


        // TODO : Send confirmation token
        // Create a token -> save
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken =
                new ConfirmationToken(
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15), // token valid for 15 minutes TODO: Place in properties
                        appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        // TODO : Send email with token
        return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
