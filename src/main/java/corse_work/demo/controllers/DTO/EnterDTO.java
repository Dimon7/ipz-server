package corse_work.demo.controllers.DTO;


import corse_work.demo.model.enums.Role;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EnterDTO {

    private String password;

    private String email;

    private String role;

}
