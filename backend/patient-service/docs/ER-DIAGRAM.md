```mermaid
erDiagram
    Patient {
        uuid patient_id PK
        varchar patient_code "unique"
        varchar first_name
        varchar last_name
        date dob
        varchar gender
        varchar phone
        varchar email "unique"
        varchar street
        varchar city
        varchar state
        varchar zip
        varchar country
        varchar notification_preference "email | sms | none"
        boolean is_active "default: true"
        timestamp created_at
        timestamp updated_at
    }

    Insurance {
        uuid insurance_id PK
        varchar provider_name
        varchar policy_number "unique"
        text coverage_details
        date valid_till
    }

    PatientInsurance {
        uuid id PK
        uuid patient_id FK
        uuid insurance_id FK
        boolean is_primary
        timestamp created_at
    }

    EmergencyContact {
        uuid contact_id PK
        varchar name
        varchar relationship
        varchar phone
        uuid patient_id FK
    }

    %% Relationships
    Patient ||--o{ PatientInsurance : "has"
    Insurance ||--o{ PatientInsurance : "covers"
    Patient ||--o{ EmergencyContact : "has"
