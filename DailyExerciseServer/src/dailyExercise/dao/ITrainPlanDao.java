package dailyExercise.dao;

import java.util.List;

import dailyExercise.bean.TrainPlan;;

public interface ITrainPlanDao {
	public boolean saveAndUpdate(List<TrainPlan> trainPlan);
	public List<TrainPlan> findById(String userId);
}
