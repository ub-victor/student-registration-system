-- Creates a trigger and function to enforce academic unit hierarchy rules at DB level
-- Rules enforced:
--  - FACULTY must have parent_id IS NULL
--  - PROGRAMME (or PROGRAM) must have parent whose type = 'FACULTY'
--  - DEPARTMENT must have parent whose type = 'PROGRAMME'

CREATE OR REPLACE FUNCTION check_academic_unit_hierarchy()
RETURNS TRIGGER AS $$
DECLARE
  new_type text;
  parent_type text;
BEGIN
  IF NEW.type IS NULL THEN
    RAISE EXCEPTION 'Academic unit type cannot be null';
  END IF;

  new_type := upper(trim(NEW.type));
  IF new_type = 'PROGRAM' THEN
    new_type := 'PROGRAMME';
    NEW.type := new_type;
  END IF;

  IF new_type = 'FACULTY' THEN
    IF NEW.parent_id IS NOT NULL THEN
      RAISE EXCEPTION 'FACULTY cannot have a parent';
    END IF;
    RETURN NEW;
  END IF;

  -- For PROGRAMME and DEPARTMENT parent is required
  IF NEW.parent_id IS NULL THEN
    RAISE EXCEPTION 'Parent id is required for %', new_type;
  END IF;

  SELECT type INTO parent_type FROM academic_units WHERE id = NEW.parent_id;
  IF parent_type IS NULL THEN
    RAISE EXCEPTION 'Parent academic unit not found (id=%).', NEW.parent_id;
  END IF;

  IF new_type = 'PROGRAMME' AND upper(trim(parent_type)) <> 'FACULTY' THEN
    RAISE EXCEPTION 'Parent academic unit for PROGRAMME must be a FACULTY';
  END IF;

  IF new_type = 'DEPARTMENT' AND upper(trim(parent_type)) <> 'PROGRAMME' THEN
    RAISE EXCEPTION 'Parent academic unit for DEPARTMENT must be a PROGRAMME';
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create trigger
DROP TRIGGER IF EXISTS trg_academic_unit_hierarchy ON academic_units;
CREATE TRIGGER trg_academic_unit_hierarchy
BEFORE INSERT OR UPDATE ON academic_units
FOR EACH ROW
EXECUTE FUNCTION check_academic_unit_hierarchy();

-- Notes:
-- Run `seed_academic_units.sql` first to create the table and basic rows,
-- then run this script to add DB-level enforcement. If you want enforcement before
-- seeding, run this script first and adjust the seed inserts to satisfy the rules.
