package ru.otus.hw16.messageSystem.message;

import ru.otus.hw16.dto.User;
import ru.otus.hw16.messageSystem.Address;
import ru.otus.hw16.messageSystem.FrontendService;
import ru.otus.hw16.messageSystem.MsgToFrontend;

import java.util.List;

public class MsgGetAllUsersAnswer extends MsgToFrontend {
    private final List<User> users;

    public MsgGetAllUsersAnswer(Address from, Address to, List<User> users) {
        super(from, to);
        this.users = users;
    }

    @Override
    public void execute(FrontendService frontendService) {
        frontendService.accept(users);
    }
}
