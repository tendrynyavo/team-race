<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page import="model.race.coureur.Coureur" %>
<%

    Coureur[] coureurs = (Coureur[]) request.getAttribute("coureurs");
    String error = (request.getAttribute("error") == null) ? "" : (String) request.getAttribute("error");

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


            <%@include file="../template/sidebar.html" %>


            <div class="col mt-4 min-vh-100" style="">
                <div class="card-body bg-light p-5 shadow-sm" style="border-radius: 20px;">
                    <h4 class="card-title mb-4">Liste des coureurs pour l'étape</h4>
                    <form action="coureur.do" method="get">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>Affecté</th>
                                        <th>Numero dossard</th>
                                        <th>Nom</th>
                                        <th>Genre</th>
                                        <th>Age</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% for (Coureur coureur : coureurs) { %>
                                    <tr>
                                        <td style="padding: 10px;">
                                            <div class="form-check form-check-success m-0">
                                                <label class="form-check-label m-0">
                                                    <input type="checkbox" class="form-check-input" name="participants" value="<%=coureur.getId() %>">
                                                    <i class="input-helper"></i>
                                                </label>
                                            </div>
                                        </td>
                                        <td>N° <%=coureur.getNumeroDossard() %></td>
                                        <td><%=coureur.getNom() %></td>
                                        <td><%=coureur.getGenre() %></td>
                                        <td><%=coureur.getAge() %> ans</td>
                                    </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>
                        <div class="mt-4">
                            <h2 class="text-danger mb-3"><%=error %></h2>
                            <input type="submit" value="Valider" class="btn btn-block btn-dark font-weight-medium auth-form-btn me-2"
                                style="border-radius: 15px;">
                            <input type="hidden" value="<%=request.getParameter("idCourse") %>" name="idCourse">
                            <input type="hidden" value="<%=request.getParameter("idEtape") %>" name="idEtape">
                        </div>
                    </form>
                    <a href="equipe/list-etape.do?id=<%=request.getParameter("idCourse") %>"><button class="btn btn-block btn-dark font-weight-medium auth-form-btn mt-2"
                                style="border-radius: 15px;">Précédent</button></a>
                </div>
            </div>
        </div>
    </div>
</body>

</html>