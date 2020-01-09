package pe.joyyir;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager; // JPA의 핵심적인 클래스

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setUsername("jyjang");
        account.setPassword("pass");

        Study study = new Study();
        study.setName("Spring Data JPA");
//        study.setOwner(account);

        account.addStudy(study);

        // 방법 1
//        entityManager.persist(account);

        // 방법 2 : hibernate api
        Session session = entityManager.unwrap(Session.class);
        session.save(account); // 이때 insert 되는게 아님
        session.save(study); // 이때 insert 되는게 아님

        Account jyjang = session.load(Account.class, account.getId()); // 이때 select 쿼리를 날리지 않는다! 캐시에서 객체를 그냥 가져온다.
        System.out.println("====================");
        System.out.println(jyjang.getUsername());
        // 이 메소드 나가서 트랜잭션이 커밋될 시점에 insert 쿼리 실행됨
    }
}
