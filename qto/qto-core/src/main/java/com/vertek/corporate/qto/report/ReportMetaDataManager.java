package com.vertek.corporate.qto.report;

import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
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
