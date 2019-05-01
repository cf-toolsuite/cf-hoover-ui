package io.pivotal.cfapp.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class GridTile<T> extends VerticalLayout {
        
    private static final long serialVersionUID = 1L;
    
    private final Grid<T> stat;

    public GridTile(String labelName) {
        this.stat = new Grid<T>();
        this.add(stat, new Label(labelName));
    }

    public Grid<T> getStat() {
        return stat;
    }

}