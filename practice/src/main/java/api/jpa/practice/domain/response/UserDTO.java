package api.jpa.practice.domain.response;

import api.jpa.practice.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class UserDTO {
    private String username;

    private UserRole userRole;

    private Date createdTime;
    private Date updatedTime;
}
