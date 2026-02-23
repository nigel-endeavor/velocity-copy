DROP VIEW IF EXISTS v_manage_cyber_services CASCADE;
CREATE OR REPLACE VIEW v_manage_cyber_services AS
SELECT s.service_id,
       s.location_id,
       o.order_id,
       l.client_location_id,
       mc.company_name AS parent_company_name,
       mc.company_id AS master_customer_id,
       mc.client_id AS parent_company_client_id,
       ec.company_name,
       ec.company_id,
       ec.client_id AS end_customer_client_id,
       s.service_type,
       CONCAT(IFNULL(l.address_1, ''),
              CASE WHEN l.address_2 IS NOT NULL AND TRIM(COALESCE(l.address_2,'')) <> '' THEN CONCAT(' ', l.address_2) ELSE '' END,
              '\n',
              IFNULL(l.city, ''), ', ',
              IFNULL(l.state_province, ''), ' ',
              IFNULL(l.postal_code, '')
       ) AS address,
       l.address_1,
       l.address_2,
       l.city,
       l.state_province,
       l.postal_code,
       s.service_status,
       s.follow_up_date,
       COALESCE((SELECT display_name FROM v_subject WHERE subject_id = o.provisioner), 'Unassigned') AS provisioner,
       COALESCE((SELECT display_name FROM v_subject WHERE subject_id = o.vertek_project_manager),
                'Unassigned') AS i90_project_manager,
       s.progress_percentage,
       s.provider,
       COALESCE(DATEDIFF(NOW(), last_status_change), 0) AS status_age,
       s.service_mrc,
       s.service_nrc,
       (SELECT milestone_name
        FROM v_service_milestone_instance smi
        WHERE smi.service_id = s.service_id
        ORDER BY milestone_instance_id DESC
        LIMIT 1) AS greatest_milestone_name,
       (SELECT milestone_date
        FROM v_service_milestone_instance smi
        WHERE smi.service_id = s.service_id
        ORDER BY milestone_instance_id DESC
        LIMIT 1) AS greatest_milestone_date,
       s.version,
       s.tenant_id,
       s.active,
       SUBSTRING((SELECT note FROM note WHERE note_id = latest_note.note_id), 1, 500) AS latest_note,
       linked,
       bundled,
       linked_bundled_parent,
       linked_bundled_parent_id,
       equip.equipment_count,
       equip.equipment_types,
       vsmi.customer_requested_install,
       vsmi.created,
       vsmi.tech_data_gathering_form_sent,
       vsmi.tech_data_gathering_meeting_scheduled,
       vsmi.tech_data_gathering_meeting_completed,
       vsmi.email_usm_anywhere_template_requirements,
       vsmi.inventory_assignment_verified,
       vsmi.new_usm_anywhere_server_build,
       vsmi.implementation_qa,
       vsmi.verify_assets_in_siem_db,
       vsmi.verify_logging_data_source,
       vsmi.schedule_vulnerability_scans,
       vsmi.bulk_alarm_tuning_phase,
       vsmi.siem_event_filtering,
       vsmi.filters_built_for_reports,
       vsmi.default_alarm_rule_additions,
       vsmi.custom_alarm_rule_additions,
       vsmi.forward_alarms_to_usm_central,
       vsmi.forward_alarms_to_d3Soc_live,
       vsmi.host_list_provided_by_client,
       vsmi.halcyon_package_given_to_client,
       vsmi.halcyon_deployed_to_hosts,
       vsmi.devops_notified_of_halcyon_addition,
       vsmi.halcyon_api_token_added_to_d3,
       vsmi.d3_connection_verified,
       vsmi.end_learning_mode,
       vsmi.test_email_sent_to_client,
       vsmi.deploy_consulting_tenant,
       vsmi.deploy_vulnerability_scans,
       vsmi.provided_customer_with_report,
       vsmi.discuss_future_cyrisma_management,
       vsmi.review_existing_ca_and_mfa_policies,
       vsmi.sign_in_policies_enabled,
       vsmi.conditional_access_policy_verification,
       vsmi.geographic_restrictions_enabled,
       vsmi.device_compliance_enabled,
       vsmi.password_reset_enabled_for_self_service,
       vsmi.break_glass_account_configured,
       vsmi.pim_enablement,
       vsmi.implementation_verified,
       s.order_type
