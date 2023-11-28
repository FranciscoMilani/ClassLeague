package br.ucs.classleague.presentation.model;

import java.beans.PropertyChangeListener;
import javax.swing.event.SwingPropertyChangeSupport;

public abstract class AbstractModel {
    
    protected SwingPropertyChangeSupport pcs;

    public AbstractModel() {
        pcs = new SwingPropertyChangeSupport(this);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
}
