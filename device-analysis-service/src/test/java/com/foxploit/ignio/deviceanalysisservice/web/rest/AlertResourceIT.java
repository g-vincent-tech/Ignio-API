package com.foxploit.ignio.deviceanalysisservice.web.rest;

import com.foxploit.ignio.deviceanalysisservice.DeviceAnalysisServiceApp;
import com.foxploit.ignio.deviceanalysisservice.domain.Alert;
import com.foxploit.ignio.deviceanalysisservice.repository.AlertRepository;
import com.foxploit.ignio.deviceanalysisservice.service.AlertService;
import com.foxploit.ignio.deviceanalysisservice.service.dto.AlertDTO;
import com.foxploit.ignio.deviceanalysisservice.service.mapper.AlertMapper;
import com.foxploit.ignio.deviceanalysisservice.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.foxploit.ignio.deviceanalysisservice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AlertResource} REST controller.
 */
@SpringBootTest(classes = DeviceAnalysisServiceApp.class)
public class AlertResourceIT {

    private static final String DEFAULT_DEVICE_ID = "NODEIGNIOF101";
    private static final String UPDATED_DEVICE_ID = "NODEIGNIOF102";

    private static final String DEFAULT_OWNER_ID = "5d733e6525099d30f4e8934d";
    private static final String UPDATED_OWNER_ID = "5d73401425099d30f4e8934f";

    private static final String DEFAULT_ALERT_TYPE = "INFO";
    private static final String UPDATED_ALERT_TYPE = "INFO";

    private static final @javax.validation.constraints.NotNull LocalDateTime DEFAULT_TIMESTAMP = LocalDateTime.of(2019, 10, 13, 17, 16, 12);
    private static final @javax.validation.constraints.NotNull LocalDateTime UPDATED_TIMESTAMP = LocalDateTime.of(2019, 10, 13, 17, 16, 13);
    private static final LocalDate SMALLER_TIMESTAMP = LocalDate.ofEpochDay(-1L);

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private AlertMapper alertMapper;

    @Autowired
    private AlertService alertService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restAlertMockMvc;

