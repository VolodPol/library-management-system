package org.project;


import org.project.exceptions.DaoException;
import org.project.utils.PagePathConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws DaoException {
//        System.out.println(new OrderDao().createOrder(
//                new UserDaoImpl().findUser("Phil"),
//                new BookDao().findBook("5739038320601"),
//                Timestamp.valueOf("2023-04-12 19:00:00"),
//                Timestamp.valueOf("2023-05-12 19:00:00")
//        ));

//        String fromRequest = "2023-01-19T16:28";

//        String before = "2023-01-19 16:28:00";
//        String today = "2023-02-01 16:20:00";
//            String start = "2023-05-12T10:00";
//            String end = "2023-05-12T14:00";
//
//            System.out.println(validateEndAfter(start, end));
//
//            System.out.println(new Timestamp(System.currentTimeMillis()));

//        System.out.println(getShiftTime(new Timestamp(System.currentTimeMillis()) ,true));
//        System.out.println(getShiftTime(new Timestamp(System.currentTimeMillis()) ,false));
//            System.out.println(validateOrderTime(start, end, Type.SUBSCRIPTION));
//            System.out.println(validateEndAfter(start, end));

        System.out.println(PagePathConfigurator.getPath("edit_book"));

    }
}