package com.aarthy.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aarthy.myapp.IntegrationTest;
import com.aarthy.myapp.domain.Contest;
import com.aarthy.myapp.repository.ContestRepository;
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
 * Integration tests for the {@link ContestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContestResourceIT {

    private static final Long DEFAULT_CONTEST_ID = 1L;
    private static final Long UPDATED_CONTEST_ID = 2L;

    private static final String DEFAULT_CONTEST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTEST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEST_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CONTEST_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_OWNER_ID = 1L;
    private static final Long UPDATED_OWNER_ID = 2L;

    private static final String DEFAULT_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTACT_NO = 1L;
    private static final Long UPDATED_CONTACT_NO = 2L;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Long DEFAULT_NO_OF_PHOTOS = 1L;
    private static final Long UPDATED_NO_OF_PHOTOS = 2L;

    private static final Long DEFAULT_NO_OF_PARTICIPANTS = 1L;
    private static final Long UPDATED_NO_OF_PARTICIPANTS = 2L;

    private static final String DEFAULT_WINNER_ID = "AAAAAAAAAA";
    private static final String UPDATED_WINNER_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PUBLIC_VOTING = false;
    private static final Boolean UPDATED_PUBLIC_VOTING = true;

    private static final String ENTITY_API_URL = "/api/contests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContestMockMvc;

    private Contest contest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contest createEntity(EntityManager em) {
        Contest contest = new Contest()
            .contestId(DEFAULT_CONTEST_ID)
            .contestName(DEFAULT_CONTEST_NAME)
            .contestDescription(DEFAULT_CONTEST_DESCRIPTION)
            .ownerId(DEFAULT_OWNER_ID)
            .organization(DEFAULT_ORGANIZATION)
            .emailId(DEFAULT_EMAIL_ID)
            .contactNo(DEFAULT_CONTACT_NO)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .isActive(DEFAULT_IS_ACTIVE)
            .noOfPhotos(DEFAULT_NO_OF_PHOTOS)
            .noOfParticipants(DEFAULT_NO_OF_PARTICIPANTS)
            .winnerId(DEFAULT_WINNER_ID)
            .publicVoting(DEFAULT_PUBLIC_VOTING);
        return contest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contest createUpdatedEntity(EntityManager em) {
        Contest contest = new Contest()
            .contestId(UPDATED_CONTEST_ID)
            .contestName(UPDATED_CONTEST_NAME)
            .contestDescription(UPDATED_CONTEST_DESCRIPTION)
            .ownerId(UPDATED_OWNER_ID)
            .organization(UPDATED_ORGANIZATION)
            .emailId(UPDATED_EMAIL_ID)
            .contactNo(UPDATED_CONTACT_NO)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .noOfPhotos(UPDATED_NO_OF_PHOTOS)
            .noOfParticipants(UPDATED_NO_OF_PARTICIPANTS)
            .winnerId(UPDATED_WINNER_ID)
            .publicVoting(UPDATED_PUBLIC_VOTING);
        return contest;
    }

    @BeforeEach
    public void initTest() {
        contest = createEntity(em);
    }

    @Test
    @Transactional
    void createContest() throws Exception {
        int databaseSizeBeforeCreate = contestRepository.findAll().size();
        // Create the Contest
        restContestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contest)))
            .andExpect(status().isCreated());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeCreate + 1);
        Contest testContest = contestList.get(contestList.size() - 1);
        assertThat(testContest.getContestId()).isEqualTo(DEFAULT_CONTEST_ID);
        assertThat(testContest.getContestName()).isEqualTo(DEFAULT_CONTEST_NAME);
        assertThat(testContest.getContestDescription()).isEqualTo(DEFAULT_CONTEST_DESCRIPTION);
        assertThat(testContest.getOwnerId()).isEqualTo(DEFAULT_OWNER_ID);
        assertThat(testContest.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testContest.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testContest.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testContest.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testContest.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testContest.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testContest.getNoOfPhotos()).isEqualTo(DEFAULT_NO_OF_PHOTOS);
        assertThat(testContest.getNoOfParticipants()).isEqualTo(DEFAULT_NO_OF_PARTICIPANTS);
        assertThat(testContest.getWinnerId()).isEqualTo(DEFAULT_WINNER_ID);
        assertThat(testContest.getPublicVoting()).isEqualTo(DEFAULT_PUBLIC_VOTING);
    }

    @Test
    @Transactional
    void createContestWithExistingId() throws Exception {
        // Create the Contest with an existing ID
        contest.setId(1L);

        int databaseSizeBeforeCreate = contestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contest)))
            .andExpect(status().isBadRequest());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContests() throws Exception {
        // Initialize the database
        contestRepository.saveAndFlush(contest);

        // Get all the contestList
        restContestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contest.getId().intValue())))
            .andExpect(jsonPath("$.[*].contestId").value(hasItem(DEFAULT_CONTEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].contestName").value(hasItem(DEFAULT_CONTEST_NAME)))
            .andExpect(jsonPath("$.[*].contestDescription").value(hasItem(DEFAULT_CONTEST_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].ownerId").value(hasItem(DEFAULT_OWNER_ID.intValue())))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].noOfPhotos").value(hasItem(DEFAULT_NO_OF_PHOTOS.intValue())))
            .andExpect(jsonPath("$.[*].noOfParticipants").value(hasItem(DEFAULT_NO_OF_PARTICIPANTS.intValue())))
            .andExpect(jsonPath("$.[*].winnerId").value(hasItem(DEFAULT_WINNER_ID)))
            .andExpect(jsonPath("$.[*].publicVoting").value(hasItem(DEFAULT_PUBLIC_VOTING.booleanValue())));
    }

    @Test
    @Transactional
    void getContest() throws Exception {
        // Initialize the database
        contestRepository.saveAndFlush(contest);

        // Get the contest
        restContestMockMvc
            .perform(get(ENTITY_API_URL_ID, contest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contest.getId().intValue()))
            .andExpect(jsonPath("$.contestId").value(DEFAULT_CONTEST_ID.intValue()))
            .andExpect(jsonPath("$.contestName").value(DEFAULT_CONTEST_NAME))
            .andExpect(jsonPath("$.contestDescription").value(DEFAULT_CONTEST_DESCRIPTION))
            .andExpect(jsonPath("$.ownerId").value(DEFAULT_OWNER_ID.intValue()))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION))
            .andExpect(jsonPath("$.emailId").value(DEFAULT_EMAIL_ID))
            .andExpect(jsonPath("$.contactNo").value(DEFAULT_CONTACT_NO.intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.noOfPhotos").value(DEFAULT_NO_OF_PHOTOS.intValue()))
            .andExpect(jsonPath("$.noOfParticipants").value(DEFAULT_NO_OF_PARTICIPANTS.intValue()))
            .andExpect(jsonPath("$.winnerId").value(DEFAULT_WINNER_ID))
            .andExpect(jsonPath("$.publicVoting").value(DEFAULT_PUBLIC_VOTING.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingContest() throws Exception {
        // Get the contest
        restContestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContest() throws Exception {
        // Initialize the database
        contestRepository.saveAndFlush(contest);

        int databaseSizeBeforeUpdate = contestRepository.findAll().size();

        // Update the contest
        Contest updatedContest = contestRepository.findById(contest.getId()).get();
        // Disconnect from session so that the updates on updatedContest are not directly saved in db
        em.detach(updatedContest);
        updatedContest
            .contestId(UPDATED_CONTEST_ID)
            .contestName(UPDATED_CONTEST_NAME)
            .contestDescription(UPDATED_CONTEST_DESCRIPTION)
            .ownerId(UPDATED_OWNER_ID)
            .organization(UPDATED_ORGANIZATION)
            .emailId(UPDATED_EMAIL_ID)
            .contactNo(UPDATED_CONTACT_NO)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .noOfPhotos(UPDATED_NO_OF_PHOTOS)
            .noOfParticipants(UPDATED_NO_OF_PARTICIPANTS)
            .winnerId(UPDATED_WINNER_ID)
            .publicVoting(UPDATED_PUBLIC_VOTING);

        restContestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedContest))
            )
            .andExpect(status().isOk());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
        Contest testContest = contestList.get(contestList.size() - 1);
        assertThat(testContest.getContestId()).isEqualTo(UPDATED_CONTEST_ID);
        assertThat(testContest.getContestName()).isEqualTo(UPDATED_CONTEST_NAME);
        assertThat(testContest.getContestDescription()).isEqualTo(UPDATED_CONTEST_DESCRIPTION);
        assertThat(testContest.getOwnerId()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testContest.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testContest.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testContest.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testContest.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testContest.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testContest.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testContest.getNoOfPhotos()).isEqualTo(UPDATED_NO_OF_PHOTOS);
        assertThat(testContest.getNoOfParticipants()).isEqualTo(UPDATED_NO_OF_PARTICIPANTS);
        assertThat(testContest.getWinnerId()).isEqualTo(UPDATED_WINNER_ID);
        assertThat(testContest.getPublicVoting()).isEqualTo(UPDATED_PUBLIC_VOTING);
    }

    @Test
    @Transactional
    void putNonExistingContest() throws Exception {
        int databaseSizeBeforeUpdate = contestRepository.findAll().size();
        contest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contest))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContest() throws Exception {
        int databaseSizeBeforeUpdate = contestRepository.findAll().size();
        contest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contest))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContest() throws Exception {
        int databaseSizeBeforeUpdate = contestRepository.findAll().size();
        contest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contest)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContestWithPatch() throws Exception {
        // Initialize the database
        contestRepository.saveAndFlush(contest);

        int databaseSizeBeforeUpdate = contestRepository.findAll().size();

        // Update the contest using partial update
        Contest partialUpdatedContest = new Contest();
        partialUpdatedContest.setId(contest.getId());

        partialUpdatedContest
            .contestId(UPDATED_CONTEST_ID)
            .contestName(UPDATED_CONTEST_NAME)
            .emailId(UPDATED_EMAIL_ID)
            .contactNo(UPDATED_CONTACT_NO)
            .noOfParticipants(UPDATED_NO_OF_PARTICIPANTS)
            .winnerId(UPDATED_WINNER_ID);

        restContestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContest))
            )
            .andExpect(status().isOk());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
        Contest testContest = contestList.get(contestList.size() - 1);
        assertThat(testContest.getContestId()).isEqualTo(UPDATED_CONTEST_ID);
        assertThat(testContest.getContestName()).isEqualTo(UPDATED_CONTEST_NAME);
        assertThat(testContest.getContestDescription()).isEqualTo(DEFAULT_CONTEST_DESCRIPTION);
        assertThat(testContest.getOwnerId()).isEqualTo(DEFAULT_OWNER_ID);
        assertThat(testContest.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testContest.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testContest.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testContest.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testContest.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testContest.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testContest.getNoOfPhotos()).isEqualTo(DEFAULT_NO_OF_PHOTOS);
        assertThat(testContest.getNoOfParticipants()).isEqualTo(UPDATED_NO_OF_PARTICIPANTS);
        assertThat(testContest.getWinnerId()).isEqualTo(UPDATED_WINNER_ID);
        assertThat(testContest.getPublicVoting()).isEqualTo(DEFAULT_PUBLIC_VOTING);
    }

    @Test
    @Transactional
    void fullUpdateContestWithPatch() throws Exception {
        // Initialize the database
        contestRepository.saveAndFlush(contest);

        int databaseSizeBeforeUpdate = contestRepository.findAll().size();

        // Update the contest using partial update
        Contest partialUpdatedContest = new Contest();
        partialUpdatedContest.setId(contest.getId());

        partialUpdatedContest
            .contestId(UPDATED_CONTEST_ID)
            .contestName(UPDATED_CONTEST_NAME)
            .contestDescription(UPDATED_CONTEST_DESCRIPTION)
            .ownerId(UPDATED_OWNER_ID)
            .organization(UPDATED_ORGANIZATION)
            .emailId(UPDATED_EMAIL_ID)
            .contactNo(UPDATED_CONTACT_NO)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .noOfPhotos(UPDATED_NO_OF_PHOTOS)
            .noOfParticipants(UPDATED_NO_OF_PARTICIPANTS)
            .winnerId(UPDATED_WINNER_ID)
            .publicVoting(UPDATED_PUBLIC_VOTING);

        restContestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContest))
            )
            .andExpect(status().isOk());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
        Contest testContest = contestList.get(contestList.size() - 1);
        assertThat(testContest.getContestId()).isEqualTo(UPDATED_CONTEST_ID);
        assertThat(testContest.getContestName()).isEqualTo(UPDATED_CONTEST_NAME);
        assertThat(testContest.getContestDescription()).isEqualTo(UPDATED_CONTEST_DESCRIPTION);
        assertThat(testContest.getOwnerId()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testContest.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testContest.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testContest.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testContest.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testContest.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testContest.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testContest.getNoOfPhotos()).isEqualTo(UPDATED_NO_OF_PHOTOS);
        assertThat(testContest.getNoOfParticipants()).isEqualTo(UPDATED_NO_OF_PARTICIPANTS);
        assertThat(testContest.getWinnerId()).isEqualTo(UPDATED_WINNER_ID);
        assertThat(testContest.getPublicVoting()).isEqualTo(UPDATED_PUBLIC_VOTING);
    }

    @Test
    @Transactional
    void patchNonExistingContest() throws Exception {
        int databaseSizeBeforeUpdate = contestRepository.findAll().size();
        contest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contest))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContest() throws Exception {
        int databaseSizeBeforeUpdate = contestRepository.findAll().size();
        contest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contest))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContest() throws Exception {
        int databaseSizeBeforeUpdate = contestRepository.findAll().size();
        contest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contest)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContest() throws Exception {
        // Initialize the database
        contestRepository.saveAndFlush(contest);

        int databaseSizeBeforeDelete = contestRepository.findAll().size();

        // Delete the contest
        restContestMockMvc
            .perform(delete(ENTITY_API_URL_ID, contest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
