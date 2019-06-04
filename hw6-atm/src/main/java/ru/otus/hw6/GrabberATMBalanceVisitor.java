package ru.otus.hw6;

public class GrabberATMBalanceVisitor implements Visitor {
    @Override
    public void visitATM(ATM atm) {
        System.out.println("ATM №" + atm.getNumber() + " " + atm.getBalanceSum() + " " + atm.getCells());
    }
}
