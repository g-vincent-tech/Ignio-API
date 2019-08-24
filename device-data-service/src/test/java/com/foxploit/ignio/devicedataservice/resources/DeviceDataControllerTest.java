package com.foxploit.ignio.devicedataservice.resources;

import com.foxploit.ignio.devicedataservice.models.DeviceData;
import com.foxploit.ignio.devicedataservice.models.SensorData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DeviceDataController.class)
public class DeviceDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceDataService deviceDataService;

    @Test
    public void getDeviceData() throws Exception {

        given(deviceDataService.getDeviceData(anyString())).willReturn(java.util.Optional.of(new DeviceData("NODEIGNIOF101", new SensorData[3], anyLong())));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/device/data/5d5ec615dceb96160ce73d31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("deviceId").value("NODEIGNIOF101"))
                .andExpect(jsonPath("sensorData").isArray())
                .andExpect(jsonPath("epoch").isNumber());
    }

//    @Test
//    public void getAllDeviceData() throws Exception {
//
//        given(deviceDataService.getAllDeviceData(anyString())).willReturn(new DeviceDataDto(anyString(), new ArrayList<DeviceData>(), new ArrayList<SensorData>()));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/device/data/all/NODEIGNIOF103"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("deviceId").isString())
//                .andExpect(jsonPath("deviceData").isArray())
//                .andExpect(jsonPath("sensorData").isArray());
//
//    }

//    @Test
//    public void saveDeviceData() {
//    }
//
//    @Test
//    public void deleteDeviceData() {
//    }

}