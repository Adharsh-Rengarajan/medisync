package com.medisync.patient_service.repository;

import com.medisync.patient_service.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    Optional<Patient> findByPatientCode(String patientCode);

    Optional<Patient> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPatientCode(String patientCode);
}
