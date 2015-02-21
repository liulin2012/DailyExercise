package dailyExercise.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionSupport;

import dailyExercise.bean.User;
import dailyExercise.service.IUserService;

@SuppressWarnings("serial")
public class UserInfoChange extends ActionSupport implements ServletResponseAware,ServletRequestAware{
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
	@Action(value="userInfo")
	public void userInfo(){
		System.out.println("userInfo¡¨Õ®");
		try{
			user=new User();
			user.setUserId(request.getParameter("userid"));
			User myUser=userService.userLogin(user);
			myUser.setPassword(request.getParameter("password"));
			myUser.setUserName(request.getParameter("username"));
			myUser.setSex(request.getParameter("sex"));
			myUser.setHeight(Float.parseFloat(request.getParameter("height").toString()));
			myUser.setWeight(Float.parseFloat(request.getParameter("weight").toString()));
			myUser.setAge(Integer.parseInt(request.getParameter("age").toString()));
			boolean ifChange=userService.userInfoChange(myUser);
			if(ifChange==true){response.getWriter().write("success");}
			else{response.getWriter().write("fail");}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
