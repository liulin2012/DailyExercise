package dailyExercise.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="trainplan")
public class TrainPlan {
	private String planId;
	private String methodId;
	private String completepercent;
	private int isoverdue;
	private String begintime;
	private String weeknumber;
	private String weekday;
	private String planname;
	private String userId;
	
	public TrainPlan(){
		planId="";
		methodId="";
		completepercent="";
		isoverdue=0;
		begintime="";
		weeknumber="";
		weekday="";
		planname="";
		userId="";
	}
	
	@GenericGenerator(name = "generator", strategy = "assigned")  
    @Id  
    @GeneratedValue(generator = "generator") 
	@Column(name = "planid", unique = true, nullable = true)
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	@Column(name="methodid")
	public String getMethodId() {
		return methodId;
	}
	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}
	@Column(name="completepercent")
	public String getCompletepercent() {
		return completepercent;
	}
	public void setCompletepercent(String completepercent) {
		this.completepercent = completepercent;
	}
	@Column(name="isoverdue")
	public int getIsoverdue() {
		return isoverdue;
	}
	public void setIsoverdue(int isoverdue) {
		this.isoverdue = isoverdue;
	}
	@Column(name="begintime")
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	@Column(name="weeknumber")
	public String getWeeknumber() {
		return weeknumber;
	}
	public void setWeeknumber(String weeknumber) {
		this.weeknumber = weeknumber;
	}
	@Column(name="weekday")
	public String getWeekday() {
		return weekday;
	}
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	@Column(name="planname")
	public String getPlanname() {
		return planname;
	}
	public void setPlanname(String planname) {
		this.planname = planname;
	}
	@Column(name="userid")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
