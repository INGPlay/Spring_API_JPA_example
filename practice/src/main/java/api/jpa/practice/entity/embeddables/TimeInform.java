package api.jpa.practice.entity.embeddables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Embeddable
@Getter
@AllArgsConstructor
public class TimeInform {
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    public TimeInform() {
        this.createdTime = new Date();
        this.updatedTime = new Date();
    }

    public void renewUpdatedTiem(){
        this.updatedTime = new Date();
    }
}
