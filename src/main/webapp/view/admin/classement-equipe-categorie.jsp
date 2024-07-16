<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page import="model.race.equipe.RankEquipe" %>
<%@page import="model.race.categorie.Categorie" %>
<%@page import="model.race.equipe.ResultatEquipe" %>
<%

    ResultatEquipe ranks = (ResultatEquipe) request.getAttribute("ranks");
    Categorie[] categories = (Categorie[]) request.getAttribute("categories");

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="../template/link.html" %>
    <script src="assets/js/Chart.js"></script>
    <title>Document</title>
    <style>
        .sticky-offset {
            top: 20px;
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
                    <div class="m-auto mt-3" style="width: 300px;">
                        <canvas id="doughnutChart" width="20" height="20"></canvas>
                    </div>
                    <div class="d-sm-flex justify-content-between align-items-start">
                      <div class="mt-3">
                        <h4 class="card-title card-title-dash">Course : 2024</h4>
                        <div class="form-group">
                            <form method="get" action="admin/classement-equipe-categorie.do">
                                <select class="form-control rounded" id="exampleSelectGender" name="categorie">
                                    <option value="">Tout Categorie</option>
                                    <% for (Categorie categorie : categories) { %>
                                    <option value="<%=categorie.getId() %>"><%=categorie.getNom() %></option>
                                    <% } %>
                                </select>
                                <input type="hidden" name="id" value="<%=request.getParameter("id") %>">
                                <input type="submit" value="OK" class="btn btn-block btn-dark font-weight-medium auth-form-btn me-2 mt-3" style="border-radius: 15px;">
                                <a href="admin/pdf.do?idCourse=<%=request.getParameter("id") %>&idEquipe=<%=ranks.getEquipes()[0].getId() %>"><button type="button" class="mt-3 btn btn-block btn-dark font-weight-medium auth-form-btn me-2"
                                style="border-radius: 15px;">Certification</button></a>
                           </form>
                        </div>
                      </div>
                    </div>
                    <div class="table-responsive  mt-1">
                        <table class="table select-table">
                          <thead>
                            <tr>
                              <th>Rang</th>
                              <th>Nom</th>
                              <th>Total</th>
                              <th></th>
                            </tr>
                          </thead>
                          <tbody>
                              <% for (RankEquipe rank : ranks.getEquipes()) { %>
                              <tr style="background-color: <%=rank.getColor() %>;">
                                <td>
                                    <h6 class="ms-2"><%=rank.getRang() %></h6>
                                </td>
                                <td>
                                    <h6><%=rank.getNom() %></h6>
                                </td>
                                <td>
                                    <h6><%=rank.getTotal() %></h6>
                                </td>
                                <td><a href="admin/classement-coureur-equipe.do?idEquipe=<%=rank.getId() %>&id=<%=request.getParameter("id") %>"><i class="bi bi-plus-lg"></i></a></td>
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
                          
    <script>

        // Sample data for the Doughnut Chart
        var data = {
            labels: [<% for (RankEquipe rank : ranks.getEquipes()) { %>'<%=rank.getNom() %>',<% } %>],
            datasets: [{
                data: [<% for (RankEquipe rank : ranks.getEquipes()) { %><%=rank.getTotal() %>,<% } %>], // Sample values for each category
                backgroundColor: [
                '#3AA6B9',
                '#FFD0D0',
                '#FF9EAA',
                '#F9F9E0',
                '#01204E',
                '#028391',
                '#F6DCAC',
                '#FEAE6F'
                ]
            }]
        };

        // Get the canvas element and render the Doughnut Chart
        var ctx = document.getElementById('doughnutChart').getContext('2d');
        var doughnutChart = new Chart(ctx, {
            type: 'pie',
            data: data,
            options: {
                title: {
                  display: true,
                  text: "Graphe par ${categorie.getNom()}"
                }
            }
        });
    </script>
</body>
</html>