    private Alert alert;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlertResource alertResource = new AlertResource(alertService);
        this.restAlertMockMvc = MockMvcBuilders.standaloneSetup(alertResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alert createEntity() {
        Alert alert = new Alert()
            .deviceId(DEFAULT_DEVICE_ID)
            .ownerId(DEFAULT_OWNER_ID)
            .alertType(DEFAULT_ALERT_TYPE)
            .timestamp(DEFAULT_TIMESTAMP);
        return alert;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alert createUpdatedEntity() {
        Alert alert = new Alert()
            .deviceId(UPDATED_DEVICE_ID)
            .ownerId(UPDATED_OWNER_ID)
            .alertType(UPDATED_ALERT_TYPE)
            .timestamp(UPDATED_TIMESTAMP);
        return alert;
    }

    @BeforeEach
    public void initTest() {
        alertRepository.deleteAll();
        alert = createEntity();
    }

    @Test
    public void createAlert() throws Exception {
        int databaseSizeBeforeCreate = alertRepository.findAll().size();

        // Create the Alert
        AlertDTO alertDTO = alertMapper.toDto(alert);
        restAlertMockMvc.perform(post("/api/alerts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertDTO)))
            .andExpect(status().isCreated());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeCreate + 1);
        Alert testAlert = alertList.get(alertList.size() - 1);
        assertThat(testAlert.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testAlert.getOwnerId()).isEqualTo(DEFAULT_OWNER_ID);
        assertThat(testAlert.getAlertType()).isEqualTo(DEFAULT_ALERT_TYPE);
        assertThat(testAlert.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    public void createAlertWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alertRepository.findAll().size();

        // Create the Alert with an existing ID
        alert.setId("existing_id");
        AlertDTO alertDTO = alertMapper.toDto(alert);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertMockMvc.perform(post("/api/alerts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkDeviceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertRepository.findAll().size();
        // set the field null
        alert.setDeviceId(null);

        // Create the Alert, which fails.
        AlertDTO alertDTO = alertMapper.toDto(alert);

        restAlertMockMvc.perform(post("/api/alerts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertDTO)))
            .andExpect(status().isBadRequest());

        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkOwnerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertRepository.findAll().size();
        // set the field null
        alert.setOwnerId(null);

        // Create the Alert, which fails.
        AlertDTO alertDTO = alertMapper.toDto(alert);

        restAlertMockMvc.perform(post("/api/alerts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertDTO)))
            .andExpect(status().isBadRequest());

        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAlertTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertRepository.findAll().size();
        // set the field null
        alert.setAlertType(null);

        // Create the Alert, which fails.
        AlertDTO alertDTO = alertMapper.toDto(alert);

        restAlertMockMvc.perform(post("/api/alerts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertDTO)))
            .andExpect(status().isBadRequest());

        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertRepository.findAll().size();
        // set the field null
        alert.setTimestamp(null);

        // Create the Alert, which fails.
        AlertDTO alertDTO = alertMapper.toDto(alert);

        restAlertMockMvc.perform(post("/api/alerts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertDTO)))
            .andExpect(status().isBadRequest());

        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAlerts() throws Exception {
        // Initialize the database
        alertRepository.save(alert);

        // Get all the alertList
        restAlertMockMvc.perform(get("/api/alerts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alert.getId())))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID.toString())))
            .andExpect(jsonPath("$.[*].ownerId").value(hasItem(DEFAULT_OWNER_ID.toString())))
            .andExpect(jsonPath("$.[*].alertType").value(hasItem(DEFAULT_ALERT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(DEFAULT_TIMESTAMP.toString()));
    }
    
    @Test
    public void getAlert() throws Exception {
        // Initialize the database
        alertRepository.save(alert);

        // Get the alert
        restAlertMockMvc.perform(get("/api/alerts/{id}", alert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alert.getId()))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID.toString()))
            .andExpect(jsonPath("$.ownerId").value(DEFAULT_OWNER_ID.toString()))
            .andExpect(jsonPath("$.alertType").value(DEFAULT_ALERT_TYPE.toString()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()));
    }

    @Test
    public void getNonExistingAlert() throws Exception {
        // Get the alert
        restAlertMockMvc.perform(get("/api/alerts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAlert() throws Exception {
        // Initialize the database
        alertRepository.save(alert);

        int databaseSizeBeforeUpdate = alertRepository.findAll().size();

        // Update the alert
        Alert updatedAlert = alertRepository.findById(alert.getId()).get();
        updatedAlert
            .deviceId(UPDATED_DEVICE_ID)
            .ownerId(UPDATED_OWNER_ID)
            .alertType(UPDATED_ALERT_TYPE)
            .timestamp(UPDATED_TIMESTAMP);
        AlertDTO alertDTO = alertMapper.toDto(updatedAlert);

        restAlertMockMvc.perform(put("/api/alerts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertDTO)))
            .andExpect(status().isOk());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
        Alert testAlert = alertList.get(alertList.size() - 1);
        assertThat(testAlert.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testAlert.getOwnerId()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testAlert.getAlertType()).isEqualTo(UPDATED_ALERT_TYPE);
        assertThat(testAlert.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    public void updateNonExistingAlert() throws Exception {
        int databaseSizeBeforeUpdate = alertRepository.findAll().size();

        // Create the Alert
        AlertDTO alertDTO = alertMapper.toDto(alert);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertMockMvc.perform(put("/api/alerts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteAlert() throws Exception {
        // Initialize the database
        alertRepository.save(alert);

        int databaseSizeBeforeDelete = alertRepository.findAll().size();

        // Delete the alert
        restAlertMockMvc.perform(delete("/api/alerts/{id}", alert.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Alert> alertList = alertRepository.findAll();
        assertThat(alertList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alert.class);
        Alert alert1 = new Alert();
        alert1.setId("id1");
        Alert alert2 = new Alert();
        alert2.setId(alert1.getId());
        assertThat(alert1).isEqualTo(alert2);
        alert2.setId("id2");
        assertThat(alert1).isNotEqualTo(alert2);
        alert1.setId(null);
        assertThat(alert1).isNotEqualTo(alert2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlertDTO.class);
        AlertDTO alertDTO1 = new AlertDTO();
        alertDTO1.setId("id1");
        AlertDTO alertDTO2 = new AlertDTO();
        assertThat(alertDTO1).isNotEqualTo(alertDTO2);
        alertDTO2.setId(alertDTO1.getId());
        assertThat(alertDTO1).isEqualTo(alertDTO2);
        alertDTO2.setId("id2");
        assertThat(alertDTO1).isNotEqualTo(alertDTO2);
        alertDTO1.setId(null);
        assertThat(alertDTO1).isNotEqualTo(alertDTO2);
    }
}
