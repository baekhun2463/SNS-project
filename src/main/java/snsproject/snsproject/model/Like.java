package snsproject.snsproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import snsproject.snsproject.model.entity.LikeEntity;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Like {
    private Integer id;
    private Integer userId;
    private String userName;
    private Integer postId;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static Like fromEntity(LikeEntity entity) {
        return new Like(
                entity.getId(),
                entity.getUser().getId(),
                entity.getUser().getUserName(),
                entity.getPost().getId(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }
}
