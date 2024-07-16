package controller.equipe;

import com.framework.ModelView;
import com.framework.annontation.url;
import controller.url.HelperUrl;
import model.race.course.Course;
import model.race.equipe.Equipe;
import model.race.penalite.Penalite;
import model.race.equipe.RankEquipe;

public class EquipeController {

    @url("admin/formulaire-penalite.do")
    public ModelView formulairePenalite(String idCourse) throws Exception {
        Course course = new Course();
        course.setId(idCourse);
        return new ModelView("admin/formulaire-penalite")
                .addItem("etapes", course.getEtape("rang", null))
                .addItem("equipes", new Equipe().findAll(null, null));
    }
    
    @url("admin/penalite.do")
    public ModelView penaliter(String temps, String idCourse, String idEtape, String idEquipe) throws Exception {
        Equipe equipe = new Equipe();
        equipe.setId(idEquipe);
        Penalite penalite = equipe.penaliser(temps, idCourse, idEtape, null);
        penalite.insert(null);
        return new ModelView()
                .sendRedirect(HelperUrl.baseUrl("admin/list-penalite.do?idCourse=" + idCourse));
    }
    
    @url("admin/list-penalite.do")
    public ModelView listPenalite(String idCourse) throws Exception {
        Penalite penalite = new Penalite();
        Course course = new Course();
        course.setId(idCourse);
        penalite.setCourse(course);
        penalite.setTable("v_penalite_dispo");
        return new ModelView("admin/list-penalite")
                .addItem("penalites", penalite.findAll(null, null));
    }
    
    @url("admin/supprimer-penalite.do")
    public ModelView supprimerPenalite(String idCourse, String[] penalites) throws Exception {
        Penalite penalite = new Penalite();
        penalite.supprimer(penalites, null);
        return new ModelView()
                .sendRedirect(HelperUrl.baseUrl("admin/list-penalite.do?idCourse=" + idCourse));
    }
    
    @url("admin/pdf.do")
    public ModelView generatePdf(String idCourse, String idEquipe) throws Exception {
        RankEquipe equipe = new RankEquipe();
        equipe.setId(idEquipe);
        Course course = new Course();
        course.setId(idCourse);
        equipe.setCourse(course);
        ((RankEquipe) equipe.getById()).viewPDF();
        return new ModelView()
                .sendRedirect(HelperUrl.baseUrl("admin/classement-equipe-categorie.do?categorie=&id=COURS0001"));
    }
    
    @url("equipe/login.do")
    public ModelView authentificationAdmin(String login, String pwd) {
        try {
            Equipe equipe = (Equipe) new Equipe().login(login, pwd);
            ModelView view = new ModelView();
            view.addSession("equipe", equipe);
            view.addSession("isconnected", true);
            view.addSession("profil", "equipe");
            view.sendRedirect(HelperUrl.baseUrl("equipe/classement-equipe-categorie.do?categorie=&id=COURS0001"));
            return view;
        } catch (Exception e) {
            return new ModelView("login/client")
                    .addItem("error", e.getMessage());
        }
    }
    
}