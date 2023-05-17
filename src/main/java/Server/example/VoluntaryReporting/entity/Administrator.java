package Server.example.VoluntaryReporting.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Administrator implements Serializable {
    private String aName;
    private String passwordMd5;
}
