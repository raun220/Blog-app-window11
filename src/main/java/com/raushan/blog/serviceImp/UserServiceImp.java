package com.raushan.blog.serviceImp;


import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raushan.blog.entity.User;
import com.raushan.blog.exception.ResourceNotFoundException;
import com.raushan.blog.payload.UserDto;
import com.raushan.blog.repositories.UserRepo;
import com.raushan.blog.service.UserService;

@Service
public class UserServiceImp implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
//		User entity = new User();
//		BeanUtils.copyProperties(userDto, entity);
//		User savedEntity = userRepo.save(entity);
//		BeanUtils.copyProperties(savedEntity, userDto);
//		return userDto;
		
		User entity = modelMapper.map(userDto, User.class);
		User savedEntity = userRepo.save(entity);
		return modelMapper.map(savedEntity, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User entity = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		entity.setName(userDto.getName());
		entity.setEmail(userDto.getEmail());
		entity.setPassword(userDto.getPassword());
		entity.setAbout(userDto.getAbout());
		User savedEntity = userRepo.save(entity);
		return modelMapper.map(savedEntity, UserDto.class);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User entity = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		return modelMapper.map(entity, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> userList = userRepo.findAll();
//		List<UserDto> userDtoList = new ArrayList<>();
//		for(User user : userList) {
//			userDtoList.add(modelMapper.map(user, UserDto.class));
//		}
//		return userDtoList;
		List<UserDto> userDtoList = userList.stream().map(user-> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
	    return userDtoList;
	}

	@Override
	public void deleteUser(Integer userId) {
		User entity = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "UserId", userId));
		userRepo.delete(entity);
	}

}
