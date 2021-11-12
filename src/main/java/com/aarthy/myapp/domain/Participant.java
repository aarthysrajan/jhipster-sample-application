package com.aarthy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Participant.
 */
@Entity
@Table(name = "participant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Participant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "f_name")
    private String fName;

    @Column(name = "m_name")
    private String mName;

    @Column(name = "l_name")
    private String lName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "phone_no")
    private Long phoneNo;

    @Column(name = "no_of_photos")
    private Long noOfPhotos;

    @Column(name = "profile_pic")
    private String profilePic;

    @Column(name = "user_rating")
    private Integer userRating;

    @Column(name = "no_of_contests_participated")
    private Long noOfContestsParticipated;

    @Column(name = "no_of_contests_won")
    private Long noOfContestsWon;

    @OneToMany(mappedBy = "participant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contests", "participant" }, allowSetters = true)
    private Set<Photo> photos = new HashSet<>();

    @OneToMany(mappedBy = "participant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "participant", "photos" }, allowSetters = true)
    private Set<Contest> contests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Participant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getfName() {
        return this.fName;
    }

    public Participant fName(String fName) {
        this.setfName(fName);
        return this;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return this.mName;
    }

    public Participant mName(String mName) {
        this.setmName(mName);
        return this;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return this.lName;
    }

    public Participant lName(String lName) {
        this.setlName(lName);
        return this;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getUserName() {
        return this.userName;
    }

    public Participant userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Participant userId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getDob() {
        return this.dob;
    }

    public Participant dob(LocalDate dob) {
        this.setDob(dob);
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmailId() {
        return this.emailId;
    }

    public Participant emailId(String emailId) {
        this.setEmailId(emailId);
        return this;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Long getPhoneNo() {
        return this.phoneNo;
    }

    public Participant phoneNo(Long phoneNo) {
        this.setPhoneNo(phoneNo);
        return this;
    }

    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Long getNoOfPhotos() {
        return this.noOfPhotos;
    }

    public Participant noOfPhotos(Long noOfPhotos) {
        this.setNoOfPhotos(noOfPhotos);
        return this;
    }

    public void setNoOfPhotos(Long noOfPhotos) {
        this.noOfPhotos = noOfPhotos;
    }

    public String getProfilePic() {
        return this.profilePic;
    }

    public Participant profilePic(String profilePic) {
        this.setProfilePic(profilePic);
        return this;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Integer getUserRating() {
        return this.userRating;
    }

    public Participant userRating(Integer userRating) {
        this.setUserRating(userRating);
        return this;
    }

    public void setUserRating(Integer userRating) {
        this.userRating = userRating;
    }

    public Long getNoOfContestsParticipated() {
        return this.noOfContestsParticipated;
    }

    public Participant noOfContestsParticipated(Long noOfContestsParticipated) {
        this.setNoOfContestsParticipated(noOfContestsParticipated);
        return this;
    }

    public void setNoOfContestsParticipated(Long noOfContestsParticipated) {
        this.noOfContestsParticipated = noOfContestsParticipated;
    }

    public Long getNoOfContestsWon() {
        return this.noOfContestsWon;
    }

    public Participant noOfContestsWon(Long noOfContestsWon) {
        this.setNoOfContestsWon(noOfContestsWon);
        return this;
    }

    public void setNoOfContestsWon(Long noOfContestsWon) {
        this.noOfContestsWon = noOfContestsWon;
    }

    public Set<Photo> getPhotos() {
        return this.photos;
    }

    public void setPhotos(Set<Photo> photos) {
        if (this.photos != null) {
            this.photos.forEach(i -> i.setParticipant(null));
        }
        if (photos != null) {
            photos.forEach(i -> i.setParticipant(this));
        }
        this.photos = photos;
    }

    public Participant photos(Set<Photo> photos) {
        this.setPhotos(photos);
        return this;
    }

    public Participant addPhoto(Photo photo) {
        this.photos.add(photo);
        photo.setParticipant(this);
        return this;
    }

    public Participant removePhoto(Photo photo) {
        this.photos.remove(photo);
        photo.setParticipant(null);
        return this;
    }

    public Set<Contest> getContests() {
        return this.contests;
    }

    public void setContests(Set<Contest> contests) {
        if (this.contests != null) {
            this.contests.forEach(i -> i.setParticipant(null));
        }
        if (contests != null) {
            contests.forEach(i -> i.setParticipant(this));
        }
        this.contests = contests;
    }

    public Participant contests(Set<Contest> contests) {
        this.setContests(contests);
        return this;
    }

    public Participant addContest(Contest contest) {
        this.contests.add(contest);
        contest.setParticipant(this);
        return this;
    }

    public Participant removeContest(Contest contest) {
        this.contests.remove(contest);
        contest.setParticipant(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Participant)) {
            return false;
        }
        return id != null && id.equals(((Participant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Participant{" +
            "id=" + getId() +
            ", fName='" + getfName() + "'" +
            ", mName='" + getmName() + "'" +
            ", lName='" + getlName() + "'" +
            ", userName='" + getUserName() + "'" +
            ", userId=" + getUserId() +
            ", dob='" + getDob() + "'" +
            ", emailId='" + getEmailId() + "'" +
            ", phoneNo=" + getPhoneNo() +
            ", noOfPhotos=" + getNoOfPhotos() +
            ", profilePic='" + getProfilePic() + "'" +
            ", userRating=" + getUserRating() +
            ", noOfContestsParticipated=" + getNoOfContestsParticipated() +
            ", noOfContestsWon=" + getNoOfContestsWon() +
            "}";
    }
}
