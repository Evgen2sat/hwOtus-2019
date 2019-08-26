package ru.otus.hw16.messageSystem;

import ru.otus.hw16.messageSystem.message.Message;

public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void execute(Addresse addresse) {
        if(addresse instanceof FrontendService) {
            execute((FrontendService)addresse);
        }
    }

    public abstract void execute(FrontendService frontendService);
}
