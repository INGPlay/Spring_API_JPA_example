package api.jpa.practice.domain.response;

import api.jpa.practice.entity.Container;
import api.jpa.practice.entity.embeddables.TimeInform;
import api.jpa.practice.entity.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UserInformResponse {
    private String Username;
    private TimeInform timeInform;
    private List<Container> Container;
    private UserRole userRole;
}
