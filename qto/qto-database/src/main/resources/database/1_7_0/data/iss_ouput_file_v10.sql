DROP VIEW IF EXISTS v_iss_output_file CASCADE;
CREATE OR REPLACE VIEW v_iss_output_file AS

SELECT
  l.order_id,
       s.tenant_id,
       l.location_id,
       s.service_id,
       s.ticket_number,
       DATE_FORMAT(apptDate.apptDate, '%m/%d/%Y') AS appointment_date,
       DATE_FORMAT(apptDate.ApptTime, '%h:%i %p') AS appointment_time,
       NULL scheduling_segment,
       (SELECT ism.iss_status
        FROM iss_status_mapping ism
        WHERE ism.vertek_status = s.service_status
		      AND ism.tenant_id = s.tenant_id
        LIMIT 1) installation_status,
       DATE_FORMAT(s.last_status_change, '%m/%d/%Y %h:%i:%s %p') AS installation_status_date,
       NULL project,
       closeout_code signonff_number,
       NULL demarc,
       NULL preinstallation_checklist_complete,
       s.provider_order_num supplier_order_number,

       DATE_FORMAT(
       case when hour(submitted.carrier_order_submitted) = 0 and minute(submitted.carrier_order_submitted) = 0 and second(submitted.carrier_order_submitted) = 0
				 then submitted.carrier_order_submitted
       when hour(submitted.carrier_order_submitted) = 4 and minute(submitted.carrier_order_submitted) = 0 and second(submitted.carrier_order_submitted) = 0
         then DATE_ADD(submitted.carrier_order_submitted, INTERVAL -4 HOUR)
       else DATE_ADD(submitted.carrier_order_submitted, INTERVAL -5 HOUR) end, '%m/%d/%Y %h:%i:%s %p') carrier_order_submitted,

       (SELECT jeop_description
        FROM (SELECT sji.service_id, ji.jeop_description, ji.start_date, ji.end_date
              FROM service_jeop_instance sji
                   JOIN jeop_instance ji ON sji.jeop_instance_id = ji.jeop_instance_id
              WHERE ji.end_date IS NULL
              UNION
              SELECT s.service_id, ji.jeop_description, ji.start_date, ji.end_date
              FROM service s
                   JOIN location l ON s.location_id = l.location_id
                   LEFT JOIN location_jeop_instance lji ON l.location_id = lji.location_id
                   LEFT JOIN jeop_instance ji ON lji.jeop_instance_id = ji.jeop_instance_id
              WHERE ji.end_date IS NULL
		            AND lji.location_id IS NOT NULL) innerJeop
        WHERE end_date IS NULL
		      AND service_id = s.service_id
        ORDER BY start_date DESC
        LIMIT 1) jeop_description,

       DATE_FORMAT(
       case when hour(foc.provider_foc) = 0 and minute(foc.provider_foc) = 0 and second(foc.provider_foc) = 0
				 then foc.provider_foc
       when hour(foc.provider_foc) = 4 and minute(foc.provider_foc) = 0 and second(foc.provider_foc) = 0
         then DATE_ADD(foc.provider_foc, INTERVAL -4 HOUR)
       else DATE_ADD(foc.provider_foc, INTERVAL -5 HOUR) end, '%m/%d/%Y %h:%i:%s %p') provider_foc,

       NULL estimated_foc_date,

       DATE_FORMAT(
       case when hour(cbs.customer_bill_start) = 0 and minute(cbs.customer_bill_start) = 0 and second(cbs.customer_bill_start) = 0
				 then cbs.customer_bill_start
       when hour(cbs.customer_bill_start) = 4 and minute(cbs.customer_bill_start) = 0 and second(cbs.customer_bill_start) = 0
         then DATE_ADD(cbs.customer_bill_start, INTERVAL -4 HOUR)
       else DATE_ADD(cbs.customer_bill_start, INTERVAL -5 HOUR) end, '%m/%d/%Y %h:%i:%s %p') customer_bill_start,

       s.service_billed_to AS circuit_owner,
       NULL local_lec_circuit_id,
       CONCAT(IFNULL(media_type, ''),
              IF(s.service_type IS NULL, '', CONCAT(' ', service_type)),
              IF(s.speed IS NULL, '', CONCAT(' ', s.speed))) AS  subProduct_type,
       CONCAT(IFNULL(s.provider, ''),
              IF(s.provider_circuit_id IS NULL, '', CONCAT(' - ', s.provider_circuit_id))) AS LECProvider,
  provider,
  provider_circuit_id,
       NULL supplier_tn,
       s.lan_ips static_ip,
       bs.pppoe_username,
       bs.pppoe_password,
       NULL vpi,
       NULL vci,
       s.provider_circuit_id dsl_number,
       NULL dsl_connection_type,
       NULL dsl_1fb_order,
       NULL dsl_owner,
       NULL dsl_provider,
       NULL router_make_model,
       NULL router_own_lease,
       NULL router_shipped,
       NULL router_configured,
       NULL router_tracking_number,
       NULL modem_make,
       NULL modem_shipped,
       NULL modem_on_site,
       NULL modem_tracking_number,
       NULL modem_ownership,
       NULL aircard_product,
       NULL aircard_provider,
       NULL aircard_tn,
       NULL aircard_status,
       NULL aircard_status_date,
       bs.mac_address modem_serial_number,
       NULL host_oms_number,
       NULL dsl_line_type,
       s.service_description site_description,
       NULL wan_router_ip,
       primary_uid AS wan_ips,
       secondary_uid AS lan_ips,
       SUBSTRING(s.download_speed, 1, LENGTH(s.download_speed) - 1) download_speed,
       CASE SUBSTRING(s.download_speed, -1)
	       WHEN 'K'
		       THEN 'kbps'
	       WHEN 'M'
		       THEN 'mbps'
	       ELSE NULL
	       END download_speed_type,
       SUBSTRING(s.upload_speed, 1, LENGTH(s.upload_speed) - 1) upload_speed,
       CASE SUBSTRING(s.upload_speed, -1)
	       WHEN 'K'
		       THEN 'kbps'
	       WHEN 'M'
		       THEN 'mbps'
	       ELSE NULL
	       END upload_speed_type,
       NULL mos_score,

       DATE_FORMAT(
       case when hour(complete.complete_date) = 0 and minute(complete.complete_date) = 0 and second(complete.complete_date) = 0
				 then complete.complete_date
       when hour(complete.complete_date) = 4 and minute(complete.complete_date) = 0 and second(complete.complete_date) = 0
         then DATE_ADD(complete.complete_date, INTERVAL -4 HOUR)
       else DATE_ADD(complete.complete_date, INTERVAL -5 HOUR) end, '%m/%d/%Y %h:%i:%s %p') complete_date,

       s.service_status ticket_status,

       DATE_FORMAT(
       case when hour(cri.customer_requested_install) = 0 and minute(cri.customer_requested_install) = 0 and second(cri.customer_requested_install) = 0
				 then cri.customer_requested_install
       when hour(cri.customer_requested_install) = 4 and minute(cri.customer_requested_install) = 0 and second(cri.customer_requested_install) = 0
         then DATE_ADD(cri.customer_requested_install, INTERVAL -4 HOUR)
       else DATE_ADD(cri.customer_requested_install, INTERVAL -5 HOUR) end, '%m/%d/%Y %h:%i:%s %p') customer_requested_install,

       NULL erfu_ptd_date,
       NULL revised_completion_date,
       NULL client_due_date,
       NULL wap_serial_number,
       NULL wap_serial_number_2,
       NULL wap_serial_number_3,
       NULL AS lec_contract_expiry_date,
       NULL pos,
       s.provider_site_account_num supplier_ban,
       sd.store_downtime,
       NULL network_use,
       s.client_service_id system_asset_number


