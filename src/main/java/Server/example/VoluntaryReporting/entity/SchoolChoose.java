package Server.example.VoluntaryReporting.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolChoose implements Serializable {  //志愿记录类

    public static final int MAX_CNT = 8;
    private int sId;
    private Integer proId1;  //专业1 必填 专业2-4可以不填
    private Integer proId2;
    private Integer proId3;
    private Integer proId4;
    private int order;
    private int acceptAdjust; //是否接受调剂


    //外部数据
    private int uniId; //院校代码
    UniverSity univerSity; //大学
    Professional[]  professionals ;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(String.format("<SchoolChoose>  考号：%d , 志愿排位:%d ,接受调剂：%s , %s ", sId, order, (acceptAdjust == 1 ? "是" : "否"),
                String.format("院校:%d, %s\n填报专业:", uniId, univerSity!=null?univerSity.getUName():"")));
        for(Professional professional :  professionals){
            if(professional ==null){
                break;
            }
            res.append("\n");
            res.append("专业名称：");
            res.append(professional.getProName());
            res.append("\t专业号：");
            res.append(professional.getProId());
        }
        return  res + "\n</SchoolChoose>\n";
    }

}