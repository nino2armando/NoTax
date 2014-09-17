package com.example.ninokhodabandeh.notax.Services;

import com.example.ninokhodabandeh.notax.Fakes.FakeContent;
import com.example.ninokhodabandeh.notax.Models.ApiResultModel;
import com.example.ninokhodabandeh.notax.Models.UserInputModel;

import java.util.ArrayList;

/**
 * Created by nino.khodabandeh on 9/15/2014.
 */
public class ApiService implements IApiService {
    @Override
    public ArrayList<ApiResultModel> getApiResult(UserInputModel model) {
        return FakeContent.getFakeApiContent();
    }
}
