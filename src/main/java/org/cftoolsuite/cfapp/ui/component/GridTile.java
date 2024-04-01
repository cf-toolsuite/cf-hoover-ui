package org.cftoolsuite.cfapp.ui.component;

import java.util.Collection;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class GridTile<T> extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    public GridTile(String labelName, Class<T> type, Collection<T> items, String[] propertyNames) {
        Grid<T> grid = new Grid<T>(type);
        grid.setColumns(propertyNames);
        grid.setItems(items);
        grid.setSizeFull();
        add(new NativeLabel(labelName), grid);
        setAlignItems(Alignment.STRETCH);
        setSizeFull();
    }

    public GridTile(String labelName, Grid<T> grid, Collection<T> items) {
        grid.setItems(items);
        grid.setSizeFull();
        add(new NativeLabel(labelName), grid);
        setAlignItems(Alignment.STRETCH);
        setSizeFull();
    }

    public GridTile(String labelName, Grid<T> grid) {
        grid.setSizeFull();
        add(new NativeLabel(labelName), grid);
        setAlignItems(Alignment.STRETCH);
        setSizeFull();
    }
}