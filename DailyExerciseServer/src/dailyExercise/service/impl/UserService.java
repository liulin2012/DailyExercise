package dailyExercise.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import dailyExercise.bean.User;
import dailyExercise.dao.IUserDao;
import dailyExercise.service.IUserService;

@Component("userService")
public class UserService implements IUserService{
	
	@Autowired
	@Qualifier("userDao")
	private IUserDao userDao;
	
	@Override
	public boolean userRegister(User user) {
		// TODO Auto-generated method stub
		if(user.getUserId()==userDao.save(user)){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public User userLogin(User user) {
		// TODO Auto-generated method stub
		return userDao.findById(user.getUserId());
	}

	@Override
	public boolean userInfoChange(User user) {
		// TODO Auto-generated method stub
		return userDao.updateUser(user);
	}

}


