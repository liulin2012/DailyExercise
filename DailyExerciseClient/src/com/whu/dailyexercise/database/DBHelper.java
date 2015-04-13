package com.whu.dailyexercise.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库辅助类
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
		Log.d("aaa", "是否执行");
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
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('肩部训练','硬汉必备','exercise_kind_back','http://',500,20,0)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('胸部训练','硬汉必备','exercise_kind_chest','http://',300,10,0)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('腹部训练','硬汉必备','exercise_kind_abdominals','http://',300,10,0)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('手部训练','硬汉必备','exercise_kind_biceps','http://',300,10,0)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('腿部训练','硬汉必备','exercise_kind_quadriceps','http://',300,10,0)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('背部训练','硬汉必备','exercise_kind_back','http://',300,10,0)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('跑步训练','硬汉必备','exercise_kind_run','http://',300,10,0)");
		
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('阿诺德哑铃推举','1.坐姿，双手持哑铃于上胸前，掌心向胸部；\n2.向上推举哑铃至手臂伸展，过程中同时转动手臂至掌心向前。','arnold_dumbbell_press','XNzAwMTk0NTM2',360,10,1)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('杠铃推举','1.双手正手握杠略宽于肩，保持杠铃水平且位于头部上前方位置；\n2.吸气，缓慢放低杠铃至肩部，然后呼气向上推起杠铃至开始位置。','barbell_shoulder_press','XNzAwMTk0NTky',3220,10,1)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('俯身哑铃侧平举','1.俯身与地面平行，双手持哑铃自然下垂，前额放在上斜椅上成支撑；\n2.上体保持静止，肘部微曲，双臂向上提起哑铃至与地面平行。','bent_over_dumbbell_rear_delt_raise_with_head_on_be','XNzAwMTk0Njc2',430,10,1)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('上斜双臂哑铃前平举','1.上斜仰卧，双臂伸展，双手持哑铃轻轻置于大腿上方，掌心向下；\n2.保持手臂伸展，肘部略曲，平举哑铃至手臂略高于肩。','front_incline_dumbbell_raise','XNzAwMTk1MzAw',500,10,1)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('哑铃十字蹲举','1.	双手心相对持哑铃，双臂伸展平行于地面，双腿成深蹲屈膝站立；\n2.向上站立，同时双臂向两侧打开至双臂与躯干平齐。','iron_cross','XNzAwMTk1NjI4',210,10,1)");
		
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('交替侧身星型支撑','1.双臂伸展支撑于地面，成俯卧撑体姿态；\n2.单臂撑地，躯干向右侧转动，至身体侧平面垂直于地面；\n3.保持右腿伸展并向上抬起，至最大程度；\n4.使身体缓慢转回，成俯卧撑体姿态；\n5.单臂撑地，躯干向左侧转动，至身体侧平面垂直于地面；\n6.保持左腿伸展并向上抬起，至最大程度。','alternating_side_stars','XNzAwMTk0NDMy',300,10,2)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('缆绳夹胸','1.箭步站立，膝部微曲，双手各执同侧缆绳，手臂被牵引伸展；\n2.手臂伸展，向身体前方拉动缆绳，双手在体前交叉。','cable_crossover','XNzAwMTk0NzQw',340,10,2)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('肘向后胸部拉伸','1.双手置于腰部两侧站立，手指向下，屈肘双臂向外展开；\n2.双肘向后，感觉要在背后靠拢，至最大程度。','elbows_back','XNzAwMTk1MTQw',300,10,2)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('练习球俯卧撑体','1.俯卧撑体，练习球置于大腿部下方，使身体平行于地面；\n2.向后屈肘，缓慢下放身体，同时双腿自然向后上方翘起。','physioball_push_ups','XNzAwMTk2MDI4',300,10,2)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('杠铃卧推至颈部','1.仰卧于平凳，双手中距握杠，双臂向上伸展，杠铃位于颈部上方；\n2.下放杠铃至杠铃触及颈部。','widegrip_decline_barbell_bench_press','XNzAwMTk0NjIw',300,10,2)");
		
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('空中蹬车','1.收膝与地面垂直，同时上体向腹部卷曲，双手置于耳侧；\n2.右腿缓慢登出，左腿缓慢收回，同时将右手肘尽力贴近左膝；\n3.回到初始位置，换另一侧重复此动作，注意保持呼吸。','air_bike','XNzAwMTk0MjQw',300,10,3)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('杠铃站姿杠铃侧弯举','1.	与肩同宽站立，身体平直，将杠铃放置于肩膀之上，头部平视前方；\n2.向右侧尽力弯曲身体；\n3.恢复至开始位置，向左侧尽力弯曲身体。','barbell_side_bends','XNzAwMTk0NjIw',300,10,3)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('悬垂翻杠','1.双手与肩同宽握杠，向上屈膝，大腿与地面平行，小腿垂直于地面；\n2.向上卷腹抬腿，至小腿触及或靠近横杠，双腿尽可能伸展。','hanging_pike','XNzAwMTk1NDMy',300,10,3)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('练习球卷腹','1.	仰卧于练习球上，练习球位于下背部位置，躯干与地面平行；\n2.向上卷腹，使躯干与地面成30度角左右；\n3.返回至开始位置。','physioball_crunches','XNzAwMTk2MDI4',300,10,3)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('坐姿俄式转体','1.	坐姿，双手合握于胸前，身体略向后倾，双脚略分开平置于地面；\n2.保持身体后仰角度不变，向左侧转动身体至最大程度；\n3.回到开始位置，保持身体角度，向右侧转动身体至最大程度。','seated_russian_twists','XNzAwMTk2Mzcy',300,10,3)");
		
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('锤式弯举','1.	双手持哑铃与肩同宽站立，双臂自然下垂伸展，掌心相对；\n2.向上弯举哑铃至触及肩部。','alternate_hammer_curl','XNzAwMTk1Mzcy',300,10,4)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('上斜哑铃弯举','1.双手持哑铃于上斜椅上，双臂自然伸展垂直于地面，掌心向前；\n2.保持上臂静止，向上弯举哑铃至肩部。','alternate_incline_dumbbell_curl','http://',300,10,4)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('站姿肱二头肌拉伸','1.身体直立，双臂伸展，双手十指相扣于体后，掌心向下；\n2.双臂保持伸展，上举至最大程度。','standing_biceps_stretch','XNzAwMTk2NTUy',300,10,4)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('站姿上斜椅单臂哑铃弯举','1.持哑铃于上斜椅后方站立，将上臂放置于上斜椅上；\n2.掌心向上，弯举哑铃至肩部位置。','standing_onearm_dumbbell_curl_over_incline_bench','http://',300,10,4)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('弓步臂弯举','1.	双脚前后分开站立，双手各持哑铃自然下垂于体侧；\n2.左腿向前一步，同时身体下蹲成弓步，前臂向上举哑铃至肩部；\n3.达到预定时间和次数后，换侧进行。','static_lunge_curls','XNzAwMTk2NjY4',300,10,4)");
		
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('交叉点地举腿','1.	俯卧撑体于地面；\n2.抬左腿向上至最大程度；\n3.下落时左腿向右落在右腿外侧，触地后立刻再次抬起；\n4.左腿落地，回到开始位置；\n5.抬右腿向上至最大程度；\n6.下落时右腿向左落在左腿外侧，触地后立刻再次抬起。','crossover_tap_leg_lifts','XNzAwMTk1MDA0',300,10,5)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('普拉提转忒','1.坐姿，双腿伸展向两侧打开至最大程度，双臂伸展自然下垂；\n2.躯干朝向右侧，自髋关节向前俯身，使躯干位于右腿上方；\n3.向左侧转动身体，手部保持靠近地面，至身体至左腿上方；\n4.起身，躯干朝向左侧，准备进行从左至右的转体。','pilates_swoop','XNzAwMTk2MDcy',300,10,5)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('向后弓步快跳','1.	与肩同宽站立，双臂自然下垂；\n2.右腿向后迈一步，身体下蹲成弓步，双手与肩齐；\n3.左腿支撑，起身后左腿原地起跳，同时右膝上提与腰齐；\n4.落地后左腿向后迈步，再次下蹲成弓步；\n5.右腿支撑起身，接原地单腿跳跃，同时左膝上提与腰齐。','reverse_lunge_skips','XNzAwMTk2MjQ0',300,10,5)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('坐姿腿后拉伸','1.	坐姿，躯干挺直，双腿伸展，双臂置于体侧；\n2.屈左膝平放，右腿保持伸展，俯身向前使头部向膝部靠拢。','seated_floor_hamstring_stretch','XNzAwMTk2MzA4',300,10,5)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('坐姿摸脚拉伸','1.	坐姿，双腿并拢，躯干保持平直，双臂自然伸展下垂于身体两侧；\n2.伸展右腿，右脚尖向上，同时双臂伸展，身体前倾尝试去握右脚尖；\n3.	回到开始位置，伸展左腿，左脚尖向上，双臂伸展尝试去握左脚尖。','seated_toe_stretch','XNzAwMTk2NDQw',300,10,5)");
		
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('实心球过顶抛投','1．面对锻炼伙伴或者墙壁站立；\n2．双手接抛来或者反弹回来的实心球；\n3．身体向后，发力反向将接到的球从头顶抛回。','catch_and_overhead_throw','XNzAwMTk0ODI4',300,10,6)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('反手引体向上','1.	双手窄距反手握杠，胸部向前略挺，身体伸展；\n2.向上拉动身体，至头部超过握杠的位置。','chin_up','XNzAwMTk0OTA4',300,10,6)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('滑轮宽握拉背','1.	双手正握滑轮手柄，上身自腰部向后倾斜约30度；\n2.向下拉动手柄至上胸部。','close_grip_front_lat_pulldown','XNzAwMTk2NzY4',300,10,6)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('坐姿缆绳高位划船','1.调节缆绳于高位，双手持缆绳V形手柄坐于地面，膝部微屈并固定；\n2.沿双臂被牵引方向，拉动手柄至触及腹部。','elevated_cable_rows','XNzAwMTk1MjA4',300,10,6)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('实心球过顶砸地','1.双手持实心球于体前，双腿略宽与肩站立，膝微屈；\n2.向后过头顶引实心球，屈臂屈肘向后，成反弓形；\n3.向前发力，躯干带动双臂向前，将实心球砸于地面之上。','overhead_slam','XNzAwMTk1OTUy',300,10,6)");
		
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('快走','1.	快走热身。','fast_walking','http://',300,10,7)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('原地踏步','1.双脚略窄与肩站立，双臂自然下垂；\n2.原地踏步，同时双臂于胸前交叉；\n3.原地踏步，双臂从体前，体侧打开至于地面平行；\n4.继续提双臂在头顶交叉，并按照相反的顺序返回开始位置。','jog_in_place_jacks','XNzAwMTk1NzI0',300,10,7)");
		sqls.add("insert into method (methodname,introduction,pictureurl,videourl,unitheat,practicetime,methodflag)"+"values ('慢跑','1.慢跑热身。','warm_up','http://',300,10,7)");
		
		//preparedplansql
		sqls.add("insert into preparedplan (planname,methodid,planintroduction,pictureurl,plantype,planflag)"+"values ('消除疲劳','-15-38-22-32-25','在办公位置就可以做的简易运动，没有场地限制，进行主要关节和部位的锻炼，工作疲劳走光光~~~','plan_3_1',2,'办公室，简易')");
		sqls.add("insert into preparedplan (planname,methodid,planintroduction,pictureurl,plantype,planflag)"+"values ('全身拉伸','-13-39-28-30-27','工作一天腰酸背痛？趁午休或下午茶时间在休息场地做一套全身拉伸运动，立马重返活力~','plan_3_2',2,'办公室，拉伸')");
		
		sqls.add("insert into preparedplan (planname,methodid,planintroduction,pictureurl,plantype,planflag)"+"values ('肌肉塑形','-9-10-38-11-25-12-26-17','主打力量型训练，多角度锻炼各部位肌肉，让你拥有健硕的身材，迷人的肌肉线条~','plan_2_1',1,'健身房，力量')");
		sqls.add("insert into preparedplan (planname,methodid,planintroduction,pictureurl,plantype,planflag)"+"values ('韧带拉伸','-23-17-8-14-17-36-21-16-27-40','健身房经典锻炼，提升柔韧度，减重两不误，在运动的过程中享受全身的放松，将工作的压力，生活的疲劳全部赶走~','plan_2_2',1,'健身房，柔韧性')");

		sqls.add("insert into preparedplan (planname,methodid,planintroduction,pictureurl,plantype,planflag)"+"values ('简单伸展','-19-33-37-30-31-22-27','吃的不消化，宅的有点霉？做做简单的伸展运动舒展筋骨，活力一百分~~~','plan_1_1',0,'居家,简易')");
		sqls.add("insert into preparedplan (planname,methodid,planintroduction,pictureurl,plantype,planflag)"+"values ('日常健身','-18-13-28-21-16-29-30','每天利用一段时间进行健身，身体各部位全面锻炼，保持好身材，提高身体素质，远离疾病~~','plan_1_2',0,'居家,日常')");
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
		+"values('root','123','男',10.2,85.2,'哈哈','qqq',15)");		
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
	 * sqlite默认不支持外键关联，这里面需要设置才可以
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
