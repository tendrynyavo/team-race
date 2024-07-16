package model.race.admin;

import com.connection.database.BddObject;

public class Admin extends BddObject {

    String login;
    String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
    public Admin() {
        setTable("admin");
        setConnection("PostgreSQL");
        setPrefix("ADM");
        setPrimaryKeyName("id_admin");
        setFunctionPK("nextval('seq_admin')");
        setCountPK(7);
    }
    
    public Admin login(String login, String pwd) throws Exception {
        this.setLogin(login);
        this.setPwd(pwd);
        Admin[] clients = (Admin[]) this.findAll(null, null, null);
        if (clients.length > 0) {
            return clients[0];
        }
        throw new Exception("Email ou mot de passe Inccorect");
    }
    
}