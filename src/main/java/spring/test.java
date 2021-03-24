package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        UserDao dao = new UserDao();

        User user = new User();
        user.setUsername("test");
        user.setPassword("123");
        dao.insert(user);

        System.out.println("Done !");
    }

}
