package main.java;

import java.time.LocalDateTime;

public class LogEntity {
    String ip;
    String name;
    LocalDateTime dateTime;
    Integer numberOfTask;
    Event event;
    Status status;

    public LogEntity(String ip, String name, LocalDateTime dateTime, Event event, Integer numberOfTask, Status status) {
        this.ip = ip;
        this.name = name;
        this.dateTime = dateTime;
        this.numberOfTask = numberOfTask;
        this.event = event;
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDate() {
        return dateTime;
    }

    public Integer getNumberOfTask() {
        return numberOfTask;
    }

    public Event getEvent() {
        return event;
    }

    public Status getStatus() {
        return status;
    }


    @Override
    public String toString() {
        return "main.java.LogEntity{" +
                "ip='" + ip + '\'' +
                ", name='" + name + '\'' +
                ", date=" + dateTime +
                ", event='" + event + '\'' +
                ", numberOfTask=" + numberOfTask +
                ", status='" + status + '\'' +
                '}';
    }


}
