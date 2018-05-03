package corse_work.demo.controllers.DTO;


import corse_work.demo.model.enums.Type;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {


    private Long id;

    private String name;

    private Long teacherId;

    private Type type;

    private String TeacherUserName;


}
