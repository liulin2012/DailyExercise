package dailyExercise.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator; 

@Entity
@Table(name="user")
public class User {
	private String userId;
	private String password;
	private String sex;
	private float height;
	private float weight;
	private String userName;
	private String headPicture;
	private int age;

	public User(){
		userId="0";
		password="0";
		sex="0";
		height=0;
		weight=0;
		userName="0";
		headPicture="0";
		age=0;
	}
	
	@GenericGenerator(name = "generator", strategy = "assigned")  
    @Id  
    @GeneratedValue(generator = "generator") 
	@Column(name = "userid", unique = true, nullable = true)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name="password" ,nullable=true)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="sex")
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Column(name="height")
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	@Column(name="weight")
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	@Column(name="username")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name="headpicture")
	public String getHeadPicture() {
		return headPicture;
	}
	public void setHeadPicture(String headPicture) {
		this.headPicture = headPicture;
	}
	@Column(name="age")
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
