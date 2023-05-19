package Server.example.VoluntaryReporting.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniverSity implements Serializable {

    private int uId ;
    private String uName;

    private List<Professional> professionals; //院校的所有专业

    @Override
    public String toString() {
        StringBuilder res  = new StringBuilder();
        res.append(String.format("<UniverSity>\n院校代码：%d,院校名称:%s,专业数量:%d",uId,uName,professionals.size()));
        if(professionals.size() != 0) {
            res.append("\n下设专业:");
            for (Professional p : professionals) {
                res.append("\n");
                res.append(p.toString());
            }
        }
        res.append("\n</UniverSity>\n");
        return res.toString();
    }
}
