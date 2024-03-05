package snsproject.snsproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import snsproject.snsproject.controller.request.UserJoinRequest;
import snsproject.snsproject.controller.response.Response;
import snsproject.snsproject.controller.response.UserJoinResponse;
import snsproject.snsproject.model.User;
import snsproject.snsproject.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request){
        User user = userService.join(request.getUserName(), request.getPassword());
        return Response.success(UserJoinResponse.fromUser(user));
    }
}
