package snsproject.snsproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import snsproject.snsproject.exception.ErrorCode;
import snsproject.snsproject.exception.SnsApplicationException;
import snsproject.snsproject.model.Alarm;
import snsproject.snsproject.model.User;
import snsproject.snsproject.model.entity.UserEntity;
import snsproject.snsproject.repository.AlarmEntityRepository;
import snsproject.snsproject.repository.UserCacheRespository;
import snsproject.snsproject.repository.UserEntityRepository;
import snsproject.snsproject.util.JwtTokenUtils;


@Service
@RequiredArgsConstructor
public class UserService {

    private final AlarmEntityRepository alarmEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder encoder;
    private final UserCacheRespository cache;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    public User loadUserByUserName(String userName) {
        return cache.getUser(userName).orElseGet(() ->
                userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(() ->
                        new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)))
                );
    }

    @Transactional
    public User join(String userName, String password) {
        // 같은 이름이 있는지
        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated",userName));
        });

        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, encoder.encode(password)));

        return User.fromEntity(userEntity);
    }

    public String login(String userName, String password) {

        User user = loadUserByUserName(userName);

        cache.setUser(user);

        if(!encoder.matches(password, user.getPassword())) {
            throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        return JwtTokenUtils.generateAccessToken(userName, secretKey, expiredTimeMs);
    }

    public Page<Alarm> alarmList(Integer userId, Pageable pageable) {
        return alarmEntityRepository.findAllByUserId(userId, pageable).map(Alarm::fromEntity);
    }
}
