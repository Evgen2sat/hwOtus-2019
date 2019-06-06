package ru.otus.hw6.visitor;

import ru.otus.hw6.atm.ATM;

public interface Visitor {
    void visitATM(ATM atm);
}
