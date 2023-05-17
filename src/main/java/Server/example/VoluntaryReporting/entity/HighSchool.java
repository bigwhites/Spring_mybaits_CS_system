package Server.example.VoluntaryReporting.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HighSchool implements Serializable {
    private int schId;
    private String schName;
}
