package org.zsafder.idealo.ogdevicemanager.presentation.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.zsafder.idealo.ogdevicemanager.application.service.DeviceService;
import org.zsafder.idealo.ogdevicemanager.domain.exception.DeviceNotFoundException;
import org.zsafder.idealo.ogdevicemanager.domain.model.Device;
import org.zsafder.idealo.ogdevicemanager.presentation.dto.DeviceDTO.DeviceDTO;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceControllerTest {
    @Mock
    private DeviceService deviceService;

    @InjectMocks
    private DeviceController deviceController;

    @Test
    void testGetAllDevices() {
        // Given
        List<DeviceDTO> devicesDto = Collections.singletonList(new DeviceDTO("Device 1", "Brand 1"));
        List<Device> devices = DeviceDTO.toDevice(devicesDto);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Device> devicePage = new PageImpl<>(devices);
        when(deviceService.getAllDevices(pageable)).thenReturn(devicePage);

        // When
        ResponseEntity<Page<DeviceDTO>> result = deviceController.getAllDevices(pageable);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertIterableEquals(devicesDto, Objects.requireNonNull(result.getBody()).getContent());
    }

    @Test
    void testGetDeviceById_ExistingDevice() {
        // Given
        DeviceDTO deviceDto = new DeviceDTO("Device 1", "Brand 1");
        Device device = DeviceDTO.toDevice(deviceDto);
        when(deviceService.getDeviceById(1L)).thenReturn(device);

        // When
        ResponseEntity<DeviceDTO> response = deviceController.getDeviceById(1L);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deviceDto, response.getBody());
    }

    @Test
    void testGetDeviceById_NonExistingDevice() {
        // Given
        Long nonExistingId = 1L;
        when(deviceService.getDeviceById(nonExistingId)).thenThrow(new DeviceNotFoundException(nonExistingId));

        // When
        Exception exception = assertThrows(DeviceNotFoundException.class, () -> deviceController.getDeviceById(nonExistingId));

        // Then
        String expectedMessage = "Device with id " + nonExistingId + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testAddDevice() {
        // Given
        DeviceDTO deviceDto = new DeviceDTO("Device 1", "Brand 1");
        Device device = DeviceDTO.toDevice(deviceDto);
        when(deviceService.addDevice(device)).thenReturn(device);

        // When
        ResponseEntity<DeviceDTO> response = deviceController.addDevice(deviceDto);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(deviceDto, response.getBody());
    }

    @Test
    void testUpdateDevice_ExistingDevice() {
        // Given
        DeviceDTO updatedDeviceDto = new DeviceDTO("Device 1", "Brand 1");
        Device updatedDevice = DeviceDTO.toDevice(updatedDeviceDto);
        when(deviceService.updateDevice(1L, updatedDevice)).thenReturn(updatedDevice);

        // When
        ResponseEntity<DeviceDTO> response = deviceController.updateDevice(1L, updatedDeviceDto);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDeviceDto, response.getBody());
    }

    @Test
    void testUpdateDevice_NonExistingDevice() {
        // Given
        Long nonExistingId = 1L;
        DeviceDTO updatedDeviceDto = new DeviceDTO("Device 1", "Brand 1");
        Device updatedDevice = DeviceDTO.toDevice(updatedDeviceDto);
        when(deviceService.updateDevice(nonExistingId, updatedDevice)).thenThrow(new DeviceNotFoundException(nonExistingId));

        // When
        Exception exception = assertThrows(DeviceNotFoundException.class, () -> deviceController.updateDevice(nonExistingId, updatedDeviceDto));

        // Then
        String expectedMessage = "Device with id " + nonExistingId + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteDevice() {
        // When
        ResponseEntity<Void> response = deviceController.deleteDevice(1L);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deviceService).deleteDevice(1L);
    }

    @Test
    void testSearchDeviceByBrand() {
        // Given
        List<DeviceDTO> devicesDto = Collections.singletonList(new DeviceDTO("Device 1", "Brand 1"));
        List<Device> devices = DeviceDTO.toDevice(devicesDto);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Device> devicePage = new PageImpl<>(devices);
        when(deviceService.searchDeviceByBrand("Brand 1", pageable)).thenReturn(devicePage);

        // When
        ResponseEntity<Page<DeviceDTO>> result = deviceController.searchDeviceByBrand("Brand 1", pageable);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertIterableEquals(devicesDto, Objects.requireNonNull(result.getBody()).getContent());
    }
}