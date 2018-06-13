package corse_work.demo.controllers.DTO;


import corse_work.demo.model.Secretary;
import corse_work.demo.model.Teacher;
import corse_work.demo.model.Team;
import corse_work.demo.model.enums.Type;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ExamDTO {

    @NonNull
    private String id;

    private String subjectId;

    private String grade;

    private String teamId;

    private String StudentUserName;

    private String TeacherUserName;

    private String SubjectName;

    private String date;

    private String StudentTeamNumber;

    private Set<String> teams;
    private Set<String> subjects;

}
