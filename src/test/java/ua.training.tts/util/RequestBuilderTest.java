package ua.training.tts.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.training.tts.constant.TestConstants;
import ua.training.tts.model.util.RequestBuilder;

import java.util.Arrays;
import java.util.List;

public class RequestBuilderTest extends Assert {

    private RequestBuilder builder;
    private List<String> values;

    @Before
    public void init() throws Exception {
        builder = new RequestBuilder();
        values = Arrays.asList(TestConstants.VALUE1, TestConstants.VALUE2);
    }

    @Test
    public void insertIntoTable() {
        String actuals = builder.insertIntoTable(TestConstants.TABLE).build().toLowerCase();
        String expected = TestConstants.INSERT_INTO_TABLE_EXPECTED.toLowerCase();
        Assert.assertEquals(expected, actuals);
    }

    @Test
    public void insertValues() {
        String actuals = builder.insertValueNames(values).build().toLowerCase();
        String expected = TestConstants.INSERT_VALUES_EXPECTED.toLowerCase();
        Assert.assertEquals(expected, actuals);
    }

    @Test
    public void selectAllFromTable() {
        String actuals = builder.selectAllFromTable(TestConstants.TABLE).build().toLowerCase();
        String expected = TestConstants.SELECT_ALL_FROM_TABLE_EXPECTED.toLowerCase();
        Assert.assertEquals(expected, actuals);
    }

    @Test
    public void where() {
        String actuals = builder.where(TestConstants.COLUMN).build().toLowerCase();
        String expected = TestConstants.WHERE_EXPECTED.toLowerCase();
        Assert.assertEquals(expected, actuals);
    }

    @Test
    public void and() {
        String actuals = builder.and(TestConstants.COLUMN).build().toLowerCase();
        String expected = TestConstants.AND_EXPECTED.toLowerCase();
        Assert.assertEquals(expected, actuals);
    }

    @Test
    public void update() {
        String actuals = builder.update(TestConstants.TABLE, values).build().toLowerCase();
        String expected = TestConstants.UPDATE_EXPECTED.toLowerCase();
        Assert.assertEquals(expected, actuals);
    }

    @Test
    public void delete() {
        String actuals = builder.delete(TestConstants.COLUMN).build().toLowerCase();
        String expected = TestConstants.DELETE.toLowerCase();
        Assert.assertEquals(expected, actuals);
    }
}