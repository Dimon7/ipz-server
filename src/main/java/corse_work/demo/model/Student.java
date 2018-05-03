package corse_work.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToOne(optional = false,cascade = {CascadeType.ALL})
    @JoinColumn(name="user_id", unique = true, nullable = false)
    private User user;

    @OneToMany(mappedBy = "student",fetch = FetchType.LAZY,cascade={CascadeType.ALL},orphanRemoval = true)
    private List<Exam> exams = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY,cascade={CascadeType.DETACH})
    @JoinColumn(name = "team_id", nullable = true)
    private Team team;

}
