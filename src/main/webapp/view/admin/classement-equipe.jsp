<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page import="model.race.equipe.RankEquipe" %>
<%

    RankEquipe[] ranks = (RankEquipe[]) request.getAttribute("ranks");

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="../template/link.html" %>
    <title>Document</title>
    <style>
        .sticky-offset {
            top: 20px;
        }
        .btn {
          border-radius: 20px; background-color: rgb(45, 86, 67);
        }
        *::-webkit-scrollbar {
          display: none;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row" style="background-color: rgb(244, 245, 247);">
           <%@include file="../template/sidebar_admin.html" %>

            <div class="col mt-4 mx-4 min-vh-100">
                <!-- Place du navbar -->
                <div class="col-12 grid-margin stretch-card">
                <div class="card card-rounded shadow-sm">
                  <div class="card-body">
                      <div><h1>Classement par équipe</h1></div>
                    <div class="d-sm-flex justify-content-between align-items-start">
                      <div class="mt-3">
                        <h4 class="card-title card-title-dash">Course : 2024</h4>
                      </div>
                    </div>
                    <div class="table-responsive  mt-1">
                        <table class="table select-table table-hover">
                          <thead>
                            <tr>
                              <th>Rang</th>
                              <th>Nom</th>
                              <th>Total</th>
                            </tr>
                          </thead>
                          <tbody>
                              <% for (RankEquipe rank : ranks) { %>
                              <tr>
                                <td>
                                    <h6 class="ms-2"><%=rank.getRang() %></h6>
                                </td>
                                <td>
                                    <h6><%=rank.getNom() %></h6>
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
