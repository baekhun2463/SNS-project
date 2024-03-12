package snsproject.snsproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import snsproject.snsproject.controller.request.UserJoinRequest;
import snsproject.snsproject.controller.request.UserLoginRequest;
import snsproject.snsproject.controller.response.AlarmResponse;
import snsproject.snsproject.controller.response.Response;
import snsproject.snsproject.controller.response.UserJoinResponse;
import snsproject.snsproject.controller.response.UserLoginResponse;
import snsproject.snsproject.model.User;
import snsproject.snsproject.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request){
        User user = userService.join(request.getName(), request.getPassword());
        return Response.success(UserJoinResponse.fromUser(user));
    }


    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getName(), request.getPassword());
        return Response.success(new UserLoginResponse(token));
    }

    @GetMapping("/alarm")
    public Response<Page<AlarmResponse>> alarm(Pageable pageable, Authentication authentication) {
        return Response.success(
                userService.alarmList(authentication.getName(), pageable)
                        .map(AlarmResponse::fromEntity)
        );
    }
}
