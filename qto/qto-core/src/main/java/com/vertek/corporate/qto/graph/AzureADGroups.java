package com.vertek.corporate.qto.graph;

public enum AzureADGroups {
    ADMIN("i90 Admins"),
    INVOICE_WRITE("i90 Invoice Write"),
    INVOICE_READ("i90 Invoice Read"),
    ORDER_WRITE_TERMINAL("i90 Order Write Terminal"),
    ORDER_READ ("i90 Order Read"),
    ORDER_WRITE("i90 Order Write"),
    INVENTORY_READ("i90 Inventory Read"),
    INVENTORY_WRITE("i90 Inventory Write"),
    FILE_IMPORT("i90 File Import"),
    TENANT_ADMIN("i90 Tenant Admin"),
    LOOKUP_ADMIN("i90 Lookup Admin"),
    DEVELOPER("i90 Developers");

    /** Group name in Azure AD*/
    private String name;

    AzureADGroups(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
