package com.foxploit.ignio.devicedataservice;

import com.foxploit.ignio.devicedataservice.domain.DeviceData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private WebApplicationContext context;

    private WebClient webClient;

//    @Before
//    public void setUp() throws Exception {
//        webClient = WebClient.builder().baseUrl("/api/").build();
//    }

    @Test
    public void getDeviceData_returnsDeviceData() throws Exception {

        // Arrange

        // Act
        ResponseEntity<DeviceData> deviceDataResponseEntity = testRestTemplate.getForEntity("/api/device/data/5d62cff41180184a00c0159a", DeviceData.class);

        // Assert
        assert deviceDataResponseEntity.getStatusCode().is2xxSuccessful();
        assert Objects.requireNonNull(deviceDataResponseEntity.getBody()).getDeviceId().equals("NODEIGNIOF101");
        assert deviceDataResponseEntity.getBody().getSensorData().length != 0;

    }

}
