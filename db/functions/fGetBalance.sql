CREATE OR REPLACE FUNCTION fGetBalance(pAccId INTEGER)
  RETURNS INTEGER AS $$
BEGIN
  RETURN (SELECT balance
          FROM accounts
          WHERE accid = pAccId);
END;
$$ LANGUAGE plpgsql;