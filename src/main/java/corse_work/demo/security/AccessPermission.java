package corse_work.demo.security;

import com.sun.org.apache.xpath.internal.SourceTree;
import corse_work.demo.model.Secretary;
import corse_work.demo.model.Student;
import corse_work.demo.model.Teacher;
import corse_work.demo.model.User;
import corse_work.demo.model.enums.Role;
import corse_work.demo.service.interfaces.SecretaryService;
import corse_work.demo.service.interfaces.StudentService;
import corse_work.demo.service.interfaces.TeacherService;
import corse_work.demo.service.interfaces.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Log(topic = "AccessPermission")
@Component
public class AccessPermission {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SecretaryService secretaryService;

    @Autowired
    private TeacherService teacherService;

    public boolean checkId(HttpServletRequest request, String id){
        // get id of user from requestURI and set as attribute

        request.setAttribute("rqId",id);
        return true;
    }


    public boolean canAccessCustomer(UsernamePasswordAuthenticationToken currentCustomer, Long id) {
        System.out.println("HERE");

        UserDetails userDetails = (UserDetails) currentCustomer.getPrincipal();

        Optional<User> user = userService.getUserByName( userDetails.getUsername() );

        Role role = user.get().getRole();

        Long userId = null;

        if(role == Role.ROLE_STUDENT){
            Optional<Student> obj = studentService.getStudentByUser(user.get());
            userId  = obj.get().getId();

            System.out.println(userId);
        }

        if(role == Role.ROLE_SECRETARY){
            Optional<Secretary> obj = secretaryService.getSecretaryByUser(user.get());
            userId  = obj.get().getId();
        }

        if(role == Role.ROLE_TEACHER){
            Optional<Teacher>  obj = teacherService.getTeacherByUser(user.get());

            userId  = obj.get().getId();
        }

        return user.isPresent() && userId.equals(id);

    }


}
