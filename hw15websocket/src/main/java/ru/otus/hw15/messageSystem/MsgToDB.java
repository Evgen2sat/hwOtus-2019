package ru.otus.hw15.messageSystem;

import ru.otus.hw15.dbService.DBService;
import ru.otus.hw15.dto.User;

public abstract class MsgToDB extends Message {

    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void execute(Addresse addresse) {
        if(addresse instanceof DBService){
            execute((DBService<User>)addresse);
        }
    }

    public abstract void execute(DBService<User> dbService);
}
