package snsproject.snsproject.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import snsproject.snsproject.model.entity.UserEntity;

import java.sql.Timestamp;

// DTO : Data Transfer Object
@AllArgsConstructor
@Getter
public class User {

    private Integer id;
    private String userName;
    private String password;
    private UserRole userRole;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static User fromEntity(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUserName(),
                entity.getPassword(),
                entity.getRole(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }

}