package corse_work.demo.controllers.DTO;


import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class StudentDTO {

    @NonNull
    private Long id;

    @NonNull
    private String UserName;

    private String TeamNumber;

    private Long TeamId;


}








