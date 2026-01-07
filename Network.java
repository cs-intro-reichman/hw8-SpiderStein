/**
 * Represents a social network. The network has users, who follow other uesrs.
 * Each user is an instance of the User class.
 */
public class Network {

    // Fields
    private User[] users; // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /**
     * Creates a network with some users. The only purpose of this constructor is
     * to allow testing the toString and getUser methods, before implementing other
     * methods.
     */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }

    /**
     * Finds in this network, and returns, the user that has the given name.
     * If there is no such user, returns null.
     * Notice that the method receives a String, and returns a User object.
     */
    public User getUser(String name) {
        for (int i = 0; i < this.userCount; i++) {
            User user = this.users[i];
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Adds a new user with the given name to this network.
     * If ths network is full, does nothing and returns false;
     * If the given name is already a user in this network, does nothing and returns
     * false;
     * Otherwise, creates a new user with the given name, adds the user to this
     * network, and returns true.
     */
    public boolean addUser(String name) {
        if (this.userCount == this.users.length) {
            return false;
        }
        if (this.getUser(name) != null) {
            return false;
        }
        this.users[this.userCount] = new User(name);
        this.userCount++;
        return true;
    }

    /**
     * Makes the user with name1 follow the user with name2. If successful, returns
     * true.
     * If any of the two names is not a user in this network,
     * or if the "follows" addition failed for some reason, returns false.
     */
    public boolean addFollowee(String name1, String name2) {
        User user1 = getUser(name1);
        User user2 = getUser(name2);
        if (user1 == null || user2 == null) {
            return false;
        }
        if (user1.getName().equals(user2.getName())) {
            return false;
        }
        if (!user1.addFollowee(name2)) {
            return false;
        }
        return true;
    }

    /**
     * For the user with the given name, recommends another user to follow. The
     * recommended user is
     * the user that has the maximal mutual number of followees as the user with the
     * given name.
     */
    public String recommendWhoToFollow(String name) {
        User user = this.getUser(name);
        if (user.follows(name) || user == null) {
            return null;
        }
        int mutualCount = -1;
        int currentMutualCount = 0;
        String recommendedUser = null;
        for (int i = 0; i < this.userCount; i++) {
            User otherUser = this.users[i];
            if (user.getName().equals(otherUser.getName())) {
                continue;
            }
            currentMutualCount = user.countMutual(otherUser);
            if (currentMutualCount > mutualCount) {
                mutualCount = currentMutualCount;
                recommendedUser = otherUser.getName();
            }
            // compare user and otherUser
        }

        return recommendedUser;
    }

    /**
     * Computes and returns the name of the most popular user in this network:
     * The user who appears the most in the follow lists of all the users.
     */
    public String mostPopularUser() {
        String popularUser = null;
        int maxCount = -1;
        int currentCount = 0;
        for (int i = 0; i < this.userCount; i++) {
            User user = this.users[i];
            currentCount = followeeCount(user.getName());
            if (currentCount > maxCount) {
                maxCount = currentCount;
                popularUser = user.getName();
            }
        }
        return popularUser;
    }

    /**
     * Returns the number of times that the given name appears in the follows lists
     * of all
     * the users in this network. Note: A name can appear 0 or 1 times in each list.
     */
    private int followeeCount(String name) {
        int count = 0;
        for (int i = 0; i < this.userCount; i++) {
            User user = this.users[i];
            if (user.follows(name)) {
                count++;
            }
        }
        return count;
    }

    // Returns a textual description of all the users in this network, and who they
    // follow.
    public String toString() {
        StringBuilder out = new StringBuilder("Network:");
        for (int i = 0; i < this.userCount; i++) {
            out.append(System.lineSeparator());
            out.append(this.users[i].toString());
        }
        return out.toString();
    }
}
