package dailyExercise.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import dailyExercise.bean.DayPlan;
import dailyExercise.dao.IDayPlanDao;

@Component("dayPlanDao")
public class DayPlanDao implements IDayPlanDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public boolean saveAndUpdate(List<DayPlan> dayPlan) {
		// TODO Auto-generated method stub
		try{
			for(int i=0;i<dayPlan.size();i++)
			{
				hibernateTemplate.merge(dayPlan.get(i));
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<DayPlan> findById(String userId) {
		// TODO Auto-generated method stub
		String HQL="from DayPlan where userId='"+userId+"'";
		return (List<DayPlan>)hibernateTemplate.find(HQL);
	}

}
