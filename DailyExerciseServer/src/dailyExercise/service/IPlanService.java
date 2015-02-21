package dailyExercise.service;

import java.util.List;

import dailyExercise.bean.DayPlan;
import dailyExercise.bean.TrainPlan;

public interface IPlanService {
	public boolean synDayPlan(List<DayPlan> dayPlan);
	public boolean synTrainPlan(List<TrainPlan> trainPlan);
	public List<DayPlan> findDayPlan(String userId);
	public List<TrainPlan> findTrainPlan(String userId);
}