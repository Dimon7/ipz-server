package corse_work.demo.service;


//import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    public String getHashOfPassword(String pass){

        //return  BCrypt.hashpw(pass, BCrypt.gensalt(12));
        return pass;
    }

    public  Boolean passwordMatched(String pass, String hash){

        //return BCrypt.checkpw(pass, hash);

        return pass.equals(hash);
    }

}
