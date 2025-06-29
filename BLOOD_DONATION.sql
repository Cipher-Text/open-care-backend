-- Blood Donor Application Database Schema

-- Users table (both donors and recipients)
CREATE TABLE users (
id SERIAL PRIMARY KEY,
email VARCHAR(255) UNIQUE NOT NULL,
password_hash VARCHAR(255) NOT NULL,
first_name VARCHAR(100) NOT NULL,
last_name VARCHAR(100) NOT NULL,
phone VARCHAR(20),
date_of_birth DATE NOT NULL,
gender VARCHAR(10) CHECK (gender IN ('Male', 'Female', 'Other')),
blood_type VARCHAR(5) NOT NULL CHECK (blood_type IN ('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-')),
weight DECIMAL(5,2), -- in kg
last_donation_date DATE,
is_eligible_to_donate BOOLEAN DEFAULT true,
user_type VARCHAR(20) DEFAULT 'donor' CHECK (user_type IN ('donor', 'recipient', 'both')),
profile_image_url VARCHAR(500),
address TEXT,
city VARCHAR(100),
state VARCHAR(100),
country VARCHAR(100) DEFAULT 'Bangladesh',
postal_code VARCHAR(20),
latitude DECIMAL(10, 8),
longitude DECIMAL(11, 8),
is_active BOOLEAN DEFAULT true,
email_verified BOOLEAN DEFAULT false,
phone_verified BOOLEAN DEFAULT false,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Blood donations tracking
CREATE TABLE donations (
id SERIAL PRIMARY KEY,
donor_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
blood_bank_id INTEGER REFERENCES blood_banks(id),
donation_date DATE NOT NULL,
blood_type VARCHAR(5) NOT NULL,
quantity_ml INTEGER DEFAULT 450, -- standard donation is 450ml
donation_type VARCHAR(20) DEFAULT 'whole_blood' CHECK (donation_type IN ('whole_blood', 'plasma', 'platelets', 'double_red')),
health_status VARCHAR(20) DEFAULT 'healthy' CHECK (health_status IN ('healthy', 'sick', 'medication')),
hemoglobin_level DECIMAL(3,1), -- g/dL
blood_pressure VARCHAR(20),
pulse_rate INTEGER,
temperature DECIMAL(3,1),
notes TEXT,
status VARCHAR(20) DEFAULT 'completed' CHECK (status IN ('scheduled', 'completed', 'cancelled', 'rejected')),
verified_by INTEGER REFERENCES users(id), -- medical staff who verified
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Blood banks/hospitals
CREATE TABLE blood_banks (
id SERIAL PRIMARY KEY,
name VARCHAR(200) NOT NULL,
type VARCHAR(50) CHECK (type IN ('blood_bank', 'hospital', 'clinic', 'mobile_unit')),
license_number VARCHAR(100),
email VARCHAR(255),
phone VARCHAR(20),
address TEXT NOT NULL,
city VARCHAR(100) NOT NULL,
state VARCHAR(100),
country VARCHAR(100) DEFAULT 'Bangladesh',
postal_code VARCHAR(20),
latitude DECIMAL(10, 8),
longitude DECIMAL(11, 8),
operating_hours JSONB, -- flexible schedule storage
services JSONB, -- array of services offered
is_active BOOLEAN DEFAULT true,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Blood requests/needs
CREATE TABLE blood_requests (
id SERIAL PRIMARY KEY,
requester_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
patient_name VARCHAR(200) NOT NULL,
patient_age INTEGER,
patient_gender VARCHAR(10),
blood_type VARCHAR(5) NOT NULL,
blood_component VARCHAR(20) DEFAULT 'whole_blood' CHECK (blood_component IN ('whole_blood', 'plasma', 'platelets', 'red_cells')),
quantity_bags INTEGER NOT NULL,
urgency_level VARCHAR(20) DEFAULT 'normal' CHECK (urgency_level IN ('emergency', 'urgent', 'normal')),
needed_by_date DATE NOT NULL,
hospital_name VARCHAR(200),
hospital_address TEXT,
contact_person VARCHAR(200),
contact_phone VARCHAR(20) NOT NULL,
description TEXT,
special_requirements TEXT,
city VARCHAR(100) NOT NULL,
state VARCHAR(100),
latitude DECIMAL(10, 8),
longitude DECIMAL(11, 8),
status VARCHAR(20) DEFAULT 'active' CHECK (status IN ('active', 'fulfilled', 'expired', 'cancelled')),
fulfilled_date DATE,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Donation responses to blood requests
CREATE TABLE donation_responses (
id SERIAL PRIMARY KEY,
request_id INTEGER REFERENCES blood_requests(id) ON DELETE CASCADE,
donor_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
response_type VARCHAR(20) DEFAULT 'interested' CHECK (response_type IN ('interested', 'committed', 'donated')),
message TEXT,
preferred_date DATE,
status VARCHAR(20) DEFAULT 'pending' CHECK (status IN ('pending', 'accepted', 'rejected', 'completed')),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
UNIQUE(request_id, donor_id)
);

-- Reward system - user levels and points
CREATE TABLE user_rewards (
id SERIAL PRIMARY KEY,
user_id INTEGER REFERENCES users(id) ON DELETE CASCADE UNIQUE,
total_donations INTEGER DEFAULT 0,
current_level VARCHAR(50) DEFAULT 'FIRST_DROP',
total_points INTEGER DEFAULT 0,
lifetime_points INTEGER DEFAULT 0, -- never decreases
level_achieved_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
badges JSONB DEFAULT '[]', -- array of earned badges
achievements JSONB DEFAULT '[]', -- array of achievements
streak_count INTEGER DEFAULT 0, -- consecutive donation streak
longest_streak INTEGER DEFAULT 0,
last_points_earned INTEGER DEFAULT 0,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Points transaction history
CREATE TABLE points_transactions (
id SERIAL PRIMARY KEY,
user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
transaction_type VARCHAR(30) CHECK (transaction_type IN ('earned', 'redeemed', 'bonus', 'penalty')),
points INTEGER NOT NULL,
reason VARCHAR(100), -- 'donation', 'referral', 'level_up', 'redeem_reward'
reference_id INTEGER, -- could reference donation_id, referral_id, etc.
reference_type VARCHAR(30), -- 'donation', 'referral', 'manual'
description TEXT,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Physical rewards and redemptions
CREATE TABLE rewards_catalog (
id SERIAL PRIMARY KEY,
title VARCHAR(200) NOT NULL,
description TEXT,
points_required INTEGER NOT NULL,
reward_type VARCHAR(30) CHECK (reward_type IN ('physical', 'digital', 'service', 'discount')),
category VARCHAR(50), -- 'merchandise', 'health', 'voucher', 'certificate'
image_url VARCHAR(500),
terms_conditions TEXT,
stock_quantity INTEGER,
is_active BOOLEAN DEFAULT true,
valid_from DATE,
valid_until DATE,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Reward redemptions
CREATE TABLE reward_redemptions (
id SERIAL PRIMARY KEY,
user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
reward_id INTEGER REFERENCES rewards_catalog(id),
points_used INTEGER NOT NULL,
redemption_code VARCHAR(50) UNIQUE,
status VARCHAR(20) DEFAULT 'pending' CHECK (status IN ('pending', 'processing', 'shipped', 'delivered', 'cancelled')),
shipping_address TEXT,
tracking_number VARCHAR(100),
redeemed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
fulfilled_at TIMESTAMP,
notes TEXT
);

-- Notifications system
CREATE TABLE notifications (
id SERIAL PRIMARY KEY,
user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
title VARCHAR(200) NOT NULL,
message TEXT NOT NULL,
type VARCHAR(30) CHECK (type IN ('donation_reminder', 'blood_request', 'level_up', 'reward', 'general')),
priority VARCHAR(10) DEFAULT 'normal' CHECK (priority IN ('low', 'normal', 'high', 'urgent')),
is_read BOOLEAN DEFAULT false,
action_url VARCHAR(500),
metadata JSONB, -- additional data for the notification
scheduled_for TIMESTAMP,
sent_at TIMESTAMP,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- User sessions for mobile/web app
CREATE TABLE user_sessions (
id SERIAL PRIMARY KEY,
user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
session_token VARCHAR(255) UNIQUE NOT NULL,
device_info JSONB,
ip_address INET,
expires_at TIMESTAMP NOT NULL,
is_active BOOLEAN DEFAULT true,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Referral system
CREATE TABLE referrals (
id SERIAL PRIMARY KEY,
referrer_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
referred_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
referral_code VARCHAR(20),
status VARCHAR(20) DEFAULT 'pending' CHECK (status IN ('pending', 'completed', 'expired')),
reward_points INTEGER DEFAULT 0,
completed_at TIMESTAMP,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
UNIQUE(referrer_id, referred_id)
);

-- Audit trail for important operations
CREATE TABLE audit_logs (
id SERIAL PRIMARY KEY,
user_id INTEGER REFERENCES users(id),
action VARCHAR(100) NOT NULL,
table_name VARCHAR(50),
record_id INTEGER,
old_values JSONB,
new_values JSONB,
ip_address INET,
user_agent TEXT,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for better performance
CREATE INDEX idx_users_blood_type ON users(blood_type);
CREATE INDEX idx_users_city ON users(city);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_donations_donor_id ON donations(donor_id);
CREATE INDEX idx_donations_date ON donations(donation_date);
CREATE INDEX idx_blood_requests_blood_type ON blood_requests(blood_type);
CREATE INDEX idx_blood_requests_city ON blood_requests(city);
CREATE INDEX idx_blood_requests_status ON blood_requests(status);
CREATE INDEX idx_blood_requests_urgency ON blood_requests(urgency_level);
CREATE INDEX idx_notifications_user_id ON notifications(user_id);
CREATE INDEX idx_notifications_unread ON notifications(user_id, is_read);
CREATE INDEX idx_points_transactions_user_id ON points_transactions(user_id);

-- Triggers for updating timestamps
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_donations_updated_at BEFORE UPDATE ON donations FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_blood_requests_updated_at BEFORE UPDATE ON blood_requests FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_user_rewards_updated_at BEFORE UPDATE ON user_rewards FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Sample data for testing
INSERT INTO blood_banks (name, type, email, phone, address, city, latitude, longitude) VALUES
('Dhaka Medical College Hospital', 'hospital', 'info@dmch.gov.bd', '+880-2-9661060', 'Ramna, Dhaka-1000', 'Dhaka', 23.7280, 90.3915),
('Red Crescent Blood Bank', 'blood_bank', 'info@redcrescent.org.bd', '+880-2-9563113', 'Mohammadpur, Dhaka', 'Dhaka', 23.7616, 90.3565),
('Chittagong Medical College Hospital', 'hospital', 'info@cmch.gov.bd', '+880-31-2503900', 'Chittagong Medical College, Chittagong', 'Chittagong', 22.3569, 91.7832);