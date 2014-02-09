package ro.infoiasi.wad.sesi.client.companies;

import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceWidgetEditor;
import ro.infoiasi.wad.sesi.core.model.Company;

public class CompanyEditor extends Composite implements ResourceWidgetEditor<Company> {
    @Override
    public Company save() {
        Company company = new Company();
        company.setName(name.getText());
        company.setActive(activeCheckbox.getValue());
        company.setDescription(description.getText());
        company.setSiteUrl(url.getText());
        return company;
    }

    @Override
    public void edit(Company bean) {
        driver.edit(bean);
        activeCheckbox.setValue(bean.isActive());

    }

    interface CompanyEditorUiBinder extends UiBinder<HTMLPanel, CompanyEditor> {
    }

    private static CompanyEditorUiBinder ourUiBinder = GWT.create(CompanyEditorUiBinder.class);

    // Empty interface declaration, similar to UiBinder
    interface Driver extends SimpleBeanEditorDriver<Company, CompanyEditor> {
    }

    // Create the Driver
    Driver driver = GWT.create(Driver.class);

    @UiField
    @Ignore
    CheckBox activeCheckbox;
    @UiField
    @Editor.Path("siteUrl")
    TextBox url;
    @UiField
    @Editor.Path("description")
    TextBox description;
    @UiField
    @Editor.Path("name")
    TextBox name;

    public CompanyEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));
        driver.initialize(this);
    }
}