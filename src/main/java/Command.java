package main.java;

abstract class Command {
    protected LogEntity logEntity;
    abstract Object execute();
}
class getIPCommand extends Command {
    LogEntity logEntity;

    public getIPCommand(LogEntity logEntity) {
        this.logEntity = logEntity;
    }

    @Override
    Object execute() {
        return logEntity.getIp();
    }
}

class getNameCommand extends Command {
    LogEntity logEntity;

    public getNameCommand(LogEntity logEntity) {
        this.logEntity = logEntity;
    }

    @Override
    Object execute() {
        return logEntity.getName();
    }
}

class getDateCommand extends Command {
    LogEntity logEntity;

    public getDateCommand(LogEntity logEntity) {
        this.logEntity = logEntity;
    }

    @Override
    Object execute() {
        return Util.convertDateTime(logEntity.getDate());
    }
}
class getEventCommand extends Command {
    LogEntity logEntity;

    public getEventCommand(LogEntity logEntity) {
        this.logEntity = logEntity;
    }

    @Override
    Object execute() {
        return logEntity.getEvent();
    }
}

class getStatusCommand extends Command {
    LogEntity logEntity;

    public getStatusCommand(LogEntity logEntity) {
        this.logEntity = logEntity;
    }

    @Override
    Object execute() {
        return logEntity.getStatus();
    }
}
