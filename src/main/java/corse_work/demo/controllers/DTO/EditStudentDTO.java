package corse_work.demo.controllers.DTO;


import lombok.*;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class EditStudentDTO {


    @NonNull
    private List<Long> ids;

    private Long idteam;


}






