-- Update existing specialization values to match the enum
UPDATE users
SET specialization = 'CARDIOLOGY'
WHERE LOWER(specialization) LIKE '%kardiyoloji%' OR LOWER(specialization) LIKE '%kalp%';

UPDATE users
SET specialization = 'DERMATOLOGY'
WHERE LOWER(specialization) LIKE '%dermatoloji%' OR LOWER(specialization) LIKE '%cilt%';

UPDATE users
SET specialization = 'NEUROLOGY'
WHERE LOWER(specialization) LIKE '%nöroloji%' OR LOWER(specialization) LIKE '%sinir%';

UPDATE users
SET specialization = 'ORTHOPEDICS'
WHERE LOWER(specialization) LIKE '%ortopedi%';

UPDATE users
SET specialization = 'OPHTHALMOLOGY'
WHERE LOWER(specialization) LIKE '%göz%' OR LOWER(specialization) LIKE '%ophthalmology%';

UPDATE users
SET specialization = 'GYNECOLOGY'
WHERE LOWER(specialization) LIKE '%kadın%' OR LOWER(specialization) LIKE '%doğum%' OR LOWER(specialization) LIKE '%gynecology%';

UPDATE users
SET specialization = 'UROLOGY'
WHERE LOWER(specialization) LIKE '%üroloji%' OR LOWER(specialization) LIKE '%urologi%';

UPDATE users
SET specialization = 'PSYCHIATRY'
WHERE LOWER(specialization) LIKE '%psikiyatri%' OR LOWER(specialization) LIKE '%psychiatry%';

UPDATE users
SET specialization = 'PEDIATRICS'
WHERE LOWER(specialization) LIKE '%çocuk%' OR LOWER(specialization) LIKE '%pediatri%';

UPDATE users
SET specialization = 'INTERNAL_MEDICINE'
WHERE LOWER(specialization) LIKE '%iç%' OR LOWER(specialization) LIKE '%dahiliye%';

UPDATE users
SET specialization = 'GENERAL_SURGERY'
WHERE LOWER(specialization) LIKE '%genel cerrahi%' OR LOWER(specialization) LIKE '%surgery%'; 