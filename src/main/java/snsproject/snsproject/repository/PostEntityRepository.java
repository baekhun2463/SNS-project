package snsproject.snsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import snsproject.snsproject.model.entity.PostEntity;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, Integer> {



}