FROM service s
     JOIN location l ON s.location_id = l.location_id
     JOIN orders o ON l.order_id = o.order_id
     JOIN company ec ON o.company_id = ec.company_id
     LEFT JOIN company mc ON ec.master_customer_id = mc.company_id
     LEFT JOIN
     (SELECT se.service_id,
             COUNT(e.equipment_id) as equipment_count,
             string_agg(DISTINCT e.equipment_type, ',') AS equipment_types
      FROM service_equipment se
           JOIN equipment e ON se.equipment_id = e.equipment_id
      GROUP BY se.service_id) equip ON s.service_id = equip.service_id
     LEFT JOIN (SELECT s.service_id,
                       MAX(CASE WHEN vsmi.milestone_code = 'CUSTOMER_REQUESTED_INSTALL'
	                                THEN vsmi.milestone_date END) AS customer_requested_install,
                       MAX(CASE WHEN vsmi.milestone_code = 'CREATED'
	                                THEN vsmi.milestone_date END) AS created,
                       MAX(CASE WHEN vsmi.milestone_code = 'TECH_DATA_GATHERING_FORM_SENT'
	                                THEN vsmi.milestone_date END) AS tech_data_gathering_form_sent,
                       MAX(CASE WHEN vsmi.milestone_code = 'TECH_DATA_GATHERING_MEETING_SCHEDULED'
	                                THEN vsmi.milestone_date END) AS tech_data_gathering_meeting_scheduled,
                       MAX(CASE WHEN vsmi.milestone_code = 'TECH_DATA_GATHERING_MEETING_COMPLETED'
	                                THEN vsmi.milestone_date END) AS tech_data_gathering_meeting_completed,
                       MAX(CASE WHEN vsmi.milestone_code = 'EMAIL_USM_TEMPLATE_REQ'
	                                THEN vsmi.milestone_date END) AS email_usm_anywhere_template_requirements,
                       MAX(CASE WHEN vsmi.milestone_code = 'INVENTORY_ASSIGNMENT_VERIFIED'
	                                THEN vsmi.milestone_date END) AS inventory_assignment_verified,
                       MAX(CASE WHEN vsmi.milestone_code = 'NEW_USM_SERVER_BUILD'
	                                THEN vsmi.milestone_date END) AS new_usm_anywhere_server_build,
                       MAX(CASE WHEN vsmi.milestone_code = 'IMPLEMENTATION_QA'
	                                THEN vsmi.milestone_date END) AS implementation_qa,
                       MAX(CASE WHEN vsmi.milestone_code = 'VERIFY_ASSETS_SIEM_DB'
	                                THEN vsmi.milestone_date END) AS verify_assets_in_siem_db,
                       MAX(CASE WHEN vsmi.milestone_code = 'VERIFY_LOGGING_DATA_SOURCES'
	                                THEN vsmi.milestone_date END) AS verify_logging_data_source,
                       MAX(CASE WHEN vsmi.milestone_code = 'SCHEDULE_VULNERABILITY_SCANS'
	                                THEN vsmi.milestone_date END) AS schedule_vulnerability_scans,
                       MAX(CASE WHEN vsmi.milestone_code = 'BULK_ALARM_TUNING_PHASE'
	                                THEN vsmi.milestone_date END) AS bulk_alarm_tuning_phase,
                       MAX(CASE WHEN vsmi.milestone_code = 'SIEM_EVENT_FILTERING'
	                                THEN vsmi.milestone_date END) AS siem_event_filtering,
                       MAX(CASE WHEN vsmi.milestone_code = 'FILTERS_BUILT_FOR_REPORTS'
	                                THEN vsmi.milestone_date END) AS filters_built_for_reports,
                       MAX(CASE WHEN vsmi.milestone_code = 'DEFAULT_ALARM_RULE_ADDITIONS'
	                                THEN vsmi.milestone_date END) AS default_alarm_rule_additions,
                       MAX(CASE WHEN vsmi.milestone_code = 'CUSTOM_ALARM_RULE_ADDITIONS'
	                                THEN vsmi.milestone_date END) AS custom_alarm_rule_additions,
                       MAX(CASE WHEN vsmi.milestone_code = 'FORWARD_ALARMS_TO_USM_CENTRAL'
	                                THEN vsmi.milestone_date END) AS forward_alarms_to_usm_central,
                       MAX(CASE WHEN vsmi.milestone_code = 'FORWARD_ALARMS_TO_D3_SOC_LIVE'
	                                THEN vsmi.milestone_date END) AS forward_alarms_to_d3Soc_live,
                       MAX(CASE WHEN vsmi.milestone_code = 'ON_HOLD'
	                                THEN vsmi.milestone_date END) AS on_hold,
                       MAX(CASE WHEN vsmi.milestone_code = 'HOST_LIST_PROVIDED_BY_CLIENT'
                                    THEN vsmi.milestone_date END) AS host_list_provided_by_client,
                       MAX(CASE WHEN vsmi.milestone_code = 'HALCYON_PACKAGE_GIVEN_TO_CLIENT'
                                    THEN vsmi.milestone_date END) AS halcyon_package_given_to_client,
                       MAX(CASE WHEN vsmi.milestone_code = 'HALCYON_DEPLOYED_TO_HOSTS'
                                    THEN vsmi.milestone_date END) AS halcyon_deployed_to_hosts,
                       MAX(CASE WHEN vsmi.milestone_code = 'DEVOPS_NOTIFIED_OF_HALCYON_ADDITION'
                                    THEN vsmi.milestone_date END) AS devops_notified_of_halcyon_addition,
                       MAX(CASE WHEN vsmi.milestone_code = 'HALCYON_API_TOKEN_ADDED_TO_D3'
                                    THEN vsmi.milestone_date END) AS halcyon_api_token_added_to_d3,
                       MAX(CASE WHEN vsmi.milestone_code = 'D3_CONNECTION_VERIFIED'
                                    THEN vsmi.milestone_date END) AS d3_connection_verified,
                       MAX(CASE WHEN vsmi.milestone_code = 'END_LEARNING_MODE'
                                    THEN vsmi.milestone_date END) AS end_learning_mode,
                       MAX(CASE WHEN vsmi.milestone_code = 'TEST_EMAIL_SENT_TO_CLIENT'
                                    THEN vsmi.milestone_date END) AS test_email_sent_to_client,
                       MAX(CASE WHEN vsmi.milestone_code = 'DEPLOY_CONSULTING_TENANT'
                                    THEN vsmi.milestone_date END) AS deploy_consulting_tenant,
                       MAX(CASE WHEN vsmi.milestone_code = 'DEPLOY_VULNERABILITY_SCANS'
                                    THEN vsmi.milestone_date END) AS deploy_vulnerability_scans,
                       MAX(CASE WHEN vsmi.milestone_code = 'PROVIDED_CUSTOMER_WITH_REPORT'
                                    THEN vsmi.milestone_date END) AS provided_customer_with_report,
                       MAX(CASE WHEN vsmi.milestone_code = 'DISCUSS_FUTURE_CYRISMA_MANAGEMENT'
                                    THEN vsmi.milestone_date END) AS discuss_future_cyrisma_management,
                       MAX(CASE WHEN vsmi.milestone_code = 'REVIEW_EXISTING_CA_AND_MFA_POLICIES'
                                    THEN vsmi.milestone_date END) AS review_existing_ca_and_mfa_policies,
                       MAX(CASE WHEN vsmi.milestone_code = 'SIGN_IN_POLICIES_ENABLED'
                                    THEN vsmi.milestone_date END) AS sign_in_policies_enabled,
                       MAX(CASE WHEN vsmi.milestone_code = 'CONDITIONAL_ACCESS_POLICY_VERIFICATION'
                                    THEN vsmi.milestone_date END) AS conditional_access_policy_verification,
                       MAX(CASE WHEN vsmi.milestone_code = 'GEOGRAPHIC_RESTRICTIONS_ENABLED'
                                    THEN vsmi.milestone_date END) AS geographic_restrictions_enabled,
                       MAX(CASE WHEN vsmi.milestone_code = 'DEVICE_COMPLIANCE_ENABLED'
                                    THEN vsmi.milestone_date END) AS device_compliance_enabled,
                       MAX(CASE WHEN vsmi.milestone_code = 'PASSWORD_RESET_ENABLED_FOR_SELF_SERVICE'
                                    THEN vsmi.milestone_date END) AS password_reset_enabled_for_self_service,
                       MAX(CASE WHEN vsmi.milestone_code = 'BREAK_GLASS_ACCOUNT_CONFIGURED'
                                    THEN vsmi.milestone_date END) AS break_glass_account_configured,
                       MAX(CASE WHEN vsmi.milestone_code = 'PIM_ENABLEMENT'
                                    THEN vsmi.milestone_date END) AS pim_enablement,
                       MAX(CASE WHEN vsmi.milestone_code = 'IMPLEMENTATION_VERIFIED'
                                    THEN vsmi.milestone_date END) AS implementation_verified
                FROM v_service_milestone_instance vsmi
                     INNER JOIN service s ON vsmi.service_id = s.service_id
                GROUP BY s.service_id) vsmi ON s.service_id = vsmi.service_id
     LEFT JOIN (SELECT MAX(n.note_id) AS note_id, service_id
                FROM note n
                     JOIN service_note sn ON n.note_id = sn.note_id
                GROUP BY service_id) latest_note ON s.service_id = latest_note.service_id
WHERE s.current_inventory = FALSE;
