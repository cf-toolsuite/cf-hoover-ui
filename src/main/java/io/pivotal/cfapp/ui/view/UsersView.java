package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.UsersView.NAV;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import io.pivotal.cfapp.domain.User;
import io.pivotal.cfapp.repository.MetricCache;
import io.pivotal.cfapp.ui.MainLayout;
import io.pivotal.cfapp.ui.component.GridTile;

@Route(value = NAV, layout = MainLayout.class)
public class UsersView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    public static final String NAV = "snapshot/detail/users";

    @Autowired
    public UsersView(
        MetricCache cache) {
        // TODO Resource bundle for title and tile labels
        H2 title = new H2("Snapshot » Detail » Users");
        HorizontalLayout firstRow = new HorizontalLayout();
        Set<User> userAccounts = cache.getSnapshotDetail().getUserAccounts().stream().map(ua -> new User(ua)).collect(Collectors.toSet());
        Set<User> serviceAccounts = cache.getSnapshotDetail().getServiceAccounts().stream().map(sa -> new User(sa)).collect(Collectors.toSet());
        GridTile<User> userAccountsGrid = new GridTile<>("User Accounts", buildGrid(userAccounts));
        GridTile<User> serviceAccountsGrid = new GridTile<>("Service Accounts", buildGrid(serviceAccounts));
        firstRow.add(userAccountsGrid, serviceAccountsGrid);
        add(title, firstRow);
        firstRow.setSizeFull();
        setSizeFull();
    }

    private Grid<User> buildGrid(Collection<User> items) {
        Grid<User> grid = new Grid<>(User.class, false);
        ListDataProvider<User> dataProvider = new ListDataProvider<>(items);
        grid.setItems(dataProvider);

        Column<User> nameColumn = grid.addColumn(LitRenderer.<User> of("${item.name}").withProperty("name", User::getName)).setHeader("Name").setTextAlign(ColumnTextAlign.START).setResizable(true);

        HeaderRow filterRow = grid.appendHeaderRow();

        TextField nameField = new TextField();
        nameField.addValueChangeListener(
            event -> dataProvider.addFilter(
                f -> StringUtils.containsIgnoreCase(f.getName(), nameField.getValue())));
        nameField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(nameColumn).setComponent(nameField);
        nameField.setSizeFull();
        nameField.setPlaceholder("Filter");

        // @see https://github.com/vaadin/vaadin-grid-flow/issues/234
        for (Column<User> column : grid.getColumns())
	        column.getElement().getParent().callJsFunction("setAttribute", "resizable", true);

        return grid;
    }

}