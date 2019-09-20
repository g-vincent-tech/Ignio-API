package com.foxploit.ignio.devicedataservice.web.rest;

import com.foxploit.ignio.devicedataservice.DevicedataserviceApp;
import com.foxploit.ignio.devicedataservice.domain.SensorData;
import com.foxploit.ignio.devicedataservice.repository.SensorDataRepository;
import com.foxploit.ignio.devicedataservice.service.SensorDataService;
import com.foxploit.ignio.devicedataservice.service.dto.SensorDataDTO;
import com.foxploit.ignio.devicedataservice.service.mapper.SensorDataMapper;
import com.foxploit.ignio.devicedataservice.web.rest.errors.ExceptionTranslator;

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


import java.util.List;

import static com.foxploit.ignio.devicedataservice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link SensorDataResource} REST controller.
 */
@SpringBootTest(classes = DevicedataserviceApp.class)
public class SensorDataResourceIT {

    private static final Float DEFAULT_TEMPERATURE = 1F;
    private static final Float UPDATED_TEMPERATURE = 2F;

    private static final Float DEFAULT_CO_PPM = 1F;
    private static final Float UPDATED_CO_PPM = 2F;

    private static final Float DEFAULT_LP_GAS_PPM = 1F;
    private static final Float UPDATED_LP_GAS_PPM = 2F;

    private static final Float DEFAULT_PARTICLE_PPM = 1F;
    private static final Float UPDATED_PARTICLE_PPM = 2F;

    private static final String DEFAULT_EPOCH = "AAAAAAAAAA";
    private static final String UPDATED_EPOCH = "BBBBBBBBBB";

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private SensorDataMapper sensorDataMapper;

    @Autowired
    private SensorDataService sensorDataService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restSensorDataMockMvc;