FROM location l
     JOIN service s ON l.location_id = s.location_id
     LEFT JOIN broadband_service bs ON s.service_id = bs.service_id
     LEFT JOIN (SELECT sch.service_id,
                       MAX(fa.appointment_date) AS apptDate,
                       MAX(DATE_ADD(fa.window_start, INTERVAL 1 HOUR)) AS ApptTime
                FROM ftdi_appointment fa
                     JOIN ftdi_dispatch fd ON fa.ftdi_dispatch_id = fd.ftdi_dispatch_id
                     JOIN activation_schedule sch ON fd.schedule_id = sch.activation_schedule_id
                GROUP BY sch.service_id) apptDate ON s.service_id = apptDate.service_id
     LEFT JOIN (SELECT smi.service_id, milestone_date carrier_order_submitted
                FROM milestone_instance mi
                     JOIN service_milestone_instance smi ON mi.milestone_instance_id = smi.milestone_instance_id
                     JOIN milestone m ON mi.milestone_id = m.milestone_id
                WHERE milestone_code = 'PROVIDER_ORDER_SUBMITTED') submitted ON s.service_id = submitted.service_id
     LEFT JOIN (SELECT smi.service_id, milestone_date provider_foc
                FROM milestone_instance mi
                     JOIN service_milestone_instance smi ON mi.milestone_instance_id = smi.milestone_instance_id
                     JOIN milestone m ON mi.milestone_id = m.milestone_id
                WHERE milestone_code = 'NETWORK_PROVIDER_FOC') foc ON s.service_id = foc.service_id
     LEFT JOIN (SELECT smi.service_id, milestone_date customer_bill_start
                FROM milestone_instance mi
                     JOIN service_milestone_instance smi ON mi.milestone_instance_id = smi.milestone_instance_id
                     JOIN milestone m ON mi.milestone_id = m.milestone_id
                WHERE milestone_code = 'DATA_PROVISIONING_COMPLETE') cbs ON s.service_id = cbs.service_id
     LEFT JOIN (SELECT smi.service_id, milestone_date complete_date
                FROM milestone_instance mi
                     JOIN service_milestone_instance smi ON mi.milestone_instance_id = smi.milestone_instance_id
                     JOIN milestone m ON mi.milestone_id = m.milestone_id
                WHERE milestone_code = 'COMPLETE') complete ON s.service_id = complete.service_id
     LEFT JOIN (SELECT smi.service_id, milestone_date customer_requested_install
                FROM milestone_instance mi
                     JOIN service_milestone_instance smi ON mi.milestone_instance_id = smi.milestone_instance_id
                     JOIN milestone m ON mi.milestone_id = m.milestone_id
                WHERE milestone_code = 'CUSTOMER_REQUESTED_INSTALL') cri ON s.service_id = cri.service_id
     LEFT JOIN (SELECT service_id,
                       MAX(location_downtown_for_cutover) store_downtime,
                       MAX(closeout_code) AS closeout_code,
                       MAX(primary_uid) AS primary_uid,
                       MAX(secondary_uid) AS secondary_uid
                FROM activation_attempt
                WHERE activation_attempt.scheduled_attempt_status = 'Complete'
                GROUP BY service_id) sd ON s.service_id = sd.service_id
WHERE s.update_client = TRUE
	AND s.eligible_for_update = TRUE
  AND s.service_status <> 'Change In Assignment'
AND s.tenant_id = (Select tenant_id from v_tenant t where t.name = 'Endeavor');


