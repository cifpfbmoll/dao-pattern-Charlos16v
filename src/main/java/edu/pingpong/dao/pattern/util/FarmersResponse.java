package edu.pingpong.dao.pattern.util;

import edu.pingpong.dao.pattern.entity.Farmer;

import java.util.List;

public class FarmersResponse {

    public List<Farmer> farmers;

    public FarmersResponse(List<Farmer> farmers) {
        this.farmers = farmers;
    }
}
