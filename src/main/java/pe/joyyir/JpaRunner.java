package pe.joyyir;

import com.querydsl.jpa.impl.JPAQuery;
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
//        Account account = new Account();
//        account.setUsername("jyjang");
//        account.setPassword("pass");
//
//        Study study = new Study();
//        study.setName("Spring Data JPA");
////        study.setOwner(account);
//
//        account.addStudy(study);
//
//        // 방법 1
////        entityManager.persist(account);
//
//        // 방법 2 : hibernate api
//        Session session = entityManager.unwrap(Session.class);
//        session.save(account);
//        session.save(study);
//
//        Account jyjang = session.load(Account.class, account.getId());
//        jyjang.setUsername("joyyir");
//        jyjang.setUsername("jyjang"); // update가 발생하지 않음!!! 바꿀 필요가 없으니까 (dirty checking, write behind)
//        System.out.println("====================");
//        System.out.println(jyjang.getUsername());

//        Post post = new Post();
//        post.setTitle("첫 글이에요");
//
//        Comment comment1 = new Comment();
//        comment1.setComment("안녕하세요");
//        post.addComment(comment1);
//
//        Comment comment2 = new Comment();
//        comment2.setComment("반가워요");
//        post.addComment(comment2);
//
//        Session session = entityManager.unwrap(Session.class);
//        session.save(post); // post도 저장되고 comment1, comment2도 저장된다.

//        Session session = entityManager.unwrap(Session.class);
//        Post post = session.get(Post.class, 1L);
//        session.delete(post); // post도 제거되고 comment1, comment2도 제거된다.
//        Comment comment = session.get(Comment.class, 2L);

//        System.out.println(comment.getComment());
//        System.out.println("이때 SELECT 실행 안함 (EAGER)");
//        System.out.println(comment.getPost().getTitle());

//        // 방법 1
//        TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post p", Post.class);
//        List<Post> posts = query.getResultList();
////        posts.forEach(System.out::println);
//
//        // 방법 2
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Post> query2 = builder.createQuery(Post.class);
//        Root<Post> root = query2.from(Post.class);
//        query2.select(root);
//        List<Post> posts2 = entityManager.createQuery(query2).getResultList();
////        posts2.forEach(System.out::println);
//
//        // 방법 3. NamedQuery 쓰면 mybatis처럼 쓸 수 있다.
//
//        // 방법 4
//        Query query3 = entityManager.createNativeQuery("select * from Post", Post.class);
//        List<Post> posts3 = query3.getResultList();
//        posts3.forEach(System.out::println);

        // QueryDSL 연동 테스트
        Account account = new Account();
        account.setUsername("jyjang");
        account.setPassword("pass");

        entityManager.persist(account);

        QAccount qAccount = QAccount.account;
        JPAQuery<?> query = new JPAQuery<Void>(entityManager);
        Account jyjang = query.select(qAccount)
                              .from(qAccount)
                              .where(qAccount.username.eq("jyjang"))
                              .fetchOne();
        System.out.println(jyjang);
    }
}
