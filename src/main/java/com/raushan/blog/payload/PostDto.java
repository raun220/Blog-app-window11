package com.raushan.blog.payload;

import java.util.Date;
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
public class PostDto {

	private String title;
	private String content;
	private Date date;
	private String image;
	private UserDto user;
	private CategoryDto category;
	Set<CommentDto> comments = new HashSet<>();
}
