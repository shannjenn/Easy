package com.jen.easy.sqlite;

/**
 * 表(预留)
 * 
 * @author Administrator
 * 
 */
final class TB {
	
	// 课程表 =============================start
	/** 课程表 */
	public final static String TB_DutyInfo = "DutyInfo";
	public final static String DutyInfo = TB_DutyInfo + " as d";
	/** 课程表 字段 */
	public final static String[] dutyInfo_Clounms = { "d.ID", "d.N", "d.P", "d.SNO", "d.MEMO", "d.O", "d.C", "d.L", "d.E", "d.S","d.M" };
	/** 课程表 字段 */
	public enum DutyInfoClounms {
		ID, N, P, SNO, MEMO, O, C, L, E, S,M, added;
	}
	
	// 课程表 =============================end
	
	
	
	// 课件表 =============================start
	/** 课件表名 */
	public final static String CourseInfo = "CourseInfo as c";
	/** 课件表 字段 */
	public final static String[] courseInfo_Clounms = { "c.ID", "c.N", "c.M", "c.P", "c.CNO", "c.SNO", "c.VER", "c.L", "c.E", "c.R", "c.A","c.S" };
	/** 课件表 字段 */
	public enum CourseInfoClounms {
		ID, N, M, P, CNO, SNO, VER, L, E, R, A, S;
	}
	// 课件表 =============================end
	
	
	
	// 场景表 =============================start
	/** 场景表名 */
	public final static String TB_SceneInfo = "SceneInfo";
	public final static String SceneInfo = TB_SceneInfo + " as s";
	/** 场景表 字段 */
	public final static String[] sceneinfo_clounms = { "s.ID", "s.CID", "s.N", "s.SNO", "s.M", "s.O", "s.L", "s.E", "s.R" };
	/** 场景件表 字段 */
	public enum SceneinfoClounms {
		ID, CID, N, SNO, M, O, L, E, R;
	}
	// 场景表 =============================end
	
	// 我的场景表 =============================start
	public final static String TB_MySceneInfo = "MySceneInfo";

	public enum MySceneInfoClounms {
		ID, M, O, L, E, R, UID;
	}
	// 我的场景表 =============================end
	
	
	
	// 句子表 =============================start
	public final static String TB_SentenceInfo = "SentenceInfo";
	public final static String SentenceInfo = "SentenceInfo as si";
	public final static String[] sentenceInfo_Clounms = { "si.ID", "si.DID", "si.CID", "si.SID", "si.O", "si.T", "si.CN", "si.EN", "si.NCN",
			"si.NEN", "si.S", "si.F", "si.P", "si.V", "si.L", "si.E" };
	public enum SentenceClounms {
		ID, DID, CID, SID, O, T, CN, EN, NCN, NEN, S, F, P, V, L, E;
	}
	// 句子表 =============================end
	
	
	
	// 学习进度表 =============================start
	public final static String TB_StudyProgress = "StudyProgress";
	public final static String CTREATE_TB_STUDYPROGRESS = "create table if not exists " + TB_StudyProgress +
			"(" + 
			StudyProgressClounms.UID.toString() + " varchar," + 
			StudyProgressClounms.SID.toString() + " varchar," + 
			StudyProgressClounms.V.toString() + " varchar," +
			StudyProgressClounms.L.toString() + " varchar," +
			StudyProgressClounms.S.toString() + " varchar," +
			StudyProgressClounms.E.toString() + " varchar," +
			StudyProgressClounms.LCount.toString() + " integer," +
			StudyProgressClounms.RCount.toString() + " integer," +
			"primary key ("+StudyProgressClounms.UID.toString()+","+StudyProgressClounms.SID.toString()+")"+
			")";
	public final static String StudyProgress = "StudyProgress sp";
	public enum StudyProgressClounms {
		UID, SID, V, L, S, E, LCount, RCount;
	}
	// 学习进度表 =============================end
	
	
	
	
	// 课程与课件关联表 =============================start
	/** 课程与课件关联表 */
	public final static String Duty_Ref_Course = "Duty_Ref_Course as drc";
	
