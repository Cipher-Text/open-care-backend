-- Medical Social Organizations Platform Database Schema

-- Organizations (NGOs, Hospitals, Clinics, Foundations)
CREATE TABLE organizations (
                               id SERIAL PRIMARY KEY,
                               name VARCHAR(300) NOT NULL,
                               registration_number VARCHAR(100) UNIQUE,
                               organization_type VARCHAR(50) CHECK (organization_type IN ('ngo', 'hospital', 'clinic', 'foundation', 'charity', 'government', 'private')),
                               specialization JSONB, -- ['blood_donation', 'medical_camps', 'free_surgery', 'ambulance', 'mental_health']
                               description TEXT,
                               mission_statement TEXT,
                               vision_statement TEXT,
                               established_date DATE,
                               website_url VARCHAR(500),
                               email VARCHAR(255),
                               phone VARCHAR(20),
                               emergency_contact VARCHAR(20),

    -- Address information
                               address TEXT NOT NULL,
                               city VARCHAR(100) NOT NULL,
                               state VARCHAR(100),
                               country VARCHAR(100) DEFAULT 'Bangladesh',
                               postal_code VARCHAR(20),
                               latitude DECIMAL(10, 8),
                               longitude DECIMAL(11, 8),
                               service_radius_km INTEGER DEFAULT 50,

    -- Organization details
                               logo_url VARCHAR(500),
                               cover_image_url VARCHAR(500),
                               certificate_url VARCHAR(500), -- registration certificate
                               license_documents JSONB, -- array of license document URLs
                               social_media JSONB, -- {facebook, twitter, instagram, youtube}

    -- Verification and status
                               verification_status VARCHAR(20) DEFAULT 'pending' CHECK (verification_status IN ('pending', 'verified', 'rejected', 'suspended')),
                               verification_date DATE,
                               verified_by INTEGER REFERENCES admin_users(id),
                               is_active BOOLEAN DEFAULT true,

    -- Performance metrics
                               total_beneficiaries INTEGER DEFAULT 0,
                               total_events INTEGER DEFAULT 0,
                               rating DECIMAL(2,1) DEFAULT 0.0,
                               total_reviews INTEGER DEFAULT 0,

                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Organization administrators and staff
CREATE TABLE organization_users (
                                    id SERIAL PRIMARY KEY,
                                    organization_id INTEGER REFERENCES organizations(id) ON DELETE CASCADE,
                                    email VARCHAR(255) UNIQUE NOT NULL,
                                    password_hash VARCHAR(255) NOT NULL,
                                    first_name VARCHAR(100) NOT NULL,
                                    last_name VARCHAR(100) NOT NULL,
                                    phone VARCHAR(20),
                                    profile_image_url VARCHAR(500),

                                    role VARCHAR(30) DEFAULT 'admin' CHECK (role IN ('super_admin', 'admin', 'coordinator', 'volunteer', 'medical_staff')),
                                    permissions JSONB DEFAULT '[]', -- granular permissions

    -- Professional details
                                    designation VARCHAR(100),
                                    qualification VARCHAR(200),
                                    experience_years INTEGER,
                                    medical_license VARCHAR(100), -- for medical staff

    -- Status
                                    is_active BOOLEAN DEFAULT true,
                                    last_login TIMESTAMP,
                                    email_verified BOOLEAN DEFAULT false,
                                    phone_verified BOOLEAN DEFAULT false,

                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Platform super admins
CREATE TABLE admin_users (
                             id SERIAL PRIMARY KEY,
                             username VARCHAR(100) UNIQUE NOT NULL,
                             email VARCHAR(255) UNIQUE NOT NULL,
                             password_hash VARCHAR(255) NOT NULL,
                             first_name VARCHAR(100) NOT NULL,
                             last_name VARCHAR(100) NOT NULL,
                             role VARCHAR(20) DEFAULT 'admin' CHECK (role IN ('super_admin', 'admin', 'moderator')),
                             is_active BOOLEAN DEFAULT true,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Medical events/activities (blood camps, medical camps, surgeries, etc.)
CREATE TABLE medical_events (
                                id SERIAL PRIMARY KEY,
                                organization_id INTEGER REFERENCES organizations(id) ON DELETE CASCADE,
                                created_by INTEGER REFERENCES organization_users(id),

    -- Event details
                                title VARCHAR(300) NOT NULL,
                                description TEXT,
                                event_type VARCHAR(50) CHECK (event_type IN ('blood_donation', 'medical_camp', 'free_surgery', 'vaccination', 'health_checkup', 'dental_camp', 'eye_camp', 'mental_health', 'nutrition', 'awareness', 'emergency_relief', 'ambulance_service')),
                                category VARCHAR(50), -- 'cardiology', 'orthopedic', 'pediatric', etc.

    -- Scheduling
                                start_date DATE NOT NULL,
                                end_date DATE,
                                start_time TIME,
                                end_time TIME,
                                duration_days INTEGER DEFAULT 1,

    -- Location
                                venue_name VARCHAR(200),
                                venue_address TEXT NOT NULL,
                                city VARCHAR(100) NOT NULL,
                                state VARCHAR(100),
                                latitude DECIMAL(10, 8),
                                longitude DECIMAL(11, 8),

    -- Capacity and resources
                                target_beneficiaries INTEGER,
                                max_capacity INTEGER,
                                current_registrations INTEGER DEFAULT 0,
                                required_volunteers INTEGER,
                                current_volunteers INTEGER DEFAULT 0,

    -- Medical resources
                                doctors_required INTEGER DEFAULT 0,
                                nurses_required INTEGER DEFAULT 0,
                                medical_equipment JSONB, -- list of required equipment
                                medicines_provided JSONB, -- list of medicines available

    -- Cost and funding
                                estimated_cost DECIMAL(12,2),
                                funding_source VARCHAR(100),
                                registration_fee DECIMAL(10,2) DEFAULT 0.00,
                                is_free BOOLEAN DEFAULT true,

    -- Media and promotion
                                banner_image_url VARCHAR(500),
                                gallery_images JSONB, -- array of image URLs
                                promotional_video_url VARCHAR(500),

    -- Status and requirements
                                status VARCHAR(20) DEFAULT 'planned' CHECK (status IN ('planned', 'approved', 'ongoing', 'completed', 'cancelled', 'postponed')),
                                approval_status VARCHAR(20) DEFAULT 'pending' CHECK (approval_status IN ('pending', 'approved', 'rejected')),
                                registration_required BOOLEAN DEFAULT false,
                                age_restrictions VARCHAR(100), -- '18-65', 'children_only', etc.
                                special_requirements TEXT,

    -- Results and impact
                                actual_beneficiaries INTEGER,
                                success_stories TEXT,
                                photos_url JSONB,
                                feedback_summary TEXT,

                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Event registrations by beneficiaries
CREATE TABLE event_registrations (
                                     id SERIAL PRIMARY KEY,
                                     event_id INTEGER REFERENCES medical_events(id) ON DELETE CASCADE,

    -- Beneficiary details
                                     first_name VARCHAR(100) NOT NULL,
                                     last_name VARCHAR(100) NOT NULL,
                                     phone VARCHAR(20) NOT NULL,
                                     email VARCHAR(255),
                                     age INTEGER,
                                     gender VARCHAR(10),
                                     blood_type VARCHAR(5),

    -- Medical information
                                     medical_history TEXT,
                                     current_medications TEXT,
                                     allergies TEXT,
                                     emergency_contact_name VARCHAR(200),
                                     emergency_contact_phone VARCHAR(20),

    -- Registration details
                                     registration_type VARCHAR(30) DEFAULT 'beneficiary' CHECK (registration_type IN ('beneficiary', 'volunteer', 'medical_staff')),
                                     preferred_time_slot VARCHAR(50),
                                     special_needs TEXT,

                                     status VARCHAR(20) DEFAULT 'registered' CHECK (status IN ('registered', 'confirmed', 'attended', 'no_show', 'cancelled')),
                                     attended_at TIMESTAMP,
                                     notes TEXT,

                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Volunteer management
CREATE TABLE volunteers (
                            id SERIAL PRIMARY KEY,
                            first_name VARCHAR(100) NOT NULL,
                            last_name VARCHAR(100) NOT NULL,
                            email VARCHAR(255) UNIQUE NOT NULL,
                            phone VARCHAR(20) NOT NULL,
                            date_of_birth DATE,
                            gender VARCHAR(10),

    -- Contact information
                            address TEXT,
                            city VARCHAR(100),
                            state VARCHAR(100),

    -- Skills and availability
                            skills JSONB, -- ['first_aid', 'translation', 'logistics', 'medical']
                            availability JSONB, -- days and time slots
                            experience_level VARCHAR(20) CHECK (experience_level IN ('beginner', 'intermediate', 'experienced')),

    -- Verification
                            background_check BOOLEAN DEFAULT false,
                            id_verification BOOLEAN DEFAULT false,
                            training_completed JSONB, -- array of completed training modules

    -- Performance
                            total_hours_volunteered INTEGER DEFAULT 0,
                            events_participated INTEGER DEFAULT 0,
                            rating DECIMAL(2,1) DEFAULT 0.0,

                            is_active BOOLEAN DEFAULT true,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Volunteer assignments to events
CREATE TABLE volunteer_assignments (
                                       id SERIAL PRIMARY KEY,
                                       event_id INTEGER REFERENCES medical_events(id) ON DELETE CASCADE,
                                       volunteer_id INTEGER REFERENCES volunteers(id) ON DELETE CASCADE,
                                       assigned_by INTEGER REFERENCES organization_users(id),

                                       role VARCHAR(50), -- 'registration_desk', 'crowd_control', 'medical_assistant'
                                       shift_start TIME,
                                       shift_end TIME,
                                       hours_worked DECIMAL(4,2) DEFAULT 0.0,

                                       status VARCHAR(20) DEFAULT 'assigned' CHECK (status IN ('assigned', 'confirmed', 'completed', 'no_show', 'cancelled')),
                                       feedback TEXT,
                                       rating INTEGER CHECK (rating >= 1 AND rating <= 5),

                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Donation and fundraising campaigns
CREATE TABLE fundraising_campaigns (
                                       id SERIAL PRIMARY KEY,
                                       organization_id INTEGER REFERENCES organizations(id) ON DELETE CASCADE,
                                       created_by INTEGER REFERENCES organization_users(id),

                                       title VARCHAR(300) NOT NULL,
                                       description TEXT NOT NULL,
                                       campaign_type VARCHAR(30) CHECK (campaign_type IN ('medical_equipment', 'patient_support', 'facility_upgrade', 'emergency_relief', 'general_fund')),

    -- Financial targets
                                       target_amount DECIMAL(15,2) NOT NULL,
                                       raised_amount DECIMAL(15,2) DEFAULT 0.00,
                                       currency VARCHAR(5) DEFAULT 'BDT',

    -- Timeline
                                       start_date DATE NOT NULL,
                                       end_date DATE NOT NULL,

    -- Media
                                       campaign_image_url VARCHAR(500),
                                       video_url VARCHAR(500),
                                       documents JSONB, -- supporting documents

    -- Status
                                       status VARCHAR(20) DEFAULT 'active' CHECK (status IN ('draft', 'active', 'paused', 'completed', 'cancelled')),
                                       approval_required BOOLEAN DEFAULT true,

    -- Transparency
                                       fund_utilization JSONB, -- how funds are being used
                                       progress_updates JSONB, -- regular updates to donors

                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Donations to campaigns
CREATE TABLE donations (
                           id SERIAL PRIMARY KEY,
                           campaign_id INTEGER REFERENCES fundraising_campaigns(id) ON DELETE CASCADE,

    -- Donor information (can be anonymous)
                           donor_name VARCHAR(200),
                           donor_email VARCHAR(255),
                           donor_phone VARCHAR(20),
                           is_anonymous BOOLEAN DEFAULT false,

    -- Donation details
                           amount DECIMAL(12,2) NOT NULL,
                           currency VARCHAR(5) DEFAULT 'BDT',
                           payment_method VARCHAR(30), -- 'card', 'bank_transfer', 'mobile_banking', 'cash'
                           transaction_id VARCHAR(100),

    -- Status
                           status VARCHAR(20) DEFAULT 'pending' CHECK (status IN ('pending', 'completed', 'failed', 'refunded')),
                           receipt_url VARCHAR(500),

    -- Tax and receipts
                           tax_deductible BOOLEAN DEFAULT true,
                           receipt_sent BOOLEAN DEFAULT false,

                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Medical resource inventory
CREATE TABLE medical_resources (
                                   id SERIAL PRIMARY KEY,
                                   organization_id INTEGER REFERENCES organizations(id) ON DELETE CASCADE,

                                   resource_type VARCHAR(50) CHECK (resource_type IN ('equipment', 'medicine', 'consumable', 'facility', 'vehicle')),
                                   name VARCHAR(200) NOT NULL,
                                   category VARCHAR(100), -- 'diagnostic', 'surgical', 'emergency', etc.
                                   description TEXT,

    -- Inventory details
                                   total_quantity INTEGER DEFAULT 0,
                                   available_quantity INTEGER DEFAULT 0,
                                   unit VARCHAR(20), -- 'pieces', 'boxes', 'bottles', etc.

    -- Specifications
                                   brand VARCHAR(100),
                                   model VARCHAR(100),
                                   specifications JSONB,
                                   purchase_date DATE,
                                   warranty_expiry DATE,

    -- Condition and maintenance
                                   condition VARCHAR(20) CHECK (condition IN ('excellent', 'good', 'fair', 'needs_repair', 'out_of_order')),
                                   last_maintenance DATE,
                                   next_maintenance_due DATE,

    -- Financial
                                   purchase_cost DECIMAL(12,2),
                                   current_value DECIMAL(12,2),

    -- Availability for sharing
                                   shareable BOOLEAN DEFAULT false,
                                   sharing_conditions TEXT,

                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Resource sharing between organizations
CREATE TABLE resource_sharing (
                                  id SERIAL PRIMARY KEY,
                                  resource_id INTEGER REFERENCES medical_resources(id) ON DELETE CASCADE,
                                  requesting_org_id INTEGER REFERENCES organizations(id),
                                  providing_org_id INTEGER REFERENCES organizations(id),

                                  quantity_requested INTEGER NOT NULL,
                                  purpose TEXT NOT NULL,
                                  start_date DATE NOT NULL,
                                  end_date DATE NOT NULL,

                                  status VARCHAR(20) DEFAULT 'pending' CHECK (status IN ('pending', 'approved', 'ongoing', 'completed', 'rejected', 'cancelled')),
                                  approval_notes TEXT,

    -- Terms
                                  cost_per_unit DECIMAL(10,2) DEFAULT 0.00,
                                  total_cost DECIMAL(12,2) DEFAULT 0.00,
                                  deposit_required DECIMAL(12,2) DEFAULT 0.00,

                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Reviews and ratings for organizations
CREATE TABLE organization_reviews (
                                      id SERIAL PRIMARY KEY,
                                      organization_id INTEGER REFERENCES organizations(id) ON DELETE CASCADE,
                                      reviewer_name VARCHAR(200),
                                      reviewer_email VARCHAR(255),
                                      reviewer_phone VARCHAR(20),

                                      rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
                                      review_text TEXT,
                                      event_id INTEGER REFERENCES medical_events(id), -- if review is for specific event

    -- Review categories
                                      service_quality INTEGER CHECK (service_quality >= 1 AND service_quality <= 5),
                                      staff_behavior INTEGER CHECK (staff_behavior >= 1 AND staff_behavior <= 5),
                                      facility_rating INTEGER CHECK (facility_rating >= 1 AND facility_rating <= 5),
                                      recommendation BOOLEAN,

    -- Moderation
                                      is_verified BOOLEAN DEFAULT false,
                                      is_visible BOOLEAN DEFAULT true,
                                      moderated_by INTEGER REFERENCES admin_users(id),

                                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Collaboration between organizations
CREATE TABLE organization_partnerships (
                                           id SERIAL PRIMARY KEY,
                                           organization_1_id INTEGER REFERENCES organizations(id),
                                           organization_2_id INTEGER REFERENCES organizations(id),

                                           partnership_type VARCHAR(50) CHECK (partnership_type IN ('resource_sharing', 'joint_events', 'referral', 'capacity_building', 'funding')),
                                           title VARCHAR(200) NOT NULL,
                                           description TEXT,

                                           start_date DATE NOT NULL,
                                           end_date DATE,

                                           status VARCHAR(20) DEFAULT 'active' CHECK (status IN ('proposed', 'active', 'completed', 'suspended', 'terminated')),

    -- Terms and conditions
                                           terms JSONB,
                                           shared_resources JSONB,
                                           responsibilities JSONB,

                                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           CHECK (organization_1_id != organization_2_id)
    );

-- Success stories and impact reports
CREATE TABLE impact_stories (
                                id SERIAL PRIMARY KEY,
                                organization_id INTEGER REFERENCES organizations(id) ON DELETE CASCADE,
                                event_id INTEGER REFERENCES medical_events(id),

                                title VARCHAR(300) NOT NULL,
                                story_text TEXT NOT NULL,
                                patient_name VARCHAR(200), -- can be anonymized
                                age INTEGER,
                                condition_treated VARCHAR(200),

    -- Before/after details
                                before_description TEXT,
                                after_description TEXT,
                                treatment_duration VARCHAR(100),

    -- Media
                                images JSONB, -- array of image URLs
                                video_url VARCHAR(500),

    -- Impact metrics
                                lives_saved INTEGER DEFAULT 0,
                                people_helped INTEGER DEFAULT 1,
                                cost_saved DECIMAL(12,2),

    -- Privacy and consent
                                consent_obtained BOOLEAN DEFAULT false,
                                is_anonymous BOOLEAN DEFAULT false,

    -- Publication
                                is_published BOOLEAN DEFAULT false,
                                featured BOOLEAN DEFAULT false,

                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- News and announcements
CREATE TABLE news_announcements (
                                    id SERIAL PRIMARY KEY,
                                    organization_id INTEGER REFERENCES organizations(id) ON DELETE CASCADE,
                                    author_id INTEGER REFERENCES organization_users(id),

                                    title VARCHAR(300) NOT NULL,
                                    content TEXT NOT NULL,
                                    announcement_type VARCHAR(30) CHECK (announcement_type IN ('news', 'announcement', 'alert', 'achievement', 'event_update')),

    -- Media
                                    featured_image_url VARCHAR(500),
                                    attachments JSONB,

    -- Targeting
                                    target_audience VARCHAR(50) DEFAULT 'public' CHECK (target_audience IN ('public', 'volunteers', 'partners', 'staff', 'donors')),
                                    priority VARCHAR(10) DEFAULT 'normal' CHECK (priority IN ('low', 'normal', 'high', 'urgent')),

    -- Publication
                                    is_published BOOLEAN DEFAULT false,
                                    publish_date TIMESTAMP,
                                    expires_at TIMESTAMP,

    -- Engagement
                                    views_count INTEGER DEFAULT 0,
                                    likes_count INTEGER DEFAULT 0,
                                    shares_count INTEGER DEFAULT 0,

                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Analytics and reporting
CREATE TABLE analytics_events (
                                  id SERIAL PRIMARY KEY,
                                  organization_id INTEGER REFERENCES organizations(id),
                                  event_type VARCHAR(50) NOT NULL, -- 'page_view', 'event_registration', 'donation', etc.
                                  event_data JSONB,
                                  user_session VARCHAR(255),
                                  ip_address INET,
                                  user_agent TEXT,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for performance
CREATE INDEX idx_organizations_type ON organizations(organization_type);
CREATE INDEX idx_organizations_city ON organizations(city);
CREATE INDEX idx_organizations_verification ON organizations(verification_status);
CREATE INDEX idx_medical_events_type ON medical_events(event_type);
CREATE INDEX idx_medical_events_date ON medical_events(start_date);
CREATE INDEX idx_medical_events_city ON medical_events(city);
CREATE INDEX idx_medical_events_status ON medical_events(status);
CREATE INDEX idx_event_registrations_event ON event_registrations(event_id);
CREATE INDEX idx_volunteers_city ON volunteers(city);
CREATE INDEX idx_volunteers_skills ON volunteers USING GIN(skills);
CREATE INDEX idx_fundraising_campaigns_org ON fundraising_campaigns(organization_id);
CREATE INDEX idx_donations_campaign ON donations(campaign_id);
CREATE INDEX idx_medical_resources_org ON medical_resources(organization_id);
CREATE INDEX idx_medical_resources_type ON medical_resources(resource_type);

-- Triggers for updating timestamps
CREATE TRIGGER update_organizations_updated_at BEFORE UPDATE ON organizations FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_organization_users_updated_at BEFORE UPDATE ON organization_users FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_medical_events_updated_at BEFORE UPDATE ON medical_events FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();