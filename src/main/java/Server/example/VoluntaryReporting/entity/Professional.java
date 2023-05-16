package Server.example.VoluntaryReporting.entity;

import lombok.Data;

@Data
public class Professional {
    private int proId;
    private String proName;//专业名称
    private int forecastScore;//预测分数
    private int maxCnt ;  // 最大招生人数
    private int curCnt ;//当前招生人数
    private Integer minScore; //录取最低分数

    private UniverSity univerSity; //所属的学校
}