    private SensorData sensorData;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SensorDataResource sensorDataResource = new SensorDataResource(sensorDataService);
        this.restSensorDataMockMvc = MockMvcBuilders.standaloneSetup(sensorDataResource)
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
    public static SensorData createEntity() {
        SensorData sensorData = new SensorData()
            .temperature(DEFAULT_TEMPERATURE)
            .co_ppm(DEFAULT_CO_PPM)
            .lp_gas_ppm(DEFAULT_LP_GAS_PPM)
            .particle_ppm(DEFAULT_PARTICLE_PPM)
            .epoch(DEFAULT_EPOCH);
        return sensorData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SensorData createUpdatedEntity() {
        SensorData sensorData = new SensorData()
            .temperature(UPDATED_TEMPERATURE)
            .co_ppm(UPDATED_CO_PPM)
            .lp_gas_ppm(UPDATED_LP_GAS_PPM)
            .particle_ppm(UPDATED_PARTICLE_PPM)
            .epoch(UPDATED_EPOCH);
        return sensorData;
    }

    @BeforeEach
    public void initTest() {
        sensorDataRepository.deleteAll();
        sensorData = createEntity();
    }

    @Test
    public void createSensorData() throws Exception {
        int databaseSizeBeforeCreate = sensorDataRepository.findAll().size();

        // Create the SensorData
        SensorDataDTO sensorDataDTO = sensorDataMapper.toDto(sensorData);
        restSensorDataMockMvc.perform(post("/api/sensor-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensorDataDTO)))
            .andExpect(status().isCreated());

        // Validate the SensorData in the database
        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        assertThat(sensorDataList).hasSize(databaseSizeBeforeCreate + 1);
        SensorData testSensorData = sensorDataList.get(sensorDataList.size() - 1);
        assertThat(testSensorData.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testSensorData.getCo_ppm()).isEqualTo(DEFAULT_CO_PPM);
        assertThat(testSensorData.getLp_gas_ppm()).isEqualTo(DEFAULT_LP_GAS_PPM);
        assertThat(testSensorData.getParticle_ppm()).isEqualTo(DEFAULT_PARTICLE_PPM);
        assertThat(testSensorData.getEpoch()).isEqualTo(DEFAULT_EPOCH);
    }

    @Test
    public void createSensorDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sensorDataRepository.findAll().size();

        // Create the SensorData with an existing ID
        sensorData.setId("existing_id");
        SensorDataDTO sensorDataDTO = sensorDataMapper.toDto(sensorData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSensorDataMockMvc.perform(post("/api/sensor-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensorDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SensorData in the database
        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        assertThat(sensorDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkTemperatureIsRequired() throws Exception {
        int databaseSizeBeforeTest = sensorDataRepository.findAll().size();
        // set the field null
        sensorData.setTemperature(null);

        // Create the SensorData, which fails.
        SensorDataDTO sensorDataDTO = sensorDataMapper.toDto(sensorData);

        restSensorDataMockMvc.perform(post("/api/sensor-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensorDataDTO)))
            .andExpect(status().isBadRequest());

        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        assertThat(sensorDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCo_ppmIsRequired() throws Exception {
        int databaseSizeBeforeTest = sensorDataRepository.findAll().size();
        // set the field null
        sensorData.setCo_ppm(null);

        // Create the SensorData, which fails.
        SensorDataDTO sensorDataDTO = sensorDataMapper.toDto(sensorData);

        restSensorDataMockMvc.perform(post("/api/sensor-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensorDataDTO)))
            .andExpect(status().isBadRequest());

        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        assertThat(sensorDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLp_gas_ppmIsRequired() throws Exception {
        int databaseSizeBeforeTest = sensorDataRepository.findAll().size();
        // set the field null
        sensorData.setLp_gas_ppm(null);

        // Create the SensorData, which fails.
        SensorDataDTO sensorDataDTO = sensorDataMapper.toDto(sensorData);

        restSensorDataMockMvc.perform(post("/api/sensor-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensorDataDTO)))
            .andExpect(status().isBadRequest());

        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        assertThat(sensorDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkParticle_ppmIsRequired() throws Exception {
        int databaseSizeBeforeTest = sensorDataRepository.findAll().size();
        // set the field null
        sensorData.setParticle_ppm(null);

        // Create the SensorData, which fails.
        SensorDataDTO sensorDataDTO = sensorDataMapper.toDto(sensorData);

        restSensorDataMockMvc.perform(post("/api/sensor-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensorDataDTO)))
            .andExpect(status().isBadRequest());

        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        assertThat(sensorDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEpochIsRequired() throws Exception {
        int databaseSizeBeforeTest = sensorDataRepository.findAll().size();
        // set the field null
        sensorData.setEpoch(null);

        // Create the SensorData, which fails.
        SensorDataDTO sensorDataDTO = sensorDataMapper.toDto(sensorData);

        restSensorDataMockMvc.perform(post("/api/sensor-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensorDataDTO)))
            .andExpect(status().isBadRequest());

        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        assertThat(sensorDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSensorData() throws Exception {
        // Initialize the database
        sensorDataRepository.save(sensorData);

        // Get all the sensorDataList
        restSensorDataMockMvc.perform(get("/api/sensor-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sensorData.getId())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].co_ppm").value(hasItem(DEFAULT_CO_PPM.doubleValue())))
            .andExpect(jsonPath("$.[*].lp_gas_ppm").value(hasItem(DEFAULT_LP_GAS_PPM.doubleValue())))
            .andExpect(jsonPath("$.[*].particle_ppm").value(hasItem(DEFAULT_PARTICLE_PPM.doubleValue())))
            .andExpect(jsonPath("$.[*].epoch").value(hasItem(DEFAULT_EPOCH.toString())));
    }
    
    @Test
    public void getSensorData() throws Exception {
        // Initialize the database
        sensorDataRepository.save(sensorData);

        // Get the sensorData
        restSensorDataMockMvc.perform(get("/api/sensor-data/{id}", sensorData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sensorData.getId()))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.co_ppm").value(DEFAULT_CO_PPM.doubleValue()))
            .andExpect(jsonPath("$.lp_gas_ppm").value(DEFAULT_LP_GAS_PPM.doubleValue()))
            .andExpect(jsonPath("$.particle_ppm").value(DEFAULT_PARTICLE_PPM.doubleValue()))
            .andExpect(jsonPath("$.epoch").value(DEFAULT_EPOCH.toString()));
    }

    @Test
    public void getNonExistingSensorData() throws Exception {
        // Get the sensorData
        restSensorDataMockMvc.perform(get("/api/sensor-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSensorData() throws Exception {
        // Initialize the database
        sensorDataRepository.save(sensorData);

        int databaseSizeBeforeUpdate = sensorDataRepository.findAll().size();

        // Update the sensorData
        SensorData updatedSensorData = sensorDataRepository.findById(sensorData.getId()).get();
        updatedSensorData
            .temperature(UPDATED_TEMPERATURE)
            .co_ppm(UPDATED_CO_PPM)
            .lp_gas_ppm(UPDATED_LP_GAS_PPM)
            .particle_ppm(UPDATED_PARTICLE_PPM)
            .epoch(UPDATED_EPOCH);
        SensorDataDTO sensorDataDTO = sensorDataMapper.toDto(updatedSensorData);

        restSensorDataMockMvc.perform(put("/api/sensor-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensorDataDTO)))
            .andExpect(status().isOk());

        // Validate the SensorData in the database
        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        assertThat(sensorDataList).hasSize(databaseSizeBeforeUpdate);
        SensorData testSensorData = sensorDataList.get(sensorDataList.size() - 1);
        assertThat(testSensorData.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testSensorData.getCo_ppm()).isEqualTo(UPDATED_CO_PPM);
        assertThat(testSensorData.getLp_gas_ppm()).isEqualTo(UPDATED_LP_GAS_PPM);
        assertThat(testSensorData.getParticle_ppm()).isEqualTo(UPDATED_PARTICLE_PPM);
        assertThat(testSensorData.getEpoch()).isEqualTo(UPDATED_EPOCH);
    }

    @Test
    public void updateNonExistingSensorData() throws Exception {
        int databaseSizeBeforeUpdate = sensorDataRepository.findAll().size();

        // Create the SensorData
        SensorDataDTO sensorDataDTO = sensorDataMapper.toDto(sensorData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSensorDataMockMvc.perform(put("/api/sensor-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensorDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SensorData in the database
        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        assertThat(sensorDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSensorData() throws Exception {
        // Initialize the database
        sensorDataRepository.save(sensorData);

        int databaseSizeBeforeDelete = sensorDataRepository.findAll().size();

        // Delete the sensorData
        restSensorDataMockMvc.perform(delete("/api/sensor-data/{id}", sensorData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        assertThat(sensorDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SensorData.class);
        SensorData sensorData1 = new SensorData();
        sensorData1.setId("id1");
        SensorData sensorData2 = new SensorData();
        sensorData2.setId(sensorData1.getId());
        assertThat(sensorData1).isEqualTo(sensorData2);
        sensorData2.setId("id2");
        assertThat(sensorData1).isNotEqualTo(sensorData2);
        sensorData1.setId(null);
        assertThat(sensorData1).isNotEqualTo(sensorData2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SensorDataDTO.class);
        SensorDataDTO sensorDataDTO1 = new SensorDataDTO();
        sensorDataDTO1.setId("id1");
        SensorDataDTO sensorDataDTO2 = new SensorDataDTO();
        assertThat(sensorDataDTO1).isNotEqualTo(sensorDataDTO2);
        sensorDataDTO2.setId(sensorDataDTO1.getId());
        assertThat(sensorDataDTO1).isEqualTo(sensorDataDTO2);
        sensorDataDTO2.setId("id2");
        assertThat(sensorDataDTO1).isNotEqualTo(sensorDataDTO2);
        sensorDataDTO1.setId(null);
        assertThat(sensorDataDTO1).isNotEqualTo(sensorDataDTO2);
    }
}
