package dailyExercise.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import dailyExercise.bean.TrainPlan;
import dailyExercise.dao.ITrainPlanDao;

@Component("trainPlanDao")
public class TrainPlanDao implements ITrainPlanDao{


	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public boolean saveAndUpdate(List<TrainPlan> trainPlan) {
		// TODO Auto-generated method stub
		try{
			for(int i=0;i<trainPlan.size();i++)
			{
				hibernateTemplate.merge(trainPlan.get(i));
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<TrainPlan> findById(String userId) {
		// TODO Auto-generated method stub
		String HQL="from TrainPlan where userId='"+userId+"'";
		return (List<TrainPlan>)hibernateTemplate.find(HQL);
	}

}
