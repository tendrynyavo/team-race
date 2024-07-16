<%
    
    String error = (request.getAttribute("error") == null) ? "" : (String) request.getAttribute("error");

%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../template/link.html" %>
        <title>Login</title>
    </head>
    <body>
        <div class="container-scroller">
            <div class="container-fluid page-body-wrapper full-page-wrapper">
              <div class="content-wrapper d-flex align-items-center auth px-0">
                <div class="row w-100 mx-0">
                  <div class="col-lg-4 mx-auto">
                    <div class="auth-form-light text-left py-5 px-4 px-sm-5 shadow-sm" style="border-radius: 20px;">
                      <h4>Hello! let's get started</h4>
                      <h6 class="fw-light">Sign in to continue.</h6>
                      <form action="equipe/login.do" method="post" class="pt-3">
                        <div class="form-group">
                            <input type="text" value="A" class="form-control form-control-lg rounded" id="exampleInputEmail1" placeholder="Email" name="login">
                        </div>
                        <div class="form-group">
                            <input type="password" value="A" class="form-control form-control-lg rounded" id="exampleInputEmail1" placeholder="Mot de passe" name="pwd">
                        </div>
                        <h2 class="text-danger"><%=error %></h2>
                        <div class="mt-3">
                            <input type="submit" class="btn btn-block btn-dark btn-lg font-weight-medium auth-form-btn" style="border-radius: 15px;" value="SIGN IN" />
                        </div>
                      </form>
                    </div>
                  </div>
                </div>
              </div>
              <!-- content-wrapper ends -->
            </div>
            <!-- page-body-wrapper ends -->
          </div>
    </body>
</html>
