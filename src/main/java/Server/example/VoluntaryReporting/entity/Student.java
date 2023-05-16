package Server.example.VoluntaryReporting.entity;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Student {

    private int sId;
    private String sName;
    private String sSex;
    private String passWordMd5;
    private int chineseScore;
    private int mathScore;
    private int EnglishScore ;
    int typeFlag ; //0代表历史类，1代表物理类
    private int sub1Score; //物理或历史类的分数
    private int sub2Score;
    private int sub3Score;
    private HighSchool school ;



}
