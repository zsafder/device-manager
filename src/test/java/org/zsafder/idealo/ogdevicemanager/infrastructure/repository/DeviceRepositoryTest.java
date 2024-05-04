package org.zsafder.idealo.ogdevicemanager.infrastructure.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.zsafder.idealo.ogdevicemanager.domain.model.Device;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DeviceRepositoryTest {

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    void testFindAll() {
        // Given
        Device device1 = new Device("Device 1", "Brand 1");
        deviceRepository.save(device1);

        Device device2 = new Device("Device 2", "Brand 2");
        deviceRepository.save(device2);

        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Device> devices = deviceRepository.findAll(pageable);

        // Then
        assertEquals(2, devices.getTotalElements());
    }

    @Test
    void testFindById_ExistingDevice() {
        // Given
        Device device = new Device("Device 1", "Brand 1");
        Device savedDevice = deviceRepository.save(device);

        // When
        Device foundDevice = deviceRepository.findById(savedDevice.getId()).orElse(null);

        // Then
        assertEquals(savedDevice, foundDevice);
    }

    @Test
    void testFindById_NonExistingDevice() {
        // When
        Device foundDevice = deviceRepository.findById(999L).orElse(null);

        // Then
        assertNull(foundDevice);
    }

    @Test
    void testSave() {
        // Given
        Device device = new Device("Device 1", "Brand 1");

        // When
        Device savedDevice = deviceRepository.save(device);

        // Then
        assertEquals(device, savedDevice);
    }

    @Test
    void testDeleteById_ExistingDevice() {
        // Given
        Device device = new Device("Device 1", "Brand 1");
        Device savedDevice = deviceRepository.save(device);

        // When
        deviceRepository.deleteById(savedDevice.getId());

        // Then
        assertFalse(deviceRepository.findById(savedDevice.getId()).isPresent());
    }

    @Test
    void testDeleteById_NonExistingDevice() {
        // When
        deviceRepository.deleteById(999L);

        // Then
        // No exception should be thrown
    }

    @Test
    void testFindByBrand_ExistingDevices() {
        // Given
        Device device1 = new Device("Device 1", "Brand 1");
        deviceRepository.save(device1);

        Device device2 = new Device("Device 2", "Brand 1");
        deviceRepository.save(device2);

        Device device3 = new Device("Device 3", "Brand 2");
        deviceRepository.save(device3);

        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Device> devices = deviceRepository.findByBrand("Brand 1", pageable);

        // Then
        assertEquals(2, devices.getTotalElements());
        assertTrue(devices.getContent().stream().allMatch(device -> device.getBrand().equals("Brand 1")));
    }

    @Test
    void testFindByBrand_NonExistingBrand() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Device> devices = deviceRepository.findByBrand("Nonexistent Brand", pageable);

        // Then
        assertTrue(devices.isEmpty());
    }
}
