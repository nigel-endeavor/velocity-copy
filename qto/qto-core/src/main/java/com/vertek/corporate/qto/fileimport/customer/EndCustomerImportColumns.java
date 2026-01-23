package com.vertek.corporate.qto.fileimport.customer;

import java.util.List;

public class EndCustomerImportColumns extends CustomerImportColumns {

    static final String END_CUSTOMER_NAME = "End Customer Name";

    static {
        COL_LIST.add(1, END_CUSTOMER_NAME);
    }

    public static List<String> getExpectedColumns() {
        return COL_LIST;
    }
}
