package com.example.demo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;



@SuppressWarnings("serial")
@Data
public class UserRequest implements Serializable{

	@NotEmpty(message="名前を入力してください")
	private String name;
	
	@Pattern(regexp="^([a-zA-Z0-9])+([a-zA-Z0-9\\._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9\\._-]+)+$",
			message="メールアドレスの形式で入力してください")
	private String email;
	
	@Pattern(regexp = "^\\d{2,4}-\\d{2,4}-\\d{4}$", message = "電話番号の形式で入力してください")
	private String phone;
	
	@NotEmpty(message="出身地を入力してください")
	private String birthplace;
	
	@NotEmpty(message="出身校を入力してください")
	private String birthschool;
	
	@NotEmpty(message="勤務地を入力してください")
	private String worklocation;
	
}
