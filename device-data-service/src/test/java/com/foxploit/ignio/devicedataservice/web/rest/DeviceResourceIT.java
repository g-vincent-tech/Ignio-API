package com.foxploit.ignio.devicedataservice.web.rest;

import com.foxploit.ignio.devicedataservice.DevicedataserviceApp;
import com.foxploit.ignio.devicedataservice.domain.Device;
import com.foxploit.ignio.devicedataservice.repository.DeviceRepository;
import com.foxploit.ignio.devicedataservice.service.DeviceService;
import com.foxploit.ignio.devicedataservice.service.dto.DeviceDTO;
import com.foxploit.ignio.devicedataservice.service.mapper.DeviceMapper;
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


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.foxploit.ignio.devicedataservice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link DeviceResource} REST controller.
 */
@SpringBootTest(classes = DevicedataserviceApp.class)
public class DeviceResourceIT {

    private static final String DEFAULT_DEVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER_ID = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PURCHASED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PURCHASED = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restDeviceMockMvc;

    private Device device;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeviceResource deviceResource = new DeviceResource(deviceService);
        this.restDeviceMockMvc = MockMvcBuilders.standaloneSetup(deviceResource)
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
    public static Device createEntity() {
        Device device = new Device()
            .deviceId(DEFAULT_DEVICE_ID)
            .ownerId(DEFAULT_OWNER_ID)
            .created(DEFAULT_CREATED)
            .purchased(DEFAULT_PURCHASED);
        return device;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Device createUpdatedEntity() {
        Device device = new Device()
            .deviceId(UPDATED_DEVICE_ID)
            .ownerId(UPDATED_OWNER_ID)
            .created(UPDATED_CREATED)
            .purchased(UPDATED_PURCHASED);
        return device;
    }

    @BeforeEach
    public void initTest() {
        deviceRepository.deleteAll();
        device = createEntity();
    }

    @Test
    public void createDevice() throws Exception {
        int databaseSizeBeforeCreate = deviceRepository.findAll().size();

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);
        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isCreated());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeCreate + 1);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testDevice.getOwnerId()).isEqualTo(DEFAULT_OWNER_ID);
        assertThat(testDevice.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testDevice.getPurchased()).isEqualTo(DEFAULT_PURCHASED);
    }

    @Test
    public void createDeviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceRepository.findAll().size();

        // Create the Device with an existing ID
        device.setId("existing_id");
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkDeviceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceRepository.findAll().size();
        // set the field null
        device.setDeviceId(null);

        // Create the Device, which fails.
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isBadRequest());

        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDevices() throws Exception {
        // Initialize the database
        deviceRepository.save(device);

        // Get all the deviceList
        restDeviceMockMvc.perform(get("/api/devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(device.getId())))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID.toString())))
            .andExpect(jsonPath("$.[*].ownerId").value(hasItem(DEFAULT_OWNER_ID.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].purchased").value(hasItem(DEFAULT_PURCHASED.toString())));
    }
    
    @Test
    public void getDevice() throws Exception {
        // Initialize the database
        deviceRepository.save(device);

        // Get the device
        restDeviceMockMvc.perform(get("/api/devices/{id}", device.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(device.getId()))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID.toString()))
            .andExpect(jsonPath("$.ownerId").value(DEFAULT_OWNER_ID.toString()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.purchased").value(DEFAULT_PURCHASED.toString()));
    }

    @Test
    public void getNonExistingDevice() throws Exception {
        // Get the device
        restDeviceMockMvc.perform(get("/api/devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDevice() throws Exception {
        // Initialize the database
        deviceRepository.save(device);

        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Update the device
        Device updatedDevice = deviceRepository.findById(device.getId()).get();
        updatedDevice
            .deviceId(UPDATED_DEVICE_ID)
            .ownerId(UPDATED_OWNER_ID)
            .created(UPDATED_CREATED)
            .purchased(UPDATED_PURCHASED);
        DeviceDTO deviceDTO = deviceMapper.toDto(updatedDevice);

        restDeviceMockMvc.perform(put("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isOk());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testDevice.getOwnerId()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testDevice.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testDevice.getPurchased()).isEqualTo(UPDATED_PURCHASED);
    }

    @Test
    public void updateNonExistingDevice() throws Exception {
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceMockMvc.perform(put("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteDevice() throws Exception {
        // Initialize the database
        deviceRepository.save(device);

        int databaseSizeBeforeDelete = deviceRepository.findAll().size();

        // Delete the device
        restDeviceMockMvc.perform(delete("/api/devices/{id}", device.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Device.class);
        Device device1 = new Device();
        device1.setId("id1");
        Device device2 = new Device();
        device2.setId(device1.getId());
        assertThat(device1).isEqualTo(device2);
        device2.setId("id2");
        assertThat(device1).isNotEqualTo(device2);
        device1.setId(null);
        assertThat(device1).isNotEqualTo(device2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceDTO.class);
        DeviceDTO deviceDTO1 = new DeviceDTO();
        deviceDTO1.setId("id1");
        DeviceDTO deviceDTO2 = new DeviceDTO();
        assertThat(deviceDTO1).isNotEqualTo(deviceDTO2);
        deviceDTO2.setId(deviceDTO1.getId());
        assertThat(deviceDTO1).isEqualTo(deviceDTO2);
        deviceDTO2.setId("id2");
        assertThat(deviceDTO1).isNotEqualTo(deviceDTO2);
        deviceDTO1.setId(null);
        assertThat(deviceDTO1).isNotEqualTo(deviceDTO2);
    }
}
