package com.example.ninokhodabandeh.notax.Fakes;

import com.example.ninokhodabandeh.notax.Models.ApiResultModel;

import java.util.ArrayList;

/**
 * Created by nino.khodabandeh on 9/15/2014.
 */
public class FakeContent {
    public static ArrayList<ApiResultModel> getFakeApiContent(){
        ArrayList<ApiResultModel> listViewContent = new ArrayList<ApiResultModel>(){{
            add(new ApiResultModel(1, "Grumppy cat's fishery, 100 West 1st street", "2.3"));
            add(new ApiResultModel(2, "Mall, 34 East main 1st street", "23.2"));
            add(new ApiResultModel(3, "Nino's Phantom, 7654 Back street", "235476"));
            add(new ApiResultModel(4, "Krista's cat, 432523 Highway One", "12"));
            add(new ApiResultModel(5, "REZ 5, 098098 salmon way, Bc", "87"));
            add(new ApiResultModel(6, "Bad Haircut for ReZ, 8769687 omg Kill me street", "324"));
            add(new ApiResultModel(7, "Salmon Smoker, 8768678 Grabage data way", "1"));
            add(new ApiResultModel(8, "Dead Lee, 567567 cat paradise Island", "0"));
            add(new ApiResultModel(9, "Fish Juice B&B, 785879 wank my life away street", "0.23"));
        }};

        return listViewContent;
    }
}
