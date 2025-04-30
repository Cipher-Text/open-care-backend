# Revised Comprehensive Dynamic Ad System Schema

Here's a consolidated schema for your location-based ad system with monetization capabilities, implementing the requested changes:

## Core Tables

### `ad_placement_type` (merged with pricing)
```sql
CREATE TABLE ad_placement_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL, -- e.g. 'popup', 'scroll', 'footer', 'dedicated_page'
    description VARCHAR(255),
    position VARCHAR(50) NOT NULL, -- 'popup', 'scroll', 'footer', etc.
    base_price DECIMAL NOT NULL,
    duration_in_days INTEGER NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);
```

### `advertisement`
```sql
CREATE TABLE advertisement (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    image_url VARCHAR(255),
    target_url VARCHAR(255),

    -- Targeting information using enum
    target_type target_type_enum NOT NULL, -- ENUM('doctor', 'hospital', 'institution')
    target_id BIGINT, -- FK to doctor/hospital/institution based on type
    placement_type_id INTEGER REFERENCES ad_placement_type(id),

    -- Location targeting
    district_id INTEGER REFERENCES district(id) ON UPDATE CASCADE,
    upazila_id INTEGER REFERENCES upazila(id) ON UPDATE CASCADE,
    union_id INTEGER REFERENCES "union"(id) ON UPDATE CASCADE,

    -- Specialization targeting (for doctors)
    speciality_id INTEGER REFERENCES speciality(id) ON UPDATE CASCADE,

    -- Demographic targeting
    age_group VARCHAR(50),
    gender VARCHAR(20),
    language_preference VARCHAR(50),

    -- Timing
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,

    -- Status
    is_active BOOLEAN DEFAULT TRUE NOT NULL,

    -- Performance metrics
    views INTEGER DEFAULT 0,
    clicks INTEGER DEFAULT 0,

    -- Metadata
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    created_by BIGINT REFERENCES users(id)
);
```

## Create Enum for Target Type
```sql
CREATE TYPE target_type_enum AS ENUM ('doctor', 'hospital', 'institution');
```

## Supporting Tables

### `ad_log` (for analytics)
```sql
CREATE TABLE ad_log (
    id BIGSERIAL PRIMARY KEY,
    advertisement_id BIGINT REFERENCES advertisement(id),
    action VARCHAR(20) NOT NULL, -- 'view', 'click'
    user_id BIGINT REFERENCES users(id),
    ip_address VARCHAR(45),
    user_agent TEXT,

    -- Location data
    district_id INTEGER REFERENCES district(id),
    upazila_id INTEGER REFERENCES upazila(id),

    -- Device info
    device_type VARCHAR(50),
    os VARCHAR(50),

    timestamp TIMESTAMP DEFAULT NOW()
);
```

### `ad_payment`
```sql
CREATE TABLE ad_payment (
    id BIGSERIAL PRIMARY KEY,
    advertisement_id BIGINT REFERENCES advertisement(id),
    amount DECIMAL NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL, -- 'pending', 'paid', 'refunded', 'failed'
    transaction_reference VARCHAR(100),
    payment_details JSONB,
    paid_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT NOW()
);
```

### `ad_campaign` (optional for grouping ads)
```sql
CREATE TABLE ad_campaign (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    advertiser_id BIGINT NOT NULL,
    advertiser_type target_type_enum NOT NULL, -- Using the same enum
    budget DECIMAL,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW()
);
```

## Indexes for Performance
```sql
CREATE INDEX idx_advertisement_active ON advertisement(is_active, end_time);
CREATE INDEX idx_advertisement_location ON advertisement(district_id, upazila_id, union_id);
CREATE INDEX idx_ad_log_advertisement ON ad_log(advertisement_id);
CREATE INDEX idx_ad_log_timestamp ON ad_log(timestamp);
```

## Views for Reporting

### `ad_performance_view`
```sql
CREATE OR REPLACE VIEW ad_performance_view AS
SELECT
    a.id,
    a.title,
    apt.name as placement_type,
    a.target_type,
    COUNT(CASE WHEN al.action = 'view' THEN 1 END) as total_views,
    COUNT(CASE WHEN al.action = 'click' THEN 1 END) as total_clicks,
    (COUNT(CASE WHEN al.action = 'click' THEN 1 END) * 100.0 /
     NULLIF(COUNT(CASE WHEN al.action = 'view' THEN 1 END), 0)) as ctr,
    ap.amount as payment_amount,
    d.name as district,
    u.name as upazila
FROM advertisement a
LEFT JOIN ad_placement_type apt ON a.placement_type_id = apt.id
LEFT JOIN ad_log al ON a.id = al.advertisement_id
LEFT JOIN ad_payment ap ON a.id = ap.advertisement_id
LEFT JOIN district d ON a.district_id = d.id
LEFT JOIN upazila u ON a.upazila_id = u.id
GROUP BY a.id, apt.name, a.target_type, ap.amount, d.name, u.name;
```

## Key Changes Made

1. **Converted `ad_target_type` to an Enum**:
    - Created a PostgreSQL enum type `target_type_enum` with values 'doctor', 'hospital', 'institution'
    - Removed the separate `ad_target_type` table
    - Updated references in the `advertisement` and `ad_campaign` tables

2. **Merged `ad_placement_type` and `ad_pricing`**:
    - Combined pricing information into the `ad_placement_type` table
    - Added duration_in_days, is_active, and timestamp fields from the previous pricing table
    - Removed the separate `ad_pricing` table

This revised schema maintains all the original functionality while implementing a more efficient structure with enums and fewer tables as requested.