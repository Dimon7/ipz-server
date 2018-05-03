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
@Table(name = "secretary")
public class Secretary {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToOne(optional = false,cascade = {CascadeType.ALL})
    @JoinColumn(name="user_id", unique = true, nullable = false)
    private User user;

    @OneToMany(mappedBy = "secretary", fetch = FetchType.LAZY,cascade={CascadeType.ALL},orphanRemoval = true)
    private List<Subject> subjects = new ArrayList<>();

}
