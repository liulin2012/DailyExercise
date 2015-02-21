package dailyExercise.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import dailyExercise.bean.User;
import dailyExercise.dao.IUserDao;

@Component("userDao")
public class UserDao implements IUserDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public String save(User user) {
		// TODO Auto-generated method stub
		try{
			return hibernateTemplate.save(user).toString();
		}catch(Exception e){
			return "fail";
		}
	}
	
	@Override
	public User findById(String userId){
		return (User)hibernateTemplate.get(User.class, userId);
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		try{
			hibernateTemplate.update(user);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
