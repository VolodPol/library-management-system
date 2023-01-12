package org.project;


import org.project.DAO.UserDaoImpl;

public class Main {
    public static void main(String[] args) {
        UserDaoImpl impl = new UserDaoImpl();
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
        System.out.println(impl.getAll());
//        System.out.println(LogInChecker.doesMatch("volodPol", "1234"));
    }
}
