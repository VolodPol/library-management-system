package org.project;


import org.project.dao.BookDao;
import org.project.dao.UserDaoImpl;
import org.project.entity.Role;
import org.project.entity.Subscription;
import org.project.entity.User;

public class Main {
    public static void main(String[] args) {
//        BookDao dao = new BookDao();
//        System.out.println(dao.getAllBooks());
//==============================================
//        User user = new User();
//
//        user.setLogin("Bora");
//        user.setPassword("23512");
//        user.setEmail("borabora@gmail.com");
//        user.setFirstName("Boris");
//        user.setSurname("Brytva");
//        user.setPhoneNumber("03245234");
//        user.setAge(18);
//
//
//        System.out.println(impl.insertUser(user));
//        impl.getAll();

//        Connection connection = DataSource.getConnection();
//        connection.close();



//        System.out.println(impl.findUser("jeff"));
//        BookDao impl = new BookDao();
//        System.out.println(impl.getAllBooks());

       User user = new User(
               "Rich",
               "285lf824Lf",
               "richii@gmail.com",
               "Richard",
               "Hammond",
               "8437593213",
               48,
               (byte) 0,
               (byte) 0,
               Role.valueOf("UNREGISTERED"),
               Subscription.valueOf("BASIC"));
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.insertUser(user);
    }
}
