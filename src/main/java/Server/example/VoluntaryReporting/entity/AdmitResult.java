package Server.example.VoluntaryReporting.entity;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmitResult implements Serializable { //录取结果
    private Integer sId; //记录的考生号
    private Integer scoreIndex ; //成绩的排名
    private Integer admitProId;//被录取的院校专业的专业号

    private Integer totalScore; //高考总分
    //外部数据
    private Professional admitPro; //录取的专业

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder(String.format(
              "<AdmitResult>\n考生号：%d ， 成绩排名：%d ， 总分%d ",sId,scoreIndex,totalScore
      ));
      if(admitPro!=null){
          builder.append("\n录取的专业为：");
          builder.append(admitPro.toString());
      }
      else {
          builder.append("未被录取\n");
      }
      builder.append("</AdmitResult>\n");
      return builder.toString();
    }
}
