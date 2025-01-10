package mysite.controller.api;

import mysite.service.UserService;
import mysite.vo.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("userApiController")
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/checkemail")
    public Object checkEmail(@RequestParam(value = "email", required = true, defaultValue = "") String email){
        UserVo userVo = userService.getUser(email);

        return Map.of("exist", userVo != null);
    }


}
