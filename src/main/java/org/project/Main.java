package org.project;

import org.project.dao.CheckoutDao;

import java.sql.Timestamp;

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

//       User user = new User(
//               "Rich",
//               "285lf824Lf",
//               "richii@gmail.com",
//               "Richard",
//               "Hammond",
//               "8437593213",
//               48,
//               (byte) 0,
//               (byte) 0,
//               Role.valueOf("UNREGISTERED"),
//               Subscription.valueOf("BASIC"));
//        UserDaoImpl userDao = new UserDaoImpl();
//        userDao.insertUser(user);

//        System.out.println(new OrderDao().createOrder(
//                new UserDaoImpl().findUser("Phil"),
//                new BookDao().findBook("5739038320601"),
//                Timestamp.valueOf("2023-04-12 19:00:00"),
//                Timestamp.valueOf("2023-05-12 19:00:00")
//        ));




//        String fromRequest = "2023-01-19T16:28";
//        fromRequest = fromRequest.replace('T', ' ').concat(":00");
//        System.out.println(fromRequest);
//        System.out.println(Timestamp.valueOf(fromRequest));

//        new CheckoutDao().getAllCheckouts().forEach(System.out::println);

        new CheckoutDao().confirmCheckout(1);
    }
}
