package ru.otus.hw15.messageSystem.message;

import ru.otus.hw15.dbService.DBService;
import ru.otus.hw15.dto.User;
import ru.otus.hw15.messageSystem.Address;
import ru.otus.hw15.messageSystem.MsgToDB;

import java.util.List;

public class MsgGetAllUsers extends MsgToDB {
    public MsgGetAllUsers(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void execute(DBService<User> dbService) {
        List<User> users = dbService.getItems();
        dbService.getMS().sendMessage(new MsgGetAllUsersAnswer(getTo(), getFrom(), users));
    }
}
