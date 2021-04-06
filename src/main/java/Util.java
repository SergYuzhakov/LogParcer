package main.java;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Util {

    public static <T extends Comparable<T>> boolean isBetweenDate(T value, T start, T end) {
        if (value == null) return false;
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) <= 0);
    }

    public static LocalDateTime getLocalDateTime(String string) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        try {
            Date date = formatter.parse(string);
            var timestamp = date.toInstant();
            return LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault());
        } catch (ParseException e) {
            return null;
        }
    }

    public static List<LogEntity> getFilteredLogEntities(List<LogEntity> entityList, Date start, Date end) throws IOException {
        final LocalDateTime after;
        final LocalDateTime before;

        if (start == null && end == null) {
            return entityList;
        } else {
            if (start == null) {
                after = LocalDateTime.MIN;
            } else {
                Instant timestampStart = start.toInstant();
                after = LocalDateTime.ofInstant(timestampStart, ZoneId.systemDefault());
            }
            if (end == null) {
                before = LocalDateTime.MAX;
            } else {
                Instant timestampEnd = end.toInstant();
                before = LocalDateTime.ofInstant(timestampEnd, ZoneId.systemDefault());
            }
            return entityList.stream()
                    .filter(logEntity -> isBetweenDate(logEntity.getDate(), after, before))
                    .collect(Collectors.toList());
        }

    }

    public static LogEntity getLogEntity(String str) {
        String[] strings = str.split("\\t");
        String[] tasks = strings[3].split(" ");
        Event event = Event.valueOf(tasks[0]);
        Status status = Status.valueOf(strings[4]);
        LocalDateTime dateTime = Util.getLocalDateTime(strings[2]);

        if (tasks.length == 1) {
            return new LogEntity(strings[0],
                    strings[1],
                    dateTime,
                    event,
                    null,
                    status);
        } else {
            int numberTask = Integer.parseInt(tasks[1]);
            return new LogEntity(strings[0],
                    strings[1],
                    dateTime,
                    event,
                    numberTask,
                    status);
        }
    }

    public static Date convertDateTime(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Object getValueFromLogEntity(LogEntity logEntity, String field) {
        Object value = null;
        switch (field) {
            case "ip":
                value = new getIPCommand(logEntity).execute();
                break;
            case "user":
                value = new getNameCommand(logEntity).execute();
                break;
            case "date":
                value = new getDateCommand(logEntity).execute();
                break;
            case "event":
                value = new getEventCommand(logEntity).execute();
                break;
            case "status":
                value = new getStatusCommand(logEntity).execute();
                break;
        }
        return value;
    }

    public static Map<Integer, String> getCommandFromQuery(String query) {
        String[] regular = {
                "get (ip|user|date|event|status)"
                , "get (ip|user|date|event|status) for (ip|user|date|event|status) = (\".*?\")"
                , "get (ip|user|date|event|status) for (ip|user|date|event|status) = (\".*?\") and date between (\".*?\") and (\".*?\")"};
        Map<Integer, String> fieldMap = new HashMap<>();
        for (int i = 0; i < regular.length; i++) {
            Pattern pattern = Pattern.compile(regular[i]);
            Matcher matcher = pattern.matcher(query);
            if (matcher.find()) {
                for (int j = 0; j < matcher.groupCount(); j++) {
                    fieldMap.put(j, matcher.group(j + 1));
                }
            }
        }
        return fieldMap;
    }

    public static Object getTypeField(String str) {
        Enum[] events = Event.values();
        Status[] statuses = Status.values();
        if (getLocalDateTime(str) != null) return convertDateTime(getLocalDateTime(str));
        for (Enum e : events) {
            if (str.equals(e.name())) return Event.valueOf(str);
        }
        for (Status s : statuses) {
            if (str.equals(s.name())) return Status.valueOf(str);
        }
        return str;
    }
}
