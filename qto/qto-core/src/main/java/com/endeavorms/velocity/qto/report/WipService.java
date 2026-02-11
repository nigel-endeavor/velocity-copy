package com.endeavorms.velocity.qto.report;

public interface WipService {

    public Long getProvisionerId();

    public Long getVertekProjectManagerId();

    public void setProvisioner(final String provisioner);

    public void setVertekProjectManager(final String vertekProjectManager);
}
