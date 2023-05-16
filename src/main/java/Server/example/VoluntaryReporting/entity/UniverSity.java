package Server.example.VoluntaryReporting.entity;

import lombok.Data;

import java.util.List;

@Data
public class UniverSity {

    private int uId ;
    private String uName;

    private List<Professional> professionals; //院校的所有专业
}
