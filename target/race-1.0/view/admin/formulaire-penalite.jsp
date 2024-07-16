<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page import="model.race.etape.Etape" %>
<%@page import="model.race.equipe.Equipe" %>
<%

    Etape[] etapes = (Etape[]) request.getAttribute("etapes");
    Equipe[] equipes = (Equipe[]) request.getAttribute("equipes");

%>
<!DOCTYPE html>
<html lang="fr">

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
                <div class="card-body bg-light rounded p-5 shadow-sm w-50">
                    <div class="card-body">
                        <h4 class="card-title">Ajout de pénalité</h4>
                        <form class="forms-sample mt-3" method="get" action="admin/penalite.do">
                            <div class="form-group">
                                <label for="exampleInputUsername1">Choix étape</label>
                                <select class="form-control rounded" id="exampleSelectGender" name="idEtape">
                                    <% for (Etape etape : etapes) { %>
                                    <option value="<%=etape.getId() %>"><%=etape.getNom() %></option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="exampleInputUsername1">Choix équipe</label>
                                <select class="form-control rounded" id="exampleSelectGender" name="idEquipe">
                                    <% for (Equipe equipe : equipes) { %>
                                    <option value="<%=equipe.getId() %>"><%=equipe.getNom() %></option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="exampleInputUsername1">Penalite</label>
                                <input type="time" step="2" class="form-control rounded" id="exampleInputUsername1"
                                    placeholder="" name="temps">
                            </div>
                            <input type="hidden" value="<%=request.getParameter("idCourse") %>" name="idCourse"/>
                            <input type="submit" class="btn btn-block btn-dark font-weight-medium auth-form-btn" style="border-radius: 15px;" value="Suivant" />
                            <a href="admin/list-penalite.do?idCourse=<%=request.getParameter("idCourse") %>"><button type="button" class="btn btn-block btn-dark font-weight-medium auth-form-btn me-2"
                                style="border-radius: 15px;">Liste Pénalité</button></a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
</body>

</html>