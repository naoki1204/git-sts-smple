package com.example.demo.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.Dao.UserDataDao;
import com.example.demo.entity.User;

import java.util.List;


@Repository
public class UserDataDaoImpl implements UserDataDao {

	
	@Autowired
	private EntityManager entityManager;
	
	public UserDataDaoImpl() {
		super();
	}
	
	public UserDataDaoImpl(EntityManager manager) {
		this();
		entityManager=manager;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> search(String name,String email,String phone,String birthplace){
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT b From User b WHERE ");
		
		boolean andFlg    = false;
		boolean nameFlg  = false;
        boolean emailFlg = false;
        boolean phoneFlg  = false;
        boolean birthplaceFlg  = false;
      
        
        if(!"".equals(name)) {
        	sql.append("b.name LIKE :name");
        	nameFlg=true;
        	andFlg=true;
        }
        
        if(!"".equals(email)) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.email LIKE :email");
            emailFlg = true;
            andFlg    = true;
        }
        
        if(!"".equals(phone)) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.phone LIKE :phone");
            phoneFlg = true;
            andFlg    = true;
        }
        
        if(!"".equals(birthplace)) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.birthplace LIKE :birthplace");
            birthplaceFlg = true;
            andFlg    = true;
        }
        
        Query query = entityManager.createQuery(sql.toString());
        
        if (nameFlg) query.setParameter("name", "%" + name + "%");
        if (emailFlg) query.setParameter("email", "%" + email + "%");
        if (phoneFlg) query.setParameter("phone", "%" + phone + "%");
        if (birthplaceFlg) query.setParameter("birthplace", "%" + birthplace + "%");
        return query.getResultList();
        
	}

}
