package com.vertek.corporate.qto.service;

/**
 * Enumerates the different service types.
 */
public enum ServiceType {
    DIA("DIA"),
    BROADBAND("Broadband"),
    UCAAS("UCaaS"),
    G("4G/5G"),
    CROSSCONNECT("Cross Connect"),
    ETHERNET("Ethernet"),
    TELEVISION("Television"),
    MPLS("MPLS"),
    THREATMDR("threatMDR"),
    RANSOMMDR("ransomMDR"),
    RISKMDR("riskMDR"),
    ENGINEERING_MDM("Engineering-MDM"),
    ENGINEERING_IAM("Engineering-IAM"),
    ENGINEERING_ENDPOINT("Engineering-Endpoint"),
    ENGINEERING_INFO_PROTECTION("Engineering-Info Protection"),
    ENGINEERING_EMAIL_MESSAGING("Engineering-Email & Messaging"),
    CYBER360MXDR("cyber360-MXDR"),
    MICROSOFTLICENSES("Microsoft Licenses");

    /** Service name. */
    private final String serviceName;

    /**
     * Constructor for initializing the enum.
     * @param serviceName the name of the service.
     */
    ServiceType(final String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Returns the service type enum by the given name.
     * @param serviceName the given name.
     * @return a matching service type enum, if it exists.
     * @throws IllegalArgumentException if it's unsupported service type string.
     */
    public static ServiceType fromServiceName(String serviceName)
            throws IllegalArgumentException {
        for (ServiceType b : ServiceType.values()) {
            if (b.serviceName.equalsIgnoreCase(serviceName)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unsupported service type '" + serviceName + "'.");
    }

    public String getServiceName() {
        return serviceName;
    }
}
