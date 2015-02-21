package dailyExercise.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionSupport;

import dailyExercise.bean.User;
import dailyExercise.service.IUserService;;

@SuppressWarnings("serial")
public class UserLogin extends ActionSupport implements ServletResponseAware,ServletRequestAware{
	private HttpServletRequest request;
	private HttpServletResponse response;
	private User user;
	
	@Autowired
	@Qualifier("userService")
	private IUserService userService;

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
	@Action(value="login")
	public void Login()
	{
//		http://localhost:8080/DailyExerciseServer/login.action?userId=111&password=de
		try{
			user=new User();
			user.setUserId(request.getParameter("userid"));
			user.setPassword(request.getParameter("password"));
//			user.setUserId("111");
//			user.setPassword("de");
			User myUser=userService.userLogin(user);
			this.response.setContentType("text/html;charset=utf-8");    
	        this.response.setCharacterEncoding("UTF-8");  
			if(myUser==null){
				response.getWriter().write("fail");
			}
			else if(!(myUser.getPassword().equals(user.getPassword()))){
				response.getWriter().write("wrong password");
			}
			else{
		        JSONObject json=new JSONObject();  
		        Map map=new HashMap<Object, String>();  
		        map.put("userid",myUser.getUserId());  
		        map.put("password", myUser.getPassword());
		        map.put("sex", myUser.getSex());
		        map.put("height", myUser.getHeight());
		        map.put("weight", myUser.getWeight());
		        map.put("username", myUser.getUserName());
		        map.put("headpicture", myUser.getHeadPicture());
		        map.put("age", myUser.getAge());
		        json.put("LoginInfo", map); 
		        response.getWriter().write(json.toString());  
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
