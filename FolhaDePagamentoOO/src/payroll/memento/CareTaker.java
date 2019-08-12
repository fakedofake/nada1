package payroll.memento;

public interface CareTaker {
    void saveState();
    void undo();
    void redo();
}
