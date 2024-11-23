package choorai.retrospect.user.service;

import choorai.retrospect.user.entity.User;
import choorai.retrospect.user.entity.repository.UserRepository;
import choorai.retrospect.user.exception.UserErrorCode;
import choorai.retrospect.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public User getCurrentUserWithCards() {
        final User currentUser = getCurrentUser();
        final Long id = currentUser.getId();

        return userRepository.findWithCardsById(id)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND_FOR_ID));
    }

}
