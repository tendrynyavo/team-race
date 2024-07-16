<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page import="model.race.etape.Etape" %>
<%

    Etape[] etapes = (Etape[]) request.getAttribute("etapes");

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="../template/link.html" %>
    <title>Document</title>
</head>
<style>

    .saisie input {
        width: 140px;
        height: 20px;
        margin-left: 20.5px;
        margin-top: 10px;
    }

    .jour {
        text-align: center;
    }

</style>
<body>
    <div class="container-fluid">
        <div class="row" style="background-color: rgb(244, 245, 247);">
            <%@include file="../template/sidebar_admin.html" %>
            <div class="col mt-4 min-vh-100" style="">
                <div class="card-body bg-light p-5 shadow-sm" style="border-radius: 20px;">
                    <h4 class="card-title mb-4">Liste des etapes</h4>
                    <div class="table-responsive">
                      <table class="table table-hover">
                        <thead>
                          <tr>
                            <th>Rang</th>
                            <th>Nom</th>
                            <th>Longueur en km</th>
                            <th>Nombre coureur par par équipe</th>
                            <th>Date de depart</th>
                            <th>Heure de depart</th>
                            <th></th>
                          </tr>
                        </thead>
                        <tbody>
                          <% for (Etape etape : etapes) { %>
                          <tr>
                            <td><%=etape.getRang() %></td>
                            <td><%=etape.getNom() %></td>
                            <td><%=etape.getLongeurKmString() %> km</td>
                            <td><%=etape.getNbrCoureurEquipe() %> coureur(s)</td>
                            <td><%=etape.getDateDepart() %></td>
                            <td><%=etape.getHeureDepart() %></td>
                            <td><a href="admin/affectation-coureur.do?idEtape=<%=etape.getId() %>&idCourse=<%=request.getParameter("id") %>"><i class="bi bi-plus-lg"></i></a></td>
                          </tr>
                          <% } %>
                        </tbody>
                      </table>
                    </div>
<!--                    <div class="mt-4">
                      <button class="btn btn-block btn-dark font-weight-medium auth-form-btn me-2" style="border-radius: 15px;">Suivant</button>
                      <button class="btn btn-block btn-dark font-weight-medium auth-form-btn" style="border-radius: 15px;">Précédent</button>
                    </div>-->
                  </div>
            </div>
        </div>
    </div>
</body>
</html>