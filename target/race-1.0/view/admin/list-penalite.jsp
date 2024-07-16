<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page import="model.race.penalite.Penalite" %>
<%

    Penalite[] penalites = (Penalite[]) request.getAttribute("penalites");

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
                    <form action="admin/supprimer-penalite.do" method="get">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>Etape</th>
                                    <th>Equipe</th>
                                    <th>Temps pénalite</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (Penalite penalite : penalites) { %>
                                <tr>
                                    <td><%=penalite.getEtape().getNom() %></td>
                                    <td><%=penalite.getEquipe().getNom() %></td>
                                    <td><%=penalite.getValeur() %></td>
                                    <td style="padding: 10px;">
                                        <div class="form-check form-check-success m-0">
                                            <label class="form-check-label m-0">
                                                <input type="checkbox" class="form-check-input" name="penalites" value="<%=penalite.getId() %>">
                                                <i class="input-helper"></i>
                                            </label>
                                        </div>
                                    </td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                    <div class="mt-4">
                        <input type="hidden" value="<%=request.getParameter("idCourse") %>" name="idCourse"/>
                        <button class="btn btn-block btn-dark font-weight-medium auth-form-btn me-2" style="border-radius: 15px;">Valider</button>
                        <a href="admin/formulaire-penalite.do?idCourse=<%=request.getParameter("idCourse") %>"><button type="button" class="btn btn-block btn-dark font-weight-medium auth-form-btn me-2"
                            style="border-radius: 15px;">Ajouter Pénalité</button></a>
                    </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>

</html>