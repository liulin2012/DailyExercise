package dailyExercise.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import dailyExercise.bean.DayPlan;
import dailyExercise.bean.TrainPlan;
import dailyExercise.dao.IDayPlanDao;
import dailyExercise.dao.ITrainPlanDao;
import dailyExercise.service.IPlanService;

@Component("planService")
public class PlanService implements IPlanService{
	
	@Autowired
	@Qualifier("dayPlanDao")
	private IDayPlanDao dayPlanDao;
	
	@Autowired
	@Qualifier("trainPlanDao")
	private ITrainPlanDao trainPlanDao;
	
	@Override
	public boolean synDayPlan(List<DayPlan> dayPlan) {
		// TODO Auto-generated method stub
		return dayPlanDao.saveAndUpdate(dayPlan);
	}

	@Override
	public boolean synTrainPlan(List<TrainPlan> trainPlan) {
		// TODO Auto-generated method stub
		return trainPlanDao.saveAndUpdate(trainPlan);
	}

	@Override
	public List<DayPlan> findDayPlan(String userId) {
		// TODO Auto-generated method stub
		return dayPlanDao.findById(userId);
	}

	@Override
	public List<TrainPlan> findTrainPlan(String userId) {
		// TODO Auto-generated method stub
		return trainPlanDao.findById(userId);
	}

}
