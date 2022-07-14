package dev.narlebone.auth;

public class NarleboneProfile {
    private final String username, uuid, accessToken;

    public NarleboneProfile(String username, String uuid, String accessToken) {
        this.username = username;
        this.uuid = uuid;
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public String getUUID() {
        return uuid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String toString() {
        return "NarleboneProfile{" +
                "username='" + username + '\'' +
                ", uuid='" + uuid + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
