package dailyExercise.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="dayplan")
public class DayPlan {
	private String dayId;
	private String date;
	private String isComplete;
	private float heatAccount;
	private int planId;
	private String userId;
	
	public DayPlan(){
		dayId="";
		date="";
		isComplete="";
		heatAccount=0;
		planId=0;
		userId="";
	}

	@GenericGenerator(name = "generator", strategy = "assigned")  
    @Id  
    @GeneratedValue(generator = "generator") 
	@Column(name = "dayid", unique = true, nullable = true)
	public String getDayId() {
		return dayId;
	}

	public void setDayId(String dayId) {
		this.dayId = dayId;
	}

	@Column(name="date")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Column(name="iscomplete")
	public String getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}

	@Column(name="heataccount")
	public float getHeatAccount() {
		return heatAccount;
	}

	public void setHeatAccount(float heatAccount) {
		this.heatAccount = heatAccount;
	}

	@Column(name="planid")
	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	@Column(name="userid")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
