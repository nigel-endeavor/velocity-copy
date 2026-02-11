package com.endeavorms.velocity.qto.authentication;

public final class Permissions {
//    read only, user and, invoicing will need to be deleted.
    public static final String READ_ONLY = "user:readonly";
    public static final String USER = "user";
    public static final String INVOICING = "invoicing";
    public static final String ADMIN = "*";
    public static final String INVOICE_WRITE = "invoice:write";
    public static final String INVOICE_READ = "invoice:read";
    public static final String ORDER_WRITE_TERMINAL = "order:write-terminal";
    public static final String ORDER_READ = "order:read";
    public static final String ORDER_WRITE = "order:write";
    public static final String ORDER_CREATE = "order:create";
    public static final String INVENTORY_READ = "inventory:read";
    public static final String INVENTORY_WRITE = "inventory:write";
    public static final String FILE_IMPORT = "file-import";
    public static final String TENANT_ADMIN = "tenant-admin";
    public static final String LOOKUP_ADMIN = "lookup-admin";
    public static final String CHANGE_TENANT = "change-tenant";
}
