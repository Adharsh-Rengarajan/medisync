# MediSync  
Distributed Healthcare Appointment & Resource Management Platform

MediSync is a microservices-based healthcare system that optimizes appointment scheduling, doctor availability, facility usage, patient flow, notifications, billing, and analytics. Built with **Spring Boot**, and **AWS**.

---

## Features
- Patient Service – Manage patient profiles, insurance details  
- Provider Service – Manage doctors, availability, and specialties  
- Appointment Service – Book appointments, handle conflicts & waitlists  
- Facility Service – Track rooms & equipment availability  
- Queue Management Service – Real-time patient flow tracking via Redis  
- Notification Service – Send reminders via AWS SES (email) & SNS (SMS)  
- Billing Service – Process payments (simulated with Stripe sandbox)  
- Analytics Service – Utilization reports, no-show predictions, CloudWatch metrics  

---

## Architecture
- Backend: Spring Boot (8 microservices)  
- Database: AWS RDS (PostgreSQL)  
- Cache/Queue: AWS ElastiCache (Redis)  
- Storage: AWS S3 (reports, invoices)  
- Messaging: AWS SNS/SES (notifications)  
- Frontend: React

```mermaid
classDiagram
    class PatientService {
        <<Microservice>>
        +registerPatient()
        +updatePatientProfile()
        +manageInsuranceDetails()
        +getPatientById()
        +listPatients()
        +deactivatePatient()
    }

    class ProviderService {
        <<Microservice>>
        +addDoctor()
        +updateDoctorProfile()
        +manageAvailability()
        +setSpecialties()
        +getDoctorSchedule()
        +findDoctorsBySpecialty()
    }

    class AppointmentService {
        <<Microservice>>
        +bookAppointment()
        +cancelAppointment()
        +rescheduleAppointment()
        +handleConflicts()
        +manageWaitlist()
        +getAppointmentsByPatient()
        +getAppointmentsByProvider()
    }

    class FacilityService {
        <<Microservice>>
        +trackRoomAvailability()
        +reserveRoom()
        +manageEquipment()
        +checkEquipmentStatus()
        +scheduleMaintenence()
        +getRoomUtilization()
    }

    class QueueManagementService {
        <<Microservice>>
        +addToQueue()
        +updateQueueStatus()
        +getQueuePosition()
        +trackPatientFlow()
        +estimateWaitTime()
        +manageCheckIn()
        -Redis cache
    }

    class NotificationService {
        <<Microservice>>
        +sendAppointmentReminder()
        +sendSMSNotification()
        +sendEmailNotification()
        +scheduleNotifications()
        +trackNotificationStatus()
        -AWS_SES integration
        -AWS_SNS integration
    }

    class BillingService {
        <<Microservice>>
        +processPayment()
        +generateInvoice()
        +handleInsuranceClaim()
        +processRefund()
        +getPaymentHistory()
        +validatePaymentMethod()
        -Stripe integration
    }

    class AnalyticsService {
        <<Microservice>>
        +generateUtilizationReport()
        +predictNoShows()
        +analyzePatientFlow()
        +calculateMetrics()
        +exportReports()
        +trackKPIs()
        -CloudWatch metrics
    }

    class APIGateway {
        <<Gateway>>
        +routeRequest()
        +authenticate()
        +rateLimit()
        +loadBalance()
    }

    class ReactFrontend {
        <<Frontend>>
        +patientPortal()
        +providerDashboard()
        +adminPanel()
        +appointmentScheduler()
        +billingInterface()
    }

    class PostgreSQL_RDS {
        <<Database>>
        +patientData
        +providerData
        +appointmentData
        +billingData
    }

    class Redis_ElastiCache {
        <<Cache>>
        +sessionCache
        +queueData
        +temporaryData
    }

    class S3_Storage {
        <<Storage>>
        +reports
        +invoices
        +documents
    }

    ReactFrontend --> APIGateway : HTTP/REST
    APIGateway --> PatientService : routes
    APIGateway --> ProviderService : routes
    APIGateway --> AppointmentService : routes
    APIGateway --> FacilityService : routes
    APIGateway --> QueueManagementService : routes
    APIGateway --> NotificationService : routes
    APIGateway --> BillingService : routes
    APIGateway --> AnalyticsService : routes

    PatientService --> PostgreSQL_RDS : persists
    ProviderService --> PostgreSQL_RDS : persists
    AppointmentService --> PostgreSQL_RDS : persists
    BillingService --> PostgreSQL_RDS : persists
    FacilityService --> PostgreSQL_RDS : persists

    QueueManagementService --> Redis_ElastiCache : caches
    AppointmentService --> Redis_ElastiCache : caches

    AnalyticsService --> S3_Storage : stores
    BillingService --> S3_Storage : stores

    AppointmentService ..> PatientService : validates patient
    AppointmentService ..> ProviderService : checks availability
    AppointmentService ..> FacilityService : reserves room
    AppointmentService ..> NotificationService : triggers reminder

    BillingService ..> PatientService : gets insurance
    BillingService ..> AppointmentService : gets service details

    QueueManagementService ..> AppointmentService : monitors appointments
    QueueManagementService ..> NotificationService : alerts delays

    AnalyticsService ..> AppointmentService : analyzes data
    AnalyticsService ..> ProviderService : tracks utilization
    AnalyticsService ..> QueueManagementService : flow metrics
```
## Quick Start (Local)
### Prerequisites
- Java 17+  
- Maven 3+  
- Docker (for PostgreSQL & Redis locally)  
- AWS Free Tier account (for deployment)  

### Run Locally
```bash
# Clone repository
git clone https://github.com/Adharsh-Rengarajan/medisync.git
cd medisync

# Build all services
mvn clean install

# Run a service (example: Patient Service)
cd patient-service
mvn spring-boot:run
