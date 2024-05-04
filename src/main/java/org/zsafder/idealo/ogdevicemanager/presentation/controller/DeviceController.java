package org.zsafder.idealo.ogdevicemanager.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zsafder.idealo.ogdevicemanager.application.service.DeviceService;
import org.zsafder.idealo.ogdevicemanager.domain.model.Device;
import org.zsafder.idealo.ogdevicemanager.presentation.dto.DeviceDTO.DeviceDTO;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    @Operation(summary = "Get all devices")
    public ResponseEntity<Page<DeviceDTO>> getAllDevices(Pageable pageable) {
        Page<Device> devices = deviceService.getAllDevices(pageable);
        return ResponseEntity.ok(devices.map(DeviceDTO::from));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get device by ID")
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable Long id) {
        Device device = deviceService.getDeviceById(id);
        return ResponseEntity.ok(DeviceDTO.from(device));
    }

    @PostMapping
    @Operation(summary = "Add a new device")
    public ResponseEntity<DeviceDTO> addDevice(@RequestBody DeviceDTO device) {
        Device createdDevice = deviceService.addDevice(DeviceDTO.toDevice(device));
        return ResponseEntity.status(HttpStatus.CREATED).body(DeviceDTO.from(createdDevice));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a device")
    public ResponseEntity<DeviceDTO> updateDevice(@PathVariable Long id, @RequestBody DeviceDTO device) {
        Device updatedDevice = deviceService.updateDevice(id, (DeviceDTO.toDevice(device)));
        return ResponseEntity.ok(DeviceDTO.from(updatedDevice));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a device")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search devices by brand")
    public ResponseEntity<Page<DeviceDTO>> searchDeviceByBrand(@RequestParam String brand, Pageable pageable) {
        Page<Device> devices = deviceService.searchDeviceByBrand(brand, pageable);
        return ResponseEntity.ok(devices.map(DeviceDTO::from));
    }
}

