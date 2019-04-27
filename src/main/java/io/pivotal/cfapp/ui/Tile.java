package io.pivotal.cfapp.ui;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class Tile extends VerticalLayout {
        
    private H3 stat;

    public Tile(String labelName) {
        this.stat = new H3();
        this.add(stat, new Label(labelName));
    }

    public H3 getStat() {
        return stat;
    }

}