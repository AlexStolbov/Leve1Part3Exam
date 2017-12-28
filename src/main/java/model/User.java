package model;

public class User implements Comparable<User> {
    private final String name;
    private final String passport;

    public User(String name, String passport) {
        this.name = name;
        this.passport = passport;
    }

    public String getName() {
        return name;
    }

    public String getPassport() {
        return passport;
    }

    @Override
    public int compareTo(User o) {
        int result = this.name.compareTo(o.name);
        if (result == 0) {
            result = this.passport.compareTo(o.passport);
        }
        return result;
    }
}
