package ro.infoiasi.wad.sesi.client.reports;

import com.github.gwtbootstrap.client.ui.ControlLabel;
import com.github.gwtbootstrap.client.ui.Icon;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.github.gwtbootstrap.client.ui.WellForm;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.commonwidgets.NamesListEditor;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.WidgetEditor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class ReportEditor extends Composite implements WidgetEditor<ReportBean>,
        ValueChangeHandler<ReportBean.MainResourceType> {

    @Override
    public ReportBean save() {
        return driver.flush();
    }

    @Override
    public void edit(ReportBean bean) {
        driver.edit(bean);
    }

    interface ReportEditorUiBinder extends UiBinder<HTMLPanel, ReportEditor> {

    }
    private static ReportEditorUiBinder ourUiBinder = GWT.create(ReportEditorUiBinder.class);

    // Empty interface declaration, similar to UiBinder
    interface Driver extends SimpleBeanEditorDriver<ReportBean, ReportEditor> {}

    // Create the Driver
    Driver driver = GWT.create(Driver.class);

    @UiField(provided = true)
    ValueListBox<ReportBean.MainResourceType> mainResourceBox = new ValueListBox<ReportBean.MainResourceType>(new Renderer<ReportBean.MainResourceType>() {
        @Override
        public String render(ReportBean.MainResourceType resourceType) {
            return resourceType == null ? "" : resourceType.toString();
        }

        @Override
        public void render(ReportBean.MainResourceType resourceType, Appendable appendable) throws IOException {
            if (resourceType != null)
                appendable.append(resourceType.toString());
        }
    });

    @UiField
    ControlLabel relatedLabel;

    @UiField
    @Ignore
    NamesListEditor specificNamesList;

    @UiField
    WellForm mainForm;
    @UiField
    Icon loadingIcon;

    private List<String> allCompanies;
    private List<String> allSchools;

    public ReportEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));
        driver.initialize(this);

        wireUiElements();


    }

    private void wireUiElements() {
        mainResourceBox.setValue(ReportBean.MainResourceType.Internships);
        mainResourceBox.setAcceptableValues(Arrays.asList(ReportBean.MainResourceType.values()));
        mainResourceBox.addValueChangeHandler(this);
    }

    @Override
    public void onValueChange(ValueChangeEvent<ReportBean.MainResourceType> event) {

        loadingIcon.setVisible(true);
        switch (event.getValue())  {

            case Internships:
                // work with companies async service

                break;
            case Students:
                break;
            default:
                break;
        }



    }
}