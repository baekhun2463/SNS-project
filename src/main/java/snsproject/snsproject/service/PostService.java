package snsproject.snsproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import snsproject.snsproject.exception.ErrorCode;
import snsproject.snsproject.exception.SnsApplicationException;
import snsproject.snsproject.model.entity.PostEntity;
import snsproject.snsproject.model.entity.UserEntity;
import snsproject.snsproject.repository.PostEntityRepository;
import snsproject.snsproject.repository.UserEntityRepository;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void create(String title, String body, String userName) {
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", userName)));

        PostEntity saved = postEntityRepository.save(PostEntity.of(title, body, userEntity));
    }
}
