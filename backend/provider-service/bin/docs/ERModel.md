```mermaid
erDiagram
    Provider {
        uuid provider_id PK
        varchar(100) first_name
        varchar(100) last_name
        varchar(150) email UK
        varchar(20) phone
        varchar(20) gender
        date date_of_birth
        int years_of_experience
        timestamp created_at
        timestamp updated_at
    }
    
    Specialty {
        uuid specialty_id PK
        varchar(100) name UK
        text description
    }
    
    ProviderSpecialty {
        uuid provider_specialty_id PK
        uuid provider_id FK
        uuid specialty_id FK
    }
    
    Availability {
        uuid availability_id PK
        uuid provider_id FK
        varchar(15) day_of_week
        time start_time
        time end_time
        boolean is_active
    }
    
    ProviderScheduleOverride {
        uuid override_id PK
        uuid provider_id FK
        date date
        boolean available
        time start_time
        time end_time
    }
    
    Provider ||--o{ ProviderSpecialty : "has"
    Specialty ||--o{ ProviderSpecialty : "assigned to"
    Provider ||--o{ Availability : "has"
    Provider ||--o{ ProviderScheduleOverride : "has"