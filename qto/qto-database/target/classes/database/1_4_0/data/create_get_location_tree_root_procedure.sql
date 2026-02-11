DROP PROCEDURE IF EXISTS GetParentLocationTreeRoot//

CREATE PROCEDURE GetParentLocationTreeRoot(IN start_location_id INT)
BEGIN
    DECLARE root_location_id INT;
    SET root_location_id = start_location_id;

    REPEAT
        SELECT parent_location_id INTO root_location_id FROM location WHERE location_id = root_location_id AND parent_location_id IS NOT NULL;
    UNTIL (SELECT parent_location_id FROM location WHERE location_id = root_location_id) IS NULL END REPEAT;

    SELECT root_location_id;
END
//
