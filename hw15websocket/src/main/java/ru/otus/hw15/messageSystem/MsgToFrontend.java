package ru.otus.hw15.messageSystem;

import ru.otus.hw15.frontendService.FrontendService;

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
