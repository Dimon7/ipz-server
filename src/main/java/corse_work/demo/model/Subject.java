package corse_work.demo.model;


import corse_work.demo.model.enums.Type;
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
@Table(name = "subject")
public class Subject {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY,cascade={CascadeType.DETACH})
    @JoinColumn(name = "secretary_id", nullable = false)
    private Secretary secretary;

    @ManyToOne(fetch = FetchType.LAZY,cascade={CascadeType.DETACH})
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @OneToMany(mappedBy = "subject",fetch = FetchType.LAZY,cascade={CascadeType.ALL},orphanRemoval = true)
    private List<Exam> exams = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Type type;






}
