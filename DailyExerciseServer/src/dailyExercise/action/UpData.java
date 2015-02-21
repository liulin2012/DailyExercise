package dailyExercise.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.validator.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionSupport;

import dailyExercise.bean.DayPlan;
import dailyExercise.bean.TrainPlan;
import dailyExercise.service.IPlanService;
import dailyExercise.service.IUserService;

@SuppressWarnings("serial")
public class UpData extends ActionSupport implements ServletResponseAware,ServletRequestAware{
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	@Autowired
	@Qualifier("planService")
	private IPlanService planService;
	
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request=arg0;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value="upUserData")
	public void upUserData(){
		try{
			String userId=request.getParameter("userid").toString();
			List<DayPlan> dayPlanList=planService.findDayPlan(userId);
			JSONArray jsonArrayDay=new JSONArray();
			for(int i=0;i<dayPlanList.size();i++)
			{
				DayPlan dayPlan=dayPlanList.get(i);
				Map map=new HashMap<Object, String>();
				map.put("dayid", dayPlan.getDayId().substring(dayPlan.getUserId().length()+3));
				map.put("date", dayPlan.getDate());
				map.put("iscomplete", dayPlan.getIsComplete());
				map.put("heataccount", dayPlan.getHeatAccount());
				map.put("planid", dayPlan.getPlanId());
				jsonArrayDay.add(map);
			}
			
			List<TrainPlan> trainPlanList=planService.findTrainPlan(userId);
			JSONArray jsonArrayTrain=new JSONArray();
			for(int i=0;i<trainPlanList.size();i++)
			{
				TrainPlan trainPlan=trainPlanList.get(i);
				Map map=new HashMap<Object, String>();
				map.put("planid", trainPlan.getPlanId().substring(trainPlan.getUserId().length()+3));
				map.put("methodid", trainPlan.getMethodId());
				map.put("completepercent", trainPlan.getCompletepercent());
				map.put("isoverdue", trainPlan.getIsoverdue());
				map.put("begintime", trainPlan.getBegintime());
				map.put("weeknumber", trainPlan.getWeeknumber());
				map.put("weekday", trainPlan.getWeekday());
				map.put("planname", trainPlan.getPlanname());
				map.put("userid", trainPlan.getUserId());
				jsonArrayTrain.add(map);
			}
			
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("dayplan", jsonArrayDay);
			jsonObj.put("trainplan", jsonArrayTrain);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");   
			response.getWriter().write(jsonObj.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	

}
