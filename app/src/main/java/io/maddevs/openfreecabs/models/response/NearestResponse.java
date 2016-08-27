package io.maddevs.openfreecabs.models.response;

import java.util.List;

import io.maddevs.openfreecabs.models.CompanyModel;

/**
 * Created by rustam on 27.08.16.
 */
public class NearestResponse {
    public boolean success;
    public List<CompanyModel> companies;
}
