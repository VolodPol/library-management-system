package org.project;


import org.project.dao.BookDao;
import org.project.entity.sorting.OrderType;
import org.project.entity.sorting.Sorting;
import org.project.exceptions.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
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

//        String before = "2023-01-19 16:28:00";
//        String today = "2023-02-01 16:20:00";
//
//        Timestamp beforeTms = Timestamp.valueOf(before);
//        Timestamp todayTms = Timestamp.valueOf(today);
//
//        System.out.println(todayTms.compareTo(beforeTms));

//        List<OrderedBookDTO> myOrderList = UserBooksProvider.getUserBooks("reader", "Volod");
//        myOrderList.forEach(System.out::println);
//
//        List<String> messages = myOrderList.stream()
//                .map(entry -> FineChecker.compareTime(entry.getReturnDate()))
//                .toList();
//
//        messages.forEach(System.out::println);
        System.out.println(OrderType.DEFAULT);
        System.out.println(OrderType.TITLE);
        System.out.println(OrderType.AUTHOR);
        System.out.println(OrderType.PUBLICATION);
        System.out.println(OrderType.DATE);
        try {
            new BookDao().getAll(0, 5, Sorting.DESC, OrderType.DATE).forEach(System.out::println);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}