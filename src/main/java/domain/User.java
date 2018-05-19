package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "users.findByName", query = "SELECT u FROM User u WHERE u.username = :name")
})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;


    @Column(nullable = false)
    private String password;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Role> roles = new ArrayList<Role>() {{ add(Role.User); }};
    private String displayname;
    private String profilePhoto;
    private String email;
    private String bio;
    private String location;
    private String website;

    @OneToMany(fetch = FetchType.LAZY)
    private List<User> following;

    @Column(nullable = false, unique = true)
    private String followingURL;

    protected User() { }

    public User(String username, String password, List<Role> roles, String displayname, String profilePhoto, String bio, String location, String website) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.displayname = displayname;
        this.profilePhoto = profilePhoto;
        this.bio = bio;
        this.location = location;
        this.website = website;
        this.followingURL = "users/"+ username+"/following";
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

    @XmlTransient @JsonbTransient
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRole() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

    @XmlTransient @JsonbTransient
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

    @XmlTransient
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFollowingURL() {
        return followingURL;
    }

    public void setFollowingURL(String followingURL) {
        this.followingURL = followingURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        return username.equals(user.username);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + username.hashCode();
        return result;
    }
}
