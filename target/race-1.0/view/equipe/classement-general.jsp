<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page import="model.race.coureur.RankCoureur" %>
<%

    RankCoureur[] ranks = (RankCoureur[]) request.getAttribute("ranks");

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="../template/link.html" %>
    <title>Document</title>
</head>
<body>
    <div class="container-fluid">
        <div class="row" style="background-color: rgb(244, 245, 247);">
            
          <%@include file="../template/sidebar.html" %>

            <div class="col mt-4 mx-4 min-vh-100">
                <!-- Place du navbar -->
                <div class="col-12 grid-margin stretch-card">
                <div class="card card-rounded shadow-sm">
                  <div class="card-body">
                      <div><h1>Classement générale</h1></div>
                    <div class="d-sm-flex justify-content-between align-items-start">
                      <div class="mt-3">
                        <h4 class="card-title card-title-dash">Course : 2024</h4>
                      </div>
                    </div>
                    <div class="table-responsive  mt-1">
                        <table class="table select-table table-hover">
                          <thead>
                            <tr>
                              <th style="width:100px;">Rang</th>
                              <th style="width:100px;">Nom</th>
                              <th style="width:100px;">Numero de dossard</th>
                              <th style="width:100px;">Genre</th>
                              <th style="width:100px;">Age</th>
                              <th style="width:100px;">Equipe</th>
                              <th style="width:100px;">Total</th>
                            </tr>
                          </thead>
                          <tbody>
                              <% for (RankCoureur rank : ranks) { %>
                              <tr>
                                <td>
                                    <h6 class="ms-2"><%=rank.getRang() %></h6>
                                </td>
                                <td>
                                    <h6><%=rank.getNom() %></h6>
                                </td>
                                <td>
                                    <h6>N° <%=rank.getNumeroDossard() %></h6>
                                </td>
                                <td>
                                    <h6><%=rank.getGenre() %></h6>
                                </td>
                                <td>
                                    <h6><%=rank.getAge() %></h6>
                                </td>
                                <td>
                                    <h6><%=rank.getEquipe().getNom() %></h6>
                                </td>
                                <td>
                                    <h6><%=rank.getTotal() %></h6>
                                </td>
                              </tr>
                              <% } %>
                          </tbody>
                        </table>
                    </div>
                  </div>
                </div>
            </div>
            </div>
        </div>
    </div>
</body>
</html>