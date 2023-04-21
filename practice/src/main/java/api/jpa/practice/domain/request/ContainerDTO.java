package api.jpa.practice.domain.request;

import api.jpa.practice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ContainerDTO {
    private User user;
    private String title;
}
