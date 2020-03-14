package com.uniovi.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.*;

import com.uniovi.entities.Post;

@Component
public class AddPostValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Post.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "addPost.title.error");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "addPost.text.error");
	}
}
