package com.vertek.corporate.qto.report;

import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;
import com.vertek.corporate.qto.common.TenantSubjectManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ReportMetaDataJpaDao extends AbstractJpaDao<ReportMetaData, Long> {

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Object[]> getInventoryReportView() {
//        Long tenantId = tenantSubjectManager.getCurrentTenant().getId();
//        return new JPAQuery<ReportMetaData>(entityManager)
//                .from(inventoryReportView)
//                .where(inventoryReportView.tenantId.eq(tenantId))
//                .fetch();

        Long tenantId = tenantSubjectManager.getCurrentTenant().getId();
        String sql = "SELECT client_location_id, " +
       " client_service_id, " +
       " alternate_id, " +
       " service_type, " +
       " l.address_1, " +
       " l.address_2, " +
       " l.city, " +
       " l.state_province, " +
       " l.postal_code, " +
       " l.country, " +
       " if (s.active, 'Yes', 'No') as active, " +
       " sub_product_type, " +
       " mc.company_name AS master_company_name, " +
       " ec.company_name AS end_customer_name, " +
       " client_project_manager, " +
       " vpm.display_name AS i90_project_manager, " +
       " service_billed_to, " +
       " project_name, " +
       " provider, " +
       " s.service_mrc, " +
       " s.contract_term, " +
       " s.contract_signed_date, " +
       " s.circuit_term_end_date, " +
       " if (ignore_for_renewals,'Yes','No') as ignore_for_renewals, " +
       " s.speed, " +
       " summary_bill, " +
       " s.account_number, " +
       " commissions_supplier, " +
       " commissions_account_number, " +
       " b.sub_agent, " +
       " b.secondary_agency, " +
       " b.agent, " +
       " b.parent_tsd, " +
       " b.gross_profit, " +
       " b.gross_profit_mrc, " +
       " b.uplift_mrc, " +
       " macd.has_macd, " +
       " macd.order_type, " +
       " s.record_source, " +
       " if (s.managed_service, 'Yes', 'No') as managed_service, " +
       " CASE " +
	       " WHEN (currentCost IS NULL AND costLastYear IS NULL) OR currentCost IS NULL " +
		       " THEN " +
		       " 0 " +
	       " ELSE " +
		       " CASE " +
			       " WHEN costLastYear IS NULL " +
				       " THEN " +
				       " 0 " +
			       " ELSE " +
				       " currentCost - costLastYear " +
			       " END " +
	       " END AS costChangeThisYear, " +
       " CASE " +
	       " WHEN (currentCost IS NULL AND costLastYear IS NULL) OR currentCost IS NULL " +
		       " THEN " +
		       " 0 " +
	       " ELSE " +
		       " CASE " +
			       " WHEN costLastYear IS NULL " +
				       " THEN " +
				       " CASE " +
					       " WHEN originalMrc IS NOT NULL " +
						       " THEN currentCost - originalMrc " +
					       " ELSE currentCost - originalCost " +
					       " END " +
			       " ELSE " +
				       " CASE " +
					       " WHEN originalMrc IS NOT NULL " +
						       " THEN currentCost - originalMrc " +
					       " ELSE currentCost - originalCost " +
					       " END " +
			       " END " +
	       " END AS costChangeLifetime, " +
       " has_dispute, " +
       " dispute_mrc, " +
       " dispute_nrc, " +
       " (SELECT milestone_date " +
        " FROM v_service_milestone_instance smi " +
        " WHERE smi.service_id = s.service_id " +
		      " AND smi.milestone_code = 'CREATED') AS dateAddedToInventory, " +
       " s.address_1 AS billing_address_1, " +
			" s.address_2 AS billing_address_2, " +
			" s.city AS billing_city, " +
			" s.state_province AS billing_state_province, " +
			" s.postal_code AS billing_postal_code, " +
			" s.country AS billing_country, " +
			" s.billing_email " +
" FROM service s " +
     " LEFT JOIN service_brokerage b ON s.service_id = b.service_id " +
     " LEFT JOIN location l ON s.location_id = l.location_id " +
     " LEFT JOIN orders o ON l.order_id = o.order_id " +
     " LEFT JOIN company mc ON o.master_customer_id = mc.company_id " +
     " LEFT JOIN company ec ON o.company_id = ec.company_id " +
     " LEFT JOIN v_subject vpm ON o.vertek_project_manager = vpm.subject_id " +
     " LEFT JOIN (SELECT 'Yes' AS has_macd, order_type, inventory_service_id, location_id, tenant_id " +
                " FROM service " +
                " WHERE order_type <> 'New' " +
		              " AND service_status <> 'Service Cancelled' " +
		              " AND service_status <> 'Service Complete' " +
		              " AND service_status <> 'Change In Assignment' " +
		              " AND service_status <> 'Disconnect Cancelled' " +
		              " AND service_status <> 'Disconnect Complete') macd ON s.service_id = macd.inventory_service_id " +
     " LEFT JOIN (SELECT svc.service_id, " +
                       " (SELECT new_value FROM cost_history ch WHERE ch.service_id = svc.service_id AND ch.cost_type = 'MRC' ORDER BY ch.cost_history_id DESC LIMIT 1) AS currentCost, " +
                       " (SELECT new_Value FROM cost_history ch WHERE ch.service_id = svc.service_id AND ch.cost_type = 'MRC' AND change_reason = 'Initial Cost' ORDER BY ch.cost_history_id DESC LIMIT 1) AS originalCost, " +
                       " (SELECT new_Value FROM cost_history ch WHERE ch.service_id = svc.service_id AND ch.cost_type = 'MRC'	AND change_reason = 'Original MRC' ORDER BY ch.cost_history_id DESC LIMIT 1) AS originalMrc, " +
                       " (SELECT new_Value FROM cost_history ch WHERE ch.service_id = svc.service_id AND ch.cost_type = 'MRC' AND update_date >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) ORDER BY ch.cost_history_id ASC LIMIT 1) AS costLastYear " +
                " FROM service svc) ch ON s.service_id = ch.service_id " +
     " LEFT JOIN (SELECT 'Yes' AS has_dispute, " +
                       " SUM(amount_disputed_mrc) AS dispute_mrc, " +
                       " SUM(amount_disputed_nrc) AS dispute_nrc, " +
                       " service_id " +
                " FROM dispute " +
                " WHERE dispute_status <> 'Dispute Closed' " +
                " GROUP BY service_id) d ON s.service_id = d.service_id " +
                " WHERE s.current_inventory = TRUE " +
                "AND s.tenant_id = :tenantId ";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("tenantId", tenantId);
        return query.getResultList();
    }

    public List<Object[]> getWipReportView() {
        Long tenantId = tenantSubjectManager.getCurrentTenant().getId();
        String sql = "SELECT client_location_id, " +
                "client_service_id, " +
                "alternate_id, " +
                "service_type, " +
                "l.address_1, " +
                "l.address_2, " +
                "l.city, " +
                "l.state_province, " +
                "l.postal_code, " +
                "l.country, " +
                "s.service_status, " +
                "s.service_sub_status, " +
                "sub_product_type, " +
                "s.order_type, " +
                "s.sub_order_type, " +
                "mc.company_name AS master_company_name, " +
                "ec.company_name AS end_customer_name, " +
                "prov.display_name AS provisioner, " +
                "client_project_manager, " +
                "vpm.display_name AS i90_project_manager, " +
                "service_billed_to, " +
                "project_name, " +
                "provider, " +
                "s.service_mrc, " +
                "s.service_nrc, " +
                "s.contract_term, " +
                "s.contract_signed_date, " +
                "s.circuit_term_end_date, " +
                "IF(ignore_for_renewals, 'Yes', 'No') AS ignore_for_renewals, " +
                "s.speed, " +
                "(SELECT milestone_date " +
                "FROM v_service_milestone_instance smi " +
                "WHERE smi.service_id = s.service_id " +
                "AND smi.milestone_code = 'CREATED') AS createdDate, " +
                "(SELECT milestone_date " +
                "FROM v_service_milestone_instance smi " +
                "WHERE smi.service_id = s.service_id " +
                "AND smi.milestone_code = 'ENGINEER_ASSIGNED') AS assignedDate, " +
                "(SELECT milestone_date " +
                "FROM v_service_milestone_instance smi " +
                "WHERE smi.service_id = s.service_id " +
                "AND smi.milestone_code = 'PROVIDER_ORDER_SUBMITTED') AS providerOrderSubmittedDate, " +
                "(SELECT milestone_date " +
                "FROM v_service_milestone_instance smi " +
                "WHERE smi.service_id = s.service_id " +
                "AND smi.milestone_code = 'NETWORK_PROVIDER_FOC') AS networkProviderFocDate, " +
                "(SELECT milestone_date " +
                "FROM v_service_milestone_instance smi " +
                "WHERE smi.service_id = s.service_id " +
                "AND smi.milestone_code = 'DATA_PROVISIONING_COMPLETE') AS dataProvisioningCompleteDate, " +
                "(SELECT milestone_date " +
                "FROM v_service_milestone_instance smi " +
                "WHERE smi.service_id = s.service_id " +
                "AND smi.milestone_code = 'ON_HOLD') AS onHoldDate, " +
                "(SELECT milestone_date " +
                "FROM v_service_milestone_instance smi " +
                "WHERE smi.service_id = s.service_id " +
                "AND smi.milestone_code = 'COMPLETE') AS completeDate, " +
                "commissions_supplier, " +
                "commissions_account_number, " +
                "b.sub_agent, " +
                "b.secondary_agency, " +
                "b.agent, " +
                "b.parent_tsd, " +
                "b.gross_profit, " +
                "b.gross_profit_mrc, " +
                "b.uplift_mrc, " +
                "s.address_1 AS billing_address_1, " +
                "s.address_2 AS billing_address_2, " +
                "s.city AS billing_city, " +
                "s.state_province AS billing_state_province, " +
                "s.postal_code AS billing_postal_code, " +
                "s.country AS billing_country, " +
                "s.billing_email " +
                "FROM service s " +
                "JOIN location l ON s.location_id = l.location_id " +
                "JOIN orders o ON l.order_id = o.order_id " +
                "LEFT JOIN service_brokerage b ON s.service_id = b.service_id " +
                "LEFT JOIN company mc ON o.master_customer_id = mc.company_id " +
                "LEFT JOIN company ec ON o.company_id = ec.company_id " +
                "LEFT JOIN v_subject vpm ON o.vertek_project_manager = vpm.subject_id " +
                "LEFT JOIN v_subject prov ON o.provisioner = prov.subject_id " +
                "WHERE s.current_inventory = FALSE " +
                "AND s.service_status NOT IN ('Service Cancelled', 'Change In Assignment', 'Disconnect Cancelled') " +
                "AND s.tenant_id = :tenantId ";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("tenantId", tenantId);
        return query.getResultList();

    }
}
