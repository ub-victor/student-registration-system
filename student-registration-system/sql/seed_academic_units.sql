-- Seed academic_units table (Postgres)
-- Creates table if missing and updates schema if the table already exists.
-- Inserts sample hierarchy: Faculty -> Programme -> Department.

CREATE TABLE IF NOT EXISTS academic_units (
  id BIGSERIAL PRIMARY KEY,
  code VARCHAR(20) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  type VARCHAR(20) NOT NULL,
  parent_id BIGINT NULL REFERENCES academic_units(id) ON DELETE SET NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
  updated_at TIMESTAMP WITH TIME ZONE
);

-- Ensure the created_at column exists and has the desired default/constraint.
ALTER TABLE academic_units
  ADD COLUMN IF NOT EXISTS parent_id BIGINT NULL REFERENCES academic_units(id) ON DELETE SET NULL,
  ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ DEFAULT now(),
  ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ;

ALTER TABLE academic_units
  ALTER COLUMN created_at SET DEFAULT now();

UPDATE academic_units
SET created_at = now()
WHERE created_at IS NULL;

ALTER TABLE academic_units
  ALTER COLUMN created_at SET NOT NULL;

-- Optional: create index on type for faster queries
CREATE INDEX IF NOT EXISTS idx_au_type ON academic_units(type);

BEGIN;

-- Faculty
INSERT INTO academic_units (code, name, type, created_at)
VALUES ('ENG', 'Faculty of Engineering', 'FACULTY', now())
ON CONFLICT (code) DO NOTHING;

-- Programme (parent = ENG)
INSERT INTO academic_units (code, name, type, parent_id, created_at)
VALUES (
  'CS',
  'Computer Science Programme',
  'PROGRAMME',
  (SELECT id FROM academic_units WHERE code = 'ENG'),
  now()
)
ON CONFLICT (code) DO NOTHING;

-- Department (parent = CS)
INSERT INTO academic_units (code, name, type, parent_id, created_at)
VALUES (
  'SE',
  'Software Engineering Department',
  'DEPARTMENT',
  (SELECT id FROM academic_units WHERE code = 'CS'),
  now()
)
ON CONFLICT (code) DO NOTHING;

COMMIT;

-- Verification queries (run these after the script)
-- SELECT a.id, a.code, a.name, a.type, p.code AS parent_code
-- FROM academic_units a
-- LEFT JOIN academic_units p ON a.parent_id = p.id
-- ORDER BY a.id;

-- Example: intentionally bad parent (DB-level only, app should reject this)
-- INSERT INTO academic_units (code, name, type, parent_id, created_at)
-- VALUES ('BADDEP','Dept with wrong parent','DEPARTMENT',(SELECT id FROM academic_units WHERE code='ENG'), now());
