package org.zsafder.idealo.ogdevicemanager.presentation.dto.DeviceDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.zsafder.idealo.ogdevicemanager.domain.model.Device;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class DeviceDTO {
    private Long id;
    private String name;
    private String brand;
    private LocalDateTime creationTime;

    public DeviceDTO(String name, String brand) {
        this.name = name;
        this.brand = brand;
        this.creationTime = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDTO deviceDTO = (DeviceDTO) o;
        return Objects.equals(id, deviceDTO.id) &&
                Objects.equals(name, deviceDTO.name) &&
                Objects.equals(brand, deviceDTO.brand) &&
                Objects.equals(creationTime, deviceDTO.creationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, creationTime);
    }

    public static DeviceDTO from(Device device) {
        return new DeviceDTO(device.getId(), device.getName(), device.getBrand(), device.getCreationTime());
    }

    public static Device toDevice(DeviceDTO deviceDTO) {
        return new Device(deviceDTO.getName(), deviceDTO.getBrand(), deviceDTO.getCreationTime());
    }

    public static List<Device> toDevice(List<DeviceDTO> devicesDTO) {
        return devicesDTO.stream().map(DeviceDTO::toDevice).collect(Collectors.toList());
    }
}
