package Server.example.VoluntaryReporting.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student  implements Serializable {

    private int sId;
    private String sName;

    private String identyId; //身份证号
    private String sSex;
    private String passWordMd5;
    private int chineseScore;
    private int mathScore;
    private int englishScore ;
    int typeFlag ; //0代表历史类，1代表物理类
    private int sub1Score; //物理或历史类的分数
    private String sub2Name;
    private int sub2Id;
    private int sub2Score;
    private  String sub3Name;
    private int sub3Id;
    private int sub3Score;
    private int totalScore;

    //外部数据
    private int highSchoolId;
    private HighSchool school ;

    @Override
    public String toString() {
        return String.format("[ 姓名：%s 考生号:%d 身份证号：%s 性别:%s 总分:%d 高中:%s \n"
                , sName, sId, identyId, sSex, totalScore, school.getSchName()) + String.format("%s成绩：%d ，%s成绩：%d，%s成绩：%d  ]",
                typeFlag == 1 ? "物理" : "历史", sub1Score, sub2Name, sub2Score, sub3Name, sub3Score);
    }
}
