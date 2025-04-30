# Enhanced Dynamic Ad System Schema

This schema enhances the location-based ad system with additional dynamic targeting capabilities, improved monetization features, and more flexible campaign management.

## Core Tables

### `ad_placement_type`
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
    
    -- Campaign association
    campaign_id BIGINT REFERENCES ad_campaign(id),

    -- Targeting information using enum
    target_type target_type_enum NOT NULL, -- ENUM('doctor', 'hospital', 'institution')
    target_id BIGINT, -- FK to doctor/hospital/institution based on type
    placement_type_id INTEGER REFERENCES ad_placement_type(id) ON DELETE RESTRICT,

    -- Location targeting
    district_id INTEGER REFERENCES district(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    upazila_id INTEGER REFERENCES upazila(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    union_id INTEGER REFERENCES "union"(id) ON UPDATE CASCADE ON DELETE RESTRICT,

    -- Specialization targeting (for doctors)
    speciality_id INTEGER REFERENCES speciality(id) ON UPDATE CASCADE ON DELETE RESTRICT,

    -- Demographic targeting
    age_group VARCHAR(50),
    gender VARCHAR(20),
    language_preference VARCHAR(50),

    -- Timing with enhanced scheduling
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    display_frequency VARCHAR(50), -- 'always', 'once_per_session', 'daily', etc.
    time_of_day_restrictions JSONB, -- For time-specific targeting (morning, evening, etc.)
    
    -- Priority and budget
    priority INTEGER DEFAULT 1, -- For resolving competing ads
    daily_budget DECIMAL, -- Optional daily spend limit
    
    -- Status
    is_active BOOLEAN DEFAULT TRUE NOT NULL,
    status VARCHAR(20) DEFAULT 'pending', -- 'pending', 'approved', 'rejected', 'paused'

    -- Performance metrics
    views INTEGER DEFAULT 0,
    clicks INTEGER DEFAULT 0,
    conversions INTEGER DEFAULT 0, -- For tracking defined conversion events

    -- Metadata
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    created_by BIGINT REFERENCES users(id),
    
    CONSTRAINT valid_dates CHECK (end_time > start_time),
    CONSTRAINT valid_budget CHECK (daily_budget IS NULL OR daily_budget > 0)
);
```

### `ad_campaign`
```sql
CREATE TABLE ad_campaign (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    
    -- Campaign owner
    advertiser_id BIGINT NOT NULL,
    advertiser_type target_type_enum NOT NULL, -- Using the same enum
    
    -- Budget and goals
    total_budget DECIMAL,
    daily_budget DECIMAL,
    target_ctr DECIMAL, -- Target click-through rate (%)
    target_impressions INTEGER, -- Target number of impressions
    
    -- Timing
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    
    -- Status
    status VARCHAR(20) DEFAULT 'draft', -- 'draft', 'active', 'paused', 'completed', 'cancelled'
    is_active BOOLEAN DEFAULT TRUE,
    
    -- Metadata
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    created_by BIGINT REFERENCES users(id),
    
    CONSTRAINT valid_campaign_dates CHECK (end_date > start_date),
    CONSTRAINT valid_campaign_budget CHECK (total_budget > 0),
    CONSTRAINT valid_daily_budget CHECK (daily_budget IS NULL OR daily_budget > 0)
);
```

## New Dynamic Targeting Tables

### `ad_tag`
```sql
CREATE TABLE ad_tag (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT NOW()
);
```

### `advertisement_tag` (Many-to-Many)
```sql
CREATE TABLE advertisement_tag (
    advertisement_id BIGINT REFERENCES advertisement(id) ON DELETE CASCADE,
    tag_id INTEGER REFERENCES ad_tag(id) ON DELETE CASCADE,
    PRIMARY KEY (advertisement_id, tag_id)
);
```

### `audience_segment`
```sql
CREATE TABLE audience_segment (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    criteria JSONB NOT NULL, -- Flexible criteria for audience definition
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    created_by BIGINT REFERENCES users(id)
);
```

### `advertisement_audience` (Many-to-Many)
```sql
CREATE TABLE advertisement_audience (
    advertisement_id BIGINT REFERENCES advertisement(id) ON DELETE CASCADE,
    audience_id INTEGER REFERENCES audience_segment(id) ON DELETE CASCADE,
    priority INTEGER DEFAULT 1,
    PRIMARY KEY (advertisement_id, audience_id)
);
```

## Supporting Tables

### `ad_log` (Enhanced for Analytics)
```sql
CREATE TABLE ad_log (
    id BIGSERIAL PRIMARY KEY,
    advertisement_id BIGINT REFERENCES advertisement(id),
    campaign_id BIGINT REFERENCES ad_campaign(id),
    action VARCHAR(20) NOT NULL, -- 'view', 'click', 'conversion'
    user_id BIGINT REFERENCES users(id),
    ip_address VARCHAR(45),
    user_agent TEXT,

    -- Location data
    district_id INTEGER REFERENCES district(id),
    upazila_id INTEGER REFERENCES upazila(id),
    
    -- Extended tracking
    session_id VARCHAR(100),
    referrer_url TEXT,
    
    -- Device info
    device_type VARCHAR(50),
    os VARCHAR(50),
    browser VARCHAR(50),
    
    -- Additional context
    context_data JSONB, -- Flexible storage for additional tracking parameters
    
    timestamp TIMESTAMP DEFAULT NOW()
);
```

### `ad_payment` (Enhanced)
```sql
CREATE TABLE ad_payment (
    id BIGSERIAL PRIMARY KEY,
    advertisement_id BIGINT REFERENCES advertisement(id),
    campaign_id BIGINT REFERENCES ad_campaign(id),
    amount DECIMAL NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL, -- 'pending', 'paid', 'refunded', 'failed'
    transaction_reference VARCHAR(100),
    payment_details JSONB,
    invoice_number VARCHAR(100),
    paid_at TIMESTAMP,
    refunded_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    created_by BIGINT REFERENCES users(id),
    
    CONSTRAINT valid_amount CHECK (amount > 0)
);
```

### `ad_pricing_model` (New)
```sql
CREATE TABLE ad_pricing_model (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL, -- 'CPM', 'CPC', 'CPA', 'flat_rate'
    description VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW()
);
```

### `ad_placement_pricing` (New for Dynamic Pricing)
```sql
CREATE TABLE ad_placement_pricing (
    id BIGSERIAL PRIMARY KEY,
    placement_type_id INTEGER REFERENCES ad_placement_type(id) ON DELETE CASCADE,
    pricing_model_id INTEGER REFERENCES ad_pricing_model(id) ON DELETE CASCADE,
    base_price DECIMAL NOT NULL,
    min_duration_days INTEGER NOT NULL DEFAULT 1,
    discount_percent DECIMAL DEFAULT 0, -- For long-term discounts
    is_active BOOLEAN DEFAULT TRUE,
    valid_from TIMESTAMP DEFAULT NOW(),
    valid_until TIMESTAMP,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    
    CONSTRAINT valid_price CHECK (base_price > 0),
    CONSTRAINT valid_discount CHECK (discount_percent >= 0 AND discount_percent <= 100),
    CONSTRAINT valid_price_period CHECK (valid_until IS NULL OR valid_until > valid_from)
);
```

## Create Type for Target Type
```sql
CREATE TYPE target_type_enum AS ENUM ('doctor', 'hospital', 'institution');
```

## Indexes for Performance
```sql
-- Core indexes
CREATE INDEX idx_advertisement_active ON advertisement(is_active, end_time);
CREATE INDEX idx_advertisement_location ON advertisement(district_id, upazila_id, union_id);
CREATE INDEX idx_advertisement_campaign ON advertisement(campaign_id);
CREATE INDEX idx_ad_log_advertisement ON ad_log(advertisement_id);
CREATE INDEX idx_ad_log_timestamp ON ad_log(timestamp);
CREATE INDEX idx_ad_log_campaign ON ad_log(campaign_id);

-- New indexes for dynamic targeting
CREATE INDEX idx_ad_tags ON advertisement_tag(advertisement_id);
CREATE INDEX idx_ad_audience ON advertisement_audience(advertisement_id);
CREATE INDEX idx_ad_audience_priority ON advertisement_audience(advertisement_id, priority);

-- Pricing indexes
CREATE INDEX idx_ad_placement_pricing_active ON ad_placement_pricing(placement_type_id, is_active);
```

## Enhanced Views for Reporting

### `ad_performance_view`
```sql
CREATE OR REPLACE VIEW ad_performance_view AS
SELECT
    a.id,
    a.title,
    ac.id as campaign_id,
    ac.name as campaign_name,
    apt.name as placement_type,
    a.target_type,
    COUNT(CASE WHEN al.action = 'view' THEN 1 END) as total_views,
    COUNT(CASE WHEN al.action = 'click' THEN 1 END) as total_clicks,
    COUNT(CASE WHEN al.action = 'conversion' THEN 1 END) as total_conversions,
    (COUNT(CASE WHEN al.action = 'click' THEN 1 END) * 100.0 /
     NULLIF(COUNT(CASE WHEN al.action = 'view' THEN 1 END), 0)) as ctr,
    (COUNT(CASE WHEN al.action = 'conversion' THEN 1 END) * 100.0 /
     NULLIF(COUNT(CASE WHEN al.action = 'click' THEN 1 END), 0)) as conversion_rate,
    COALESCE(SUM(ap.amount), 0) as total_spent,
    CASE WHEN COUNT(CASE WHEN al.action = 'click' THEN 1 END) > 0 
         THEN COALESCE(SUM(ap.amount), 0) / COUNT(CASE WHEN al.action = 'click' THEN 1 END)
         ELSE 0 END as cost_per_click,
    d.name as district,
    u.name as upazila
FROM advertisement a
LEFT JOIN ad_campaign ac ON a.campaign_id = ac.id
LEFT JOIN ad_placement_type apt ON a.placement_type_id = apt.id
LEFT JOIN ad_log al ON a.id = al.advertisement_id
LEFT JOIN ad_payment ap ON a.id = ap.advertisement_id
LEFT JOIN district d ON a.district_id = d.id
LEFT JOIN upazila u ON a.upazila_id = u.id
GROUP BY a.id, ac.id, ac.name, apt.name, a.target_type, d.name, u.name;
```

### `campaign_performance_view` (New)
```sql
CREATE OR REPLACE VIEW campaign_performance_view AS
SELECT
    ac.id as campaign_id,
    ac.name as campaign_name,
    ac.advertiser_type,
    ac.advertiser_id,
    COUNT(DISTINCT a.id) as total_ads,
    SUM(a.views) as total_views,
    SUM(a.clicks) as total_clicks,
    SUM(a.conversions) as total_conversions,
    CASE WHEN SUM(a.views) > 0 
         THEN (SUM(a.clicks) * 100.0) / SUM(a.views)
         ELSE 0 END as campaign_ctr,
    COALESCE(SUM(ap.amount), 0) as total_spent,
    ac.total_budget,
    CASE WHEN ac.total_budget > 0
         THEN (COALESCE(SUM(ap.amount), 0) * 100.0) / ac.total_budget
         ELSE 0 END as budget_utilization_percent,
    ac.status
FROM ad_campaign ac
LEFT JOIN advertisement a ON ac.id = a.campaign_id
LEFT JOIN ad_payment ap ON ac.id = ap.campaign_id
GROUP BY ac.id, ac.name, ac.advertiser_type, ac.advertiser_id, ac.total_budget, ac.status;
```

### `location_performance_view` (New)
```sql
CREATE OR REPLACE VIEW location_performance_view AS
SELECT
    d.id as district_id,
    d.name as district_name,
    u.id as upazila_id,
    u.name as upazila_name,
    COUNT(DISTINCT a.id) as total_ads,
    COUNT(DISTINCT a.campaign_id) as total_campaigns,
    COUNT(CASE WHEN al.action = 'view' THEN 1 END) as total_views,
    COUNT(CASE WHEN al.action = 'click' THEN 1 END) as total_clicks,
    (COUNT(CASE WHEN al.action = 'click' THEN 1 END) * 100.0 /
     NULLIF(COUNT(CASE WHEN al.action = 'view' THEN 1 END), 0)) as average_ctr
FROM district d
LEFT JOIN upazila u ON d.id = u.district_id
LEFT JOIN advertisement a ON d.id = a.district_id OR u.id = a.upazila_id
LEFT JOIN ad_log al ON a.id = al.advertisement_id
GROUP BY d.id, d.name, u.id, u.name;
```

## Key Enhancements Summary

1. **Dynamic Targeting Capabilities**:
   - Added tag-based targeting (ad_tag and advertisement_tag tables)
   - Introduced audience segmentation (audience_segment table)
   - Added display frequency and time-of-day restrictions to advertisements

2. **Enhanced Campaign Management**:
   - Added targeting goals (CTR, impressions)
   - Improved campaign status tracking
   - Added validation constraints for dates and budgets

3. **Dynamic Pricing**:
   - Separated pricing models (CPM, CPC, CPA, flat rate)
   - Added time-limited pricing with discounts
   - Support for multiple pricing models per placement type

4. **Advanced Analytics**:
   - Extended ad_log with more tracking parameters
   - Added conversion tracking
   - Created additional reporting views for campaigns and locations

5. **Improved Flexibility**:
   - JSON fields for extensible tracking and targeting
   - Priority settings for ads and audience segments
   - More comprehensive status tracking

6. **Data Integrity**:
   - Added CHECK constraints for data validation
   - Improved foreign key relationships with DELETE and UPDATE rules
   - More comprehensive indexing strategy

This enhanced schema offers significantly more flexibility and dynamic capabilities while maintaining the core functionality of the original design.