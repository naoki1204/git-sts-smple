package com.example.demo.Dao;

import java.io.Serializable;
import java.util.List;

import com.example.demo.entity.User;

public interface UserDataDao extends Serializable{
	
	public List<User> search(String name,String email,String phone);

}
