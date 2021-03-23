package main.java;

import main.java.query.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static main.java.Util.*;


public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    private final Path logDir;
    private final List<LogEntity> logEntities = new ArrayList<>();

    public LogParser(Path logDir) {
        this.logDir = logDir;
        readLogs(logDir);
    }

    private void readLogs(Path logDir) {
        List<Path> listPath = null;
        try {
            listPath = Files.list(logDir)
                    .filter(Files::isRegularFile)
                    .filter(file -> file.toString().toLowerCase().endsWith(".log"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (listPath != null) {
            for (Path f : listPath) {
                try {
                    logEntities.addAll(new ArrayList<>(Files.readAllLines(f).stream().map(Util::getLogEntity).collect(Collectors.toList())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        return getUniqueIPs(after, before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .map(LogEntity::getIp).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(x -> x.getName().equals(user))
                    .map(LogEntity::getIp).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(x -> x.getEvent().equals(event))
                    .map(LogEntity::getIp).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(x -> x.getStatus().equals(status))
                    .map(LogEntity::getIp).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<String> getAllUsers() {
        try {
            return getFilteredLogEntities(logEntities, null, null).stream()
                    .map(LogEntity::getName).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .map(LogEntity::getName).collect(Collectors.toSet()).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(u -> u.getName().equals(user))
                    .map(LogEntity::getEvent).collect(Collectors.toSet()).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(u -> u.getIp().equals(ip))
                    .map(LogEntity::getName).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(u -> u.getEvent().equals(Event.LOGIN))
                    .map(LogEntity::getName).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(u -> u.getEvent().equals(Event.DOWNLOAD_PLUGIN))
                    .map(LogEntity::getName).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(u -> u.getEvent().equals(Event.WRITE_MESSAGE))
                    .map(LogEntity::getName).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(u -> u.getEvent().equals(Event.SOLVE_TASK))
                    .map(LogEntity::getName).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(u -> u.getEvent().equals(Event.SOLVE_TASK))
                    .filter(logEntity -> logEntity.getNumberOfTask() == task)
                    .map(LogEntity::getName).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(u -> u.getEvent().equals(Event.DONE_TASK))
                    .map(LogEntity::getName).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(u -> u.getEvent().equals(Event.DONE_TASK))
                    .filter(logEntity -> logEntity.getNumberOfTask() == task)
                    .map(LogEntity::getName).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(u -> u.getName().equals(user))
                    .filter(logEntity -> logEntity.getEvent().equals(event))
                    .map(logEntity -> convertDateTime(logEntity.getDate())).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(logEntity -> logEntity.getStatus().equals(Status.FAILED))
                    .map(logEntity -> convertDateTime(logEntity.getDate())).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(logEntity -> logEntity.getStatus().equals(Status.ERROR))
                    .map(logEntity -> convertDateTime(logEntity.getDate())).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(u -> u.getName().equals(user))
                    .filter(logEntity -> logEntity.getEvent().equals(Event.LOGIN))
                    .map(logEntity -> convertDateTime(logEntity.getDate())).sorted().findFirst().orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(u -> u.getName().equals(user))
                    .filter(logEntity -> logEntity.getEvent().equals(Event.SOLVE_TASK))
                    .filter(logEntity -> logEntity.getNumberOfTask() == task)
                    .map(logEntity -> convertDateTime(logEntity.getDate()))
                    .sorted().findFirst().orElse(null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(u -> u.getName().equals(user))
                    .filter(logEntity -> logEntity.getEvent().equals(Event.DONE_TASK))
                    .filter(logEntity -> logEntity.getNumberOfTask() == task)
                    .map(logEntity -> convertDateTime(logEntity.getDate()))
                    .sorted().findFirst().orElse(null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(u -> u.getName().equals(user))
                    .filter(logEntity -> logEntity.getEvent().equals(Event.WRITE_MESSAGE))
                    .map(logEntity -> convertDateTime(logEntity.getDate())).collect(Collectors.toSet());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(u -> u.getName().equals(user))
                    .filter(logEntity -> logEntity.getEvent().equals(Event.DOWNLOAD_PLUGIN))
                    .map(logEntity -> convertDateTime(logEntity.getDate())).collect(Collectors.toSet());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return getAllEvents(after, before).size();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .map(LogEntity::getEvent)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(logEntity -> logEntity.getIp().equals(ip))
                    .map(LogEntity::getEvent)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(logEntity -> logEntity.getName().equals(user))
                    .map(LogEntity::getEvent)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(logEntity -> logEntity.getStatus().equals(Status.FAILED))
                    .map(LogEntity::getEvent)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(logEntity -> logEntity.getStatus().equals(Status.ERROR))
                    .map(LogEntity::getEvent)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(logEntity -> logEntity.getEvent().equals(Event.SOLVE_TASK) && logEntity.getNumberOfTask() == task)
                    .collect(Collectors.toSet()).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(logEntity -> logEntity.getEvent().equals(Event.DONE_TASK) && logEntity.getNumberOfTask() == task)
                    .collect(Collectors.toSet()).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(logEntity -> logEntity.getEvent().equals(Event.SOLVE_TASK))
                    .collect(Collectors.toMap(k -> k.getNumberOfTask()
                            , v -> getNumberOfAttemptToSolveTask(v.getNumberOfTask(), after, before)
                            , (v1, v2) -> v1));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {

        try {
            return getFilteredLogEntities(logEntities, after, before).stream()
                    .filter(logEntity -> logEntity.getEvent().equals(Event.DONE_TASK))
                    .collect(Collectors.toMap(k -> k.getNumberOfTask()
                            , v -> getNumberOfSuccessfulAttemptToSolveTask(v.getNumberOfTask(), after, before)
                            , (v1, v2) -> v1));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Object> execute(String query) {
        Map<Integer, String> commandMap = getCommandFromQuery(query);
        if (commandMap.size() == 1) {
            String field = commandMap.get(0);
            return logEntities.stream()
                    .map(logEntity -> (getValueFromLogEntity(logEntity, field))).collect(Collectors.toSet());
        } else {
            String outField = commandMap.get(0);
            String filterField = commandMap.get(1);
            String expectedField = commandMap.get(2).replaceAll("\"", "");
            Object expected = getTypeField(expectedField);
            return logEntities.stream()
                    .filter(logEntity -> getValueFromLogEntity(logEntity, filterField).equals(expected))
                    .map(logEntity -> getValueFromLogEntity(logEntity, outField))
                    .collect(Collectors.toSet());
        }
    }

}
