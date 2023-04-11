package com.ccclogic.nerve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignUnAssignRecord {
    private List<Integer> assign;
    private List<Integer> unassign;


    public List<Integer> getAssign() {
        return ObjectUtils.defaultIfNull(assign, new ArrayList<>());
    }

    public void setAssign(List<Integer> assign) {
        this.assign = assign;
    }

    public List<Integer> getUnassign() {
        return ObjectUtils.defaultIfNull(unassign, new ArrayList<>());
    }

    public void setUnassign(List<Integer> unassign) {
        this.unassign = unassign;
    }
}
