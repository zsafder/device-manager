package org.zsafder.idealo.ogdevicemanager.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.zsafder.idealo.ogdevicemanager.domain.exception.DeviceNotFoundException;
import org.zsafder.idealo.ogdevicemanager.domain.model.Device;
import org.zsafder.idealo.ogdevicemanager.infrastructure.repository.DeviceRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceServiceImplTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceServiceImpl deviceService;

    @Test
    void testGetAllDevices() {
        List<Device> devices = Collections.singletonList(new Device());
        Pageable pageable = PageRequest.of(0, 10);
        Page<Device> devicePage = new PageImpl<>(devices);
        when(deviceRepository.findAll(pageable)).thenReturn(devicePage);

        // When
        Page<Device> result = deviceService.getAllDevices(pageable);

        // Then
        assertEquals(devicePage, result);
    }

    @Test
    void testGetDeviceById_ExistingDevice() {
        // Given
        Device device = new Device();
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));

        // When
        Device result = deviceService.getDeviceById(1L);

        // Then
        assertEquals(device, result);
    }

    @Test
    void testGetDeviceById_NonExistingDevice() {
        // Given
        Long nonExistingId = 1L;
        when(deviceRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(DeviceNotFoundException.class, () -> deviceService.getDeviceById(nonExistingId));
    }

    @Test
    void testAddDevice() {
        // Given
        Device device = new Device();
        when(deviceRepository.save(device)).thenReturn(device);

        // When
        Device result = deviceService.addDevice(device);

        // Then
        assertEquals(device, result);
    }

    @Test
    void testUpdateDevice_ExistingDevice() {
        // Given
        Device existingDevice = new Device();
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(existingDevice));

        Device updatedDevice = new Device();
        when(deviceRepository.save(updatedDevice)).thenReturn(updatedDevice);

        // When
        Device result = deviceService.updateDevice(1L, updatedDevice);

        // Then
        assertEquals(updatedDevice, result);
    }

    @Test
    void testUpdateDevice_NonExistingDevice() {
        // Given
        Long nonExistingId = 1L;
        Device updatedDevice = new Device();
        when(deviceRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(DeviceNotFoundException.class, () -> deviceService.updateDevice(nonExistingId, updatedDevice));
    }

    @Test
    void testDeleteDevice() {
        // When
        deviceService.deleteDevice(1L);

        // Then
        verify(deviceRepository).deleteById(1L);
    }

    @Test
    void testSearchDeviceByBrand() {
        // Given
        List<Device> devices = Collections.singletonList(new Device());
        Pageable pageable = PageRequest.of(0, 10);
        Page<Device> devicePage = new PageImpl<>(devices);
        when(deviceRepository.findByBrand("Brand", pageable)).thenReturn(devicePage);

        // When
        Page<Device> result = deviceService.searchDeviceByBrand("Brand", pageable);

        // Then
        assertEquals(devicePage, result);
    }
}
