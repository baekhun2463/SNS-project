package snsproject.snsproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import snsproject.snsproject.exception.ErrorCode;
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
        // 같은 이름이 있는지
        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated",userName));
        });

        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, password));

        return User.fromEntity(userEntity);
    }

    public String login(String userName, String password) {

        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, ""));

        if(!userEntity.getPassword().equals(password)) {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME,"");
        }

        return "";
    }
}
