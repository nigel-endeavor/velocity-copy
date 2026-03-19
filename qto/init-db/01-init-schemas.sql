-- PostgreSQL initialization script for qto-main database
-- Runs automatically on first container start via Docker entrypoint

-- Create the platform schema (used for multi-tenant infrastructure)
CREATE SCHEMA IF NOT EXISTS platform;

-- Grant usage to the application user
GRANT ALL ON SCHEMA platform TO qto_user;
GRANT ALL ON SCHEMA public TO qto_user;
