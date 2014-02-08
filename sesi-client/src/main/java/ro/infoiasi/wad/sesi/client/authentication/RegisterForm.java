package ro.infoiasi.wad.sesi.client.authentication;

import com.github.gwtbootstrap.client.ui.*;
import com.github.gwtbootstrap.client.ui.constants.ControlGroupType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.companies.CompaniesService;
import ro.infoiasi.wad.sesi.client.students.StudentsService;
import ro.infoiasi.wad.sesi.client.teachers.TeachersService;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.HasEventBus;
import ro.infoiasi.wad.sesi.core.model.UserAccountType;

import java.io.IOException;
import java.util.Arrays;


public class RegisterForm extends Composite implements HasEventBus {
    @Override
    public HandlerManager getEventBus() {
        return eventBus;
    }

    interface RegisterFormUiBinder extends UiBinder<HTMLPanel, RegisterForm> {
    }

    private static RegisterFormUiBinder ourUiBinder = GWT.create(RegisterFormUiBinder.class);

    private final HandlerManager eventBus;

    @UiField
    TextBox userName;
    @UiField
    ControlGroup userNameControlGroup;
    @UiField
    ControlGroup passwordControlGroup;
    @UiField
    PasswordTextBox password;
    @UiField
    HelpInline passwordHelpInline;
    @UiField
    HelpInline userNameHelpInline;
    @UiField
    PasswordTextBox retypePassword;
    @UiField
    HelpInline retypePasswordHelpInline;
    @UiField
    ControlGroup emailControlGroup;
    @UiField
    HelpInline emailHelpInline;
    @UiField
    TextBox email;
    @UiField
    Button registerBtn;
    @UiField
    Form registerForm;
    @UiField
    HelpInline nameHelpInline;
    @UiField
    TextBox fullName;
    @UiField
    ControlGroup nameControlGroup;

    @UiField(provided = true)
    ValueListBox<UserAccountType> accountList = new ValueListBox<UserAccountType>(new Renderer<UserAccountType>() {
        @Override
        public String render(UserAccountType account) {
            return account == null ? "" : account.getDescription();
        }

        @Override
        public void render(UserAccountType account, Appendable appendable) throws IOException {
            if (account != null)
                appendable.append(account.getDescription());
        }
    });
    @UiField
    ControlGroup retypePasswordControlGroup;
    @UiField
    Icon loadingResultsIcon;
    @UiField
    Label errorLabel;

    public RegisterForm(HandlerManager eventBus) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.eventBus = eventBus;

        accountList.setValue(UserAccountType.STUDENT_ACCOUNT);
        accountList.setAcceptableValues(Arrays.asList(UserAccountType.values()));

    }

    @UiHandler("retypePassword")
    public void onRetypePasswordLostFocus(BlurEvent event) {

        if (!password.getText().equals(retypePassword.getText())) {
            retypePasswordControlGroup.setType(ControlGroupType.ERROR);
            retypePasswordHelpInline.setText("Passwords do not match!");
            registerBtn.setEnabled(false);
        } else {
            retypePasswordControlGroup.setType(ControlGroupType.SUCCESS);
            retypePasswordHelpInline.setText("Passwords match!");
            registerBtn.setEnabled(true);

        }
    }

    @UiHandler("password")
    public void onPasswordLostFocus(BlurEvent event) {

        if (password.getText().length() < 6) {
            passwordControlGroup.setType(ControlGroupType.ERROR);
            passwordHelpInline.setText("Password must have at least 6 characters!");
            registerBtn.setEnabled(false);

        } else {
            passwordControlGroup.setType(ControlGroupType.NONE);
            passwordHelpInline.setText("");
            registerBtn.setEnabled(true);

        }
    }

    @UiHandler("userName")
    public void onUsernameLostFocus(BlurEvent event) {

        if (userName.getText().indexOf(' ') != -1) {
            userNameControlGroup.setType(ControlGroupType.ERROR);
            userNameHelpInline.setText("Username must not contain spaces!");
            registerBtn.setEnabled(false);

        } else {
            userNameControlGroup.setType(ControlGroupType.NONE);
            userNameHelpInline.setText("");
            registerBtn.setEnabled(true);

        }
    }

    @UiHandler("accountList")
    public void onChangeValue(ValueChangeEvent<UserAccountType> event) {

    }

    @UiHandler("registerBtn")
    public void onClickRegister(ClickEvent event) {

        String username = userName.getText();
        String name = fullName.getText();
        String passwd = password.getText();

        loadingResultsIcon.setVisible(true);
        errorLabel.setVisible(false);

        UserAccountType accountType = accountList.getValue();

        switch (accountType) {
            case COMPANY_ACCOUNT:
                CompaniesService.App.getInstance().registerCompany(username, passwd, name, new RegisterAsyncCallback());
                break;
            case STUDENT_ACCOUNT:
                StudentsService.App.getInstance().registerStudent(username, passwd, name, new RegisterAsyncCallback());
                break;
            case TEACHER_ACCOUNT:
                TeachersService.App.getInstance().registerTeacher(username, passwd, name, new RegisterAsyncCallback());
                break;
        }
    }

    private class RegisterAsyncCallback implements AsyncCallback<Boolean> {
        @Override
        public void onFailure(Throwable caught) {
            loadingResultsIcon.setVisible(false);
            errorLabel.setVisible(true);
        }

        @Override
        public void onSuccess(Boolean result) {
            loadingResultsIcon.setVisible(false);
            if (Boolean.TRUE.equals(result)) {
                errorLabel.setVisible(false);
                eventBus.fireEvent(new RegisterSuccessfulEvent());
            } else {

                errorLabel.setVisible(true);
                errorLabel.setText("Username already exists!");
            }
        }
    }
}