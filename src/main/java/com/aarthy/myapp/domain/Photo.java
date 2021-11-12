package com.aarthy.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Photo.
 */
@Entity
@Table(name = "photo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "photo_id")
    private Long photoId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "pic_rating")
    private Integer picRating;

    @Column(name = "genre")
    private String genre;

    @Column(name = "description")
    private String description;

    @Column(name = "in_contest")
    private Boolean inContest;

    @Column(name = "contest_id")
    private Long contestId;

    @ManyToMany
    @JoinTable(
        name = "rel_photo__contest",
        joinColumns = @JoinColumn(name = "photo_id"),
        inverseJoinColumns = @JoinColumn(name = "contest_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "participant", "photos" }, allowSetters = true)
    private Set<Contest> contests = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "photos", "contests" }, allowSetters = true)
    private Participant participant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Photo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPhotoId() {
        return this.photoId;
    }

    public Photo photoId(Long photoId) {
        this.setPhotoId(photoId);
        return this;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Photo userId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPicRating() {
        return this.picRating;
    }

    public Photo picRating(Integer picRating) {
        this.setPicRating(picRating);
        return this;
    }

    public void setPicRating(Integer picRating) {
        this.picRating = picRating;
    }

    public String getGenre() {
        return this.genre;
    }

    public Photo genre(String genre) {
        this.setGenre(genre);
        return this;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return this.description;
    }

    public Photo description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getInContest() {
        return this.inContest;
    }

    public Photo inContest(Boolean inContest) {
        this.setInContest(inContest);
        return this;
    }

    public void setInContest(Boolean inContest) {
        this.inContest = inContest;
    }

    public Long getContestId() {
        return this.contestId;
    }

    public Photo contestId(Long contestId) {
        this.setContestId(contestId);
        return this;
    }

    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    public Set<Contest> getContests() {
        return this.contests;
    }

    public void setContests(Set<Contest> contests) {
        this.contests = contests;
    }

    public Photo contests(Set<Contest> contests) {
        this.setContests(contests);
        return this;
    }

    public Photo addContest(Contest contest) {
        this.contests.add(contest);
        contest.getPhotos().add(this);
        return this;
    }

    public Photo removeContest(Contest contest) {
        this.contests.remove(contest);
        contest.getPhotos().remove(this);
        return this;
    }

    public Participant getParticipant() {
        return this.participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Photo participant(Participant participant) {
        this.setParticipant(participant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Photo)) {
            return false;
        }
        return id != null && id.equals(((Photo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Photo{" +
            "id=" + getId() +
            ", photoId=" + getPhotoId() +
            ", userId=" + getUserId() +
            ", picRating=" + getPicRating() +
            ", genre='" + getGenre() + "'" +
            ", description='" + getDescription() + "'" +
            ", inContest='" + getInContest() + "'" +
            ", contestId=" + getContestId() +
            "}";
    }
}
