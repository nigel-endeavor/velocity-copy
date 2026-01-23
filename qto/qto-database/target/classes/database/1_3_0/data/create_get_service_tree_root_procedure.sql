DROP PROCEDURE IF EXISTS GetServiceTreeRoot//

CREATE PROCEDURE GetServiceTreeRoot(IN start_service_id INT)
BEGIN
    DECLARE root_service_id INT;
    SET root_service_id = start_service_id;

    REPEAT
        SELECT parent_service_id INTO root_service_id FROM v_service_history WHERE service_id = root_service_id AND parent_service_id IS NOT NULL;
    UNTIL (SELECT parent_service_id FROM v_service_history WHERE service_id = root_service_id) IS NULL END REPEAT;

    SELECT root_service_id;
END
//
