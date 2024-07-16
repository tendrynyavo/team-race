<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page import="model.race.coureur.CoureurComplet" %>
<%

    CoureurComplet[] coureurs = (CoureurComplet[]) request.getAttribute("participants");
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
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>Numero dossard</th>
                                        <th>Nom</th>
                                        <th>Genre</th>
                                        <th>Age</th>
                                        <th>Chrono</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% for (CoureurComplet coureur : coureurs) { %>
                                    <tr>
                                        <td>N° <%=coureur.getNumeroDossard() %></td>
                                        <td><%=coureur.getNom() %></td>
                                        <td><%=coureur.getGenre() %></td>
                                        <td><%=coureur.getAge() %> ans</td>
                                        <td><%=coureur.getArriveString() %></td>
                                    </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>
                        <h2 class="text-danger mb-3"><%=error %></h2>
                        <div class="mt-4">
                            <a href="equipe/affectation-coureur.do?idEtape=<%=request.getParameter("idEtape") %>&idCourse=<%=request.getParameter("idCourse") %>""><button class="btn btn-block btn-dark font-weight-medium auth-form-btn me-2"
                                style="border-radius: 15px;">Ajouter coureur</button></a>
                        </div>
                    <a href="equipe/list-etape.do?id=<%=request.getParameter("idCourse") %>"><button class="btn btn-block btn-dark font-weight-medium auth-form-btn mt-2"
                                style="border-radius: 15px;">Précédent</button></a>
                </div>
            </div>
        </div>
    </div>
</body>