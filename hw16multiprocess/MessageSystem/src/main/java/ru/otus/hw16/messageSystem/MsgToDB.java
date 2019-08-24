package ru.otus.hw16.messageSystem;

import ru.otus.hw16.dto.User;

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
