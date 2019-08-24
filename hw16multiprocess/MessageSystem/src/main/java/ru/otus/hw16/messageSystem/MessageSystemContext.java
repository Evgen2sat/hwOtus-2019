package ru.otus.hw16.messageSystem;

public final class MessageSystemContext {
    private Address frontendAddress;
    private Address dbAddress;
    private final MessageSystem messageSystem;

    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public Address getFrontendAddress() {
        return frontendAddress;
    }

    public void setFrontendAddress(Address frontendAddress) {
        this.frontendAddress = frontendAddress;
    }

    public Address getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(Address dbAddress) {
        this.dbAddress = dbAddress;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }
}
