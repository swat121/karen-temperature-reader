package com.karen.karen_mqtt_integration.repo;

import com.karen.karen_mqtt_integration.dto.sensor.SensorDataResponseDTO;
import com.karen.karen_mqtt_integration.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("SELECT r FROM Request r " +
            "JOIN FETCH r.device d " +
            "JOIN FETCH r.sensor s " +
            "JOIN FETCH r.sensorData sd " +
            "WHERE d.macAddress = :macAddress " +
            "AND s.name = :sensorName")
    List<Request> findByDeviceMacAddressAndSensorName(@Param("macAddress") String macAddress,
                                                      @Param("sensorName") String sensorName);
}
