package snsproject.snsproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import snsproject.snsproject.model.entity.CommentEntity;
import snsproject.snsproject.model.entity.PostEntity;
import snsproject.snsproject.model.entity.UserEntity;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Comment {
    private Integer id;

    private String comment;

    private String userName;

    private Integer postId;

    private Timestamp registeredAt;

    private Timestamp updatedAt;

    private Timestamp deletedAt;

    public static Comment fromEntity(CommentEntity entity) {
        return new Comment(
                entity.getId(),
                entity.getComment(),
                entity.getUser().getUserName(),
                entity.getPost().getId(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }
}
