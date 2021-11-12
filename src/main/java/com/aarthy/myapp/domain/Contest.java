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
 * A Contest.
 */
@Entity
@Table(name = "contest")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "contest_id")
    private Long contestId;

    @Column(name = "contest_name")
    private String contestName;

    @Column(name = "contest_description")
    private String contestDescription;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "organization")
    private String organization;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "contact_no")
    private Long contactNo;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "no_of_photos")
    private Long noOfPhotos;

    @Column(name = "no_of_participants")
    private Long noOfParticipants;

    @Column(name = "winner_id")
    private String winnerId;

    @Column(name = "public_voting")
    private Boolean publicVoting;

    @ManyToOne
    @JsonIgnoreProperties(value = { "photos", "contests" }, allowSetters = true)
    private Participant participant;

    @ManyToMany(mappedBy = "contests")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contests", "participant" }, allowSetters = true)
    private Set<Photo> photos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Contest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContestId() {
        return this.contestId;
    }

    public Contest contestId(Long contestId) {
        this.setContestId(contestId);
        return this;
    }

    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    public String getContestName() {
        return this.contestName;
    }

    public Contest contestName(String contestName) {
        this.setContestName(contestName);
        return this;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getContestDescription() {
        return this.contestDescription;
    }

    public Contest contestDescription(String contestDescription) {
        this.setContestDescription(contestDescription);
        return this;
    }

    public void setContestDescription(String contestDescription) {
        this.contestDescription = contestDescription;
    }

    public Long getOwnerId() {
        return this.ownerId;
    }

    public Contest ownerId(Long ownerId) {
        this.setOwnerId(ownerId);
        return this;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOrganization() {
        return this.organization;
    }

    public Contest organization(String organization) {
        this.setOrganization(organization);
        return this;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getEmailId() {
        return this.emailId;
    }

    public Contest emailId(String emailId) {
        this.setEmailId(emailId);
        return this;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Long getContactNo() {
        return this.contactNo;
    }

    public Contest contactNo(Long contactNo) {
        this.setContactNo(contactNo);
        return this;
    }

    public void setContactNo(Long contactNo) {
        this.contactNo = contactNo;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Contest startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Contest endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Contest isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getNoOfPhotos() {
        return this.noOfPhotos;
    }

    public Contest noOfPhotos(Long noOfPhotos) {
        this.setNoOfPhotos(noOfPhotos);
        return this;
    }

    public void setNoOfPhotos(Long noOfPhotos) {
        this.noOfPhotos = noOfPhotos;
    }

    public Long getNoOfParticipants() {
        return this.noOfParticipants;
    }

    public Contest noOfParticipants(Long noOfParticipants) {
        this.setNoOfParticipants(noOfParticipants);
        return this;
    }

    public void setNoOfParticipants(Long noOfParticipants) {
        this.noOfParticipants = noOfParticipants;
    }

    public String getWinnerId() {
        return this.winnerId;
    }

    public Contest winnerId(String winnerId) {
        this.setWinnerId(winnerId);
        return this;
    }

    public void setWinnerId(String winnerId) {
        this.winnerId = winnerId;
    }

    public Boolean getPublicVoting() {
        return this.publicVoting;
    }

    public Contest publicVoting(Boolean publicVoting) {
        this.setPublicVoting(publicVoting);
        return this;
    }

    public void setPublicVoting(Boolean publicVoting) {
        this.publicVoting = publicVoting;
    }

    public Participant getParticipant() {
        return this.participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Contest participant(Participant participant) {
        this.setParticipant(participant);
        return this;
    }

    public Set<Photo> getPhotos() {
        return this.photos;
    }

    public void setPhotos(Set<Photo> photos) {
        if (this.photos != null) {
            this.photos.forEach(i -> i.removeContest(this));
        }
        if (photos != null) {
            photos.forEach(i -> i.addContest(this));
        }
        this.photos = photos;
    }

    public Contest photos(Set<Photo> photos) {
        this.setPhotos(photos);
        return this;
    }

    public Contest addPhoto(Photo photo) {
        this.photos.add(photo);
        photo.getContests().add(this);
        return this;
    }

    public Contest removePhoto(Photo photo) {
        this.photos.remove(photo);
        photo.getContests().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contest)) {
            return false;
        }
        return id != null && id.equals(((Contest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contest{" +
            "id=" + getId() +
            ", contestId=" + getContestId() +
            ", contestName='" + getContestName() + "'" +
            ", contestDescription='" + getContestDescription() + "'" +
            ", ownerId=" + getOwnerId() +
            ", organization='" + getOrganization() + "'" +
            ", emailId='" + getEmailId() + "'" +
            ", contactNo=" + getContactNo() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", noOfPhotos=" + getNoOfPhotos() +
            ", noOfParticipants=" + getNoOfParticipants() +
            ", winnerId='" + getWinnerId() + "'" +
            ", publicVoting='" + getPublicVoting() + "'" +
            "}";
    }
}
