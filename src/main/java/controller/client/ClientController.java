package controller.client;

import com.framework.ModelView;
import com.framework.annontation.url;
import controller.url.HelperUrl;
import model.race.admin.Admin;

public class ClientController {

    @url("login/equipe.do")
    public ModelView client() {
        return new ModelView("login/client");
    }
    
    @url("login/admin.do")
    public ModelView admin() {
        return new ModelView("login/admin");
    }
    
    @url("admin/login.do")
    public ModelView authentificationAdmin(String login, String pwd) {
        try {
            Admin admin = new Admin().login(login, pwd);
            ModelView view = new ModelView();
            view.addSession("admin", admin);
            view.addSession("isconnected", true);
            view.addSession("profil", "admin");
            view.sendRedirect(HelperUrl.baseUrl("admin/list-etape.do?id=COURS0001"));
            return view;
        } catch (Exception e) {
            return new ModelView("login/admin")
                    .addItem("error", e.getMessage());
        }
    }
    
    @url("log-out.do")
    public ModelView disconnected() {
        ModelView view = new ModelView();
        view.setInvalidate(true);
        return view.sendRedirect(HelperUrl.baseUrl("login/equipe.do"));
    }
    
}