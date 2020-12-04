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
	
	private String email;
	
	@Pattern(regexp = "^\\d{2,4}-\\d{2,4}-\\d{4}$", message = "電話番号の形式で入力してください")
	private String phone;
	
	@NotEmpty(message="出身地を入力してください")
	private String birthplace;
	
}
