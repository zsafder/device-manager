package org.zsafder.idealo.ogdevicemanager.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zsafder.idealo.ogdevicemanager.domain.exception.DeviceNotFoundException;
import org.zsafder.idealo.ogdevicemanager.domain.model.Device;
import org.zsafder.idealo.ogdevicemanager.infrastructure.repository.DeviceRepository;

import java.time.LocalDateTime;

@Service
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Page<Device> getAllDevices(Pageable pageable) {
        return deviceRepository.findAll(pageable);
    }

    @Override
    public Device getDeviceById(Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));
    }

    @Override
    public Device addDevice(Device device) {
        device.setCreationTime(LocalDateTime.now());
        return deviceRepository.save(device);
    }

    @Override
    public Device updateDevice(Long id, Device device) {
        Device existingDevice = deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException(id));
        if (existingDevice != null) {
            device.setId(id);
            device.setCreationTime(existingDevice.getCreationTime());
            return deviceRepository.save(device);
        }
        return null;
    }

    @Override
    public void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }

    @Override
    public Page<Device> searchDeviceByBrand(String brand, Pageable pageable) {
        return deviceRepository.findByBrand(brand, pageable);
    }
}
