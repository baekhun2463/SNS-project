package snsproject.snsproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import snsproject.snsproject.exception.SnsApplicationException;
import snsproject.snsproject.model.User;
import snsproject.snsproject.model.entity.UserEntity;
import snsproject.snsproject.repository.UserEntityRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;

    public User join(String userName, String password) {

        Optional<UserEntity> userEntity = userEntityRepository.findByUserName(userName);

        userEntityRepository.save(new UserEntity());

        return new User();
    }

    public String login(String userName, String password) {

        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException());

        if(!userEntity.getPassword().equals(password)) {
            throw new SnsApplicationException();
        }

        return "";
    }
}
