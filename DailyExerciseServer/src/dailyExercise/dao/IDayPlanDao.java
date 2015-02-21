package dailyExercise.dao;

import java.util.List;

import dailyExercise.bean.DayPlan;

public interface IDayPlanDao {
	public boolean saveAndUpdate(List<DayPlan> dayPlan);
	public List<DayPlan> findById(String userId);
}
