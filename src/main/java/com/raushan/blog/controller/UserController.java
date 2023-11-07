package com.raushan.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raushan.blog.payload.ApiResponse;
import com.raushan.blog.payload.UserDto;
import com.raushan.blog.serviceImp.UserServiceImp;

@RestController
@RequestMapping("/api/user/")
public class UserController {

	@Autowired
	private UserServiceImp serviceImp;
	
	@PostMapping("/createUser")
	ResponseEntity<UserDto> craeteUserController(@RequestBody UserDto userDto){
		UserDto user = serviceImp.createUser(userDto);
		return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);
	}
	
	@PutMapping("/updateUser")
	ResponseEntity<UserDto> updateUserController(@RequestBody UserDto userDto, Integer userId){
		UserDto user = serviceImp.updateUser(userDto, userId);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/{userId}")
	ResponseEntity<UserDto> findUserController(@PathVariable("userId") Integer userId){
		UserDto user = serviceImp.getUserById(userId);
		return new ResponseEntity<UserDto>(user, HttpStatus.OK);
	}
	
	@GetMapping("/")
	ResponseEntity<List<UserDto>> findUserController(){
		List<UserDto> user = serviceImp.getAllUsers();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}")
	ResponseEntity<ApiResponse> deleteUserController(@PathVariable("userId") Integer userId){
		serviceImp.deleteUser(userId);
		return new ResponseEntity<>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
	}
	
}
