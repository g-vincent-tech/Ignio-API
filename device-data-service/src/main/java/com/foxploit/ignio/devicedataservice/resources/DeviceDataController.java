package com.foxploit.ignio.devicedataservice.resources;

import com.foxploit.ignio.devicedataservice.domain.DeviceData;
import com.foxploit.ignio.devicedataservice.domain.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/device")
public class DeviceDataController {

    @Autowired
    private DeviceDataService deviceDataService;

    public DeviceDataController(DeviceDataService deviceDataService) {
        this.deviceDataService = deviceDataService;
    }

    // Get one device data response
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
    public Optional<DeviceData> getDeviceData(@PathVariable("id") String id) {
        return deviceDataService.getDeviceData(id);
    }

    // Get all device data response from a device
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/data/all/{deviceId}", method = RequestMethod.GET)
    public DeviceDataDto getAllDeviceData(@PathVariable String deviceId) {
        List<DeviceData> allDeviceDataList = deviceDataService.getAllDeviceData(deviceId);
        List<SensorData> sensorDataList = new ArrayList<>();
        allDeviceDataList.forEach(deviceData -> {
            sensorDataList.addAll(Arrays.asList(deviceData.getSensorData()));
        });

        return new DeviceDataDto(deviceId, sensorDataList);
    }

    // Save device data response
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    public DeviceData saveDeviceData(@RequestBody DeviceData deviceData) {
        return deviceDataService.saveDeviceData(deviceData);
    }

    // Delete device data response
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/data/{id}", method = RequestMethod.DELETE)
    public void deleteDeviceData(@PathVariable String id) {
        deviceDataService.deleteDeviceData(id);
    }

}
