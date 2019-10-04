package com.foxploit.ignio.devicedataservice.web.rest;

import com.foxploit.ignio.devicedataservice.DevicedataserviceApp;
import com.foxploit.ignio.devicedataservice.domain.DeviceData;
import com.foxploit.ignio.devicedataservice.repository.DeviceDataRepository;
import com.foxploit.ignio.devicedataservice.service.DeviceDataService;
import com.foxploit.ignio.devicedataservice.service.dto.DeviceDataDTO;
import com.foxploit.ignio.devicedataservice.service.mapper.DeviceDataMapper;
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
 * Integration tests for the {@link DeviceDataResource} REST controller.
 */
@SpringBootTest(classes = DevicedataserviceApp.class)
public class DeviceDataResourceIT {

    private static final String DEFAULT_DEVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EPOCH = "AAAAAAAAAA";
    private static final String UPDATED_EPOCH = "BBBBBBBBBB";

    @Autowired
    private DeviceDataRepository deviceDataRepository;

    @Autowired
    private DeviceDataMapper deviceDataMapper;

    @Autowired
    private DeviceDataService deviceDataService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restDeviceDataMockMvc;

    private DeviceData deviceData;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeviceDataResource deviceDataResource = new DeviceDataResource(deviceDataService);
        this.restDeviceDataMockMvc = MockMvcBuilders.standaloneSetup(deviceDataResource)
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
    public static DeviceData createEntity() {
        DeviceData deviceData = new DeviceData()
            .deviceId(DEFAULT_DEVICE_ID)
            .epoch(DEFAULT_EPOCH);
        return deviceData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceData createUpdatedEntity() {
        DeviceData deviceData = new DeviceData()
            .deviceId(UPDATED_DEVICE_ID)
            .epoch(UPDATED_EPOCH);
        return deviceData;
    }

    @BeforeEach
    public void initTest() {
        deviceDataRepository.deleteAll();
        deviceData = createEntity();
    }

    @Test
    public void createDeviceData() throws Exception {
        int databaseSizeBeforeCreate = deviceDataRepository.findAll().size();

        // Create the DeviceData
        DeviceDataDTO deviceDataDTO = deviceDataMapper.toDto(deviceData);
        restDeviceDataMockMvc.perform(post("/api/device-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDataDTO)))
            .andExpect(status().isCreated());

        // Validate the DeviceData in the database
        List<DeviceData> deviceDataList = deviceDataRepository.findAll();
        assertThat(deviceDataList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceData testDeviceData = deviceDataList.get(deviceDataList.size() - 1);
        assertThat(testDeviceData.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testDeviceData.getEpoch()).isEqualTo(DEFAULT_EPOCH);
    }

    @Test
    public void createDeviceDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceDataRepository.findAll().size();

        // Create the DeviceData with an existing ID
        deviceData.setId("existing_id");
        DeviceDataDTO deviceDataDTO = deviceDataMapper.toDto(deviceData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceDataMockMvc.perform(post("/api/device-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceData in the database
        List<DeviceData> deviceDataList = deviceDataRepository.findAll();
        assertThat(deviceDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkDeviceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceDataRepository.findAll().size();
        // set the field null
        deviceData.setDeviceId(null);

        // Create the DeviceData, which fails.
        DeviceDataDTO deviceDataDTO = deviceDataMapper.toDto(deviceData);

        restDeviceDataMockMvc.perform(post("/api/device-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDataDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceData> deviceDataList = deviceDataRepository.findAll();
        assertThat(deviceDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEpochIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceDataRepository.findAll().size();
        // set the field null
        deviceData.setEpoch(null);

        // Create the DeviceData, which fails.
        DeviceDataDTO deviceDataDTO = deviceDataMapper.toDto(deviceData);

        restDeviceDataMockMvc.perform(post("/api/device-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDataDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceData> deviceDataList = deviceDataRepository.findAll();
        assertThat(deviceDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDeviceData() throws Exception {
        // Initialize the database
        deviceDataRepository.save(deviceData);

        // Get all the deviceDataList
        restDeviceDataMockMvc.perform(get("/api/device-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceData.getId())))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID.toString())))
            .andExpect(jsonPath("$.[*].epoch").value(hasItem(DEFAULT_EPOCH.toString())));
    }
    
    @Test
    public void getDeviceData() throws Exception {
        // Initialize the database
        deviceDataRepository.save(deviceData);

        // Get the deviceData
        restDeviceDataMockMvc.perform(get("/api/device-data/{id}", deviceData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deviceData.getId()))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID.toString()))
            .andExpect(jsonPath("$.epoch").value(DEFAULT_EPOCH.toString()));
    }

    @Test
    public void getNonExistingDeviceData() throws Exception {
        // Get the deviceData
        restDeviceDataMockMvc.perform(get("/api/device-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDeviceData() throws Exception {
        // Initialize the database
        deviceDataRepository.save(deviceData);

        int databaseSizeBeforeUpdate = deviceDataRepository.findAll().size();

        // Update the deviceData
        DeviceData updatedDeviceData = deviceDataRepository.findById(deviceData.getId()).get();
        updatedDeviceData
            .deviceId(UPDATED_DEVICE_ID)
            .epoch(UPDATED_EPOCH);
        DeviceDataDTO deviceDataDTO = deviceDataMapper.toDto(updatedDeviceData);

        restDeviceDataMockMvc.perform(put("/api/device-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDataDTO)))
            .andExpect(status().isOk());

        // Validate the DeviceData in the database
        List<DeviceData> deviceDataList = deviceDataRepository.findAll();
        assertThat(deviceDataList).hasSize(databaseSizeBeforeUpdate);
        DeviceData testDeviceData = deviceDataList.get(deviceDataList.size() - 1);
        assertThat(testDeviceData.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testDeviceData.getEpoch()).isEqualTo(UPDATED_EPOCH);
    }

    @Test
    public void updateNonExistingDeviceData() throws Exception {
        int databaseSizeBeforeUpdate = deviceDataRepository.findAll().size();

        // Create the DeviceData
        DeviceDataDTO deviceDataDTO = deviceDataMapper.toDto(deviceData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceDataMockMvc.perform(put("/api/device-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceData in the database
        List<DeviceData> deviceDataList = deviceDataRepository.findAll();
        assertThat(deviceDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteDeviceData() throws Exception {
        // Initialize the database
        deviceDataRepository.save(deviceData);

        int databaseSizeBeforeDelete = deviceDataRepository.findAll().size();

        // Delete the deviceData
        restDeviceDataMockMvc.perform(delete("/api/device-data/{id}", deviceData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeviceData> deviceDataList = deviceDataRepository.findAll();
        assertThat(deviceDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceData.class);
        DeviceData deviceData1 = new DeviceData();
        deviceData1.setId("id1");
        DeviceData deviceData2 = new DeviceData();
        deviceData2.setId(deviceData1.getId());
        assertThat(deviceData1).isEqualTo(deviceData2);
        deviceData2.setId("id2");
        assertThat(deviceData1).isNotEqualTo(deviceData2);
        deviceData1.setId(null);
        assertThat(deviceData1).isNotEqualTo(deviceData2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceDataDTO.class);
        DeviceDataDTO deviceDataDTO1 = new DeviceDataDTO();
        deviceDataDTO1.setId("id1");
        DeviceDataDTO deviceDataDTO2 = new DeviceDataDTO();
        assertThat(deviceDataDTO1).isNotEqualTo(deviceDataDTO2);
        deviceDataDTO2.setId(deviceDataDTO1.getId());
        assertThat(deviceDataDTO1).isEqualTo(deviceDataDTO2);
        deviceDataDTO2.setId("id2");
        assertThat(deviceDataDTO1).isNotEqualTo(deviceDataDTO2);
        deviceDataDTO1.setId(null);
        assertThat(deviceDataDTO1).isNotEqualTo(deviceDataDTO2);
    }
}
