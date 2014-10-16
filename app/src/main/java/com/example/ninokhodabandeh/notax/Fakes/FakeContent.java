package com.example.ninokhodabandeh.notax.Fakes;

import com.example.ninokhodabandeh.notax.Models.ApiResultModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by nino.khodabandeh on 9/15/2014.
 */
public class FakeContent {
    public static ArrayList<ApiResultModel> getFakeApiContent(){
        ArrayList<ApiResultModel> listViewContent1 = new ArrayList<ApiResultModel>(){{
            add(new ApiResultModel(1, "1Grumppy cat's fishery, 100 West 1st street", "2.3"));
            add(new ApiResultModel(2, "1Mall, 34 East main 1st street", "23.2"));
            add(new ApiResultModel(3, "1Nino's Phantom, 7654 Back street", "235476"));
            add(new ApiResultModel(4, "1Krista's cat, 432523 Highway One", "12"));
            add(new ApiResultModel(5, "1REZ 5, 098098 salmon way, Bc", "87"));
            add(new ApiResultModel(6, "1Bad Haircut for ReZ, 8769687 omg Kill me street", "324"));
            add(new ApiResultModel(7, "1Salmon Smoker, 8768678 Grabage data way", "1"));
            add(new ApiResultModel(8, "1Dead Lee, 567567 cat paradise Island", "0"));
            add(new ApiResultModel(9, "1Fish Juice B&B, 785879 wank my life away street", "0.23"));
        }};

        ArrayList<ApiResultModel> listViewContent2 = new ArrayList<ApiResultModel>(){{
            add(new ApiResultModel(1, "2Grumppy cat's fishery, 100 West 1st street", "2.3"));
            add(new ApiResultModel(2, "2Mall, 34 East main 1st street", "23.2"));
            add(new ApiResultModel(3, "2Nino's Phantom, 7654 Back street", "235476"));
            add(new ApiResultModel(4, "2Krista's cat, 432523 Highway One", "12"));
            add(new ApiResultModel(5, "2REZ 5, 098098 salmon way, Bc", "87"));
            add(new ApiResultModel(6, "2Bad Haircut for ReZ, 8769687 omg Kill me street", "324"));
            add(new ApiResultModel(7, "2Salmon Smoker, 8768678 Grabage data way", "1"));
            add(new ApiResultModel(8, "2Dead Lee, 567567 cat paradise Island", "0"));
            add(new ApiResultModel(9, "2Fish Juice B&B, 785879 wank my life away street", "0.23"));
        }};


        ArrayList<ApiResultModel> listViewContent3 = new ArrayList<ApiResultModel>(){{
            add(new ApiResultModel(1, "3Grumppy cat's fishery, 100 West 1st street", "2.3"));
            add(new ApiResultModel(2, "3Mall, 34 East main 1st street", "23.2"));
            add(new ApiResultModel(3, "3Nino's Phantom, 7654 Back street", "235476"));
            add(new ApiResultModel(4, "3Krista's cat, 432523 Highway One", "12"));
            add(new ApiResultModel(5, "3REZ 5, 098098 salmon way, Bc", "87"));
            add(new ApiResultModel(6, "3Bad Haircut for ReZ, 8769687 omg Kill me street", "324"));
            add(new ApiResultModel(7, "3Salmon Smoker, 8768678 Grabage data way", "1"));
            add(new ApiResultModel(8, "3Dead Lee, 567567 cat paradise Island", "0"));
            add(new ApiResultModel(9, "3Fish Juice B&B, 785879 wank my life away street", "0.23"));
        }};

        ArrayList<ApiResultModel> listViewContent4 = new ArrayList<ApiResultModel>(){{
            add(new ApiResultModel(1, "4Grumppy cat's fishery, 100 West 1st street", "2.3"));
            add(new ApiResultModel(2, "4Mall, 34 East main 1st street", "23.2"));
            add(new ApiResultModel(3, "4Nino's Phantom, 7654 Back street", "235476"));
            add(new ApiResultModel(4, "4Krista's cat, 432523 Highway One", "12"));
            add(new ApiResultModel(5, "4REZ 5, 098098 salmon way, Bc", "87"));
            add(new ApiResultModel(6, "4Bad Haircut for ReZ, 8769687 omg Kill me street", "324"));
            add(new ApiResultModel(7, "4Salmon Smoker, 8768678 Grabage data way", "1"));
            add(new ApiResultModel(8, "4Dead Lee, 567567 cat paradise Island", "0"));
            add(new ApiResultModel(9, "4Fish Juice B&B, 785879 wank my life away street", "0.23"));
        }};


        List<ArrayList<ApiResultModel>> allLists = new ArrayList<ArrayList<ApiResultModel>>();
        allLists.add(listViewContent1);
        allLists.add(listViewContent2);
        allLists.add(listViewContent3);
        allLists.add(listViewContent4);


        int n = (int)(Math.random() * 3 + 0);

        return allLists.get(n);
    }
}
