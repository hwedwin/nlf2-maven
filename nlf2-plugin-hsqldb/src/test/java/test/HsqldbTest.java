package test;

import com.nlf.extend.dao.sql.SqlDaoFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HsqldbTest {

  @Before
  public void before() {
    SqlDaoFactory.getDao().getTemplate().sql("create table user(id int GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,name varchar(255) DEFAULT NULL);").update();
    SqlDaoFactory.getDao().getInserter().table("user").set("name","张三").insert();
  }

  @After
  public void after() {
    SqlDaoFactory.getDao().getTemplate().sql("drop table user if exists;").update();
  }

  @Test
  public void test() {
    int count = SqlDaoFactory.getDao().getSelecter().table("user").count();
    Assert.assertTrue(count>0);
  }
}
