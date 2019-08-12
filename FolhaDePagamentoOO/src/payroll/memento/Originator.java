package payroll.memento;

public interface Originator {
    Memento createMemento();
    void setMemento(Memento mem);
}
