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
        session.save(account);
        session.save(study);

        Account jyjang = session.load(Account.class, account.getId());
        jyjang.setUsername("joyyir");
        jyjang.setUsername("jyjang"); // update가 발생하지 않음!!! 바꿀 필요가 없으니까 (dirty checking, write behind)
        System.out.println("====================");
        System.out.println(jyjang.getUsername());
    }
}
