<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page import="model.race.coureur.CoureurComplet" %>
<%

    CoureurComplet[] coureurs = (CoureurComplet[]) request.getAttribute("coureurs");
    String error = (request.getParameter("error") == null) ? "" : (String) request.getParameter("error");
    
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
                <div class="card-body bg-light p-5 shadow-sm mb-5" style="border-radius: 20px;">
                    <h4 class="card-title mb-4">Liste des coureurs pour l'étape</h4>
                    <div class="d-sm-flex justify-content-between align-items-start">
                      <div class="mt-2 ">
                        <h3 class="card-title card-title-dash">Rang : ${etape.getRang()}</h3>
                        <h3 class="card-title card-title-dash">Etape : ${etape.getNom()}</h3>
                        <h3 class="card-title card-title-dash">Date de depart : ${etape.getDateDepart()}</h3>
                        <h3 class="card-title card-title-dash">Heure de depart : ${etape.getHeureDepart()}</h3>
                      </div>
                    </div>
                    <form action="resultat" method="get">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Rang</th>
                                        <th width="100px">Numero dossard</th>
                                        <th>Nom</th>
                                        <th>Genre</th>
                                        <th>Age</th>
                                        <th>Equipe</th>
                                        <th>Arrivée</th>
                                        <th>Chrono</th>
                                        <th>Penalite</th>
                                        <th>Total Temps</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% for (CoureurComplet coureur : coureurs) { %>
                                    <tr>
                                        <td><%=coureur.getRang() %></td>
                                        <td>N° <%=coureur.getNumeroDossard() %></td>
                                        <td><%=coureur.getNom() %></td>
                                        <td><%=coureur.getGenre() %></td>
                                        <td><%=coureur.getAge() %> ans</td>
                                        <td><%=coureur.getEquipe().getNom() %></td>
                                        <% if (coureur.getArrive() != null) { %>
                                        <td><%=coureur.getArriveString() %></td>
                                        <% } else { %>
                                        <td style="padding: 0px;">
                                            <div class="form-group position-absolute">
                                                <input type="datetime-local" step="1" class="form-control rounded" id="exampleInputUsername1" name="<%=coureur.getId() %>"
                                                       value="${etape.getDateDepart()}T${etape.getHeureDepart()}" placeholder="Username" style="margin-top: -15px;margin-left: 20px;margin-right: 80px; width: 255px;">
                                            </div>
                                        </td>
                                        <% } %>
                                        <td><%=coureur.getChronoString() %></td>
                                        <td><%=coureur.getPenaliteString() %></td>
                                        <td><%=coureur.getTempsFinaleString() %></td>
                                    </tr>
                                    <% } %>
                                <a href="affectation-coureur.jsp"></a>
                                </tbody>
                            </table>
                        </div>
                        <div class="mt-4">
                            <h2 class="text-danger mb-3"><%=error %></h2>
                            <input type="submit" value="Valider" class="btn btn-block btn-dark font-weight-medium auth-form-btn me-2"
                                style="border-radius: 15px;">
                            <a href="admin/list-etape.do?id=<%=request.getParameter("idCourse") %>"><button type="button" class="btn btn-block btn-dark font-weight-medium auth-form-btn"
                                style="border-radius: 15px;">Précédent</button></a>
                            <input type="hidden" value="<%=request.getParameter("idCourse") %>" name="idCourse">
                            <input type="hidden" value="<%=request.getParameter("idEtape") %>" name="idEtape">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>

</html>