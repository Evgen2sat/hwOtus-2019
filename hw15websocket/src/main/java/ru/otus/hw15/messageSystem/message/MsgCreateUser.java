package ru.otus.hw15.messageSystem.message;

import ru.otus.hw15.dbService.DBService;
import ru.otus.hw15.dto.User;
import ru.otus.hw15.messageSystem.Address;
import ru.otus.hw15.messageSystem.MsgToDB;

import java.util.List;

public class MsgCreateUser extends MsgToDB {

    private final User user;

    public MsgCreateUser(Address from, Address to, User user) {
        super(from, to);
        this.user = user;
    }

    @Override
    public void execute(DBService<User> dbService) {
        dbService.create(this.user);
        List<User> users = dbService.getItems();
        dbService.getMS().sendMessage(new MsgCreateUserAnswer(getTo(), getFrom(), users));
    }
}
