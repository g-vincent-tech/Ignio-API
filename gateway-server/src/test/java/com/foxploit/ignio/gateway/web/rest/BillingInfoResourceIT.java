package com.foxploit.ignio.gateway.web.rest;

import com.foxploit.ignio.gateway.IgniogatewayApp;
import com.foxploit.ignio.gateway.domain.BillingInfo;
import com.foxploit.ignio.gateway.repository.BillingInfoRepository;
import com.foxploit.ignio.gateway.service.BillingInfoService;
import com.foxploit.ignio.gateway.service.dto.BillingInfoDTO;
import com.foxploit.ignio.gateway.service.mapper.BillingInfoMapper;
import com.foxploit.ignio.gateway.web.rest.errors.ExceptionTranslator;

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


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.foxploit.ignio.gateway.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BillingInfoResource} REST controller.
 */
@SpringBootTest(classes = IgniogatewayApp.class)
public class BillingInfoResourceIT {

    private static final Set<String> DEFAULT_IGNIOS = new HashSet<>(Arrays.asList("NODEIGNIOF105", "NODEIGNIOF106"));
    private static final Set<String> UPDATED_IGNIOS = new HashSet<>(Arrays.asList("NODEIGNIOF105", "NODEIGNIOF106", "NODEIGNIOF107"));

    private static final Long DEFAULT_CREDIT_CARD_NUMBER = 1L;
    private static final Long UPDATED_CREDIT_CARD_NUMBER = 2L;

    private static final String DEFAULT_CREDIT_CARD_TYPE = "VISA";
    private static final String UPDATED_CREDIT_CARD_TYPE = "MASTER CARD";

    private static final String DEFAULT_CVV_2 = "051";
    private static final String UPDATED_CVV_2 = "052";

    private static final String DEFAULT_EXPIRES_ON = "02/21";
    private static final String UPDATED_EXPIRES_ON = "02/22";

    private static final String DEFAULT_BILLING_ADDRESS = "\"Valley of Death\", Serpentine Road, Colombo 08";
    private static final String UPDATED_BILLING_ADDRESS = "\"Valley of Death\", Serpentine Road, Colombo 10";

    private static final String DEFAULT_CITY = "Colombo";
    private static final String UPDATED_CITY = "Galle";

    private static final String DEFAULT_COUNTRY = "Sri Lanka";
    private static final String UPDATED_COUNTRY = "Italy";

    private static final Integer DEFAULT_POSTAL_CODE = 11200;
    private static final Integer UPDATED_POSTAL_CODE = 11220;

    @Autowired
    private BillingInfoRepository billingInfoRepository;

    @Autowired
    private BillingInfoMapper billingInfoMapper;

    @Autowired
    private BillingInfoService billingInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restBillingInfoMockMvc;

    private BillingInfo billingInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BillingInfoResource billingInfoResource = new BillingInfoResource(billingInfoService);
        this.restBillingInfoMockMvc = MockMvcBuilders.standaloneSetup(billingInfoResource)
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
    public static BillingInfo createEntity() {
        return new BillingInfo()
            .ignios(DEFAULT_IGNIOS)
            .creditCardNumber(DEFAULT_CREDIT_CARD_NUMBER)
            .creditCardType(DEFAULT_CREDIT_CARD_TYPE)
            .cvv2(DEFAULT_CVV_2)
            .expiresOn(DEFAULT_EXPIRES_ON)
            .billingAddress(DEFAULT_BILLING_ADDRESS)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY)
            .postalCode(DEFAULT_POSTAL_CODE);
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillingInfo createUpdatedEntity() {
        return new BillingInfo()
            .ignios(UPDATED_IGNIOS)
            .creditCardNumber(UPDATED_CREDIT_CARD_NUMBER)
            .creditCardType(UPDATED_CREDIT_CARD_TYPE)
            .cvv2(UPDATED_CVV_2)
            .expiresOn(UPDATED_EXPIRES_ON)
            .billingAddress(UPDATED_BILLING_ADDRESS)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .postalCode(UPDATED_POSTAL_CODE);
    }

