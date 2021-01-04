package com.agoda.common.helper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(InputHelper.class)
public class InputHelperTest {

    @Test(expected = IllegalArgumentException.class)
    public void throwsIllegalArgumentExceptionIfWrongInputPath() {
        String[] inputArgs = new String[]{ "compress", "fakePath/xyz", "fakePath/abc", "1"};
       InputHelper.validateInput(inputArgs);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsIllegalArgumentExceptionIfWrongActionSpecified() {
        String[] inputArgs = new String[]{ "wrongcompress", "fakePath/xyz", "fakePath/abc", "1"};
        InputHelper.validateInput(inputArgs);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsIllegalArgumentExceptionIfWrongParams() {
        String[] inputArgs = new String[]{ "fakePath/xyz", "fakePath/abc", "1"};
        InputHelper.validateInput(inputArgs);
    }

    @Test(expected = NumberFormatException.class)
    public void throwsNumberFormatExceptionIfMaxFileSizeGivenWrong() throws Exception {
        String[] inputArgs = new String[]{ "compress","fakePath/xyz", "fakePath/abc", "wrong"};
        PowerMockito.mockStatic(InputHelper.class, Mockito.CALLS_REAL_METHODS);
        PowerMockito.doNothing().when(InputHelper.class,"checkInputAndOutputDir", ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        InputHelper.validateInput(inputArgs);
    }


}
