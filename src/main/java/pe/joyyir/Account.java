package pe.joyyir;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class Account {

    @Id @GeneratedValue
    private Long id;

//    @Column // 이것이 생략되어 있는 것임 (unique, nullable 등 설정 가능)
    private String username;

    private String password;

    @Temporal(TemporalType.TIMESTAMP) // DATE(날짜), TIME(시간), TIMESTAMP(날짜+시간), JPA 2.1 까지는 @Temporal은 Date와 Calander 밖에 지원하지 않음
    private Date created;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "home_street")) // 테이블의 home_street 컬럼을 homeAddress 객체의 street 필드로 매핑
    })
    private Address homeAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
