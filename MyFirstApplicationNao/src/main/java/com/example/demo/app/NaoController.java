package com.example.demo.app;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;



@Controller
@RequestMapping("/")
public class NaoController {

	@Autowired
	UserService userService;
	
	@PersistenceContext
    EntityManager entityManager;
	
//	一覧画面を表示

	@GetMapping("list")
	public String displyList(Model model,@ModelAttribute("formModel") User user) {
		
		model.addAttribute("msg", "ユーザー管理");
		model.addAttribute("msg2", "検索条件を入力してください");
		List<User> userlist =userService.searchAll();
		model.addAttribute("userlist",userlist);
		return "/list";
	}
	
	//検索結果
	@PostMapping("list")
	public String select(@ModelAttribute("formModel") User user,Model model) {
		
		model.addAttribute("msg", "検索結果");
		
		List<User> result=userService.search(user.getName(), user.getEmail(), user.getPhone(),user.getBirthplace());
		
		model.addAttribute("userlist", result);
		return "/list";
	}
	

	//ユーザー詳細情報表示
	@GetMapping("/{id}")
	public String displayView(@PathVariable Long id, Model model) {
		User user =userService.findById(id);
		model.addAttribute("userRequest",user);
		return "/view";
	}
	
	
	
//	新規登録を表示

	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String displyAdd(Model model) {
		model.addAttribute("userRequest",new UserRequest());
		return "/add";
	}
	
	
	//ユーザー編集画面表示
	
	@GetMapping("/{id}/edit")
	public String displayEdit(@PathVariable Long id,Model model) {
		User user=userService.findById(id);
		
		UserUpdateRequest userUpdateRequest =new UserUpdateRequest();
		userUpdateRequest.setId(user.getId());
		userUpdateRequest.setName(user.getName());
		userUpdateRequest.setEmail(user.getEmail());
		userUpdateRequest.setPhone(user.getPhone());
		userUpdateRequest.setBirthplace(user.getBirthplace());
		userUpdateRequest.setBirthschool(user.getBirthschool());
		userUpdateRequest.setWorklocation(user.getWorklocation());
		model.addAttribute("userUpdateRequest",userUpdateRequest);
		return "/edit";
		
		
	}
		
	//ユーザー更新
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(@Validated @ModelAttribute UserUpdateRequest 
			userUpdateRequest,BindingResult result,Model model) {
		if(result.hasErrors()) {
			List<String> errorList=new ArrayList<String>();
			for(ObjectError error :result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			
			model.addAttribute("validationError", errorList);
			return "/edit";
		}
		
		userService.update(userUpdateRequest);
		return String.format("redirect:/%d", userUpdateRequest.getId());
	}
	
	
//	新規登録
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String create(@Validated@ModelAttribute UserRequest userRequest,
			BindingResult result,Model model) {
		if(result.hasErrors()) {
			List<String> errorList=new ArrayList<String>();
			for(ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError",errorList);
			return"/add";
		}
		userService.create(userRequest);
		return "redirect:/list";
	}
	

	
	//ユーザー情報の削除
	@GetMapping("/{id}/delete")
	public String delete(@PathVariable Long id, Model model) {
		
		userService.delete(id);
		return "redirect:/list";
	}
}
