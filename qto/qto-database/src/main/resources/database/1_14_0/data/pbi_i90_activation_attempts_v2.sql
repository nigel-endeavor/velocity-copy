DROP VIEW IF EXISTS pbi_i90_activation_attempts CASCADE;
CREATE OR REPLACE VIEW pbi_i90_activation_attempts AS
SELECT s.service_id AS 'Service ID',
       s.tenant_id AS 'Tenant ID',
       s.current_inventory AS 'Current Inventory',
       aa.activation_attempt_id AS 'Activation Attempt ID',
       CONCAT('svc-', s.service_id,
              IF(aa.activation_attempt_id IS NULL, '',
                 CONCAT('aa-', aa.activation_attempt_id))) AS 'Composite ID',
       scheduled_attempt_status AS 'Scheduled Attempt Status',
       internal_tech_assigned AS 'Internal Tech Assigned',
       field_dispatch_vendor AS 'Field Dispatch Vendor',
       ftdi_vendor_id AS 'FTDI Vendor ID',
       field_tech_name AS 'Field Tech Name',
       field_tech_phone AS 'Field Tech Phone',
       field_tech_check_in AS 'FT Check-in Time',
       field_tech_check_out AS 'FT Check-out Time',
       tested_download_speed AS 'Tested Download Speed',
       tested_upload_speed AS 'Tested Upload Speed',
       latency AS Latency,
       backup_download_speed AS 'Backup Download Speed',
       backup_upload_speed AS 'Backup Upload Speed',
       signal_rsrp AS 'Signal/RSRP',
       sinr_rsrq AS 'SINR/RSRQ',
       primary_uid AS 'Primary UID',
       secondary_uid AS 'Secondary UID',
       managed_router_serial_number AS 'Managed Router Serial Number',
       location_downtown_for_cutover AS 'Location Downtime For Cutover',
       closeout_code AS 'Closeout Code',
       network_complete_date AS 'Network Complete Date',
       voip_complete_date AS 'UCaaS Complete Date',
       replace_4g_5g AS 'Replace 4G/5G with Broadband/DIA',
       issue_notes AS 'Internal TTU Notes',
       scheduled_check_in_time AS 'Scheduled Check-in Time',
       attempt_number 'Attempt Number',
       cancelled_date AS 'Cancelled Date',
       cancelled_by AS 'Cancelled By',
       close_notes AS 'Close Notes',
       IF(cancelled_date IS NOT NULL AND
          DATEDIFF(scheduled_check_in_time, cancelled_date) >= 0 AND
#           # calculates datediff excluding weekends
          (5 * (DATEDIFF(scheduled_check_in_time, cancelled_date) DIV 7) +
           CAST(SUBSTRING(
               7 * WEEKDAY(cancelled_date) + WEEKDAY(scheduled_check_in_time) + 1 FROM 0123444401233334012222340111123400012345001234550 FOR 1) AS integer)) < 3, 1, 0) AS 'Second Day Cancel',
       same_day_schedule AS 'Same Day Schedule',
       ftdi_vendor_status AS 'Vendor Status'

FROM service s
     JOIN activation_attempt aa ON s.service_id = aa.service_id
WHERE s.tenant_id != (SELECT tenant_id FROM v_tenant WHERE name = 'Endeavor');
