/*
package service.impl;

import book.library.java.dao.impl.AuthorDaoImpl;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.Author;
import book.library.java.service.impl.AuthorServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AuthorServiceImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    AuthorServiceImpl authorService;

    @Autowired
    Author authorTest;

    @BeforeTest
    public void beforeTest() {
        authorTest = new Author();
        authorTest.setId(1);
        authorTest.setFirstName("Ihor");
        authorTest.setSecondName("Miniailo");
        authorService = new AuthorServiceImpl(new AuthorDaoImpl());
    }

    @Test()
    public void testAuthorServiceValidate() throws BusinessException {
        Author authorTest = new Author();
        authorTest.setId(1);
        authorTest.setFirstName("Ihor");
        authorTest.setSecondName("Miniailo");
        //authorService.validateAuthor(authorTest);
        Assert.assertNotNull(authorTest);
    }

    @Test(expectedExceptions = BusinessException.class)
    public void testAuthorServiceValidateEx() throws BusinessException {
        Author authorTest = new Author();
        authorTest.setId(1);
        authorTest.setFirstName("");
        authorTest.setSecondName("Miniailo");
        //authorService.validateAuthor(authorTest);
        Assert.assertNotNull(authorTest);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testAuthorServiceCreate() throws BusinessException, DaoException {
        //authorService.create(authorTest);
    }

}
*/
