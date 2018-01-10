CREATE OR REPLACE FUNCTION fAnalyse(pDelta INTEGER)
  RETURNS INTEGER AS $$
BEGIN
  RETURN (SELECT COUNT(1) AS count
          FROM history
          WHERE delta = pDelta);
END;
$$ LANGUAGE plpgsql;