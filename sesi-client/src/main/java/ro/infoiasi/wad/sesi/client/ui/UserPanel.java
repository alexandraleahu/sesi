package ro.infoiasi.wad.sesi.client.ui;

import ro.infoiasi.wad.sesi.client.authentication.LoginServiceWrapper;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import ro.infoiasi.wad.sesi.resources.SesiResources;

public class UserPanel extends HorizontalPanel {

    private boolean logged = false;

    public UserPanel() {
        this.resetPanel();
        this.updatePanelForLoggedOut();
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean arg) {
        if (logged == arg) {
            return;
        }
        logged = arg;
        resetPanel();
        if (logged) {
            updatePanelForLoggedIn();
        } else {
            updatePanelForLoggedOut();
        }
    }

    private void resetPanel() {
        this.clear();
        this.setSpacing(5);
    }

    private void updatePanelForLoggedIn() {
        this.add(new LogoutLink());
        this.add(new ProfileLink());
    }

    private void updatePanelForLoggedOut() {
        this.add(new LoginLink());
        this.add(new AuthenticationLink());
    }

    class LoginLink extends Hyperlink {

        LoginLink() {
            this.setText("Login");
            this.setStyleName(SesiResources.INSTANCE.style().standardMarginRight());
            addClickHandler(new LoginHandler());
        }

        class LoginHandler implements ClickHandler {

            @Override
            public void onClick(ClickEvent arg0) {
                new LoginPopup().show();
            }
        }

        class LoginPopup extends PopupPanel {

            private final Label USER = new Label("Username");
            private final Label PASS = new Label("Password");
            private final TextBox user = new TextBox();
            private final PasswordTextBox pass = new PasswordTextBox();
            private final Button submit = new Button("Submit");
            private final Button cancel = new Button("Cancel");

            LoginPopup() {
                VerticalPanel vp = new VerticalPanel();
                vp.setSpacing(5);
                HorizontalPanel u = new HorizontalPanel();
                u.add(USER);
                u.add(user);
                u.setSpacing(5);
                HorizontalPanel p = new HorizontalPanel();
                p.add(PASS);
                p.add(pass);
                p.setSpacing(5);
                vp.add(u);
                vp.add(p);
                HorizontalPanel s = new HorizontalPanel();
                s.add(submit);
                s.add(cancel);
                s.setSpacing(5);
                vp.add(s);
                submit.addClickHandler(new SubmitHandler());
                cancel.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent e) {
                        LoginPopup.this.hide();
                    }
                });
                this.add(vp);
                center();
            }

            class SubmitHandler implements ClickHandler {
                @Override
                public void onClick(ClickEvent arg0) {
                    String u = user.getText();
                    String p = pass.getText();

                    if(u.length() > 0 && p.length() > 0) {
                        if (LoginServiceWrapper.login(u, p)) {
                            LoginPopup.this.hide();
                            UserPanel.this.setLogged(true);
                            Cookies.setCookie("currentUser", u);
                            Cookies.setCookie("currentUserRole", LoginServiceWrapper.getUserType(u));
                        } else {
                            Err.show("Invalid username or password.");
                        }
                    } else {
                        Err.show("Username and password cannot be blank.");
                    }
                }
            }
        }
    }

    class AuthenticationLink extends Hyperlink {

        AuthenticationLink() {
            this.setText("Authenticate");
            this.setStyleName(SesiResources.INSTANCE.style().standardMarginRight());

            addClickHandler(new AuthenticationHandler());
        }

        class AuthenticationHandler implements ClickHandler {
            @Override
            public void onClick(ClickEvent arg0) {
                new AuthenticationPopup().show();
            }
        }

        class AuthenticationPopup extends PopupPanel {
            private final Label USER = new Label("Username");
            private final Label PASS = new Label("Password");
            private final Label CONF = new Label("Confirm Password");
            private final Label TYPE = new Label("Account type");
            private final Label MAIL = new Label("Email");
            private final TextBox user = new TextBox();
            private final PasswordTextBox pass = new PasswordTextBox();
            private final PasswordTextBox conf = new PasswordTextBox();
            private final ListBox type = new ListBox();
            private final TextBox mail = new TextBox();
            private final Button submit = new Button("Submit");
            private final Button cancel = new Button("Cancel");

            AuthenticationPopup() {
                type.addItem("Student");
                type.addItem("Company");
                type.addItem("Teacher");
                HorizontalPanel main = new HorizontalPanel();
                VerticalPanel lp = new VerticalPanel();
                VerticalPanel rp = new VerticalPanel();
                main.setSpacing(5);
                lp.setSpacing(14);
                rp.setSpacing(5);

                lp.add(USER);
                lp.add(PASS);
                lp.add(CONF);
                lp.add(MAIL);
                lp.add(TYPE);
                lp.add(submit);

                rp.add(user);
                rp.add(pass);
                rp.add(conf);
                rp.add(mail);
                rp.add(type);
                rp.add(cancel);
                submit.addClickHandler(new SubmitHandler());
                cancel.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent e) {
                        AuthenticationPopup.this.hide();
                    }
                });
                main.add(lp);
                main.add(rp);
                add(main);
                center();
            }

            class SubmitHandler implements ClickHandler {
                @Override
                public void onClick(ClickEvent arg0) {
                    String u = user.getText();
                    String p = pass.getText();
                    String c = conf.getText();
                    String m = mail.getText();
                    String t = type.getItemText(type.getSelectedIndex());
                    if (p.length() == 0 || u.length() == 0 || c.length() == 0) {
                        Err.show("Username, password and password confirmation cannot be blank.");
                        return;
                    }
                    if (!p.equals(c)) {
                        Err.show("Password does not match password confirmation.");
                        return;
                    }
                    if(!LoginServiceWrapper.authenticate(u, p, t)) {
                        Err.show("Username '" + u + "' is already taken.");
                        return;
                    }
                    AuthenticationPopup.this.hide();
                }
            }
        }
    }

    class LogoutLink extends Hyperlink {

        LogoutLink() {
            this.setText("Logout");
            addClickHandler(new LogoutHandler());
        }

        class LogoutHandler implements ClickHandler {

            @Override
            public void onClick(ClickEvent arg0) {
                Cookies.removeCookie("currentUser");
                Cookies.removeCookie("currentUserRole");
                UserPanel.this.setLogged(false);
            }
        }
    }

    class ProfileLink extends Hyperlink {

        ProfileLink() {
            this.setText("Profile");
            addClickHandler(new ProfileHandler());
        }

        class ProfileHandler implements ClickHandler {

            @Override
            public void onClick(ClickEvent arg0) {
                new ProfilePopup().show();
            }
        }

        class ProfilePopup extends PopupPanel {
        }
    }
}