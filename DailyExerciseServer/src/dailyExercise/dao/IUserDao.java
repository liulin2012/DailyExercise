package dailyExercise.dao;

import dailyExercise.bean.User;

public interface IUserDao {
	
	public String save(User user);
	public User findById(String userId);
	public boolean updateUser(User user);
}
