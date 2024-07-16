package controller.etape;

import com.framework.ModelView;
import com.framework.annontation.session;
import com.framework.annontation.url;
import controller.session.SessionController;
import controller.url.HelperUrl;
import model.race.course.Course;
import model.race.equipe.Equipe;

public class EtapeController extends SessionController {

    @session
    @url("equipe/list-etape.do")
    public ModelView list(String id) throws Exception {
        Equipe equipe = (Equipe) this.getSession().get("equipe");
        if (equipe == null) {
            return new ModelView()
                    .sendRedirect(HelperUrl.baseUrl("login/equipe.do"));
        }
        Course course = new Course();
        course.setId(id);
        ModelView view = new ModelView("equipe/list-etape");
        view.addItem("etapes", equipe.getEtape(id, "rang", null));
        return view;
    }
    
    @url("admin/list-etape.do")
    public ModelView listAdmin(String id) throws Exception {
        Course course = new Course();
        course.setId(id);
        ModelView view = new ModelView("admin/list-etape");
        view.addItem("etapes", course.getEtape("rang", null));
        return view;
    }
    
}
