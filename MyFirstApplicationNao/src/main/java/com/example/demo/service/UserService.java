package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserDataDaoImpl;
import com.example.demo.repository.UserRepository;

@Service
@Transactional(rollbackOn = Exception.class)
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private UserDataDaoImpl userDataDaoImpl;
	
	
//	ユーザー検索
	public List<User> searchAll() {
		return userRepository.findAll();
	}
	
//	ユーザー主キー検索
	
	public User findById(Long id) {
		return userRepository.findById(id).get();
	}
	
//カテゴリー検索
	
	public List<User> search(String name,String email,String phone,String birthplace){
		
		List<User> result =new ArrayList<User>();
		
		if("".equals(name) && "".equals(email) && "".equals(phone) && "".equals(birthplace)) {
			result=userRepository.findAll();
		}
		else {
			result =userDataDaoImpl.search(name, email, phone,birthplace);
		}
		return result;
		
	}
	
	
//	ユーザー情報新規登録
	public void create(UserRequest userRequest) {
		userRepository.save(CreateUser(userRequest));
	}
	
	
//	ユーザー情報更新
	
	public void update(UserUpdateRequest userUpdateRequest) {
		User user =findById(userUpdateRequest.getId());
		
		user.setName(userUpdateRequest.getName());
		user.setEmail(userUpdateRequest.getEmail());
		user.setPhone(userUpdateRequest.getPhone());
		user.setBirthplace(userUpdateRequest.getBirthplace());
		user.setBirthschool(userUpdateRequest.getBirthschool());
		user.setUpdateDate(new Date());
		userRepository.save(user);
	}
	
//ユーザー情報削除
	
	public void delete(Long id){
		User user=findById(id);
		userRepository.delete(user);
	}
	
//	エンティティ作成
	//@Getter
	private User CreateUser(UserRequest userRequest) {
		Date now =new Date();
		User user=new User();
		user.setName(userRequest.getName());
		user.setEmail(userRequest.getEmail());
		user.setPhone(userRequest.getPhone());
		user.setBirthplace(userRequest.getBirthplace());
		user.setBirthschool(userRequest.getBirthschool());
		user.setCreateDate(now);
		user.setUpdateDate(now);
		
		return user;
	}
}
