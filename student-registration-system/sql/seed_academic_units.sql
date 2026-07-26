-- Seed academic_units table (Postgres)
-- Creates table if missing and inserts sample hierarchy: Faculty -> Programme -> Department

CREATE TABLE IF NOT EXISTS academic_units (
  id BIGSERIAL PRIMARY KEY,
  code VARCHAR(20) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  type VARCHAR(20) NOT NULL,
  parent_id BIGINT NULL REFERENCES academic_units(id) ON DELETE SET NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
  updated_at TIMESTAMP WITH TIME ZONE
);

-- Optional: create index on type for faster queries
CREATE INDEX IF NOT EXISTS idx_au_type ON academic_units(type);

-- Insert sample data inside a transaction
BEGIN;

-- Faculty
INSERT INTO academic_units (code, name, type)
VALUES ('ENG', 'Faculty of Engineering', 'FACULTY')
ON CONFLICT (code) DO NOTHING;

-- Programme (parent = ENG)
INSERT INTO academic_units (code, name, type, parent_id)
VALUES (
  'CS',
  'Computer Science Programme',
  'PROGRAMME',
  (SELECT id FROM academic_units WHERE code = 'ENG')
)
ON CONFLICT (code) DO NOTHING;

-- Department (parent = CS)
INSERT INTO academic_units (code, name, type, parent_id)
VALUES (
  'SE',
  'Software Engineering Department',
  'DEPARTMENT',
  (SELECT id FROM academic_units WHERE code = 'CS')
)
ON CONFLICT (code) DO NOTHING;

COMMIT;

-- Verification queries (run these after the script)
-- SELECT a.id, a.code, a.name, a.type, p.code AS parent_code
-- FROM academic_units a
-- LEFT JOIN academic_units p ON a.parent_id = p.id
-- ORDER BY a.id;

-- Example: intentionally bad parent (DB-level only, app should reject this)
-- INSERT INTO academic_units (code, name, type, parent_id)
-- VALUES ('BADDEP','Dept with wrong parent','DEPARTMENT',(SELECT id FROM academic_units WHERE code='ENG'));
