package corse_work.demo.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority{

    ROLE_TEACHER, ROLE_STUDENT, ROLE_SECRETARY;

    public String getAuthority() {
        return name();
    }
}
