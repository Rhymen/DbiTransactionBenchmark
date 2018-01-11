CREATE OR REPLACE FUNCTION fDeposit(pAccId INTEGER, pTellerId INTEGER, pBranchId INTEGER, pDelta INTEGER)
  RETURNS INTEGER AS $$
DECLARE
  newBalance INTEGER;
BEGIN
  UPDATE accounts
  SET balance = balance + pDelta
  WHERE accid = pAccId
  RETURNING balance
    INTO newBalance;

  UPDATE tellers
  SET balance = balance + pDelta
  WHERE tellerid = pTellerId;

  UPDATE branches
  SET balance = balance + pDelta
  WHERE branchid = pBranchId;

  INSERT INTO history (accid, tellerid, delta, branchid, accbalance, cmmnt)
  VALUES (pAccId, pTellerId, pDelta, pBranchId, newBalance, '');

  RETURN newBalance;
END;
$$ LANGUAGE plpgsql;