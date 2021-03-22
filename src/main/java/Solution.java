package main.java;

import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Solution {
    public static void main(String[] args) throws ParseException {
        LogParser logParser = new LogParser(Paths.get("c:/logs/"));
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        Date after = format.parse("19.03.2016 00:00:00");
        Date before = format.parse("21.10.2021 19:45:25");
        /*
        String user = "Amigo";
        main.java.Event event = main.java.Event.DONE_TASK;
        main.java.Status status = main.java.Status.OK;
        System.out.printf("Users with unique IP = %d",logParser.getNumberOfUniqueIPs(null, null));
        System.out.println();
        System.out.println("Unique IP:");
        logParser.getUniqueIPs(null, null).stream().forEach(System.out::println);

        System.out.printf("Get IP for user - %s:", user);
        System.out.println();

        logParser.getIPsForUser(user, null, null).stream().forEach(System.out::println);
        System.out.printf("Get IP for event - %s:", event.name());
        System.out.println();

        logParser.getIPsForEvent(event, null, null).stream().forEach(System.out::println);

        System.out.printf("Get IP for status - %s:", status.name());
        System.out.println();

        logParser.getIPsForStatus(status, null, null).stream().forEach(System.out::println);

        System.out.println("All Users:");
        logParser.getAllUsers().stream().forEach(System.out::println);
        int countUsers = logParser.getNumberOfUsers(null, null);
        System.out.printf("Count unique users = %d", countUsers );
        System.out.println();

        System.out.printf("Count unique events for %s = %d", user, logParser.getNumberOfUserEvents(user, null, null));
        System.out.println();
        System.out.printf("All Users with IP 127.0.0.1 : %s", logParser.getUsersForIP("127.0.0.1", null, null));
        System.out.println();
        System.out.printf("All logged Users : %s", logParser.getLoggedUsers( null, null));
        System.out.println();
        System.out.printf("All downloaded plugin Users : %s", logParser.getDownloadedPluginUsers( null, null));
        System.out.println();
        System.out.printf("All wrote message Users : %s", logParser.getWroteMessageUsers( null, null));
        System.out.println();
        System.out.printf("All solved task Users : %s", logParser.getSolvedTaskUsers( null, null));
        System.out.println();
        System.out.printf("All solved task - %d Users : %s", 18, logParser.getSolvedTaskUsers( null, null, 18));
        System.out.println();
        System.out.printf("All done task Users : %s", logParser.getDoneTaskUsers( null, null));
        System.out.println();
        System.out.printf("All done task - %d Users : %s", 48, logParser.getDoneTaskUsers( null, null, 48));


        System.out.println();
        System.out.printf("Amigo first login at - %s", logParser.getDateWhenUserLoggedFirstTime(user, null, null));
        System.out.println();
        System.out.printf("Count solved Task %d  = %d ", 18 ,logParser.getNumberOfAttemptToSolveTask(18, null, null));
        System.out.println("номер_задачи = сколько_раз_ее_решили:");
        logParser.getAllDoneTasksAndTheirNumber(null, null).entrySet().stream().forEach(System.out::println);

        System.out.println("Get main.java.Event for Vasya Pupkin :" );
        logParser.getEventsForUser("Vasya Pupkin", null, null).stream().forEach(System.out::println);
        System.out.println("номер_задачи = количество_попыток_решить_ее:");
        logParser.getAllSolvedTasksAndTheirNumber(null,null).entrySet().stream().forEach(System.out::println);

         */
        logParser.execute("get ip for user = \"Eduard Petrovich Morozko\"").stream().forEach(System.out::println);
    }

}
