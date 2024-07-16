<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
    String error = (request.getAttribute("error") == null) ? "" : (String) request.getAttribute("error");

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="../template/link.html" %>
    <title>Dashboard</title>
    <style>
        .sticky-offset {
            top: 20px;
        }
        .btn {
            border-radius: 20px; 
            background-color: rgb(45, 86, 67);
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row" style="background-color: rgb(244, 245, 247);">
            
            
            <%@include file="../template/sidebar_admin.html" %>


            <div class="col mt-4 mx-4 min-vh-100">
                <!-- Place du navbar -->
                <div class="row w-50 container bg-light mt-4 p-5 ms-4 rounded shadow-sm">
                    <form enctype="multipart/form-data" method="post" action="upload.do" class="pt-3">
                        <label class="form-label fw-bold mb-3">Etape</label>
                        <div class="form-group">
                            <input type="file" class="form-control form-control-lg rounded" name="fileCsv">
                        </div>
                        <input type="hidden" name="file" value="Etape">
                        <div class="mt-3">
                            <input type="submit" class="btn btn-block btn-dark font-weight-medium auth-form-btn" style="border-radius: 15px;" value="Valider" />
                        </div>
                    </form>
                </div>
                <div class="row w-50 container bg-light mt-4 p-5 ms-4 rounded shadow-sm">
                    <form enctype="multipart/form-data" method="post" action="upload.do" class="pt-3">
                        <label class="form-label fw-bold mb-3">Resultat</label>
                        <div class="form-group">
                            <input type="file" class="form-control form-control-lg rounded" name="fileCsv">
                        </div>
                        <input type="hidden" name="file" value="Resultat">
                        <div class="mt-3">
                            <input type="submit" class="btn btn-block btn-dark font-weight-medium auth-form-btn" style="border-radius: 15px;" value="Valider" />
                        </div>
                    </form>
                </div>
                <div class="row w-50 container bg-light mt-4 p-5 ms-4 rounded shadow-sm">
                    <form enctype="multipart/form-data" method="post" action="upload.do" class="pt-3">
                        <label class="form-label fw-bold mb-3">Point</label>
                        <div class="form-group">
                            <input type="file" class="form-control form-control-lg rounded" name="fileCsv">
                        </div>
                        <input type="hidden" name="file" value="Point">
                        <div class="mt-3">
                            <input type="submit" class="btn btn-block btn-dark font-weight-medium auth-form-btn" style="border-radius: 15px;" value="Valider" />
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>