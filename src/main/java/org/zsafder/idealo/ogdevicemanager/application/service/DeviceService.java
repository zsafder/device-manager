package org.zsafder.idealo.ogdevicemanager.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zsafder.idealo.ogdevicemanager.domain.model.Device;

public interface DeviceService {
    Page<Device> getAllDevices(Pageable pageable);
    Device getDeviceById(Long id);
    Device addDevice(Device device);
    Device updateDevice(Long id, Device device);
    void deleteDevice(Long id);
    Page<Device> searchDeviceByBrand(String brand, Pageable pageable);
}