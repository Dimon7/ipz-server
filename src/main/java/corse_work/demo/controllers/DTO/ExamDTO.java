package corse_work.demo.controllers.DTO;


import corse_work.demo.model.Secretary;
import corse_work.demo.model.Teacher;
import corse_work.demo.model.Team;
import corse_work.demo.model.enums.Type;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ExamDTO {

    private Long id;

    private Long subjectId;

    private Long grade;

    private Long teamId;

    private String StudentUserName;

    private String TeacherUserName;

    private String SubjectName;

    private String date;

    private String StudentTeamNumber;

}
