package com.whu.dailyexercise.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * ���ݿ⸨����
 * @author darktemple9
 *
 */
public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.d("aaa", "�Ƿ�ִ��");
		String methodsql="create table method(methodid integer primary key autoincrement," +
				"methodname varchar, introduction varchar, pictureurl varchar, " +
				"videourl varchar," +
				"unitheat float, practicetime int,methodflag int)";
		String usersql="create table user(userid varchar primary key," +
				"password varchar, sex varchar, height float, weight float," +
				"username varchar, headpictureurl varchar,age intger)";
		String trainplansql="create table trainplan(planid integer primary key autoincrement," +
				"methodid varchar, completepercent varchar, isoverdue int, begintime varchar, weeknumber integer, weekday varchar, planname varchar," +
				"userid varchar references user(userid) on update cascade on delete cascade)";
		String dayplansql="create table dayplan(dayid integer primary key autoincrement,"+
				"date varchar, iscomplete varchar, heataccount float,planid integer references trainplan(planid)  on update cascade on delete cascade)";
		String preparedplansql="create table preparedplan(prepareplanid integer primary key autoincrement,planname varchar,methodid varchar,planintroduction varchar,pictureurl varchar,plantype integer,planflag varchar)";
		String methodpicturesql="create table methodpicture(pictureid integer primary key autoincrement, pictureurl varchar, methodid integer references method(methodid) on update cascade on delete cascade) ";
		
		db.execSQL(usersql);
		db.execSQL(methodsql);
		db.execSQL(trainplansql);
		db.execSQL(dayplansql);
		db.execSQL(preparedplansql);
		db.execSQL(methodpicturesql);		
		
		db.beginTransaction();
		List<String> sqls=new ArrayList<String>();
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('�粿ѵ��','Ӳ���ر�','exercise_kind_back','http://',500,20,0)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('�ز�ѵ��','Ӳ���ر�','exercise_kind_chest','http://',300,10,0)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('����ѵ��','Ӳ���ر�','exercise_kind_abdominals','http://',300,10,0)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('�ֲ�ѵ��','Ӳ���ر�','exercise_kind_biceps','http://',300,10,0)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('�Ȳ�ѵ��','Ӳ���ر�','exercise_kind_quadriceps','http://',300,10,0)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('����ѵ��','Ӳ���ر�','exercise_kind_back','http://',300,10,0)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('�ܲ�ѵ��','Ӳ���ر�','exercise_kind_run','http://',300,10,0)");
		
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('��ŵ�������ƾ�','1.���ˣ�˫�ֳ�����������ǰ���������ز���\n2.�����ƾ��������ֱ���չ��������ͬʱת���ֱ���������ǰ��','arnold_dumbbell_press','XNzAwMTk0NTM2',360,10,1)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('�����ƾ�','1.˫�������ո��Կ��ڼ磬���ָ���ˮƽ��λ��ͷ����ǰ��λ�ã�\n2.�����������ŵ͸������粿��Ȼ��������������������ʼλ�á�','barbell_shoulder_press','XNzAwMTk0NTky',3220,10,1)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('���������ƽ��','1.���������ƽ�У�˫�ֳ�������Ȼ�´���ǰ�������б���ϳ�֧�ţ�\n2.���屣�־�ֹ���ⲿ΢����˫���������������������ƽ�С�','bent_over_dumbbell_rear_delt_raise_with_head_on_be','XNzAwMTk0Njc2',430,10,1)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('��б˫������ǰƽ��','1.��б���ԣ�˫����չ��˫�ֳ������������ڴ����Ϸ����������£�\n2.�����ֱ���չ���ⲿ������ƽ���������ֱ��Ը��ڼ硣','front_incline_dumbbell_raise','XNzAwMTk1MzAw',500,10,1)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('����ʮ�ֶ׾�','1.	˫������Գ����壬˫����չƽ���ڵ��棬˫�ȳ������ϥվ����\n2.����վ����ͬʱ˫�����������˫��������ƽ�롣','iron_cross','XNzAwMTk1NjI4',210,10,1)");
		
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('�����������֧��','1.˫����չ֧���ڵ��棬�ɸ��Գ�����̬��\n2.���۳ŵأ��������Ҳ�ת�����������ƽ�洹ֱ�ڵ��棻\n3.����������չ������̧�������̶ȣ�\n4.ʹ���建��ת�أ��ɸ��Գ�����̬��\n5.���۳ŵأ����������ת�����������ƽ�洹ֱ�ڵ��棻\n6.����������չ������̧�������̶ȡ�','alternating_side_stars','XNzAwMTk0NDMy',300,10,2)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('��������','1.����վ����ϥ��΢����˫�ָ�ִͬ���������ֱ۱�ǣ����չ��\n2.�ֱ���չ��������ǰ������������˫������ǰ���档','cable_crossover','XNzAwMTk0NzQw',340,10,2)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('������ز�����','1.˫��������������վ������ָ���£�����˫������չ����\n2.˫����󣬸о�Ҫ�ڱ���£�������̶ȡ�','elbows_back','XNzAwMTk1MTQw',300,10,2)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('��ϰ���Գ���','1.���Գ��壬��ϰ�����ڴ��Ȳ��·���ʹ����ƽ���ڵ��棻\n2.������⣬�����·����壬ͬʱ˫����Ȼ����Ϸ�����','physioball_push_ups','XNzAwMTk2MDI4',300,10,2)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('��������������','1.������ƽ�ʣ�˫���о��ոܣ�˫��������չ������λ�ھ����Ϸ���\n2.�·Ÿ��������崥��������','widegrip_decline_barbell_bench_press','XNzAwMTk0NjIw',300,10,2)");
		
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('���еų�','1.��ϥ����洹ֱ��ͬʱ�����򸹲�������˫�����ڶ��ࣻ\n2.���Ȼ����ǳ������Ȼ����ջأ�ͬʱ�������⾡��������ϥ��\n3.�ص���ʼλ�ã�����һ���ظ��˶�����ע�Ᵽ�ֺ�����','air_bike','XNzAwMTk0MjQw',300,10,3)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('����վ�˸�������','1.	���ͬ��վ��������ƽֱ������������ڼ��֮�ϣ�ͷ��ƽ��ǰ����\n2.���Ҳྡ���������壻\n3.�ָ�����ʼλ�ã�����ྡ���������塣','barbell_side_bends','XNzAwMTk0NjIw',300,10,3)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('��������','1.˫�����ͬ���ոܣ�������ϥ�����������ƽ�У�С�ȴ�ֱ�ڵ��棻\n2.���Ͼ�̧�ȣ���С�ȴ����򿿽���ܣ�˫�Ⱦ�������չ��','hanging_pike','XNzAwMTk1NDMy',300,10,3)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('��ϰ���','1.	��������ϰ���ϣ���ϰ��λ���±���λ�ã����������ƽ�У�\n2.���Ͼ���ʹ����������30�Ƚ����ң�\n3.��������ʼλ�á�','physioball_crunches','XNzAwMTk2MDI4',300,10,3)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('���˶�ʽת��','1.	���ˣ�˫�ֺ�������ǰ������������㣬˫���Էֿ�ƽ���ڵ��棻\n2.������������ǶȲ��䣬�����ת�����������̶ȣ�\n3.�ص���ʼλ�ã���������Ƕȣ����Ҳ�ת�����������̶ȡ�','seated_russian_twists','XNzAwMTk2Mzcy',300,10,3)");
		
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('��ʽ���','1.	˫�ֳ��������ͬ��վ����˫����Ȼ�´���չ��������ԣ�\n2.������������������粿��','alternate_hammer_curl','XNzAwMTk1Mzcy',300,10,4)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('��б�������','1.˫�ֳ���������б���ϣ�˫����Ȼ��չ��ֱ�ڵ��棬������ǰ��\n2.�����ϱ۾�ֹ����������������粿��','alternate_incline_dumbbell_curl','http://',300,10,4)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('վ���Ŷ�ͷ������','1.����ֱ����˫����չ��˫��ʮָ���������������£�\n2.˫�۱�����չ���Ͼ������̶ȡ�','standing_biceps_stretch','XNzAwMTk2NTUy',300,10,4)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('վ����б�ε����������','1.����������б�κ�վ�������ϱ۷�������б���ϣ�\n2.�������ϣ�����������粿λ�á�','standing_onearm_dumbbell_curl_over_incline_bench','http://',300,10,4)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('���������','1.	˫��ǰ��ֿ�վ����˫�ָ���������Ȼ�´�����ࣻ\n2.������ǰһ����ͬʱ�����¶׳ɹ�����ǰ�����Ͼ��������粿��\n3.�ﵽԤ��ʱ��ʹ����󣬻�����С�','static_lunge_curls','XNzAwMTk2NjY4',300,10,4)");
		
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('�����ؾ���','1.	���Գ����ڵ��棻\n2.̧�������������̶ȣ�\n3.����ʱ������������������࣬���غ������ٴ�̧��\n4.������أ��ص���ʼλ�ã�\n5.̧�������������̶ȣ�\n6.����ʱ������������������࣬���غ������ٴ�̧��','crossover_tap_leg_lifts','XNzAwMTk1MDA0',300,10,5)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('������ת߯','1.���ˣ�˫����չ������������̶ȣ�˫����չ��Ȼ�´���\n2.���ɳ����Ҳ࣬���Źؽ���ǰ����ʹ����λ�������Ϸ���\n3.�����ת�����壬�ֲ����ֿ������棬�������������Ϸ���\n4.�������ɳ�����࣬׼�����д������ҵ�ת�塣','pilates_swoop','XNzAwMTk2MDcy',300,10,5)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('��󹭲�����','1.	���ͬ��վ����˫����Ȼ�´���\n2.���������һ���������¶׳ɹ�����˫������룻\n3.����֧�ţ����������ԭ��������ͬʱ��ϥ���������룻\n4.��غ���������������ٴ��¶׳ɹ�����\n5.����֧��������ԭ�ص�����Ծ��ͬʱ��ϥ���������롣','reverse_lunge_skips','XNzAwMTk2MjQ0',300,10,5)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('�����Ⱥ�����','1.	���ˣ�����ֱͦ��˫����չ��˫��������ࣻ\n2.����ϥƽ�ţ����ȱ�����չ��������ǰʹͷ����ϥ����£��','seated_floor_hamstring_stretch','XNzAwMTk2MzA4',300,10,5)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('������������','1.	���ˣ�˫�Ȳ�£�����ɱ���ƽֱ��˫����Ȼ��չ�´����������ࣻ\n2.��չ���ȣ��ҽż����ϣ�ͬʱ˫����չ������ǰ�㳢��ȥ���ҽż⣻\n3.	�ص���ʼλ�ã���չ���ȣ���ż����ϣ�˫����չ����ȥ����ż⡣','seated_toe_stretch','XNzAwMTk2NDQw',300,10,5)");
		
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('ʵ���������Ͷ','1����Զ���������ǽ��վ����\n2��˫�ֽ��������߷���������ʵ����\n3��������󣬷������򽫽ӵ������ͷ���׻ء�','catch_and_overhead_throw','XNzAwMTk0ODI4',300,10,6)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('������������','1.	˫��խ�෴���ոܣ��ز���ǰ��ͦ��������չ��\n2.�����������壬��ͷ�������ոܵ�λ�á�','chin_up','XNzAwMTk0OTA4',300,10,6)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('���ֿ�������','1.	˫�����ջ����ֱ������������������бԼ30�ȣ�\n2.���������ֱ������ز���','close_grip_front_lat_pulldown','XNzAwMTk2NzY4',300,10,6)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('����������λ����','1.���������ڸ�λ��˫�ֳ�����V���ֱ����ڵ��棬ϥ��΢�����̶���\n2.��˫�۱�ǣ�����������ֱ�������������','elevated_cable_rows','XNzAwMTk1MjA4',300,10,6)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('ʵ��������ҵ�','1.˫�ֳ�ʵ��������ǰ��˫���Կ����վ����ϥ΢����\n2.����ͷ����ʵ��������������󣬳ɷ����Σ�\n3.��ǰ���������ɴ���˫����ǰ����ʵ�������ڵ���֮�ϡ�','overhead_slam','XNzAwMTk1OTUy',300,10,6)");
		
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('����','1.	��������','fast_walking','http://',300,10,7)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('ԭ��̤��','1.˫����խ���վ����˫����Ȼ�´���\n2.ԭ��̤����ͬʱ˫������ǰ���棻\n3.ԭ��̤����˫�۴���ǰ���������ڵ���ƽ�У�\n4.������˫����ͷ�����棬�������෴��˳�򷵻ؿ�ʼλ�á�','jog_in_place_jacks','XNzAwMTk1NzI0',300,10,7)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('����','1.��������','warm_up','http://',300,10,7)");
		
		//preparedplansql
		sqls.add("insert into preparedplan (planname,methodid,planintroduction,pictureurl,plantype,planflag)"+"values ('����ƣ��','-15-38-22-32-25','�ڰ칫λ�þͿ������ļ����˶���û�г������ƣ�������Ҫ�ؽںͲ�λ�Ķ���������ƣ���߹��~~~','plan_3_1',2,'�칫�ң�����')");
		sqls.add("insert into preparedplan (planname,methodid,planintroduction,pictureurl,plantype,planflag)"+"values ('ȫ������','-13-39-28-30-27','����һ�����ᱳʹ�������ݻ������ʱ������Ϣ������һ��ȫ�������˶��������ط�����~','plan_3_2',2,'�칫�ң�����')");
		
		sqls.add("insert into preparedplan (planname,methodid,planintroduction,pictureurl,plantype,planflag)"+"values ('��������','-9-10-38-11-25-12-26-17','����������ѵ������Ƕȶ�������λ���⣬����ӵ�н�˶����ģ����˵ļ�������~','plan_2_1',1,'����������')");
		sqls.add("insert into preparedplan (planname,methodid,planintroduction,pictureurl,plantype,planflag)"+"values ('�ʹ�����','-23-17-8-14-17-36-21-16-27-40','��������������������Ͷȣ��������������˶��Ĺ���������ȫ��ķ��ɣ���������ѹ���������ƣ��ȫ������~','plan_2_2',1,'������������')");

		sqls.add("insert into preparedplan (planname,methodid,planintroduction,pictureurl,plantype,planflag)"+"values ('����չ','-19-33-37-30-31-22-27','�ԵĲ�������լ���е�ù�������򵥵���չ�˶���չ��ǣ�����һ�ٷ�~~~','plan_1_1',0,'�Ӽ�,����')");
		sqls.add("insert into preparedplan (planname,methodid,planintroduction,pictureurl,plantype,planflag)"+"values ('�ճ�����','-18-13-28-21-16-29-30','ÿ������һ��ʱ����н����������λȫ����������ֺ���ģ�����������ʣ�Զ�뼲��~~','plan_1_2',0,'�Ӽ�,�ճ�')");
		//1-1
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(8,'arnold_dumbbell_press_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(8,'arnold_dumbbell_press_2')");
		//1-2
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(9,'barbell_shoulder_press_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(9,'barbell_shoulder_press_2')");
		//1-3
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(10,'bent_over_dumbbell_rear_delt_raise_with_head_on_be_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(10,'bent_over_dumbbell_rear_delt_raise_with_head_on_be_2')");
		//1-4
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(11,'front_incline_dumbbell_raise_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(11,'front_incline_dumbbell_raise_2')");
		//1-5
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(12,'iron_cross_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(12,'iron_cross_2')");
		//2-1
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(13,'alternating_side_stars_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(13,'alternating_side_stars_2')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(13,'alternating_side_stars_3')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(13,'alternating_side_stars_4')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(13,'alternating_side_stars_5')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(13,'alternating_side_stars_6')");
		//2-2
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(14,'cable_crossover_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(14,'cable_crossover_2')");
		//2-3
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(15,'elbows_back_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(15,'elbows_back_2')");
		//2-4
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(16,'physioball_push_ups_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(16,'physioball_push_ups_2')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(16,'physioball_push_ups_3')");
		//2-5
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(17,'widegrip_decline_barbell_bench_press_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(17,'widegrip_decline_barbell_bench_press_2')");
		//3-1
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(18,'air_bike_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(18,'air_bike_2')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(18,'air_bike_3')");
		//3-2
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(19,'barbell_side_bends_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(19,'barbell_side_bends_2')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(19,'barbell_side_bends_3')");
		//3-3
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(20,'hanging_pike_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(20,'hanging_pike_2')");
		//3-4
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(21,'physioball_crunches_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(21,'physioball_crunches_2')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(21,'physioball_crunches_3')");
		//3-5
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(22,'seated_russian_twists_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(22,'seated_russian_twists_2')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(22,'seated_russian_twists_3')");
		//4-1
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(23,'alternate_hammer_curl_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(23,'alternate_hammer_curl_2')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(23,'alternate_hammer_curl_3')");
		//4-2
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(24,'alternate_incline_dumbbell_curl_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(24,'alternate_incline_dumbbell_curl_2')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(24,'alternate_incline_dumbbell_curl_3')");
		//4-3
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(25,'standing_biceps_stretch_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(25,'standing_biceps_stretch_2')");
		//4-4
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(26,'standing_onearm_dumbbell_curl_over_incline_bench_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(26,'standing_onearm_dumbbell_curl_over_incline_bench_2')");
		//4-5
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(27,'static_lunge_curls_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(27,'static_lunge_curls_2')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(27,'static_lunge_curls_3')");
		//5-1
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(28,'crossover_tap_leg_lifts_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(28,'crossover_tap_leg_lifts_2')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(28,'crossover_tap_leg_lifts_3')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(28,'crossover_tap_leg_lifts_4')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(28,'crossover_tap_leg_lifts_5')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(28,'crossover_tap_leg_lifts_6')");
		//5-2
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(29,'pilates_swoop_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(29,'pilates_swoop_2')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(29,'pilates_swoop_3')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(29,'pilates_swoop_4')");
		//5-3
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(30,'reverse_lunge_skips_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(30,'reverse_lunge_skips_2')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(30,'reverse_lunge_skips_3')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(30,'reverse_lunge_skips_4')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(30,'reverse_lunge_skips_5')");
		//5-4
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(31,'seated_floor_hamstring_stretch_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(31,'seated_floor_hamstring_stretch_2')");
        //5-5
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(32,'seated_toe_stretch_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(32,'seated_toe_stretch_2')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(32,'seated_toe_stretch_3')");
		//6-1
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(33,'catch_and_overhead_throw_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(33,'catch_and_overhead_throw_2')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(33,'catch_and_overhead_throw_3')");
        //6-2
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(34,'chin_up_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(34,'chin_up_2')");
		//6-3
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(35,'close_grip_front_lat_pulldown_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(35,'close_grip_front_lat_pulldown_2')");
		//6-4
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(36,'elevated_cable_rows_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(36,'elevated_cable_rows_2')");
		//6-5
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(37,'overhead_slam_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(37,'overhead_slam_2')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(37,'overhead_slam_3')");
		//7-1
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(38,'fast_walking_1')");
		//7-2
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(39,'jog_in_place_jacks_1')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(39,'jog_in_place_jacks_2')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(39,'jog_in_place_jacks_3')");
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(39,'jog_in_place_jacks_4')");
		//7_3
		sqls.add("insert into methodpicture(methodid,pictureurl)"+"values(40,'warm_up_1')");

		sqls.add("insert into user(userid,password,sex, height, weight,username, headpictureurl,age)"
		+"values('root','123','��',10.2,85.2,'����','qqq',15)");		
		try{
			for(String sql:sqls)
			{
				db.execSQL(sql);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			db.setTransactionSuccessful();
			db.endTransaction();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * sqliteĬ�ϲ�֧�������������������Ҫ���òſ���
	 */
	@Override
	public void onOpen(SQLiteDatabase db)
	{
		super.onOpen(db);
		if(!db.isReadOnly())
		{
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

}
