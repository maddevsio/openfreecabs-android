package io.maddevs.openfreecabs.utils;

import java.util.ArrayList;
import java.util.List;

import io.maddevs.openfreecabs.models.CompanyModel;

/**
 * Created by rustam on 18.08.16.
 */
public class DataStorage {
    public static DataStorage instance = new DataStorage();
    public List<CompanyModel> companies = new ArrayList<>();
}
