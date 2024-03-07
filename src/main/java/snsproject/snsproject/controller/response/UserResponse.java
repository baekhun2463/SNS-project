package snsproject.snsproject.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import snsproject.snsproject.model.User;
import snsproject.snsproject.model.UserRole;

@Getter
@AllArgsConstructor
public class UserResponse {

    private Integer id;
    private String userName;
    private UserRole role;

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getUserRole()
        );
    }



}
