package com.poscodx.mysite07.controller.api;


import com.poscodx.mysite07.dto.JsonResult;
import com.poscodx.mysite07.service.UserService;
import com.poscodx.mysite07.vo.UserVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("userApiController")
@RequestMapping("/api/user")
public class UserController {
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/checkemail")
	public JsonResult checkEmail(@RequestParam(value="email", required=true, defaultValue="") String email) {
		UserVo userVo = userService.getUser(email);
		return JsonResult.success(Map.of("exist", userVo != null));
	}
}
