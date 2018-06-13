package corse_work.demo.controllers.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class TokenDTO {


    private String token;

    private Long id;

    private String role;

}