	/**我的课程表*/
	public final static String MyDutyInfo = "MyDutyInfo as mdi";
	/** 课程与课程类型关联表 */
	public final static String Type_Ref_Duty = "Type_Ref_Duty as trd";
	// 课程与课件关联表 =============================end
	
	
	//=======================收藏=======我的收藏，关键词表====================start
	public final static String TB_MyWord = "MyWord";
	public final static String MyWord = TB_MyWord + " as mw";
	public final static String TB_KeyWord = "KeyWord";
	public final static String KeyWord = TB_KeyWord + " as kw";
	public final static String[] keyWordInfo_Clounms = { "kw.CN","kw.EN","kw.ID", "kw.SID", "kw.S", "kw.K", "kw.C", "kw.W", "kw.M", "kw.L", "kw.E"};
	public enum KeyWordClounms {
		ID, SID, S, K, C, W, M, L, E,CN,EN;
	}
	//=======================收藏=======我的收藏，关键词表====================end

	// 课件下载表 =============================start
	public final static String TB_CourseFileInfo = "CourseFileInfo";
	public final static String CourseFileInfo = TB_CourseFileInfo + " as cfi";
	public final static String CTREATE_TB_COURSEFILEINFO = "create table if not exists " + TB_CourseFileInfo +
			"(" + 
			CourseFIleInfoClounms.CID.toString() + " varchar," + 
			CourseFIleInfoClounms.UID.toString() + " varchar," + 
			CourseFIleInfoClounms.N.toString() + " varchar," +
			CourseFIleInfoClounms.M.toString() + " varchar," +
			CourseFIleInfoClounms.P.toString() + " varchar," +
			CourseFIleInfoClounms.CNO.toString() + " varchar," +
			CourseFIleInfoClounms.SNO.toString() + " varchar," + 
			CourseFIleInfoClounms.VER.toString() + " varchar," + 
			CourseFIleInfoClounms.L.toString() + " varchar," + 
			CourseFIleInfoClounms.E.toString() + " varchar," + 
			CourseFIleInfoClounms.folder.toString() + " varchar," + 
			"primary key ("+CourseFIleInfoClounms.CID.toString()+","+CourseFIleInfoClounms.UID.toString()+")"+
			")";
	public enum CourseFIleInfoClounms {
		UID, CID, N, M, P, CNO, SNO, VER, L, E, folder;
	}
	
	// 课件下载表 =============================end
	
	/*<item>cn.com.zte.positionenglish.app.entity.basics.CourseInfo</item>
	<item>cn.com.zte.positionenglish.app.entity.basics.DutyInfo</item>
	<item>cn.com.zte.positionenglish.app.entity.basics.ExamPositionInfo</item>
	<item>cn.com.zte.positionenglish.app.entity.basics.FinishCourse</item>
	<item>cn.com.zte.positionenglish.app.entity.basics.LUTInfo</item>
	<item>cn.com.zte.positionenglish.app.entity.basics.MyCourseInfo</item>
	<item>cn.com.zte.positionenglish.app.entity.basics.MySceneInfo</item>
	<item>cn.com.zte.positionenglish.app.entity.basics.MyWordProgress</item>
	<item>cn.com.zte.positionenglish.app.entity.basics.Type_Ref_Duty</item>
	<item>cn.com.zte.positionenglish.app.entity.basics.TypeInfo</item>
	<item>cn.com.zte.positionenglish.app.entity.common.UserInfo</item>
	<item>cn.com.zte.positionenglish.app.entity.basics.Duty_Ref_Course</item>
	<item>cn.com.zte.positionenglish.app.entity.basics.MyDutyInfo</item>
	<item>cn.com.zte.positionenglish.app.entity.curriculum.StudyProgress</item>
	<item>cn.com.zte.positionenglish.app.entity.collection.MyWord</item>
	<item>cn.com.zte.positionenglish.app.entity.collection.KeyWord</item>
	<item>cn.com.zte.positionenglish.app.entity.collection.SentenceInfo</item>
	<item>cn.com.zte.positionenglish.app.entity.examination.ExamResultInfo</item>*/

}
