package com.foxploit.ignio.devicedataservice.resources;

import com.foxploit.ignio.devicedataservice.models.DeviceData;
import com.foxploit.ignio.devicedataservice.repositories.DeviceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
class DeviceDataService {

    @Autowired
    private DeviceDataRepository deviceDataRepository;


    Optional<DeviceData> getDeviceData(String id) throws NoSuchElementException {
        Optional<DeviceData> deviceData = deviceDataRepository.findById(id);
        if (deviceData.isEmpty()) {
            throw new NoSuchElementException("There is no Device Data element with the given id :" + id);
        }
        return deviceData;
    }

    DeviceData saveDeviceData(DeviceData deviceData) {
        return deviceDataRepository.save(deviceData);
    }

    void deleteDeviceData(String id) {
        Optional<DeviceData> deviceDataOptional = getDeviceData(id);
        if (deviceDataOptional.isPresent()) {
            deviceDataRepository.deleteById(id);
        }
    }

    List<DeviceData> getAllDeviceData(String deviceId) {
        return deviceDataRepository.findAllByDeviceId(deviceId);
    }

}
