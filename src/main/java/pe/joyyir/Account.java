package pe.joyyir;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    // 1 account <--- 1 study
    // 1 account ---> n study
    // 1 account <--> n study
    // account_studies라는 테이블이 생긴다.
//    @OneToMany // 단방향
    @OneToMany(mappedBy = "owner") // 양방향. Study의 owner 필드와 매핑됨을 전달함. Account가 non-owning side가 되고 Stduy가 owning side가 됨 (FK를 가진 쪽이 owning side)
    private Set<Study> studies = new HashSet<>();

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

    public Set<Study> getStudies() {
        return studies;
    }

    public void setStudies(Set<Study> studies) {
        this.studies = studies;
    }


    // 관계를 설정할 때는 아래와 같이 쌍으로 설정해야함
    // 이러한 관계를 설정하는 메소드를 convenient method 라고 함
    public void addStudy(Study study) {
        this.getStudies().add(study);
        study.setOwner(this);
    }

    // 지울 때도 둘 다 처리
    public void removeStudy(Study study) {
        this.getStudies().remove(study);
        study.setOwner(null);
    }
}
