package io.pivotal.cfapp.ui.view;

import static io.pivotal.cfapp.ui.view.HomeView.NAV;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

import io.pivotal.cfapp.ui.MainLayout;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Route(value = NAV, layout = MainLayout.class)
public class HomeView extends Div {

    private static final long serialVersionUID = 1L;
	public static final String NAV = "";

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        log.trace("Navigated to ROOT... now redirecting!");
        UI.getCurrent().navigate(SnapshotApplicationSummaryView.class);
  }
}