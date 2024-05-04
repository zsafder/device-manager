package org.zsafder.idealo.ogdevicemanager.infrastructure.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zsafder.idealo.ogdevicemanager.domain.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Page<Device> findByBrand(String brand, Pageable pageable);
}
