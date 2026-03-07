-- Create tenant_subject table in platform
USE platform;

CREATE TABLE IF NOT EXISTS tenant_subject (
  tenant_subject_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  tenant_id BIGINT UNSIGNED NOT NULL,
  subject_id INT NOT NULL,
  tenant_subject_selected BIT(1) NOT NULL DEFAULT b'0',
  version INT NOT NULL DEFAULT 1,
  PRIMARY KEY (tenant_subject_id),
  KEY fk_ts_tenant (tenant_id),
  KEY fk_ts_subject (subject_id),
  CONSTRAINT fk_ts_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (tenant_id),
  CONSTRAINT fk_ts_subject FOREIGN KEY (subject_id) REFERENCES subject (subject_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SELECT 'tenant_subject created successfully';

-- Insert a default tenant if none exists
INSERT INTO tenant (name, active, version)
SELECT 'default', b'1', 1
WHERE NOT EXISTS (SELECT 1 FROM tenant LIMIT 1);

SELECT 'Tenant count:';
SELECT COUNT(*) FROM tenant;
SELECT 'Subject count:';
SELECT COUNT(*) FROM subject;
SELECT 'TenantSubject count:';
SELECT COUNT(*) FROM tenant_subject;
