package Server.example.VoluntaryReporting.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Professional implements Serializable {
    private int proId;
    private String proName;//专业名称
    private int forecastScore;//预测分数
    private int maxCnt ;  // 最大招生人数
    private int curCnt ;//当前招生人数
    private Integer minScore; //录取最低分数

    private int uId ; //学校id
    private UniverSity univerSity; //所属的学校

    @Override
    public String toString() {
      return String.format(
              "专业号:%d ，专业名称:%s ，院校代码：%d ，专业预测分数:%d，最大招生人数:%d，当前录取人数:%d，最低分数:%s %s",
              proId,proName,uId,forecastScore,maxCnt,curCnt,minScore==null?"unknow":String.valueOf(minScore),
              univerSity==null?"":String.format("所属院校:%s",univerSity.getUName())
      );

    }
}