    @BeforeEach
    public void initTest() {
        billingInfoRepository.deleteAll();
        billingInfo = createEntity();
    }

    @Test
    public void createBillingInfo() throws Exception {
        int databaseSizeBeforeCreate = billingInfoRepository.findAll().size();

        // Create the BillingInfo
        BillingInfoDTO billingInfoDTO = billingInfoMapper.toDto(billingInfo);
        restBillingInfoMockMvc.perform(post("/api/billing-info")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the BillingInfo in the database
        List<BillingInfo> billingInfoList = billingInfoRepository.findAll();
        assertThat(billingInfoList).hasSize(databaseSizeBeforeCreate + 1);
        BillingInfo testBillingInfo = billingInfoList.get(billingInfoList.size() - 1);
        assertThat(testBillingInfo.getIgnios()).isEqualTo(DEFAULT_IGNIOS);
        assertThat(testBillingInfo.getCreditCardNumber()).isEqualTo(DEFAULT_CREDIT_CARD_NUMBER);
        assertThat(testBillingInfo.getCreditCardType()).isEqualTo(DEFAULT_CREDIT_CARD_TYPE);
        assertThat(testBillingInfo.getCvv2()).isEqualTo(DEFAULT_CVV_2);
        assertThat(testBillingInfo.getExpiresOn()).isEqualTo(DEFAULT_EXPIRES_ON);
        assertThat(testBillingInfo.getBillingAddress()).isEqualTo(DEFAULT_BILLING_ADDRESS);
        assertThat(testBillingInfo.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testBillingInfo.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testBillingInfo.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
    }

    @Test
    public void createBillingInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billingInfoRepository.findAll().size();

        // Create the BillingInfo with an existing ID
        billingInfo.setId("existing_id");
        BillingInfoDTO billingInfoDTO = billingInfoMapper.toDto(billingInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillingInfoMockMvc.perform(post("/api/billing-info")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BillingInfo in the database
        List<BillingInfo> billingInfoList = billingInfoRepository.findAll();
        assertThat(billingInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllBillingInfos() throws Exception {
        // Initialize the database
        billingInfoRepository.save(billingInfo);

        // Get all the billingInfoList
        restBillingInfoMockMvc.perform(get("/api/billing-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingInfo.getId())))
            .andExpect(jsonPath("$.[*].ignios").isArray())
            .andExpect(jsonPath("$.[*].creditCardNumber").value(hasItem(DEFAULT_CREDIT_CARD_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].creditCardType").value(hasItem(DEFAULT_CREDIT_CARD_TYPE)))
            .andExpect(jsonPath("$.[*].cvv2").value(hasItem(DEFAULT_CVV_2)))
            .andExpect(jsonPath("$.[*].expiresOn").value(hasItem(DEFAULT_EXPIRES_ON)))
            .andExpect(jsonPath("$.[*].billingAddress").value(hasItem(DEFAULT_BILLING_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)));
    }
    
    @Test
    public void getBillingInfo() throws Exception {
        // Initialize the database
        billingInfoRepository.save(billingInfo);

        // Get the billingInfo
        restBillingInfoMockMvc.perform(get("/api/billing-info/{id}", billingInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(billingInfo.getId()))
            .andExpect(jsonPath("$.ignios").isArray())
            .andExpect(jsonPath("$.creditCardNumber").value(DEFAULT_CREDIT_CARD_NUMBER.intValue()))
            .andExpect(jsonPath("$.creditCardType").value(DEFAULT_CREDIT_CARD_TYPE))
            .andExpect(jsonPath("$.cvv2").value(DEFAULT_CVV_2))
            .andExpect(jsonPath("$.expiresOn").value(DEFAULT_EXPIRES_ON))
            .andExpect(jsonPath("$.billingAddress").value(DEFAULT_BILLING_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE));
    }

    @Test
    public void getNonExistingBillingInfo() throws Exception {
        // Get the billingInfo
        restBillingInfoMockMvc.perform(get("/api/billing-info/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBillingInfo() throws Exception {
        // Initialize the database
        billingInfoRepository.save(billingInfo);

        int databaseSizeBeforeUpdate = billingInfoRepository.findAll().size();

        // Update the billingInfo
        BillingInfo updatedBillingInfo = billingInfoRepository.findById(billingInfo.getId()).get();
        updatedBillingInfo
            .ignios(UPDATED_IGNIOS)
            .creditCardNumber(UPDATED_CREDIT_CARD_NUMBER)
            .creditCardType(UPDATED_CREDIT_CARD_TYPE)
            .cvv2(UPDATED_CVV_2)
            .expiresOn(UPDATED_EXPIRES_ON)
            .billingAddress(UPDATED_BILLING_ADDRESS)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .postalCode(UPDATED_POSTAL_CODE);
        BillingInfoDTO billingInfoDTO = billingInfoMapper.toDto(updatedBillingInfo);

        restBillingInfoMockMvc.perform(put("/api/billing-info")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingInfoDTO)))
            .andExpect(status().isOk());

        // Validate the BillingInfo in the database
        List<BillingInfo> billingInfoList = billingInfoRepository.findAll();
        assertThat(billingInfoList).hasSize(databaseSizeBeforeUpdate);
        BillingInfo testBillingInfo = billingInfoList.get(billingInfoList.size() - 1);
        assertThat(testBillingInfo.getIgnios()).isEqualTo(UPDATED_IGNIOS);
        assertThat(testBillingInfo.getCreditCardNumber()).isEqualTo(UPDATED_CREDIT_CARD_NUMBER);
        assertThat(testBillingInfo.getCreditCardType()).isEqualTo(UPDATED_CREDIT_CARD_TYPE);
        assertThat(testBillingInfo.getCvv2()).isEqualTo(UPDATED_CVV_2);
        assertThat(testBillingInfo.getExpiresOn()).isEqualTo(UPDATED_EXPIRES_ON);
        assertThat(testBillingInfo.getBillingAddress()).isEqualTo(UPDATED_BILLING_ADDRESS);
        assertThat(testBillingInfo.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testBillingInfo.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testBillingInfo.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
    }

    @Test
    public void updateNonExistingBillingInfo() throws Exception {
        int databaseSizeBeforeUpdate = billingInfoRepository.findAll().size();

        // Create the BillingInfo
        BillingInfoDTO billingInfoDTO = billingInfoMapper.toDto(billingInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillingInfoMockMvc.perform(put("/api/billing-info")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BillingInfo in the database
        List<BillingInfo> billingInfoList = billingInfoRepository.findAll();
        assertThat(billingInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteBillingInfo() throws Exception {
        // Initialize the database
        billingInfoRepository.save(billingInfo);

        int databaseSizeBeforeDelete = billingInfoRepository.findAll().size();

        // Delete the billingInfo
        restBillingInfoMockMvc.perform(delete("/api/billing-info/{id}", billingInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BillingInfo> billingInfoList = billingInfoRepository.findAll();
        assertThat(billingInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillingInfo.class);
        BillingInfo billingInfo1 = new BillingInfo();
        billingInfo1.setId("id1");
        BillingInfo billingInfo2 = new BillingInfo();
        billingInfo2.setId(billingInfo1.getId());
        assertThat(billingInfo1).isEqualTo(billingInfo2);
        billingInfo2.setId("id2");
        assertThat(billingInfo1).isNotEqualTo(billingInfo2);
        billingInfo1.setId(null);
        assertThat(billingInfo1).isNotEqualTo(billingInfo2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillingInfoDTO.class);
        BillingInfoDTO billingInfoDTO1 = new BillingInfoDTO();
        billingInfoDTO1.setId("id1");
        BillingInfoDTO billingInfoDTO2 = new BillingInfoDTO();
        assertThat(billingInfoDTO1).isNotEqualTo(billingInfoDTO2);
        billingInfoDTO2.setId(billingInfoDTO1.getId());
        assertThat(billingInfoDTO1).isEqualTo(billingInfoDTO2);
        billingInfoDTO2.setId("id2");
        assertThat(billingInfoDTO1).isNotEqualTo(billingInfoDTO2);
        billingInfoDTO1.setId(null);
        assertThat(billingInfoDTO1).isNotEqualTo(billingInfoDTO2);
    }
}
