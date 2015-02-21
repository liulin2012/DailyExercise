package dailyExercise.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionSupport;

import dailyExercise.bean.DayPlan;
import dailyExercise.bean.TrainPlan;
import dailyExercise.service.IPlanService;


@SuppressWarnings("serial")
public class InfoSyn extends ActionSupport implements ServletResponseAware,ServletRequestAware{
	private HttpServletRequest request;
	private HttpServletResponse response;
//	private TrainPlan trainPlan;
//	private DayPlan dayPlan;
	
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
	
	@Action(value="infoSyn")
	public void infoSyn(){
		System.out.println("连通infosyn");
//		[{"planid":1,"methodid":"-8-10-11-12-18-20-21-22-28-29-30","weeknumber":4,"weekday":"-1-1-1-0-0-0-0","begindate":"2014-04-15","isoverdue":0}]
//		System.out.println(request.getParameter("myplanlist").toString());
//		System.out.println(request.getParameter("mydaylist").toString());
		try{
			JSONArray jsonArrayTrain=JSONArray.fromObject(request.getParameter("myplanlist"));
			List<TrainPlan> trainPlanList=new ArrayList<TrainPlan>();
			String userId="";
			for(int i=0;i<jsonArrayTrain.size();i++)
			{
				JSONObject jsonObj=jsonArrayTrain.getJSONObject(i);
				TrainPlan trainPlan=new TrainPlan();
				trainPlan.setPlanId(jsonObj.get("userid").toString()+"___"+jsonObj.get("planid").toString());
				trainPlan.setMethodId(jsonObj.get("methodid").toString());
				trainPlan.setCompletepercent(jsonObj.get("completepercent").toString());
				trainPlan.setIsoverdue(Integer.parseInt(jsonObj.get("isoverdue").toString()));
				trainPlan.setBegintime(jsonObj.get("begindate").toString());
				trainPlan.setWeeknumber(jsonObj.get("weeknumber").toString());
				trainPlan.setWeekday(jsonObj.get("weekday").toString());
				trainPlan.setPlanname(jsonObj.get("planname").toString());
				trainPlan.setUserId(jsonObj.get("userid").toString());
				userId=jsonObj.get("userid").toString();
				trainPlanList.add(trainPlan);
			}
			if(planService.synTrainPlan(trainPlanList)){System.out.println("trainPlan同步成功");}
			
			JSONArray jsonArrayDay=JSONArray.fromObject(request.getParameter("mydaylist"));
			System.out.println(request.getParameter("mydaylist"));
			List<DayPlan> dayPlanList=new ArrayList<DayPlan>();
			for(int i=0;i<jsonArrayDay.size();i++)
			{
				JSONObject jsonObj=jsonArrayDay.getJSONObject(i);
				DayPlan dayPlan=new DayPlan();
				dayPlan.setDayId(userId+"___"+jsonObj.get("dayid").toString());
				dayPlan.setDate(jsonObj.getString("date").toString());
				dayPlan.setIsComplete(jsonObj.get("iscomplete").toString());
				dayPlan.setHeatAccount(Float.parseFloat(jsonObj.get("heataccount").toString()));
				dayPlan.setPlanId(Integer.parseInt(jsonObj.get("planid").toString()));
				dayPlan.setUserId(userId);
				dayPlanList.add(dayPlan);
			}
			if(planService.synDayPlan(dayPlanList)){System.out.println("dayPlan同步成功");}
			System.out.println("同步成功");
			response.getWriter().write("success");
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
}
