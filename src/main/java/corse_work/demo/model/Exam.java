package corse_work.demo.model;


import corse_work.demo.model.enums.Type;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "exam")
public class Exam {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade={CascadeType.DETACH})
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY,cascade={CascadeType.DETACH})
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;


    private String date;

    private Long grade;

    @ManyToOne(fetch = FetchType.LAZY,cascade={CascadeType.DETACH})
    @JoinColumn(name="subject_id",  nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY,cascade={CascadeType.DETACH})
    @JoinColumn(name="teacher_id",  nullable = false)
    private Teacher teacher;
}
