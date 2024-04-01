package org.cftoolsuite.cfapp.ui.component;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class Tile extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    public Tile(String labelName, String number) {
        H3 stat = new H3(number);
        this.add(stat, new NativeLabel(labelName));
        setSizeFull();
    }

}