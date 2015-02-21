package dailyExercise.service;

import dailyExercise.bean.User;

public interface IUserService {
	public boolean userRegister(User user);
	public User userLogin(User user);
	public boolean userInfoChange(User user);
}
