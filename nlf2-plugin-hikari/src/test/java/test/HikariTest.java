package test;

import com.nlf.extend.dao.sql.SqlDaoFactory;
import org.junit.Assert;
import org.junit.Test;

public class HikariTest {
  @Test
  public void test() {
    int count = SqlDaoFactory.getDao().getSelecter().table("user").count();
    Assert.assertTrue(count>0);
  }
}
