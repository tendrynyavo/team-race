package controller.coureur;

import com.framework.ModelView;
import com.framework.annontation.session;
import com.framework.annontation.url;
import controller.session.SessionController;
import controller.url.HelperUrl;
import model.race.categorie.Categorie;
import model.race.coureur.Coureur;
import model.race.coureur.CoureurComplet;
import model.race.course.Course;
import model.race.equipe.Equipe;
import model.race.etape.Etape;

public class CoureurController extends SessionController {
    
    @session
    @url("equipe/affectation-coureur.do")
    public ModelView list(String idEtape, String idCourse) throws Exception {
        try {
            Equipe equipe = (Equipe) this.getSession().get("equipe");
            if (equipe == null) {
                return new ModelView()
                        .sendRedirect(HelperUrl.baseUrl("login/equipe.do"));
            }
            equipe.checkNombreCoureur(idCourse, idEtape, null);
            ModelView view = new ModelView("equipe/affectation-coureur");
            view.addItem("coureurs", equipe.getCoureurs(idCourse, idEtape, true, null));
            view.addItem("equipe", equipe);
            return view;
        } catch (Exception e) {
            return participant(idEtape, idCourse)
                    .addItem("error", e.getMessage());
        }
    }
    
    @url("admin/affectation-coureur.do")
    public ModelView listAdmin(String idEtape, String idCourse) throws Exception {
        Etape etape = new Etape();
        etape.setTable("v_etape_course");
        etape.setId(idEtape);
        Course course = new Course();
        course.setId(idCourse);
        etape.setCourse(course);
        ModelView view = new ModelView("admin/affectation-coureur");
        view.addItem("coureurs", (CoureurComplet[]) new Coureur().getCoureurs(idCourse, idEtape, null));
        view.addItem("etape", etape.getById());
        return view;
    }
    
    @session
    @url("coureur.do")
    public ModelView affecter(String idCourse, String idEtape, String[] participants) throws Exception {
        Equipe equipe = (Equipe) this.getSession().get("equipe");
        if (equipe == null) {
            return new ModelView()
                    .sendRedirect(HelperUrl.baseUrl("login/equipe.do"));
        }
        try {
            Etape etape = new Etape();
            etape.setId(idEtape);
            etape.affecterCoureurs(equipe.getId(), idCourse, participants);
            return new ModelView()
                    .sendRedirect(HelperUrl.baseUrl(String.format("equipe/participants.do?idEtape=%s&idCourse=%s", idEtape, idCourse)));
        } catch (Exception e) {
            return this.list(idEtape, idCourse)
                    .addItem("error", e.getMessage());
        }
    }
    
    @url("equipe/classement-coureur.do")
    public ModelView classement(String id) throws Exception {
        Course course = new Course();
        course.setId(id);
        ModelView view = new ModelView("equipe/classement-general");
        view.addItem("ranks", course.getRankCoureur(null));
        return view;
    }
    
    @url("equipe/classement-coureur-equipe.do")
    public ModelView classementEquipe(String idEquipe, String id) throws Exception {
        Equipe equipe = new Equipe();
        equipe.setId(idEquipe);
        ModelView view = new ModelView("equipe/classement-general");
        view.addItem("ranks", equipe.getRankCoureurs(id, null));
        return view;
    }
    
    @url("admin/classement-coureur-equipe.do")
    public ModelView classementEquipeAdmin(String idEquipe, String id) throws Exception {
        Equipe equipe = new Equipe();
        equipe.setId(idEquipe);
        ModelView view = new ModelView("admin/classement-general");
        view.addItem("ranks", equipe.getRankCoureurs(id, null));
        return view;
    }
    
    @url("admin/classement-coureur.do")
    public ModelView classementAdmin(String id) throws Exception {
        Course course = new Course();
        course.setId(id);
        ModelView view = new ModelView("admin/classement-general");
        view.addItem("ranks", course.getRankCoureur(null));
        return view;
    }
    
    @url("equipe/classement-equipe-categorie.do")
    public ModelView classementEquipeCategorie(String id, String categorie) throws Exception {
        Course course = new Course();
        Categorie c = new Categorie();
        c.setId(categorie);
        course.setId(id);
        ModelView view = new ModelView("equipe/classement-equipe-categorie");
        view.addItem("ranks", course.getRankEquipeCategorie(categorie, null));
        view.addItem("categories", new Categorie().findAll(null, null, null));
        view.addItem("categorie", c.getById());
        return view;
    }
    
    @url("admin/classement-equipe-categorie.do")
    public ModelView classementEquipeCategorieAdmin(String id, String categorie) throws Exception {
        Course course = new Course();
        Categorie c = new Categorie();
        c.setId(categorie);
        course.setId(id);
        ModelView view = new ModelView("admin/classement-equipe-categorie");
        view.addItem("ranks", course.getRankEquipeCategorie(categorie, null));
        view.addItem("categories", new Categorie().findAll(null, null, null));
        view.addItem("categorie", c.getById());
        return view;
    }
    
    @session
    @url("equipe/participants.do")
    public ModelView participant(String idEtape, String idCourse) throws Exception {
        Equipe equipe = (Equipe) this.getSession().get("equipe");
        if (equipe == null) {
            return new ModelView()
                    .sendRedirect(HelperUrl.baseUrl("login/equipe.do"));
        }
        ModelView view = new ModelView("equipe/participants");
        view.addItem("participants", equipe.getParticipants(idCourse, idEtape, null));
        view.addItem("equipe", equipe);
        return view;
    }
    
    @url("generate.do")
    public ModelView generer() throws Exception {
        Coureur coureur = new Coureur();
        coureur.generer(null);
        return new ModelView()
                .sendRedirect(HelperUrl.baseUrl("admin/classement-equipe-categorie.do?categorie=&id=COURS0001"));
    }
    
    @url("reset.do")
    public ModelView reset() throws Exception {
        Course course = new Course();
        course.reset();
        return new ModelView()
                .sendRedirect(HelperUrl.baseUrl("admin/classement-coureur.do?id=COURS0001"));
    }
    
}