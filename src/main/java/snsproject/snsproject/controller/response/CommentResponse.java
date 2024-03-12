package snsproject.snsproject.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import snsproject.snsproject.model.Comment;
import snsproject.snsproject.model.Post;
import snsproject.snsproject.model.User;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class CommentResponse {
    private Integer id;

    private String comment;

    private Integer postId;

    private String userName;

    private Timestamp registeredAt;

    private Timestamp updatedAt;

    private Timestamp deletedAt;

    public static CommentResponse fromComment(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getComment(),
                comment.getPostId(),
                comment.getUserName(),
                comment.getRegisteredAt(),
                comment.getUpdatedAt(),
                comment.getDeletedAt()
        );
    }
}
