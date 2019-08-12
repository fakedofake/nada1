package payroll.memento;

import java.util.Stack;

public class ConcreteCareTaker implements CareTaker {
    private Stack<Memento> states;
    private Stack<Memento> toRedo;
    private Originator originator;

    public ConcreteCareTaker(Originator o){
        originator = o;
        states = new Stack<Memento>();
        toRedo = new Stack<Memento>();
    }

    public void saveState(){
        Memento m = originator.createMemento();
        states.push(m);
        if (!toRedo.isEmpty()){
            toRedo.clear();
        }
    }

    public void undo(){
            Memento m = originator.createMemento();
            originator.setMemento(states.pop());
            toRedo.push(m);
    }

    public void redo(){
            Memento m = originator.createMemento();
            originator.setMemento(toRedo.pop());
            states.push(m);
    }
}
