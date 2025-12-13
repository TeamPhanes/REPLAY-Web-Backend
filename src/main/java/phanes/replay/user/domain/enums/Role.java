package phanes.replay.user.domain.enums;

public enum Role {
    ADMIN,USER;

    public static Role from(String value) {
        try {
            return Role.valueOf(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new RuntimeException("Invalid role in token");
        }
    }
}