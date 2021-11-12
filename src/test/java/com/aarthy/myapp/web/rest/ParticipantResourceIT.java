package com.aarthy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aarthy.myapp.IntegrationTest;
import com.aarthy.myapp.domain.Participant;
import com.aarthy.myapp.repository.ParticipantRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ParticipantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParticipantResourceIT {

    private static final String DEFAULT_F_NAME = "AAAAAAAAAA";
    private static final String UPDATED_F_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_M_NAME = "AAAAAAAAAA";
    private static final String UPDATED_M_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_L_NAME = "AAAAAAAAAA";
    private static final String UPDATED_L_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_PHONE_NO = 1L;
    private static final Long UPDATED_PHONE_NO = 2L;

    private static final Long DEFAULT_NO_OF_PHOTOS = 1L;
    private static final Long UPDATED_NO_OF_PHOTOS = 2L;

    private static final String DEFAULT_PROFILE_PIC = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_PIC = "BBBBBBBBBB";

    private static final Integer DEFAULT_USER_RATING = 1;
    private static final Integer UPDATED_USER_RATING = 2;

    private static final Long DEFAULT_NO_OF_CONTESTS_PARTICIPATED = 1L;
    private static final Long UPDATED_NO_OF_CONTESTS_PARTICIPATED = 2L;

    private static final Long DEFAULT_NO_OF_CONTESTS_WON = 1L;
    private static final Long UPDATED_NO_OF_CONTESTS_WON = 2L;

    private static final String ENTITY_API_URL = "/api/participants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParticipantMockMvc;

    private Participant participant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participant createEntity(EntityManager em) {
        Participant participant = new Participant()
            .fName(DEFAULT_F_NAME)
            .mName(DEFAULT_M_NAME)
            .lName(DEFAULT_L_NAME)
            .userName(DEFAULT_USER_NAME)
            .userId(DEFAULT_USER_ID)
            .dob(DEFAULT_DOB)
            .emailId(DEFAULT_EMAIL_ID)
            .phoneNo(DEFAULT_PHONE_NO)
            .noOfPhotos(DEFAULT_NO_OF_PHOTOS)
            .profilePic(DEFAULT_PROFILE_PIC)
            .userRating(DEFAULT_USER_RATING)
            .noOfContestsParticipated(DEFAULT_NO_OF_CONTESTS_PARTICIPATED)
            .noOfContestsWon(DEFAULT_NO_OF_CONTESTS_WON);
        return participant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participant createUpdatedEntity(EntityManager em) {
        Participant participant = new Participant()
            .fName(UPDATED_F_NAME)
            .mName(UPDATED_M_NAME)
            .lName(UPDATED_L_NAME)
            .userName(UPDATED_USER_NAME)
            .userId(UPDATED_USER_ID)
            .dob(UPDATED_DOB)
            .emailId(UPDATED_EMAIL_ID)
            .phoneNo(UPDATED_PHONE_NO)
            .noOfPhotos(UPDATED_NO_OF_PHOTOS)
            .profilePic(UPDATED_PROFILE_PIC)
            .userRating(UPDATED_USER_RATING)
            .noOfContestsParticipated(UPDATED_NO_OF_CONTESTS_PARTICIPATED)
            .noOfContestsWon(UPDATED_NO_OF_CONTESTS_WON);
        return participant;
    }

    @BeforeEach
    public void initTest() {
        participant = createEntity(em);
    }

    @Test
    @Transactional
    void createParticipant() throws Exception {
        int databaseSizeBeforeCreate = participantRepository.findAll().size();
        // Create the Participant
        restParticipantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participant)))
            .andExpect(status().isCreated());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeCreate + 1);
        Participant testParticipant = participantList.get(participantList.size() - 1);
        assertThat(testParticipant.getfName()).isEqualTo(DEFAULT_F_NAME);
        assertThat(testParticipant.getmName()).isEqualTo(DEFAULT_M_NAME);
        assertThat(testParticipant.getlName()).isEqualTo(DEFAULT_L_NAME);
        assertThat(testParticipant.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testParticipant.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testParticipant.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testParticipant.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testParticipant.getPhoneNo()).isEqualTo(DEFAULT_PHONE_NO);
        assertThat(testParticipant.getNoOfPhotos()).isEqualTo(DEFAULT_NO_OF_PHOTOS);
        assertThat(testParticipant.getProfilePic()).isEqualTo(DEFAULT_PROFILE_PIC);
        assertThat(testParticipant.getUserRating()).isEqualTo(DEFAULT_USER_RATING);
        assertThat(testParticipant.getNoOfContestsParticipated()).isEqualTo(DEFAULT_NO_OF_CONTESTS_PARTICIPATED);
        assertThat(testParticipant.getNoOfContestsWon()).isEqualTo(DEFAULT_NO_OF_CONTESTS_WON);
    }

    @Test
    @Transactional
    void createParticipantWithExistingId() throws Exception {
        // Create the Participant with an existing ID
        participant.setId(1L);

        int databaseSizeBeforeCreate = participantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParticipantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participant)))
            .andExpect(status().isBadRequest());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParticipants() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        // Get all the participantList
        restParticipantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(participant.getId().intValue())))
            .andExpect(jsonPath("$.[*].fName").value(hasItem(DEFAULT_F_NAME)))
            .andExpect(jsonPath("$.[*].mName").value(hasItem(DEFAULT_M_NAME)))
            .andExpect(jsonPath("$.[*].lName").value(hasItem(DEFAULT_L_NAME)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].phoneNo").value(hasItem(DEFAULT_PHONE_NO.intValue())))
            .andExpect(jsonPath("$.[*].noOfPhotos").value(hasItem(DEFAULT_NO_OF_PHOTOS.intValue())))
            .andExpect(jsonPath("$.[*].profilePic").value(hasItem(DEFAULT_PROFILE_PIC)))
            .andExpect(jsonPath("$.[*].userRating").value(hasItem(DEFAULT_USER_RATING)))
            .andExpect(jsonPath("$.[*].noOfContestsParticipated").value(hasItem(DEFAULT_NO_OF_CONTESTS_PARTICIPATED.intValue())))
            .andExpect(jsonPath("$.[*].noOfContestsWon").value(hasItem(DEFAULT_NO_OF_CONTESTS_WON.intValue())));
    }

    @Test
    @Transactional
    void getParticipant() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        // Get the participant
        restParticipantMockMvc
            .perform(get(ENTITY_API_URL_ID, participant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(participant.getId().intValue()))
            .andExpect(jsonPath("$.fName").value(DEFAULT_F_NAME))
            .andExpect(jsonPath("$.mName").value(DEFAULT_M_NAME))
            .andExpect(jsonPath("$.lName").value(DEFAULT_L_NAME))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.emailId").value(DEFAULT_EMAIL_ID))
            .andExpect(jsonPath("$.phoneNo").value(DEFAULT_PHONE_NO.intValue()))
            .andExpect(jsonPath("$.noOfPhotos").value(DEFAULT_NO_OF_PHOTOS.intValue()))
            .andExpect(jsonPath("$.profilePic").value(DEFAULT_PROFILE_PIC))
            .andExpect(jsonPath("$.userRating").value(DEFAULT_USER_RATING))
            .andExpect(jsonPath("$.noOfContestsParticipated").value(DEFAULT_NO_OF_CONTESTS_PARTICIPATED.intValue()))
            .andExpect(jsonPath("$.noOfContestsWon").value(DEFAULT_NO_OF_CONTESTS_WON.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingParticipant() throws Exception {
        // Get the participant
        restParticipantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewParticipant() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        int databaseSizeBeforeUpdate = participantRepository.findAll().size();

        // Update the participant
        Participant updatedParticipant = participantRepository.findById(participant.getId()).get();
        // Disconnect from session so that the updates on updatedParticipant are not directly saved in db
        em.detach(updatedParticipant);
        updatedParticipant
            .fName(UPDATED_F_NAME)
            .mName(UPDATED_M_NAME)
            .lName(UPDATED_L_NAME)
            .userName(UPDATED_USER_NAME)
            .userId(UPDATED_USER_ID)
            .dob(UPDATED_DOB)
            .emailId(UPDATED_EMAIL_ID)
            .phoneNo(UPDATED_PHONE_NO)
            .noOfPhotos(UPDATED_NO_OF_PHOTOS)
            .profilePic(UPDATED_PROFILE_PIC)
            .userRating(UPDATED_USER_RATING)
            .noOfContestsParticipated(UPDATED_NO_OF_CONTESTS_PARTICIPATED)
            .noOfContestsWon(UPDATED_NO_OF_CONTESTS_WON);

        restParticipantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedParticipant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedParticipant))
            )
            .andExpect(status().isOk());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
        Participant testParticipant = participantList.get(participantList.size() - 1);
        assertThat(testParticipant.getfName()).isEqualTo(UPDATED_F_NAME);
        assertThat(testParticipant.getmName()).isEqualTo(UPDATED_M_NAME);
        assertThat(testParticipant.getlName()).isEqualTo(UPDATED_L_NAME);
        assertThat(testParticipant.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testParticipant.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testParticipant.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testParticipant.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testParticipant.getPhoneNo()).isEqualTo(UPDATED_PHONE_NO);
        assertThat(testParticipant.getNoOfPhotos()).isEqualTo(UPDATED_NO_OF_PHOTOS);
        assertThat(testParticipant.getProfilePic()).isEqualTo(UPDATED_PROFILE_PIC);
        assertThat(testParticipant.getUserRating()).isEqualTo(UPDATED_USER_RATING);
        assertThat(testParticipant.getNoOfContestsParticipated()).isEqualTo(UPDATED_NO_OF_CONTESTS_PARTICIPATED);
        assertThat(testParticipant.getNoOfContestsWon()).isEqualTo(UPDATED_NO_OF_CONTESTS_WON);
    }

    @Test
    @Transactional
    void putNonExistingParticipant() throws Exception {
        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
        participant.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, participant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(participant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParticipant() throws Exception {
        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
        participant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(participant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParticipant() throws Exception {
        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
        participant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParticipantWithPatch() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        int databaseSizeBeforeUpdate = participantRepository.findAll().size();

        // Update the participant using partial update
        Participant partialUpdatedParticipant = new Participant();
        partialUpdatedParticipant.setId(participant.getId());

        partialUpdatedParticipant
            .lName(UPDATED_L_NAME)
            .userName(UPDATED_USER_NAME)
            .noOfPhotos(UPDATED_NO_OF_PHOTOS)
            .profilePic(UPDATED_PROFILE_PIC)
            .noOfContestsWon(UPDATED_NO_OF_CONTESTS_WON);

        restParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParticipant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParticipant))
            )
            .andExpect(status().isOk());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
        Participant testParticipant = participantList.get(participantList.size() - 1);
        assertThat(testParticipant.getfName()).isEqualTo(DEFAULT_F_NAME);
        assertThat(testParticipant.getmName()).isEqualTo(DEFAULT_M_NAME);
        assertThat(testParticipant.getlName()).isEqualTo(UPDATED_L_NAME);
        assertThat(testParticipant.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testParticipant.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testParticipant.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testParticipant.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testParticipant.getPhoneNo()).isEqualTo(DEFAULT_PHONE_NO);
        assertThat(testParticipant.getNoOfPhotos()).isEqualTo(UPDATED_NO_OF_PHOTOS);
        assertThat(testParticipant.getProfilePic()).isEqualTo(UPDATED_PROFILE_PIC);
        assertThat(testParticipant.getUserRating()).isEqualTo(DEFAULT_USER_RATING);
        assertThat(testParticipant.getNoOfContestsParticipated()).isEqualTo(DEFAULT_NO_OF_CONTESTS_PARTICIPATED);
        assertThat(testParticipant.getNoOfContestsWon()).isEqualTo(UPDATED_NO_OF_CONTESTS_WON);
    }

    @Test
    @Transactional
    void fullUpdateParticipantWithPatch() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        int databaseSizeBeforeUpdate = participantRepository.findAll().size();

        // Update the participant using partial update
        Participant partialUpdatedParticipant = new Participant();
        partialUpdatedParticipant.setId(participant.getId());

        partialUpdatedParticipant
            .fName(UPDATED_F_NAME)
            .mName(UPDATED_M_NAME)
            .lName(UPDATED_L_NAME)
            .userName(UPDATED_USER_NAME)
            .userId(UPDATED_USER_ID)
            .dob(UPDATED_DOB)
            .emailId(UPDATED_EMAIL_ID)
            .phoneNo(UPDATED_PHONE_NO)
            .noOfPhotos(UPDATED_NO_OF_PHOTOS)
            .profilePic(UPDATED_PROFILE_PIC)
            .userRating(UPDATED_USER_RATING)
            .noOfContestsParticipated(UPDATED_NO_OF_CONTESTS_PARTICIPATED)
            .noOfContestsWon(UPDATED_NO_OF_CONTESTS_WON);

        restParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParticipant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParticipant))
            )
            .andExpect(status().isOk());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
        Participant testParticipant = participantList.get(participantList.size() - 1);
        assertThat(testParticipant.getfName()).isEqualTo(UPDATED_F_NAME);
        assertThat(testParticipant.getmName()).isEqualTo(UPDATED_M_NAME);
        assertThat(testParticipant.getlName()).isEqualTo(UPDATED_L_NAME);
        assertThat(testParticipant.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testParticipant.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testParticipant.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testParticipant.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testParticipant.getPhoneNo()).isEqualTo(UPDATED_PHONE_NO);
        assertThat(testParticipant.getNoOfPhotos()).isEqualTo(UPDATED_NO_OF_PHOTOS);
        assertThat(testParticipant.getProfilePic()).isEqualTo(UPDATED_PROFILE_PIC);
        assertThat(testParticipant.getUserRating()).isEqualTo(UPDATED_USER_RATING);
        assertThat(testParticipant.getNoOfContestsParticipated()).isEqualTo(UPDATED_NO_OF_CONTESTS_PARTICIPATED);
        assertThat(testParticipant.getNoOfContestsWon()).isEqualTo(UPDATED_NO_OF_CONTESTS_WON);
    }

    @Test
    @Transactional
    void patchNonExistingParticipant() throws Exception {
        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
        participant.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, participant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(participant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParticipant() throws Exception {
        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
        participant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(participant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParticipant() throws Exception {
        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
        participant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(participant))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParticipant() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        int databaseSizeBeforeDelete = participantRepository.findAll().size();

        // Delete the participant
        restParticipantMockMvc
            .perform(delete(ENTITY_API_URL_ID, participant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
