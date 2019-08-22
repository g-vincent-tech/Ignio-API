package com.foxploit.ignio.devicedataservice.resources;

import com.foxploit.ignio.devicedataservice.models.DeviceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
    public Optional<DeviceData> getDeviceData(@PathVariable("id") String id) {
        return deviceDataService.verifyDeviceData(id);
    }

    // Get all device data response from a device
    @RequestMapping(value = "/data/all/{deviceId}", method = RequestMethod.GET)
    public List<DeviceData> getAllDeviceData(@PathVariable String deviceId) {
        return deviceDataService.getAllDeviceData(deviceId);
    }

    // Save device data response
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    public DeviceData saveDeviceData(@RequestBody DeviceData deviceData) {
        return deviceDataService.saveDeviceData(deviceData);
    }

    // Delete device data response
    @RequestMapping(value = "/data/{id}", method = RequestMethod.DELETE)
    public void deleteDeviceData(@PathVariable String id) {
        deviceDataService.deleteDeviceData(id);
    }

}
