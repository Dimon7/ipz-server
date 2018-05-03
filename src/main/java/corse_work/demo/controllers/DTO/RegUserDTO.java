package corse_work.demo.controllers.DTO;


import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegUserDTO {

    @NotEmpty(message = "name")
    private String name;
    @NotEmpty(message = "password")
    private String password;
    @NotEmpty(message = "email")
    private String email;

    private Long teamId;
}
