CREATE OR REPLACE FUNCTION fDeposit(pAccId INTEGER, pTellerId INTEGER, pBranchId INTEGER, pDelta INTEGER)
  RETURNS VOID AS $$
BEGIN
  UPDATE branches
  SET balance = balance + pDelta
  WHERE branchid = pBranchId;
  UPDATE tellers
  SET balance = balance + pDelta
  WHERE tellerid = pTellerId;
  UPDATE accounts
  SET balance = balance + pDelta
  WHERE accid = pAccId;
  INSERT INTO history (accid, tellerid, delta, branchid, accbalance, cmmnt)
    SELECT
      pAccId,
      pTellerId,
      pDelta,
      pBranchId,
      balance,
      ''
    FROM accounts
    WHERE accid = pAccId;

END;
$$ LANGUAGE plpgsql;