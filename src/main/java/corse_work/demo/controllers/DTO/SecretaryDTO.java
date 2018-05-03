package corse_work.demo.controllers.DTO;


import lombok.*;
import lombok.extern.java.Log;

@Log
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class SecretaryDTO {


    @NonNull
    private Long id;

    @NonNull
    private String UserName;


}
