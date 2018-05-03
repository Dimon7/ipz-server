package corse_work.demo.model;


import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String number;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY,cascade={CascadeType.ALL},orphanRemoval = true)
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "team",fetch = FetchType.LAZY,cascade={CascadeType.ALL},orphanRemoval = true)
    private List<Exam> exams = new ArrayList<>();
}
