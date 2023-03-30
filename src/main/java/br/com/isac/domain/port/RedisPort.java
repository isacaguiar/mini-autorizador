package br.com.isac.domain.port;

import br.com.isac.domain.vo.LockedKey;

public interface LockPort {

  LockedKey buy(String key);

  public boolean isLocked(String key);

  void release(LockedKey lockedKey);
}
