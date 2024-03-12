package snsproject.snsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import snsproject.snsproject.model.entity.LikeEntity;
import snsproject.snsproject.model.entity.PostEntity;
import snsproject.snsproject.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity, Integer> {
    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

    @Query(value = "SELECT COUNT(*) FROM LikeEntity entity WHERE entity.post = :post")
    Integer countByPost(@Param("post")PostEntity post);

    List<LikeEntity> findAllByPost(PostEntity post);
}
