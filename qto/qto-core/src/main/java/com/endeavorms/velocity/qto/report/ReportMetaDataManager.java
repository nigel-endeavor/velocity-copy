package com.endeavorms.velocity.qto.report;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
public class ReportMetaDataManager extends StandardManager<ReportMetaData> {

    @Inject
    private ReportMetaDataJpaDao dao;

    @Override
    protected ReportMetaDataJpaDao getDao() {
        return dao;
    }

    public List<Object[]> getInventoryReportView() {
        return getDao().getInventoryReportView();
    }

    public List<Object[]> getWipReportView() {
        return getDao().getWipReportView();
    }

}
