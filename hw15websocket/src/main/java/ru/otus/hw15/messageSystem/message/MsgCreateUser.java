package ru.otus.hw15.messageSystem.message;

import ru.otus.hw15.dbService.DBService;
import ru.otus.hw15.dto.User;
import ru.otus.hw15.messageSystem.Address;
import ru.otus.hw15.messageSystem.MsgToDB;

public class MsgCreateUser extends MsgToDB {

    private final User user;

    public MsgCreateUser(Address from, Address to, User user) {
        super(from, to);
        this.user = user;
    }

    @Override
    public void execute(DBService<User> dbService) {
        User user = dbService.create(this.user);
        dbService.getMS().sendMessage(new MsgCreateUserAnswer(getTo(), getFrom(), user));
    }
}
