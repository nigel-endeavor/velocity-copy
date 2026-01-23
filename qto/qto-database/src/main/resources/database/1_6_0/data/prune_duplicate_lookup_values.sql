DELETE lv1
FROM
    lookup_value AS lv1,
    lookup_value AS lv2
WHERE
    lv1.lookup_value_id < lv2.lookup_value_id
      AND (lv1.lookup_type_id = lv2.lookup_type_id)
      AND (lv1.lookup_value = lv2.lookup_value)
      AND (lv1.tenant_id = lv2.tenant_id);