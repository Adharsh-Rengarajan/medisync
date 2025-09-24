package com.medisync.patient_service.repository;

import com.medisync.patient_service.entity.PatientInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientInsuranceRepository extends JpaRepository<PatientInsurance, UUID> {

    List<PatientInsurance> findByPatient_PatientId(UUID patientId);

    Optional<PatientInsurance> findByPatient_PatientIdAndIsPrimaryTrue(UUID patientId);
}
