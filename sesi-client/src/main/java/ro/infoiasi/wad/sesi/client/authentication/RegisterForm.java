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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.core.model.UserAccountType;
import ro.infoiasi.wad.sesi.client.util.HasEventBus;

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
    PasswordTextBox email;
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

    private UserAccountType account;

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
        } else {
            retypePasswordControlGroup.setType(ControlGroupType.SUCCESS);
            retypePasswordHelpInline.setText("Passwords match!");
        }
    }

    @UiHandler("password")
    public void onPasswordLostFocus(BlurEvent event) {

        if (password.getText().length() < 6) {
            passwordControlGroup.setType(ControlGroupType.ERROR);
            passwordHelpInline.setText("Password must have at least 6 characters!");
        } else {
            passwordControlGroup.setType(ControlGroupType.NONE);
            passwordHelpInline.setText("");
        }
    }

    @UiHandler("accountList")
    public void onChangeValue(ValueChangeEvent<UserAccountType> event) {
        account = event.getValue();

    }

    @UiHandler("registerBtn")
    public void onClickRegister(ClickEvent event) {

    }
}