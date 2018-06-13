package corse_work.demo.controllers.DTO;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SummarDTO {

    List<String> good = new ArrayList<>();

    List<String> borg = new ArrayList<>();

    List<String> bad = new ArrayList<>();

    List<SummarSubjectDTO> s = new ArrayList<>();

    List<SummarTeamDTO> t = new ArrayList<>();

    public void setBorg(String borg) {
        this.borg.add(borg);
    }

    public void setGood(String good) {
        this.good.add(good);
    }

    public void setBad(String bad){
        this.bad.add(bad);
    }


    public void setT( String teamNumber, Double avr){
        SummarTeamDTO summarTeamDTO = new SummarTeamDTO( teamNumber, avr );
        this.t.add( summarTeamDTO );
    }

    public void setS(String subName, Double avr){
        SummarSubjectDTO summarSubjectDTO = new SummarSubjectDTO( subName, avr);
        this.s.add(summarSubjectDTO);
    }



}

