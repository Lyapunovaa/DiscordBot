package apiClient.configs;

public class TokenManager {
    private static TokenManager instance;
    private String iamtoken;

    private TokenManager() {
    }

    public static TokenManager getInstance() {
        if (instance == null) {
            instance = new TokenManager();
        }
        return instance;
    }

    public void setIamtoken(String token) {
        this.iamtoken = token;
    }

    public String getIamtoken() {
        return iamtoken;
    }

}
