package ru.otus.hw15.messageSystem;

public abstract class Message {
    private final Address to;
    private final Address from;

    public Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Address getTo() {
        return to;
    }

    public Address getFrom() {
        return from;
    }

    public abstract void execute(Addresse addresse);
}
