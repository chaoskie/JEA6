package domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "users.findByName", query = "SELECT u FROM User u WHERE u.username = :name")
})
@JsonIgnoreProperties({"password", "following"})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Role role = Role.User;
    private String displayname;
    private String profilePhoto;
    private String bio;
    private String location;
    private String website;

    @OneToMany(fetch = FetchType.LAZY)
    private List<User> following;

    public User() { }

    public User(String username, String password, Role role, String displayname, String profilePhoto, String bio, String location, String website) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.displayname = displayname;
        this.profilePhoto = profilePhoto;
        this.bio = bio;
        this.location = location;
        this.website = website;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public void followUser(User user) throws IllegalArgumentException {
        if (this.following.contains(user)) {
            throw new IllegalArgumentException("User already follows this user");
        }

        this.following.add(user);
    }

    public void unfollowUser(User user) throws IllegalArgumentException {
        if (!this.following.contains(user)) {
            throw new IllegalArgumentException("User didn't follow this user");
        }

        this.following.remove(user);
    }
}
