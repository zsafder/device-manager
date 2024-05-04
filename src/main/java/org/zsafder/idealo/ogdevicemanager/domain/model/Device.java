package org.zsafder.idealo.ogdevicemanager.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private LocalDateTime creationTime;

    public Device() {
        this.creationTime = LocalDateTime.now();
    }

    public Device(String name, String brand) {
        this(name, brand, LocalDateTime.now());
    }
    public Device(String name, String brand, LocalDateTime creationTime) {
        this.name = name;
        this.brand = brand;
        this.creationTime = creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device deviceDTO = (Device) o;
        return Objects.equals(id, deviceDTO.id) &&
                Objects.equals(name, deviceDTO.name) &&
                Objects.equals(brand, deviceDTO.brand) &&
                Objects.equals(creationTime, deviceDTO.creationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, creationTime);
    }
}

