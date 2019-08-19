package ru.otus.hw15.messageSystem.message;

import ru.otus.hw15.frontendService.FrontendService;
import ru.otus.hw15.dto.User;
import ru.otus.hw15.messageSystem.Address;
import ru.otus.hw15.messageSystem.MsgToFrontend;

public class MsgCreateUserAnswer extends MsgToFrontend {
    private final User user;

    public MsgCreateUserAnswer(Address from, Address to, User user) {
        super(from, to);
        this.user = user;
    }

    @Override
    public void execute(FrontendService frontendService) {
        frontendService.accept(user);
    }
}
