package controller.session;

import java.util.HashMap;
import java.util.Map;

public class SessionController {

    Map<String, Object> session;

    public Map<String, Object> getSession() {
        return session;
    }

    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }
    
}
