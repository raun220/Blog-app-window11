package com.raushan.blog.payload;

import java.util.HashSet;
import java.util.Set;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private Integer id;
	private String name;
	private String email;
	private String password;
	private String about;
//	Set<CommentDto> comments = new HashSet<>();
}
