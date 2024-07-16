import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.race.coureur.Coureur;
import model.race.etape.Etape;

/**
 *
 * @author tendr
 */
@WebServlet(urlPatterns={"/resultat"})
public class ResultatServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String idCourse = request.getParameter("idCourse");
        String idEtape = request.getParameter("idEtape");
        try {
            Coureur[] coureurs = new Coureur().getCoureurs(idCourse, idEtape, null);
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < coureurs.length; i++) {
                String time = request.getParameter(coureurs[i].getId());
                if (time != null && !time.isEmpty()) {
                    result.append(coureurs[i].getId() + "," + time + ";");
                }
            }
            Etape etape = new Etape();
            etape.setId(idEtape);
            etape.affecterTemps(result.toString(), idCourse, null);
            response.sendRedirect(String.format("http://localhost:8080/race/admin/affectation-coureur.do?idEtape=%s&idCourse=%s", idEtape, idCourse));
        } catch (Exception e) {
            response.sendRedirect(String.format("http://localhost:8080/race/admin/affectation-coureur.do?idEtape=%s&idCourse=%s&error=%s", idEtape, idCourse, e.getMessage()));
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}